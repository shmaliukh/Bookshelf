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
    public static void main(String[] args) throws ParseException, IOException, ClassNotFoundException {
        //System.out.println(Arrays.toString( args));
        Terminal terminal = new Terminal();
        terminal.startWork();
    }
}