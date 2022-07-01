package org.ShmaliukhVlad;

import java.io.IOException;
import java.text.ParseException;

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
        Terminal terminal = new Terminal();
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