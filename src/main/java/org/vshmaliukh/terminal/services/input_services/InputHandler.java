package org.vshmaliukh.terminal.services.input_services;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.web.ScannerWrapper;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.vshmaliukh.terminal.services.input_services.ConstantsForUserInputHandler.*;

@Slf4j
public abstract class InputHandler {

    private final ScannerWrapper scanner;
    private final PrintWriter printWriter;

    private boolean validationResult;
    private String inputString = "";
    private int currentRecursionLevel;

    protected InputHandler(ScannerWrapper scanner, PrintWriter printWriter) {
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
        if (inputStr == null || inputStr.length() > 8 || inputStr.length() == 0) {
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
            String trim = scanner.nextLine().trim();
            if(trim.equals("")){
                scanner.close();
            }
            inputString = trim;
        }
    }

    protected  String getUserString(String message, Pattern pattern){
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

    protected  int getUserInteger(String message, Pattern pattern){
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

    protected Date getUserDate(String message, SimpleDateFormat dateFormat){
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
        if(tryAgain(new SimpleDateFormat(DATE_FORMAT_FOR_INPUT_HANDLER).format(DEFAULT_DATE))){
            return getUserDate(message, dateFormat);
        }
        return DEFAULT_DATE;
    }

    private void getUserString(String message) {
        informMessageToUser(message);
        readStringFromLine();
    }

    protected boolean getUserBoolean(String message, Pattern pattern) {
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
        printWriter.print(message + System.lineSeparator());
    }
}
