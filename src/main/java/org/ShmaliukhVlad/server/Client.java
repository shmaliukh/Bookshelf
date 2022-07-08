package org.ShmaliukhVlad.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        Client client = new Client();
    }

    Socket socket = new Socket("localhost", 8888);

    Scanner scanner = new Scanner(socket.getInputStream());
    PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

    Client() throws IOException {
        System.out.println("Client start");
        inThread.start();
        outThread.start();
    }

    Thread inThread = new Thread(() -> {
        while (scanner.hasNextLine()){
            System.out.println(scanner.nextLine());
        }
    });

    Thread outThread = new Thread(() -> {
        Scanner scanner1 = new Scanner(System.in);

        String userInput = "";
        while (!userInput.equals("0")){
            userInput = scanner1.nextLine();
            printWriter.println(userInput);
            printWriter.flush();
        }
    });


}
