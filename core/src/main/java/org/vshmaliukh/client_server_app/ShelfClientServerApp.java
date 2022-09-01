package org.vshmaliukh.client_server_app;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.client_server_app.server.MultithreadedSocketServer;

import java.io.IOException;

@Slf4j
public class ShelfClientServerApp {

    public static void main(String[] args) {
        ServerThread serverThread = new ServerThread();
        serverThread.start();
    }

    static class ServerThread extends Thread {

        MultithreadedSocketServer multithreadedSocketServer = new MultithreadedSocketServer();

        @Override
        public synchronized void run() {
            try {
                multithreadedSocketServer.start();
            } catch (IOException e) {
                log.error("[ServerThread] problem to start thread. Exception: ", e);
            }
        }
    }
}
