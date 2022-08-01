package org.vshmaliukh;

import javax.servlet.http.HttpServlet;
import java.io.Closeable;
import java.util.Scanner;

public class ScannerWrapper implements Closeable {
    private Scanner scanner;

    public  ScannerWrapper(Scanner scanner){
        this.scanner = scanner;
    }

    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public String nextLine() {
        return scanner.nextLine();
    }

    @Override
    public void close() {
        scanner.close();
    }
}
