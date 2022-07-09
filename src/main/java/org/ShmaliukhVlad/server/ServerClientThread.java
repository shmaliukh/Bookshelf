package org.ShmaliukhVlad.server;

import org.ShmaliukhVlad.Terminal;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerClientThread extends Thread{
    private Socket serverClient;
    private int userId;
    private int terminalConfig;

    private Scanner scanner;
    private PrintWriter printWriter;

    public ServerClientThread(Socket socket, int userCounter, int terminalConfig) {
        this.terminalConfig = terminalConfig;
        serverClient = socket;
        userId = userCounter;
        try {
            scanner = new Scanner(serverClient.getInputStream());
            printWriter = new PrintWriter(serverClient.getOutputStream());
        } catch (IOException e) {
            System.err.println("Server scanner/reader error");
        }
    }

    final Thread outThread = new Thread(() -> {
        while (true){
            printWriter.flush();
        }// FIXME make stop point
    });

    @Override
    public void run(){
        System.out.println("    [org.ShmaliukhVlad.server.Client] " + this.userId +" - start");
        outThread.start();
        Terminal terminal = new Terminal(scanner, printWriter);
        try {
            terminal.startWork(terminalConfig, true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            System.out.println("    [org.ShmaliukhVlad.server.Client] " + this.userId +" - stop");
            scanner.close();
            printWriter.close();
        }
    }
}
