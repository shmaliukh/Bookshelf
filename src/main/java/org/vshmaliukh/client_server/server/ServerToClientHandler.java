package org.vshmaliukh.client_server.server;

import org.vshmaliukh.Terminal;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerToClientHandler {

    private Socket socketForClient;
    private int userCounter;

    private Scanner scanner;
    private PrintWriter printWriter;

    private TerminalToClientThread terminalToClientThread;

    public ServerToClientHandler(Socket socket, int userCounter){
        socketForClient = socket;
        this.userCounter = userCounter;

        try {
            scanner = new Scanner(socketForClient.getInputStream());
            printWriter = new PrintWriter(socketForClient.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Problem to start server for client");
            throw new RuntimeException(e);
        }
    }

    void start(){
        terminalToClientThread = new TerminalToClientThread(userCounter, new Terminal(scanner,printWriter));
        terminalToClientThread.start();
    }
}
