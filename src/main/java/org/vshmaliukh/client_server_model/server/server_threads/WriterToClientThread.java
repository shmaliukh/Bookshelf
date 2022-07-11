package org.vshmaliukh.client_server_model.server.server_threads;

import java.io.PrintWriter;

public class WriterToClientThread extends Thread {

    private PrintWriter printWriterToClient;

    public WriterToClientThread(PrintWriter printWriter) {
        printWriterToClient = printWriter;
    }

    @Override
    public void run() {
        while (true) {
            printWriterToClient.flush();
        }// FIXME make stop point
    }
}
