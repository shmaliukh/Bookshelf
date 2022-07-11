package org.vshmaliukh.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultithreadedSocketServer {

    private ServerSocket serverSocket;

    private int maxConnection;
    private int serverPort;
    private int userCounter;
    private boolean isServerActive;

    public MultithreadedSocketServer(){
        serverPort = 8888;
        maxConnection = 10;
        userCounter = 0;
        isServerActive = true;
        try {
            serverSocket = new ServerSocket(serverPort, maxConnection);
        } catch (IOException e) {
            System.err.println("Problem to set up server socket");
            throw new RuntimeException(e);
        }
    }

    private void start(String[] args) throws IOException {
        System.out.println("Server start");
        int terminalConfig = getTerminalConfig(args);

        while (isServerActive){
            userCounter++;
            Socket serverClient = serverSocket.accept();
            System.out.println("    [Client] " + userCounter + " - connected");

            ServerClientThread serverClientThread = new ServerClientThread(serverClient, userCounter, terminalConfig);
            serverClientThread.start();
        }
    }

    private static int getTerminalConfig(String[] args) {
        // TODO create better validation
        int config = 0;
        if(args.length > 0 && args[0]!=null){
            config = Integer.parseInt(args[0]);
            if(config > 2){
                config -= 3;
            }
        }
        return config;
    }

    public static void main(String[] args) throws IOException {
        MultithreadedSocketServer server = new MultithreadedSocketServer();
        server.start(args);
    }
}
