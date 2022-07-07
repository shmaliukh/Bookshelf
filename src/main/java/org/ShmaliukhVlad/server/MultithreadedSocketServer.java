package org.ShmaliukhVlad.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultithreadedSocketServer {
    static final ServerSocket SERVER_SOCKET;
    static final int MAX_CONNECTION = 10;
    static final int SERVER_PORT = 8888;
    static int userCounter = 0;

    static {
        try {
            SERVER_SOCKET = new ServerSocket(SERVER_PORT, MAX_CONNECTION);
        } catch (IOException e) {
            System.err.println("Problem to set up server socket");
            throw new RuntimeException(e);
        }

    }
    public static void main(String[] args) throws IOException {
        System.out.println("Server start");

        while (true){
            userCounter++;
            Socket serverClient = SERVER_SOCKET.accept();
            System.out.println("client №" + userCounter + " connected");
            ServerClientThread serverClientThread = new ServerClientThread(serverClient, userCounter);
            serverClientThread.start();
        }
    }
}
