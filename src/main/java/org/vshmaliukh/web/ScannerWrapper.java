package org.vshmaliukh.web;

import javax.servlet.http.HttpServlet;
import java.io.Closeable;
import java.util.Scanner;

public class ScannerWrapper implements Closeable {

    public Scanner scanner;

    public ScannerWrapper(Scanner scanner) {
        this.scanner = scanner;
    }

    public boolean hasNextLine() {
        boolean b = scanner.hasNextLine();
        return b;
    }

    public String nextLine(){
        String s = scanner.nextLine();
        return s;
    }

    @Override
    public void close() {
        scanner.close();
    }
}
