package org.vshmaliukh;

import org.vshmaliukh.client_server.server.MultithreadedSocketServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        TerminalThread terminalThread = new TerminalThread();
        terminalThread.start();

        ServerThread serverThread = new ServerThread();
        serverThread.start();
    }

    static class TerminalThread extends Thread{
        private final Scanner scanner = new Scanner(System.in);
        private final PrintWriter printWriter = new PrintWriter(System.out, true);
        private final Terminal terminal = new Terminal(scanner, printWriter);

        @Override
        public synchronized void run() {
            try {
                terminal.startWork(false);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class ServerThread extends Thread{
        MultithreadedSocketServer multithreadedSocketServer = new MultithreadedSocketServer();

        @Override
        public synchronized void run() {
            try {
                multithreadedSocketServer.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}