package org.vshmaliukh.client_server.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.vshmaliukh.constants.ConstantsForClientServerModel.MAX_CONNECTION_NUMBER;
import static org.vshmaliukh.constants.ConstantsForClientServerModel.SOCKET_PORT_NUMBER;

@Slf4j
public class MultithreadedSocketServer {

    private final ServerSocket serverSocket;

    private int userCounter;
    private boolean isServerActive;

    public MultithreadedSocketServer(){
        userCounter = 0;
        isServerActive = true;
        try {
            serverSocket = new ServerSocket(SOCKET_PORT_NUMBER, MAX_CONNECTION_NUMBER);
        } catch (IOException e) {
            log.error("Problem to set up server socket");
            throw new RuntimeException(e);
        }
    }

    public void start() throws IOException {
        log.info("Server start");

        while (isServerActive){
            userCounter++;
            Socket serverClient = serverSocket.accept();
            log.info("    [Client] " + userCounter + " - connected");

            ServerToClientHandler serverToClientHandler = new ServerToClientHandler(serverClient, userCounter);
            serverToClientHandler.start();
        }
    }

    public static void main(String[] args) throws IOException {
        MultithreadedSocketServer server = new MultithreadedSocketServer();
        server.start();
    }
}
