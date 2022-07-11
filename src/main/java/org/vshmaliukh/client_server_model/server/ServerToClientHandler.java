package org.vshmaliukh.client_server_model.server;

import org.vshmaliukh.Terminal;
import org.vshmaliukh.client_server_model.server.server_threads.TerminalToClientThread;
import org.vshmaliukh.client_server_model.server.server_threads.WriterToClientThread;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerToClientHandler { // TODO rename class

    private Socket socketForClient;
    private int userCounter;
    private int terminalConfig;

    private Scanner scanner;
    private PrintWriter printWriter;

    private TerminalToClientThread terminalToClientThread;
    private WriterToClientThread writerToClientThread;

    public ServerToClientHandler(Socket socket, int userCounter, int terminalConfig){
        socketForClient = socket;
        this.userCounter = userCounter;
        this.terminalConfig = terminalConfig;

        try {
            scanner = new Scanner(socketForClient.getInputStream());
            printWriter = new PrintWriter(socketForClient.getOutputStream());
        } catch (IOException e) {
            System.err.println("Problem to start server for client");
            throw new RuntimeException(e);
        }
    }

    void startThreads(){
        terminalToClientThread = new TerminalToClientThread(userCounter, new Terminal(scanner,printWriter), terminalConfig);
        writerToClientThread = new WriterToClientThread(printWriter);

        terminalToClientThread.start();
        writerToClientThread.start();
    }
}
