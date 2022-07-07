package org.ShmaliukhVlad.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultithreadedSocketServer {
    private static final ServerSocket SERVER_SOCKET;
    private static final int MAX_CONNECTION = 10;
    private static final int SERVER_PORT = 8888;
    private static int userCounter = 0;

    static {
        try {
            SERVER_SOCKET = new ServerSocket(SERVER_PORT, MAX_CONNECTION);
        } catch (IOException e) {
            System.err.println("Problem to set up server socket");
            throw new RuntimeException(e);
        }
    }

    private static int terminalConfig = 0;

    public static void main(String[] args) throws IOException {
        System.out.println("Server start");

        terminalConfig = getTerminalConfig(args);

        while (true){
            userCounter++;
            Socket serverClient = SERVER_SOCKET.accept();
            System.out.println("client №" + userCounter + " connected");

            ServerClientThread serverClientThread = new ServerClientThread(serverClient, userCounter, terminalConfig);
            serverClientThread.start();
        }
    }

    private static int getTerminalConfig(String[] args) {
        if(args.length > 0 && args[0]!=null){
            return Integer.parseInt(args[0]) - 3;
        }
        return 0;
    }
}
