package org.ShmaliukhVlad.server;

import org.ShmaliukhVlad.Terminal;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerClientThread extends Thread{
    Socket serverClient;
    int userId;
    int terminalConfig;

    public ServerClientThread(Socket socket, int userCounter, int terminalConfig) {
        this.terminalConfig = terminalConfig;
        serverClient = socket;
        userId = userCounter;
    }

    @Override
    public void run(){
        try(Scanner scanner = new Scanner(serverClient.getInputStream());
            PrintWriter printWriter = new PrintWriter(serverClient.getOutputStream(),true)){

            Terminal terminal = new Terminal(scanner, printWriter);
            terminal.startWork(terminalConfig, true);

            serverClient.close();
        } catch (Exception e) {
            System.err.println("Some err");// TODO
            throw new RuntimeException(e);
        }
        finally {
            System.out.println("Client №" + userId + " exit");
        }
    }
}
