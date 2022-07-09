package org.ShmaliukhVlad.server;

import org.ShmaliukhVlad.Terminal;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerClientThread extends Thread{

    private Socket socketForClient;
    private int userCounter;
    private int terminalConfig;

    private Scanner scanner;
    private PrintWriter printWriter;
    private WriterToClientThread writerToClientThread;

    public ServerClientThread(Socket socket, int userCounter, int terminalConfig) {
        this.terminalConfig = terminalConfig;
        this.userCounter = userCounter;
        socketForClient = socket;
        try {
            scanner = new Scanner(socketForClient.getInputStream());
            printWriter = new PrintWriter(socketForClient.getOutputStream());
        } catch (IOException e) {
            System.err.println("Server scanner/reader error");
        }
        writerToClientThread = new WriterToClientThread(printWriter);
    }

    @Override
    public void run(){
        System.out.println("    [Client] " + this.userCounter +" - start");

        writerToClientThread.start(); // FIXME thread inside thread make parallel threads

        Terminal terminal = new Terminal(scanner, printWriter);
        try {
            terminal.startWork(terminalConfig, true);
        } catch (Exception e) {
            System.err.println("Problem to start terminal");
            throw new RuntimeException(e);
        }
        finally {
            System.out.println("    [Client] " + this.userCounter +" - stop");
            scanner.close();
            printWriter.close();
        }
    }
}

class WriterToClientThread extends Thread {

    PrintWriter printWriterToClient;

    WriterToClientThread(PrintWriter printWriter) {
        printWriterToClient = printWriter;

    }

    @Override
    public void run() {
        while (true) {
            printWriterToClient.flush();
        }// FIXME make stop point
    }
}