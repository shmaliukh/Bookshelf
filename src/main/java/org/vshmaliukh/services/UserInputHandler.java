package org.vshmaliukh.services;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.vshmaliukh.constants.ConstantsForUserInputHandler.*;

public class UserInputHandler {

    private final Scanner scanner;
    private final PrintWriter printWriter;

    private boolean validationResult;
    private String inputString = "";
    private int currentRecursionLevel;

    public UserInputHandler(Scanner scanner, PrintWriter printWriter) {
        this.scanner = scanner;
        this.printWriter = printWriter;
    }

    public <T> boolean tryAgain(T defaultValue){
        if(currentRecursionLevel < MAX_RECURSION_LEVEL){
            informMessageToUser(MESSAGE_WRONG_INPUT_TRY_AGAIN + " "
                    + (MAX_RECURSION_LEVEL - currentRecursionLevel)
                    + " more attempt(s) left");
            currentRecursionLevel++;
            return true;
        }
        currentRecursionLevel = 0;
        informMessageToUser(MESSAGE_DEFAULT_VALUE_SET + "'" + defaultValue + "'");
        return false;
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

    String getUserString(String message, Pattern pattern){
        getUserString(message);
        validationResult = isValidInputString(inputString, pattern);
        if (validationResult) {
            currentRecursionLevel = 0;
            return inputString;
        }
        if(tryAgain(DEFAULT_STRING)){
            return getUserString(message, pattern);
        }
        return DEFAULT_STRING;
    }

    int getUserInteger(String message, Pattern pattern){
        getUserString(message);
        inputString = inputString.replaceAll("[\\D]", "");
        validationResult = isValidInputInteger(inputString, pattern);
        if (validationResult) {
            currentRecursionLevel = 0;
            return Integer.parseInt(inputString);
        }
        if(tryAgain(DEFAULT_INTEGER)){
            return getUserInteger(message, pattern);
        }
        return DEFAULT_INTEGER;
    }

    Date getUserDate(String message, SimpleDateFormat dateFormat) throws ParseException {
        getUserString(message);
        validationResult = isValidInputDate(inputString, dateFormat);
        if (validationResult) {
            currentRecursionLevel = 0;
            return dateFormat.parse(inputString);
        }
        if(tryAgain(DATE_FORMAT_FOR_INPUT_HANDLER.format(DEFAULT_DATE))){
            return getUserDate(message, dateFormat);
        }
        return DEFAULT_DATE;
    }

    private void getUserString(String message) {
        informMessageToUser(message);
        readStringFromLine();
    }

    boolean getUserBoolean(String message, Pattern pattern) {
        getUserString(message);
        validationResult = isValidInputString(inputString, pattern);
        if (validationResult) {
            currentRecursionLevel = 0;
            return Boolean.parseBoolean(inputString);
        }
        if(tryAgain(DEFAULT_BOOLEAN)){
            return getUserBoolean(message, pattern);
        }
        return DEFAULT_BOOLEAN;
    }

    private void informMessageToUser(String message) {
        printWriter.println(message);
    }


}
