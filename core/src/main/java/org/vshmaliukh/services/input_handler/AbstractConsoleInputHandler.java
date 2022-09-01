package org.vshmaliukh.services.input_handler;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.services.input_services.AbstractInputHandler;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

@Slf4j
public class AbstractConsoleInputHandler extends AbstractInputHandler {

    private final Scanner scanner;
    private final PrintWriter printWriter;

    private boolean validationResult;
    private String inputString = "";
    private int currentRecursionLevel;

    protected AbstractConsoleInputHandler(Scanner scanner, PrintWriter printWriter) {
        this.scanner = scanner;
        this.printWriter = printWriter;
    }

    public <T> boolean tryAgain(T defaultValue) {
        if (currentRecursionLevel < ConstantsForConsoleUserInputHandler.MAX_RECURSION_LEVEL) {
            informMessageToUser(ConstantsForConsoleUserInputHandler.MESSAGE_WRONG_INPUT_TRY_AGAIN + " "
                    + (ConstantsForConsoleUserInputHandler.MAX_RECURSION_LEVEL - currentRecursionLevel)
                    + " more attempt(s) left");
            currentRecursionLevel++;
            return true;
        }
        currentRecursionLevel = 0;
        informMessageToUser(ConstantsForConsoleUserInputHandler.MESSAGE_DEFAULT_VALUE_SET + "'" + defaultValue + "'");
        return false;
    }

    private void readStringFromLine() {
        if (scanner.hasNextLine()) {
            inputString = scanner.nextLine().trim();
        }
    }

    protected String getUserString(String message, Pattern pattern) {
        getUserString(message);
        validationResult = isValidInputString(inputString, pattern);
        if (validationResult) {
            currentRecursionLevel = 0;
            return inputString;
        }
        if (tryAgain(ConstantsForConsoleUserInputHandler.DEFAULT_STRING)) {
            return getUserString(message, pattern);
        }
        return ConstantsForConsoleUserInputHandler.DEFAULT_STRING;
    }

    protected Integer getUserInteger(String message, Pattern pattern) {
        getUserString(message);
        inputString = inputString.replaceAll("[\\D]", "");
        validationResult = isValidInputInteger(inputString, pattern);
        if (validationResult) {
            currentRecursionLevel = 0;
            return Integer.parseInt(inputString);
        }
        if (tryAgain(ConstantsForConsoleUserInputHandler.DEFAULT_INT)) {
            return getUserInteger(message, pattern);
        }
        return ConstantsForConsoleUserInputHandler.DEFAULT_INT;
    }

    protected Date getUserDate(String message, SimpleDateFormat dateFormat) {
        getUserString(message);
        validationResult = isValidInputDate(inputString, dateFormat);
        if (validationResult) {
            currentRecursionLevel = 0;
            try {
                return dateFormat.parse(inputString);
            } catch (ParseException pe) {
                log.error("[InputHandler] Problem to parse user date input. Exception: ", pe);
            }
        }
        if (tryAgain(new SimpleDateFormat(ConstantsForConsoleUserInputHandler.DATE_FORMAT_FOR_INPUT_HANDLER).format(ConstantsForConsoleUserInputHandler.DEFAULT_DATE))) {
            return getUserDate(message, dateFormat);
        }
        return ConstantsForConsoleUserInputHandler.DEFAULT_DATE;
    }

    private void getUserString(String message) {
        informMessageToUser(message);
        readStringFromLine();
    }

    protected Boolean getUserBoolean(String message, Pattern pattern) {
        getUserString(message);
        validationResult = isValidInputString(inputString, pattern);
        if (validationResult) {
            currentRecursionLevel = 0;
            return inputString.equals("y") || inputString.equals("true");
        }
        if (tryAgain(ConstantsForConsoleUserInputHandler.DEFAULT_BOOLEAN)) {
            return getUserBoolean(message, pattern);
        }
        return ConstantsForConsoleUserInputHandler.DEFAULT_BOOLEAN;
    }

    private void informMessageToUser(String message) {
        printWriter.println(message);
    }
}
