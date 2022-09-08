package org.vshmaliukh;

import org.vshmaliukh.server.MultithreadedSocketServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            MultithreadedSocketServer.main(args);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
