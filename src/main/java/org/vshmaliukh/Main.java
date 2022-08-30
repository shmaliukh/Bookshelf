package org.vshmaliukh;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleException;
import org.vshmaliukh.client_server_app.server.MultithreadedSocketServer;
import org.vshmaliukh.console_terminal_app.ConsoleUI;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

@Slf4j
public class Main {

    public static final String APP_TYPE_OF_WORK_ENV = "APP_TYPE_OF_WORK";

    public static final String CONSOLE_TYPE = "console";
    public static final String SERVER_CLIENT_TYPE = "server-client";
    public static final String WEB_TYPE = "web";

    public static void main(String[] args) {
        String typeOfWork = System.getenv(APP_TYPE_OF_WORK_ENV);
        runAppByTypeOfWork(typeOfWork);
    }

    static void runAppByTypeOfWork(String typeOfWork) {
        switch (typeOfWork.toLowerCase()) {
            case CONSOLE_TYPE:
                TerminalThread terminalThread = new TerminalThread();
                terminalThread.start();
                break;
            case SERVER_CLIENT_TYPE:
                ServerThread serverThread = new ServerThread();
                serverThread.start();
                break;
            case WEB_TYPE:
                try {
                    ShelfWebApp.main(null);
                } catch (LifecycleException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                terminalThread = new TerminalThread();
                terminalThread.start();
                break;
        }

    }

    static class TerminalThread extends Thread {
        private final Scanner scanner = new Scanner(System.in);
        private final PrintWriter printWriter = new PrintWriter(System.out, true);
        private final ConsoleUI consoleTerminal = new ConsoleUI(scanner, printWriter);

        @Override
        public synchronized void run() {
            try {
                consoleTerminal.startWork(false);
            } catch (Exception e) {
                log.error("[TerminalThread] problem to start thread. Exception: ", e);
            }
        }
    }

    static class ServerThread extends Thread {
        MultithreadedSocketServer multithreadedSocketServer = new MultithreadedSocketServer();

        @Override
        public synchronized void run() {
            try {
                multithreadedSocketServer.start();
            } catch (IOException e) {
                log.error("[ServerThread] problem to start thread. Exception: ", e);
            }
        }
    }
}