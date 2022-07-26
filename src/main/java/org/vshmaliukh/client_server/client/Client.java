package org.vshmaliukh.client_server.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static org.vshmaliukh.client_server.ConstantsForClientServerModel.SOCKET_PORT_NUMBER;

public class Client {

    private final Socket socket;

    private final Scanner socketScanner;
    private final Scanner localScanner;
    private final PrintWriter socketPrintWriter;
    private final PrintWriter localPrintWriter;

    private final Thread inThread;
    private final Thread outThread;

    public Client() throws IOException {

        socket = new Socket("localhost", SOCKET_PORT_NUMBER);

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
        Client client = new Client();
    }
}