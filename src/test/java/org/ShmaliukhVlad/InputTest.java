package org.ShmaliukhVlad;

import org.ShmaliukhVlad.serices.UserInput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.io.*;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class InputTest {
    PrintStream printStream = new PrintStream(new ByteArrayOutputStream());

    @DisplayName("validate data from user input (date) of Literature object")
    @ParameterizedTest(name = "{index} ==> input string ''{0}'' expected state ''{1}''")
    @CsvSource(delimiter = '|', quoteCharacter = '"', textBlock =
            //" # INPUT     | BOOLEAN" + "\n" +
            "  31-12-2022   |  true"  + "\n" +
            "  01-01-2022   |  true"  + "\n" +
            "  01-01-0001   |  true"  + "\n" +
            "  31-12-9999   |  true"  + "\n" +
            "  20-02-2022   |  true"  + "\n" +
            "  28-02-2022   |  true"  + "\n" +
            "  29-02-2024   |  true"  + "\n" +
            "  29-02-2022   |  true"  + "\n" +
            "  30-02-2024   |  true"  + "\n" +
            "  00-00-0000   |  false" + "\n" +
            "  01-13-2022   |  false" + "\n" +
            "  01-00-2022   |  false" + "\n" +
            "  01 01 2022   |  false" + "\n" +
            "  01/01/2022   |  false" + "\n" +
            "  01_01_2022   |  false" )
    public void testDateValidation(String input, boolean expectedBoolean) {
        assertEquals(expectedBoolean, UserInput.isValidLiteratureDate(input));
    }

    @DisplayName("test user input for name of Literature object")
    @ParameterizedTest(name = "{index} ==> input string ''{0}'' expected string ''{1}''")
    @CsvFileSource(resources = "/inputName.cvs", numLinesToSkip = 1)
    public void testNameInput(String input, String expected){
        assertEquals(expected, UserInput.getUserLiteratureName(new Scanner(input), printStream));
    }

    @DisplayName("test for user input for date of issue")
    @ParameterizedTest(name = "{index} ==> input string ''{0}'' expected result ''{1}''")
    @MethodSource("providedStringForDateInput")
    public void testDateInput(String input){
        //TODO
        //assertEquals(expected);
    }

    private static Stream<Arguments> providedStringForDateInput(){
        return Stream.of(
                Arguments.of("01-01-0001","")//TODO
        );
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
        assertFalse(UserInput.isValidLiteratureIsBorrowed(input));
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
    @CsvFileSource(resources = "/inputAuthorsForValidation.cvs", numLinesToSkip = 1)//TODO new test items
    public void testAuthorValidation_true(String input){
        assertTrue(UserInput.isValidLiteratureAuthor(input));
    }
}
