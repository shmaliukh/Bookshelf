package org.vshmaliukh.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TelnetClient {

    public static final int SOCKET_PORT_NUMBER = 8080;

    private final Socket socket;

    private final Scanner socketScanner;
    private final Scanner localScanner;
    private final PrintWriter socketPrintWriter;
    private final PrintWriter localPrintWriter;

    private final Thread inThread;
    private final Thread outThread;

    public TelnetClient() throws IOException {

        socket = new Socket("localhost", SOCKET_PORT_NUMBER); // TODO use ENV for configuration socket

        socketScanner = new Scanner(socket.getInputStream());
        localScanner = new Scanner(System.in);
        socketPrintWriter = new PrintWriter(socket.getOutputStream(), true);
        localPrintWriter = new PrintWriter(System.out, true);

        localPrintWriter.println("Client start");

        inThread = new ScannerToWriterRedirector(socketScanner, localPrintWriter);
        outThread = new ScannerToWriterRedirector(localScanner, socketPrintWriter);

        inThread.start();
        outThread.start();
    }

    public static void main(String[] args) throws IOException {
        TelnetClient client = new TelnetClient();
    }
}