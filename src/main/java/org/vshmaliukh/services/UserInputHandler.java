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
    private String inputString;

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

    public String getUserName(Scanner scanner, PrintWriter printWriter){
        String userName = "default";
        printWriter.println("Enter user name:");

        if(scanner.hasNextLine()){
            userName = scanner.nextLine().trim().replace(" ","");
            // TODO validation
        }
        return userName;
    }

    public boolean getUserLiteratureIsBorrowed(Scanner scanner, PrintWriter printWriter) {
        printWriter.println("Enter 'Y' if Literature object is borrowed OR 'N' if not borrowed");
        if(scanner.hasNextLine()){
            inputString = scanner.nextLine().trim();
            validationResult = isValidInputString(inputString, PATTERN_FOR_IS_BORROWED);
            if (isValidInputString(inputString, PATTERN_FOR_IS_BORROWED)) {
                return true;
            }
        }
        printWriter.println("Wrong input. Try again");
        return getUserLiteratureIsBorrowed(scanner, printWriter);
    }



    public int getUserLiteraturePages(Scanner scanner, PrintWriter printWriter) {
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
        return getUserLiteraturePages(scanner, printWriter);
    }

    public String getUserLiteratureName(Scanner scanner, PrintWriter printWriter) {
        printWriter.println("Enter literature object's name (not empty one line text):");
        if(scanner.hasNextLine()){
            inputString = scanner.nextLine();
            validationResult = isValidInputString(inputString, PATTERN_FOR_NAME);
            if (validationResult) {
                return inputString;
            }
        }
        printWriter.println("Wrong input for literature name. Try again");
        return getUserLiteratureName(scanner, printWriter);
    }

    public String getUserLiteratureAuthor(Scanner scanner, PrintWriter printWriter) {
        printWriter.println("Enter author:");
        if(scanner.hasNextLine()){
            inputString = scanner.nextLine().trim();
            validationResult = isValidInputString(inputString, PATTERN_FOR_AUTHOR);
            if (validationResult) {
                return inputString;
            }
        }
        printWriter.println("Wrong input for literature name. Try again");
        return getUserLiteratureName(scanner, printWriter);
    }

    public Date getUserDateOfIssue(Scanner scanner, PrintWriter printWriter) throws ParseException {
        printWriter.println("Enter book's date of issue 'DD-MM-YYYY' (28-06-2022),\n" +
        "DD - day, MM - month, YYYY -year (numbers), use '-' between numbers");
        DATE_FORMAT.setLenient(false); // is necessary???
        if(scanner.hasNextLine()){
            inputString = scanner.nextLine().trim();
            validationResult = isValidLiteratureDate(inputString);
            if(isValidLiteratureDate(inputString)){
                return DATE_FORMAT.parse(inputString);
            }
        }
        printWriter.println("Wrong input. Try again.");
        return getUserDateOfIssue(scanner, printWriter);
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
