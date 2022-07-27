package org.vshmaliukh.terminal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.PrintWriter;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TerminalTest {

    Terminal terminal = new Terminal(new Scanner(System.in), new PrintWriter(System.out, true));

    @Test
    void testStartWithUserConfig_noUser() {
        terminal.startWithUserConfig(false);
        String name = terminal.getUser().getName();
        assertFalse(name.isEmpty());
        assertEquals("no_user", name);
    }

    @Test
    void testStartWithUserConfig_userMode() {
        Scanner scanner = new Scanner("testName");
        Terminal terminal = new Terminal(scanner, new PrintWriter(System.out, true));
        terminal.startWithUserConfig(true);
        String name = terminal.getUser().getName();
        assertFalse(name.isEmpty());
        assertEquals("testName", name);
    }

    @Test
    void testStartWork_oneFileMode() {
        //Scanner scanner = new Scanner("1" + System.lineSeparator() + "0");
        //Terminal terminal = new Terminal(scanner, new PrintWriter(System.out, true));
        //terminal.startWork(false);
    }

    @Test
    void testStartWork_perTypeFileMode() {
        //Scanner scanner = new Scanner("2" + System.lineSeparator() + "0");
        //Terminal terminal = new Terminal(scanner, new PrintWriter(System.out, true));
        //terminal.startWork(false);
    }

    @Test
    void testStartWork_perTypeFileMode_user() {
        //Scanner scanner = new Scanner("test" + System.lineSeparator() + "2" + System.lineSeparator() + "0");
        //Terminal terminal = new Terminal(scanner, new PrintWriter(System.out, true));
        //assertFalse(terminal.startWork(true));
    }

    @ParameterizedTest(name = "{index} ==> input string ''{0}''")
    @ValueSource(strings = {"0", "2147483647", "1", "000", "1_0", "100-000", "1a2sF`;'lj'", "/*-+.0", "01234567891", "________-123", "______________________________________________________________________________________________-123"})
    void getUserChoice(String str) {
        Scanner scanner = new Scanner(str);
        Terminal terminal = new Terminal(scanner, new PrintWriter(System.out, true));
        int userChoice = terminal.getUserChoice();
        System.out.println("userChoice = " + userChoice);
        assertTrue(userChoice >= 0 && userChoice < Integer.MAX_VALUE);
    }

}