package org.vshmaliukh.services;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.vshmaliukh.constants.ConstantsForTerminal.DATE_FORMAT;
import static org.vshmaliukh.constants.ConstantsForUserInputHandler.*;

public class UserInputHandler {

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
        return isMatcher(inputStr, pattern);
    }

    public boolean isValidInputInteger(String inputStr, Pattern pattern) {
        if (inputStr == null || inputStr.length() > 8) {
            return false;
        }
        return isMatcher(inputStr, pattern);
    }

    public boolean isValidInputDate(String inputStr, SimpleDateFormat dateFormat) {
        if (inputStr == null) {
            return false;
        }
        try{
            dateFormat.setLenient(false);
            dateFormat.parse(inputStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isMatcher(String inputStr, Pattern pattern) {
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }

    private void readStringFromLine(){
        if(scanner.hasNextLine()) {
            inputString = scanner.nextLine().trim();
        }
    }

    private String getUserString(String message, Pattern pattern){
        informMessageToUser(message);
        readStringFromLine();
        validationResult = isValidInputString(inputString, pattern);
        if (validationResult) {
            return inputString;
        }
        informMessageToUser(MESSAGE_WRONG_INPUT_TRY_AGAIN);
        return getUserString(message, pattern);
    }

    private int getUserInteger(String message, Pattern pattern){
        informMessageToUser(message);
        readStringFromLine();
        inputString = inputString.replaceAll("[\\D]", "");
        validationResult = isValidInputInteger(inputString, pattern);
        if (validationResult) {
            return Integer.parseInt(inputString);
        }
        informMessageToUser(MESSAGE_WRONG_INPUT_TRY_AGAIN);
        return getUserInteger(message, pattern);
    }

    private Date getUserDate(String message, SimpleDateFormat dateFormat) throws ParseException {
        informMessageToUser(message);
        readStringFromLine();
        validationResult = isValidInputDate(inputString, dateFormat);
        if (validationResult) {
            return dateFormat.parse(inputString);
        }
        informMessageToUser(MESSAGE_WRONG_INPUT_TRY_AGAIN);
        return getUserDate(message,  dateFormat);
    }

    private void informMessageToUser(String messageWrongInputTryAgain) {
        printWriter.println(messageWrongInputTryAgain);
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
        return getUserInteger(
                MESSAGE_ENTER_LITERATURE_PAGES_NUMBER,
                PATTERN_FOR_PAGES);
    }

    public Date getUserDateOfIssue() throws ParseException {
        return getUserDate(
                MESSAGE_ENTER_LITERATURE_DATE,
                DATE_FORMAT);
    }
}
