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

    private String getUserString(String message, Pattern pattern, String wrongInputMessage){
        printWriter.println(message);
        readStringFromLine();
        validationResult = isValidInputString(inputString, pattern);
        if (validationResult) {
            return inputString;
        }
        printWriter.println(wrongInputMessage);
        return getUserString(message, pattern, wrongInputMessage);
    }

    public String getUserName(){
        return getUserString(
                "Enter user name:",
                PATTERN_FOR_USER_NAME,
                "Wrong input for name. Try again");
    }

    public String getUserLiteratureName() {
        return getUserString(
                "Enter literature object's name (not empty one line text):",
                PATTERN_FOR_NAME,
                "Wrong input for literature name. Try again");
    }

    public String getUserLiteratureAuthor() {
        return getUserString(
                "Enter author:",
                PATTERN_FOR_AUTHOR,
                "Wrong input for literature author. Try again");
    }

    public boolean getUserLiteratureIsBorrowed() {
        return Boolean.getBoolean(getUserString(
                "Enter 'Y' if Literature object is borrowed OR 'N' if not borrowed",
                PATTERN_FOR_IS_BORROWED,
                "Wrong input. Try again"));
    }

    public int getUserLiteraturePages() {
        printWriter.println("Enter pages number: (program ignores all not number symbols, max 8 symbols)");
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
        printWriter.println("Wrong input for literature pages (must be bigger than '0' and not start with '0'). Try again");
        return getUserLiteraturePages();
    }

    public Date getUserDateOfIssue() throws ParseException {
        printWriter.println("Enter book's date of issue 'DD-MM-YYYY' (28-06-2022),\n" +
        "DD - day, MM - month, YYYY -year (numbers), use '-' between numbers");
        if(scanner.hasNextLine()){
            inputString = scanner.nextLine().trim();
            validationResult = isValidLiteratureDate(inputString);
            if(isValidLiteratureDate(inputString)){
                DATE_FORMAT.setLenient(false);
                return DATE_FORMAT.parse(inputString);
            }
        }
        printWriter.println("Wrong input. Try again.");
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
