package org.ShmaliukhVlad;

import java.io.IOException;
import java.text.ParseException;

import static org.ShmaliukhVlad.constants.ConstantValues.SAVE_READ_ONE_FILE;
import static org.ShmaliukhVlad.constants.ConstantValues.SAVE_READ_TWO_FILES;

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
        Terminal terminal = new Terminal();
        terminal.startWork(SAVE_READ_TWO_FILES);
    }
}