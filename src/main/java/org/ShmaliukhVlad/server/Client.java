package org.ShmaliukhVlad.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        Client client = new Client();
    }


    Socket socket = new Socket("localhost", MultithreadedSocketServer.SERVER_PORT);

    Scanner socketScanner = new Scanner(socket.getInputStream());
    Scanner localScanner = new Scanner(System.in);
    PrintWriter socketPrintWriter = new PrintWriter(socket.getOutputStream());
    PrintWriter localPrintWriter = new PrintWriter(System.out);

    Client() throws IOException {
        System.out.println("Client start");
        inThread.start();
        outThread.start();
    }

    Thread inThread = new ScannerToWriterRedirector(socketScanner, localPrintWriter);
    Thread outThread = new ScannerToWriterRedirector(localScanner, socketPrintWriter);

    public static class ScannerToWriterRedirector extends Thread {
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
                }
                printWriter.println(userInput);
                printWriter.flush();
            }
        }
    }
}
