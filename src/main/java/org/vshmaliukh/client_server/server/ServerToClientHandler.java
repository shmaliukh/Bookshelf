package org.vshmaliukh.client_server.server;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.Terminal;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

@Slf4j
public class ServerToClientHandler {

    private final Socket socketForClient;
    private final int userCounter;

    private Scanner scanner;
    private PrintWriter printWriter;

    public ServerToClientHandler(Socket socket, int userCounter) {
        this.socketForClient = socket;
        this.userCounter = userCounter;

        initScannerAndPrinter();
    }

    private void initScannerAndPrinter() {
        try {
            scanner = new Scanner(socketForClient.getInputStream());
            printWriter = new PrintWriter(socketForClient.getOutputStream(), true);
        } catch (IOException ioe) {
            log.error("[ServerToClientHandler]: Problem to start server for client. Exception: " + ioe);
        }
    }

    void start() {
        TerminalToClientThread terminalToClientThread = new TerminalToClientThread(userCounter, new Terminal(scanner, printWriter));
        terminalToClientThread.start();
    }
}
