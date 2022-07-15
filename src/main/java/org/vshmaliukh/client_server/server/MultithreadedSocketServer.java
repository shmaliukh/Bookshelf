package org.vshmaliukh.client_server.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.vshmaliukh.constants.ConstantsForClientServerModel.MAX_CONNECTION_NUMBER;
import static org.vshmaliukh.constants.ConstantsForClientServerModel.SOCKET_PORT_NUMBER;

public class MultithreadedSocketServer {

    private ServerSocket serverSocket;

    private int maxConnection;
    private int serverPort;
    private int userCounter;
    private boolean isServerActive;

    public MultithreadedSocketServer(){
        serverPort = SOCKET_PORT_NUMBER;
        maxConnection = MAX_CONNECTION_NUMBER;
        userCounter = 0;
        isServerActive = true;
        try {
            serverSocket = new ServerSocket(serverPort, maxConnection);
        } catch (IOException e) {
            System.err.println("Problem to set up server socket");
            throw new RuntimeException(e);
        }
    }

    public void start() throws IOException {
        System.out.println("Server start");

        while (isServerActive){
            userCounter++;
            Socket serverClient = serverSocket.accept();
            System.out.println("    [Client] " + userCounter + " - connected");

            ServerToClientHandler serverToClientHandler = new ServerToClientHandler(serverClient, userCounter);
            serverToClientHandler.start();
        }
    }

    public static void main(String[] args) throws IOException {
        MultithreadedSocketServer server = new MultithreadedSocketServer();
        server.start();
    }
}
