package org.vshmaliukh.services.input_services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.vshmaliukh.console_terminal_app.input_handler.ConsoleInputHandlerForLiterature;
import org.vshmaliukh.console_terminal_app.input_handler.ConsoleInputHandlerForUser;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.vshmaliukh.console_terminal_app.ConsoleGsonShelfHandler.DATE_FORMAT_STR;
import static org.vshmaliukh.console_terminal_app.input_handler.ConstantsForConsoleUserInputHandler.*;

class InputHandlerTest {

    private final PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(System.out), true);
    private Scanner scanner = new Scanner(System.in);

    private ConsoleInputHandlerForLiterature userInputHandler = new ConsoleInputHandlerForLiterature(scanner, printWriter);

    private final String entersForRecursion;

    {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < MAX_RECURSION_LEVEL; i++) {
            stringBuilder.append(System.lineSeparator());
        }
        entersForRecursion = stringBuilder.toString();
    }

    @DisplayName("validate data from user input (date) of Literature object")
    @ParameterizedTest(name = "{index} ==> input string ''{0}'' expected state ''{1}''")
    @CsvSource(delimiter = '|', quoteCharacter = '"', textBlock = "" +
            //" # INPUT     | BOOLEAN" + "\n" +
            "  2022-12-31   |  true" + "\n" +
            "  2022-01-01   |  true" + "\n" +
            "  0001-01-01   |  true" + "\n" +
            "  9999-12-31   |  true" + "\n" +
            "  2022-02-20   |  true" + "\n" +
            "  2022-02-28   |  true" + "\n" +
            "  2024-02-29   |  true" + "\n" +
            "  2022-02-29   |  false" + "\n" +
            "  2024-02-30   |  false" + "\n" +
            "  0000-00-00   |  false" + "\n" +
            "  2022-13-01   |  false" + "\n" +
            "  2022-00-01   |  false" + "\n" +
            "  2022-01 01   |  false" + "\n" +
            "  2022-01/01   |  false" + "\n" +
            "  2022-01_01   |  false")
    void testDateValidation(String input, boolean expectedBoolean) {
        assertEquals(expectedBoolean, userInputHandler.isValidInputDate(input.trim(), new SimpleDateFormat(DATE_FORMAT_STR)));
    }

    @DisplayName("test user input for name of Literature object")
    @ParameterizedTest(name = "{index} ==> input string ''{0}'' expected string ''{1}''")
    @CsvFileSource(resources = "/inputName.csv", numLinesToSkip = 1)
    void testNameInput(String input, String expected) {
        userInputHandler = new ConsoleInputHandlerForLiterature(new Scanner(input), printWriter);
        assertEquals(expected, userInputHandler.getUserLiteratureName());
    }

    @DisplayName("test for user input for date of issue")
    @ParameterizedTest(name = "{index} ==> input string ''{0}'' expected result ''{1}''")
    @MethodSource("providedStringForDateInput")
    void testDateInput(String expectedOut, String input) throws ParseException {
        userInputHandler = new ConsoleInputHandlerForLiterature(new Scanner(input), printWriter);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STR);
        dateFormat.setLenient(false);
        Date currentDate = userInputHandler.getUserLiteratureDateOfIssue();
        Date expectedDate = dateFormat.parse(expectedOut);
        assertEquals(expectedDate.getTime(), currentDate.getTime());
    }

    private static Stream<Arguments> providedStringForDateInput() {
        return Stream.of(
                Arguments.of("2022-12-31", "2022-12-31"),
                Arguments.of("2022-01-01", "2022-01-01"),
                Arguments.of("0001-01-01", "0001-01-01"),
                Arguments.of("9999-12-31", "9999-12-31"),
                Arguments.of("2022-02-20", "2022-02-20"),
                Arguments.of("2022-02-28", "2022-02-28"),
                Arguments.of("2024-02-29", "2024-02-29")
        );
    }

    @DisplayName("test user input for choosing state (author) of Literature object")
    @ParameterizedTest(name = "{index} ==> input string ''{0}'' expected string ''{0}''")
    @CsvFileSource(resources = "/inputAuthorsForValidation.csv", numLinesToSkip = 1)
    void testAuthorInput(String input) {
        userInputHandler = new ConsoleInputHandlerForLiterature(new Scanner(input), printWriter);
        assertEquals(input, userInputHandler.getUserLiteratureName());
    }

    @DisplayName("validate data from user input (name) of Literature object")
    @ParameterizedTest(name = "{index} ==> input string ''{0}''")
    @CsvFileSource(resources = "/inputNameForValidation.csv", numLinesToSkip = 1)
    void testIsNameValidate_true(String input) {
        assertTrue(userInputHandler.isValidInputString(input.trim(), ConstantsForItemInputValidation.PATTERN_FOR_NAME));
    }

    @DisplayName("validate data from user input (is borrowed) of Literature object")
    @ParameterizedTest(name = "{index} ==> input string ''{0}''")
    @CsvFileSource(resources = "/inputIsBorrowedValid.csv", numLinesToSkip = 1)
    void testIsBorrowedValidation_true(String input) {
        assertTrue(userInputHandler.isValidInputString(input.trim(), ConstantsForItemInputValidation.PATTERN_FOR_IS_BORROWED));
    }

    @DisplayName("validate data from user input (is borrowed) of Literature object after user Enter")
    @ParameterizedTest(name = "{index} ==> input string ''{0}''")
    @ValueSource(strings = {"\nn", "\nN", "\nY", "\ny", "\n n", "\n\tn", "\n\nn",})
    void testIsBorrowedValidation_trueAfterSomeEnter(String input) {
        assertTrue(userInputHandler.isValidInputString(input.trim(), ConstantsForItemInputValidation.PATTERN_FOR_IS_BORROWED));
    }

    @DisplayName("validate data from user input (is borrowed) of Literature object")
    @ParameterizedTest(name = "{index} ==> input string ''{0}''")
    @ValueSource(strings = {"", "  ", "c", "1", "0", "\n", "nn", "NN", "n n", "_n", "Nn"})
    void testIsBorrowedValidation_false(String input) {
        assertFalse(userInputHandler.isValidInputString(input.trim(), ConstantsForItemInputValidation.PATTERN_FOR_IS_BORROWED));
    }

    @DisplayName("validate data from user input (pages number) of Literature object")
    @ParameterizedTest(name = "{index} ==> input string ''{0}''")
    @ValueSource(strings = {"1", " 1", "1 ", " 1 ", "\n1", "2147483647", "1000"})
    void testPagesValidation_true(String input) {
        assertTrue(userInputHandler.isValidInputString(input.trim(), ConstantsForItemInputValidation.PATTERN_FOR_PAGES));
    }

    @DisplayName("validate data from user input (pages number) of Literature object")
    @ParameterizedTest(name = "{index} ==> input string ''{0}''")
    @ValueSource(strings = {"", "  ", "0", "c", "_1", "o", "\n", "*1", "+1", "-1", "1 000", "0", "1.2", "six", "13/1", "2,147,483,647", "-1321"})
    void testPagesValidation_false(String input) {
        assertFalse(userInputHandler.isValidInputInteger(input.trim(), ConstantsForItemInputValidation.PATTERN_FOR_PAGES));
    }

    @DisplayName("validate data from user input (author) of Literature object")
    @ParameterizedTest(name = "{index} ==> input string ''{0}''")
    @CsvFileSource(resources = "/inputAuthorsForValidation.csv", numLinesToSkip = 1)
    void testAuthorValidation_true(String input) {
        assertTrue(userInputHandler.isValidInputString(input.trim(), ConstantsForItemInputValidation.PATTERN_FOR_AUTHOR));
    }

    @Test
    void testRecursion_wrongInputMaxTimesForLiteraturePages() {
        scanner = new Scanner(entersForRecursion);
        userInputHandler = new ConsoleInputHandlerForLiterature(scanner, printWriter);

        assertEquals(DEFAULT_INTEGER, userInputHandler.getUserLiteraturePages());
    }

    @Test
    void testRecursion_wrongInputMaxTimesForLiteratureBoolean() {
        scanner = new Scanner(entersForRecursion);
        userInputHandler = new ConsoleInputHandlerForLiterature(scanner, printWriter);

        assertEquals(DEFAULT_BOOLEAN, userInputHandler.getUserLiteratureIsBorrowed());
    }

    @Test
    void testRecursion_wrongInputMaxTimesForLiteratureDate() {
        scanner = new Scanner(entersForRecursion);
        userInputHandler = new ConsoleInputHandlerForLiterature(scanner, printWriter);

        assertEquals(DEFAULT_DATE, userInputHandler.getUserLiteratureDateOfIssue());
    }

    @Test
    void testRecursion_wrongInputMaxTimesForLiteratureName() {
        scanner = new Scanner(entersForRecursion);
        userInputHandler = new ConsoleInputHandlerForLiterature(scanner, printWriter);

        assertEquals(DEFAULT_STRING, userInputHandler.getUserLiteratureName());
    }

    @Test
    void testRecursion_wrongInputMaxTimesForUserName() {
        scanner = new Scanner(entersForRecursion);
        ConsoleInputHandlerForUser userInputHandler = new ConsoleInputHandlerForUser(scanner, printWriter);

        assertEquals(DEFAULT_STRING, userInputHandler.getUserName());
    }

    @Test
    void testRecursion_wrongInputMaxTimesForTypeOfWorkWithFiles() {
        scanner = new Scanner(entersForRecursion);
        ConsoleInputHandlerForUser userInputHandler = new ConsoleInputHandlerForUser(scanner, printWriter);

        assertEquals(DEFAULT_INTEGER, userInputHandler.getTypeOfWorkWithFiles());
    }
}