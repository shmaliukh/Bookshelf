package org.ShmaliukhVlad;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server{
    static final ServerSocket SERVER_SOCKET;
    int userCounter;

    Socket socket = SERVER_SOCKET.accept();
    Scanner scanner = new Scanner(socket.getInputStream());
    PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);

    List<Thread> listOfThreads = new ArrayList<>();

    static {
        try {
            SERVER_SOCKET = new ServerSocket(8887);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Server() throws IOException {
    }

    void start() throws ParseException, IOException {

        System.out.println("Server start");
        String userInput = "";



        Terminal terminal = new Terminal(scanner, printWriter);
        terminal.startWork(0);

        //while (!userInput.equals("0")){
        //    //userInput = scanner.next();
        //    //System.out.println("client says: " + userInput);
//
        //
        //    printWriter.println("Server answer: " + userInput);
        //    printWriter.flush();
//
//
//
        //}
    }

    public static void main(String[] args) throws IOException, ParseException {
        Server server = new Server();
        server.start();
    }
}
