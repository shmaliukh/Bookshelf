package org.vshmaliukh.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class MultithreadedSocketServer {

    public static final String MODULE_NAME = "server-app-module";

    public static final int SOCKET_PORT_NUMBER = 8080;
    public static final int MAX_CONNECTION_NUMBER = 10;

    private ServerSocket serverSocket;

    private int userCounter;
    private boolean isServerActive;

    public MultithreadedSocketServer() {
        userCounter = 0;
        isServerActive = true;

        initServerSocket();
    }

    private void initServerSocket() {
        try {
            serverSocket = new ServerSocket(SOCKET_PORT_NUMBER, MAX_CONNECTION_NUMBER);
        } catch (IOException ioe) {
            log.error("[MultithreadedSocketServer]: Problem to set up server socket. Exception: " + ioe);
        }
    }

    public void start() throws IOException {
        log.info("Server start");

        while (isServerActive) {
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
