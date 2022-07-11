package org.vshmaliukh.client_server.client;

import java.io.PrintWriter;
import java.util.Scanner;

public class ScannerToWriterRedirector extends Thread {

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