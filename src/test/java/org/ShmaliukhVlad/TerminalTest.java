package org.ShmaliukhVlad;

import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TerminalTest {

    InputStream sysInBackup = System.in; // backup System.in to restore it later
    ByteArrayInputStream in = new ByteArrayInputStream("My string".getBytes());

    @Test
    @DisplayName("test simulates user terminal")
    void startWork() {
        Terminal terminal = new Terminal();
        terminal.startWork();
        //TODO
    }

    @Test
    @RepeatedTest(5)
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