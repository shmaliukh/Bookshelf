package org.vshmaliukh.client_server_model.client;

import org.vshmaliukh.client_server_model.ConstantsForClientServerModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;

    private Scanner socketScanner;
    private Scanner localScanner;
    private PrintWriter socketPrintWriter;
    private PrintWriter localPrintWriter;

    private Thread inThread;
    private Thread outThread;

    public Client() throws IOException {

        System.out.println("Client start");

        socket = new Socket(ConstantsForClientServerModel.HOST_NAME, ConstantsForClientServerModel.SOCKET_PORT_NUMBER);

        socketScanner = new Scanner(socket.getInputStream());
        localScanner = new Scanner(System.in);
        socketPrintWriter = new PrintWriter(socket.getOutputStream());
        localPrintWriter = new PrintWriter(System.out);

        inThread = new ScannerToWriterRedirector(socketScanner, localPrintWriter);
        outThread = new ScannerToWriterRedirector(localScanner, socketPrintWriter);

        inThread.start();
        outThread.start();
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
    }
}