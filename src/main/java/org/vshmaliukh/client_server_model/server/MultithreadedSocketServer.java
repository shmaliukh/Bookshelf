package org.vshmaliukh.client_server_model.server;

import org.vshmaliukh.client_server_model.server.server_threads.WriterToClientThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.vshmaliukh.client_server_model.ConstantsForClientServerModel.MAX_CONNECTION_NUMBER;
import static org.vshmaliukh.client_server_model.ConstantsForClientServerModel.SOCKET_PORT_NUMBER;

public class MultithreadedSocketServer {

    private ServerSocket serverSocket;

    private int maxConnection;
    private int serverPort;
    private int userCounter;
    private boolean isServerActive;

    private WriterToClientThread writerToClientThread;

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

    private void start(String[] args) throws IOException {
        System.out.println("Server start");
        int terminalConfig = getTerminalConfig(args);

        while (isServerActive){
            userCounter++;
            Socket serverClient = serverSocket.accept();
            System.out.println("    [Client] " + userCounter + " - connected");

            ServerToClientHandler serverToClientHandler = new ServerToClientHandler(serverClient, userCounter, terminalConfig);
            serverToClientHandler.startThreads(); // TODO
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
