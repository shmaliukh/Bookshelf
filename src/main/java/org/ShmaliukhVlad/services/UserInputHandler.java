package org.ShmaliukhVlad.services;

import lombok.NoArgsConstructor;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.ShmaliukhVlad.constants.ConstantValues.DATE_FORMAT;

@NoArgsConstructor
public class UserInputHandler {

    //private final Logger LOGGER = LoggerFactory.getLogger(UserInput.class);
    //TODO cover code with LOGGER ???

    private final Pattern  patternForIsBorrowed = Pattern.compile("[yn]", Pattern.CASE_INSENSITIVE);
    private final Pattern patternForPages = Pattern.compile("^[1-9]+[0-9]*$");
    private final Pattern patternForName = Pattern.compile("^(.{1,100}$)");
    private final Pattern patternForAuthor = Pattern.compile("^(.{1,100}$)");
    //TODO create another regular expression for 'author' input

    public String getUserName(Scanner scanner, PrintWriter printWriter){
        String userName = "default";
        printWriter.println("Enter user name:");

        if(scanner.hasNextLine()){
            userName = scanner.nextLine().trim().replaceAll(" ","");
            // TODO validation
        }
        return userName;
    }

    public boolean getUserLiteratureIsBorrowed(Scanner scanner, PrintWriter printWriter) {
        printWriter.println("Enter 'Y' if Literature object is borrowed OR 'N' if not borrowed");
        if(scanner.hasNextLine()){
            String answer = scanner.nextLine().trim();
            if (isValidLiteratureIsBorrowed(answer)) {
                return true;
            }
        }
        printWriter.println("Wrong input. Try again");
        return getUserLiteratureIsBorrowed(scanner, printWriter);
    }

    public boolean isValidLiteratureIsBorrowed(String answer) {
        if (answer == null) {
            return false;
        }
        Matcher m = patternForIsBorrowed.matcher(answer);
        return m.matches();
    }

    public int getUserLiteraturePages(Scanner scanner, PrintWriter printWriter) {
        printWriter.println("Enter pages number: (program ignores all not number symbols, max 8 symbols)");
        if(scanner.hasNext()){
            String inputStr = scanner.nextLine().replaceAll("[\\D]", "").trim();
            if (inputStr.length() > 0){
                if(inputStr.length() > 8){
                    inputStr = inputStr.substring(0,8);
                }
                if (isValidLiteraturePages(inputStr)) {
                    return Integer.parseInt(inputStr);
                }
            }
        }
        printWriter.println("Wrong input for literature pages (must be bigger than '0' and not start with '0'). Try again");
        return getUserLiteraturePages(scanner, printWriter);
    }

    public boolean isValidLiteraturePages(String pages) {
        if (pages == null) {
            return false;
        }
        Matcher m = patternForPages.matcher(pages);
        return m.matches();
    }

    public String getUserLiteratureName(Scanner scanner, PrintWriter printWriter) {
        printWriter.println("Enter literature object's name (not empty one line text):");
        if(scanner.hasNextLine()){
            String name = scanner.nextLine();
            boolean validationResult = isValidLiteratureName(name);
            if (validationResult) {
                return name;
            }
        }
        printWriter.println("Wrong input for literature name. Try again");
        return getUserLiteratureName(scanner, printWriter);
    }

    public boolean isValidLiteratureName(String name) {
        if (name == null) {
            return false;
        }
        Matcher m = patternForName.matcher(name);
        return m.matches();
    }

    public String getUserLiteratureAuthor(Scanner scanner, PrintWriter printWriter) {
        printWriter.println("Enter author:");
        if(scanner.hasNextLine()){
            String author = scanner.nextLine().trim();
            boolean validationResult = isValidLiteratureName(author);
            if (validationResult) {
                return author;
            }
        }
        printWriter.println("Wrong input for literature name. Try again");
        return getUserLiteratureName(scanner, printWriter);
    }

    public boolean isValidLiteratureAuthor(String name) {
        if (name == null) {
            return false;
        }
        Matcher m = patternForAuthor.matcher(name);
        return m.matches();
    }

    public Date getUserDateOfIssue(Scanner scanner, PrintWriter printWriter) throws ParseException {
        printWriter.println("Enter book's date of issue 'DD-MM-YYYY' (28-06-2022),\n" +
        "DD - day, MM - month, YYYY -year (numbers), use '-' between numbers");
        String dateStr;
        DATE_FORMAT.setLenient(false);
        if(scanner.hasNextLine()){
            dateStr = scanner.nextLine().trim();
            if(isValidLiteratureDate(dateStr)){
                return DATE_FORMAT.parse(dateStr);
            }
        }
        printWriter.println("Wrong input. Try again.");
        return getUserDateOfIssue(scanner, printWriter);
    }

    public boolean isValidLiteratureDate(String input) {
        if(input == null){
            return false;
        }
        DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(input);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
