package org.ShmaliukhVlad;

import org.ShmaliukhVlad.server.MultithreadedSocketServer;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ParseException, IOException{
        Terminal terminal = new Terminal(new Scanner(System.in), new PrintWriter(new OutputStreamWriter(System.out)));
        int typeOfConfig;

        if(args.length>0){
            typeOfConfig = Integer.parseInt(args[0]);
        }
        else{
            typeOfConfig = 0;
        }
        if(typeOfConfig > 2 && typeOfConfig <6){
            MultithreadedSocketServer.main(new String[]{String.valueOf(typeOfConfig)});
        }
        else {
            terminal.startWork(typeOfConfig);
        }


    }
}