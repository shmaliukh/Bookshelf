package org.ShmaliukhVlad;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Scanner;

/**
 * @author ShmaliukhVlad
 * @version 1.3.0
 * This is Main class for starting project
 */
public class Main {
    /**
     * Here is start point of a program
     * @param args command line values
     */
    public static void main(String[] args) throws ParseException, IOException{
        Terminal terminal = new Terminal(new Scanner(System.in), new PrintWriter(new OutputStreamWriter(System.out)));
        int typeOfConfig;

        if(args.length>0){
            typeOfConfig = Integer.parseInt(args[0]);
        }
        else{
            typeOfConfig = 0;
        }

        terminal.startWork(typeOfConfig);
    }
}