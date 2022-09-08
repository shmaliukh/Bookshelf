package org.vshmaliukh.console_terminal_app;

import java.io.PrintWriter;
import java.util.Scanner;

public class ShelfConsoleApp {

    public static final String MODULE_NAME = "console-app-module";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        PrintWriter printWriter = new PrintWriter(System.out, true);

        ConsoleUI consoleUI = new ConsoleUI(scanner, printWriter);

        consoleUI.startWork(true);
    }
}
