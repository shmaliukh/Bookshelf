package org.vshmaliukh.server;

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

        socket = new Socket("localhost", 8888);

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

class ScannerToWriterRedirector extends Thread {

    private Scanner scanner;
    private PrintWriter printWriter;

    public ScannerToWriterRedirector(Scanner scanner, PrintWriter printWriter) {
        this.scanner = scanner;
        this.printWriter = printWriter;
    }

    @Override
    public void run() {
        String userInput = "";
        while (scanner.hasNextLine()) {
            userInput = scanner.nextLine();
            if (userInput.equals("0")) {
                break;
                // TODO create another exit value
            }
            printWriter.println(userInput);
            printWriter.flush();
        }
    }
}