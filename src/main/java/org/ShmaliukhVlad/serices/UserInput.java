package org.ShmaliukhVlad.serices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInput {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInput.class);

    private static final Pattern  patternForIsBorrowed = Pattern.compile("[yn]", Pattern.CASE_INSENSITIVE);
    private static final Pattern patternForPages = Pattern.compile("^[1-9]+[0-9]*$");
    private static final Pattern patternForName = Pattern.compile("^(.{1,100}$)");
    private static final Pattern patternForAuthor = Pattern.compile("^(.{1,100}$)"); //TODO some regular expression
    //private static final Pattern patternForDate = Pattern.compile("(^([0][1-9]|[12][0-9]|[3][0-1])-(0[1-9]|1[0-2])-{4}$)");
    private static final Pattern patternForDate = Pattern.compile("(^(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-(\\d{4})$)");
    //private static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private UserInput() {
    }

    public static boolean getUserLiteratureIsBorrowed(Scanner scanner, PrintStream printStream) {
        printStream.println("Enter 'Y' if Literature object is borrowed OR 'N' if not borrowed");
        //if(scanner.hasNextLine()){
            String answer = scanner.nextLine().trim();
            if (isValidLiteratureIsBorrowed(answer)) {
                return true;
            }
        //}
        printStream.println("Wrong input. Try again");
        return getUserLiteratureIsBorrowed(scanner, printStream);
    }

    public static boolean isValidLiteratureIsBorrowed(String answer) {
        if (answer == null) {
            return false;
        }
        Matcher m = patternForIsBorrowed.matcher(answer);
        return m.matches();
    }

    public static int getUserLiteraturePages(Scanner scanner, PrintStream printStream) {
        printStream.println("Enter pages number: (number without spaces which bigger than 0)");
        if(scanner.hasNextInt()){
            int pages = scanner.nextInt();
            if (isValidLiteraturePages(String.valueOf(pages))) {
                return pages;
            }
        }
        printStream.println("Wrong input for literature pages. Try again");
        return getUserLiteraturePages(scanner, printStream);
    }

    public static boolean isValidLiteraturePages(String pages) {
        if (pages == null) {
            return false;
        }
        Matcher m = patternForPages.matcher(pages);
        return m.matches();
    }

    public static String getUserLiteratureName(Scanner scanner, PrintStream printStream) {
        printStream.println("Enter name:");
        //if(scanner.hasNextLine()){
            String name = scanner.nextLine().trim();
            LOGGER.debug("name = " + name); //TODO loggers
            boolean validationResult = isValidLiteratureName(name);
            LOGGER.debug("validationResult: " + validationResult);
            if (validationResult) {
                return name;
            }
        //}
        printStream.println("Wrong input for literature name. Try again");
        return getUserLiteratureName(scanner, printStream);
    }

    public static boolean isValidLiteratureName(String name) {
        if (name == null) {
            return false;
        }
        Matcher m = patternForName.matcher(name);
        return m.matches();
    }

    public static String getUserLiteratureAuthor(Scanner scanner, PrintStream printStream) {
        printStream.println("Enter author:");
        //if(scanner.hasNextLine()){
            String author = scanner.nextLine().trim();
            LOGGER.debug("author = " + author);
            boolean validationResult = isValidLiteratureName(author);
            LOGGER.debug("validationResult: " + validationResult);
            if (validationResult) {
                return author;
            }
        //}
        printStream.println("Wrong input for literature name. Try again");
        return getUserLiteratureName(scanner, printStream);
    }

    public static boolean isValidLiteratureAuthor(String name) {
        if (name == null) {
            return false;
        }
        Matcher m = patternForAuthor.matcher(name);
        return m.matches();
    }

    public static Date getUserDateOfIssue(Scanner scanner, PrintStream printStream) {
        printStream.println("Enter book's date of issue 'DD-MM-YYYY' (28-06-2022),\n" +
        "DD - day, MM - month, YYYY -year (numbers), use '-' between numbers");// TODO write instruction for user date input
        String dateStr;
        //if(scanner.hasNextLine()){
            dateStr = scanner.nextLine().trim();
            if(isValidLiteratureDate(dateStr)){
                return new Date(dateStr);
            }
        //}
        printStream.println("Wrong input. Try again.");
        return getUserDateOfIssue(scanner, printStream);
    }

    public static boolean isValidLiteratureDate(String input) {
        if(input == null){
            return false;
        }
        Matcher m = patternForDate.matcher(input);
//        try {
//            //Date.parse(input);
//            Date date = dateFormat.parse(input);
//            LOGGER.debug("input date string {}, parsed date {}", input, dateFormat.parse(input));
//        } catch (ParseException e) {
//            //throw new RuntimeException(e);
//            return false;
//        }
        return m.matches();

    }
}
