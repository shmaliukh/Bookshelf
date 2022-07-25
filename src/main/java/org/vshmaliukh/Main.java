package org.vshmaliukh;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.client_server.server.MultithreadedSocketServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

@Slf4j
public class Main {

    public static void main(String[] args) {
        //try {
        //    Thread.sleep(60_000L);
        //} catch (InterruptedException e) {
        //    throw new RuntimeException(e);
        //}
        TerminalThread terminalThread = new TerminalThread();
        terminalThread.start();

        ServerThread serverThread = new ServerThread();
        serverThread.start();
    }

    static class TerminalThread extends Thread {
        private final Scanner scanner = new Scanner(System.in);
        private final PrintWriter printWriter = new PrintWriter(System.out, true);
        private final Terminal terminal = new Terminal(scanner, printWriter);

        @Override
        public synchronized void run() {
            try {
                terminal.startWork(false);
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
                log.error("[ServerThread] problem to start thread. Exception: " + e);
            }
        }
    }
}