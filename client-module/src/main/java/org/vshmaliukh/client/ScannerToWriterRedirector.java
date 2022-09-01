package org.vshmaliukh.client;

import java.io.PrintWriter;
import java.util.Scanner;

public class ScannerToWriterRedirector extends Thread {

    private final Scanner scanner;
    private final PrintWriter printWriter;

    public ScannerToWriterRedirector(Scanner scanner, PrintWriter printWriter) {
        this.scanner = scanner;
        this.printWriter = printWriter;
    }

    @Override
    public void run() {
        String userInput;
        while (scanner.hasNextLine()) {
            userInput = scanner.nextLine();
            if (userInput.equals("_EXIT_")) {
                break;
                // TODO create another exit value
            }
            printWriter.println(userInput);
        }
    }
}