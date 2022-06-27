package org.ShmaliukhVlad;

import org.ShmaliukhVlad.serices.UserInput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class InputTest {
    PrintStream printStream = new PrintStream(new ByteArrayOutputStream());

    @DisplayName("test user input for name of Literature object")
    @ParameterizedTest(name = "{index} ==> input string ''{0}'' expected string ''{1}''")
    @CsvFileSource(resources = "/inputName.cvs", numLinesToSkip = 1)
    public void testNameInput(String input, String expected){
        assertEquals(expected, UserInput.getUserLiteratureName(new Scanner(input), printStream));
    }

    @DisplayName("test user input for choosing state (is borrowed) of Literature object")
    @ParameterizedTest(name = "{index} ==> input string ''{0}'' expected string ''{1}''")
    @CsvFileSource(resources = "/inputName.cvs", numLinesToSkip = 1)
    public void testNameInput_false(String input, String expected){
        assertEquals(expected, UserInput.getUserLiteratureName(new Scanner(input), printStream));
    }

    @DisplayName("test user input for choosing state (author) of Literature object")
    @ParameterizedTest(name = "{index} ==> input string ''{0}'' expected string ''{0}''")
    @CsvFileSource(resources = "/inputAuthorsForValidation.cvs", numLinesToSkip = 1)
    public void testAuthorInput(String input){
        assertEquals(input, UserInput.getUserLiteratureName(new Scanner(input), printStream));
    }

    @DisplayName("validate data from user input (name) of Literature object")
    @ParameterizedTest(name = "{index} ==> input string ''{0}''")
    @CsvFileSource(resources = "/inputNameForValidation.cvs", numLinesToSkip = 1)
    public void testIsNameValidate_true(String input){
        assertTrue(UserInput.isValidLiteratureName(input));
    }

    @DisplayName("validate data from user input (is borrowed) of Literature object")
    @ParameterizedTest(name = "{index} ==> input string ''{0}''")
    @CsvFileSource(resources = "/inputIsBorrowedValid.cvs", numLinesToSkip = 1)
    public void testIsBorrowedValidation_true(String input){
        assertTrue(UserInput.isValidLiteratureIsBorrowed(input));
    }

    @DisplayName("validate data from user input (is borrowed) of Literature object after user Enter")
    @ParameterizedTest(name = "{index} ==> input string ''{0}''")
    @ValueSource(strings = {"\nn", "\nN", "\nY", "\ny", "\n n", "\n\tn", "\n\nn",})
    public void testIsBorrowedValidation_trueAfterSomeEnter(String input){
        assertTrue(UserInput.isValidLiteratureIsBorrowed(input));
    }

    @DisplayName("validate data from user input (is borrowed) of Literature object")
    @ParameterizedTest(name = "{index} ==> input string ''{0}''")
    @ValueSource(strings = {"", "  ", "c", "1", "0", "\n", "nn", "NN", "n n", "_n", "Nn"})
    public void testIsBorrowedValidation_false(String input){
        assertFalse(UserInput.isValidLiteratureIsBorrowed(input));
    }

    @DisplayName("validate data from user input (pages number) of Literature object")
    @ParameterizedTest(name = "{index} ==> input string ''{0}''")
    @ValueSource(strings = {"1", " 1", "1 ", " 1 ", "\n1", "2147483647", "1000"})
    public void testPagesValidation_true(String input){
        assertTrue(UserInput.isValidLiteraturePages(input));
    }

    @DisplayName("validate data from user input (pages number) of Literature object")
    @ParameterizedTest(name = "{index} ==> input string ''{0}''")
    @ValueSource(strings = {"", "  ", "0", "c", "_1", "o", "\n", "*1", "+1", "-1", "1 000", "0", "1.2", "six", "13/1", "2,147,483,647", "-1321"})
    public void testPagesValidation_false(String input){
        assertFalse(UserInput.isValidLiteraturePages(input));
    }

    @DisplayName("validate data from user input (author) of Literature object")
    @ParameterizedTest(name = "{index} ==> input string ''{0}''")
    @CsvFileSource(resources = "/inputAuthorsForValidation.cvs", numLinesToSkip = 1)
    public void testAuthorValidation_true(String input){
        assertTrue(UserInput.isValidLiteratureAuthor(input));
    }
}