package org.ShmaliukhVlad;

import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Magazine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;

public class InputTest {

    @Test
    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, -3, 15, Integer.MAX_VALUE})
    @DisplayName("test user input for integer")//Todo
    public void testDateUserInput() {
        String expectedAnswer = "Wrong input";
        String actualAnswer;

        //InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("""
        bookName
        10d
        0
        """.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);
        Terminal terminal = new Terminal(in, printStream);
        Magazine userMagazine = terminal.getUserMagazine();
        System.out.println(out);
        out.reset();
        System.out.println(out);
        String expectedMenu = """
                Enter name:
                Enter number of pages:
                Enter 1 if Magazine is NOT borrowed
                Press another key to continue
                SomeError
                """;
        //System.setIn(sysInBackup);

    }

    @Test
    public void someTest(){

    }


    public void close() {

    }
}
