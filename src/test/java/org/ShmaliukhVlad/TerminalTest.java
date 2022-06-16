package org.ShmaliukhVlad;

import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TerminalTest {

    /**
     * Special capture variables for the capture console output
     */
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    PrintStream old = System.out;

    @Test
    @DisplayName("test state of terminal start")
    void startWork() {
        String expectedString = """
                        
                        Enter number of  command you wand to execute:
                        1 - Add new Literature object to Shelf
                        2 - Delete  Literature object by index from Shelf
                        3 - Borrow  Literature object by index from Shelf
                        4 - Arrive  Literature object by index back to Shelf
                        5 - Print list of available Books sorted by parameter...
                        6 - Print list of available Magazines sorted by parameter...
                        7 - Save in file
                        8 - Deserialize
                        9 - Print current state of Shelf
                        0 - Exit""";

        Terminal terminal = new Terminal();
        terminal.startWork();

        System.setOut(ps);
        System.out.flush();
        System.setOut(old);

        terminal.setPlay(false);

        assertEquals(expectedString, baos.toString().trim());

    }

    @Test
    @DisplayName("test getRandomString")
    @Description("generate string with expected length")
    void getRandomString() {
        int expectedLength = new Random().nextInt(10);
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_ ";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < expectedLength; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }

        assertEquals(sb.toString().length(), expectedLength);
    }
}