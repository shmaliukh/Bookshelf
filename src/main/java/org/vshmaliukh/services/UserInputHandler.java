package org.vshmaliukh.services;

import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.vshmaliukh.constants.ConstantsForTerminal.DATE_FORMAT;
import static org.vshmaliukh.constants.ConstantsForUserInputHandler.*;

public class UserInputHandler {

    //private final Logger LOGGER = LoggerFactory.getLogger(UserInput.class);
    //TODO cover code with LOGGER ???

    private final Scanner scanner;
    private final PrintWriter printWriter;

    private boolean validationResult;
    private String inputString = "";

    public UserInputHandler(Scanner scanner, PrintWriter printWriter) {
        this.scanner = scanner;
        this.printWriter = printWriter;
    }

    public boolean isValidInputString(String inputStr, Pattern pattern) {
        if (inputStr == null) {
            return false;
        }
        Matcher m = pattern.matcher(inputStr);
        return m.matches();
    }

    private void readStringFromLine(){
        if(scanner.hasNextLine()) {
            if (scanner.hasNextLine()) {
                inputString = scanner.nextLine().trim();
            }
        }
    }

    private String getUserString(String message, Pattern pattern){
        printWriter.println(message);
        readStringFromLine();
        validationResult = isValidInputString(inputString, pattern);
        if (validationResult) {
            return inputString;
        }
        printWriter.println(MESSAGE_WRONG_INPUT_TRY_AGAIN);
        return getUserString(message, pattern);
    }

    public String getUserName(){
        return getUserString(
                MESSAGE_ENTER_USER_NAME,
                PATTERN_FOR_USER_NAME);
    }

    public String getUserLiteratureName() {
        return getUserString(
                MESSAGE_ENTER_LITERATURE_NAME,
                PATTERN_FOR_NAME);
    }

    public String getUserLiteratureAuthor() {
        return getUserString(
                MESSAGE_ENTER_LITERATURE_AUTHOR,
                PATTERN_FOR_AUTHOR);
    }

    public boolean getUserLiteratureIsBorrowed() {
        return Boolean.getBoolean(getUserString(
                MESSAGE_ENTER_LITERATURE_IS_BORROWED,
                PATTERN_FOR_IS_BORROWED));
    }

    public int getUserLiteraturePages() {
        printWriter.println(MESSAGE_ENTER_LITERATURE_PAGES_NUMBER);
        if(scanner.hasNext()){
            inputString = scanner.nextLine().replaceAll("[\\D]", "").trim();
            if (inputString.length() > 0){
                if(inputString.length() > 8){
                    inputString = inputString.substring(0,8);
                }
                if (isValidInputString(inputString, PATTERN_FOR_PAGES)) {
                    return Integer.parseInt(inputString);
                }
            }
        }
        printWriter.println(MESSAGE_WRONG_INPUT_FOR_LITERATURE_PAGES);
        return getUserLiteraturePages();
    }

    public Date getUserDateOfIssue() throws ParseException {
        printWriter.println(MESSAGE_ENTER_LITERATURE_DATE);
        if(scanner.hasNextLine()){
            inputString = scanner.nextLine().trim();
            validationResult = isValidLiteratureDate(inputString);
            if(isValidLiteratureDate(inputString)){
                DATE_FORMAT.setLenient(false);
                return DATE_FORMAT.parse(inputString);
            }
        }
        printWriter.println(MESSAGE_WRONG_INPUT_TRY_AGAIN);
        return getUserDateOfIssue();
    }

    public boolean isValidLiteratureDate(String input) {
        if(input == null){
            return false;
        }
        DATE_FORMAT.setLenient(false);
        try {
            DATE_FORMAT.parse(input);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
