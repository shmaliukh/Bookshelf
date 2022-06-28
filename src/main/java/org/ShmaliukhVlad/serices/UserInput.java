package org.ShmaliukhVlad.serices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.Year;
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

    private UserInput() {
    }

    public static boolean getUserLiteratureIsBorrowed(Scanner scanner, PrintStream printStream) {
        printStream.println("Enter 'Y' if Literature object is borrowed OR 'N' if no borrowed");
        String answer = scanner.nextLine();
        if (isValidLiteratureIsBorrowed(answer)) {
            return true;
        }
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
        String pages = scanner.nextLine();
        if (isValidLiteraturePages(pages.trim())) {
            return Integer.getInteger(pages);
        }
        printStream.println("Wrong input for literature pages. Try again");
        return getUserLiteraturePages(scanner, printStream);
    }

    public static boolean isValidLiteraturePages(String pages) {
        if (pages == null) {
            return false;
        }
        Matcher m = patternForPages.matcher(pages.trim());
        return m.matches();
    }

    public static String getUserLiteratureName(Scanner scanner, PrintStream printStream) {
        printStream.println("Enter name:");
        String name = scanner.nextLine();
        LOGGER.debug("name = " + name);
        boolean validationResult = isValidLiteratureName(name);
        LOGGER.debug("validationResult: " + validationResult);
        if (validationResult) {
            return name.trim();
        }
        printStream.println("Wrong input for literature name. Try again");
        return getUserLiteratureName(scanner, printStream);
    }

    public static boolean isValidLiteratureName(String name) {
        if (name == null) {
            return false;
        }
        Matcher m = patternForName.matcher(name.trim());
        return m.matches();
    }

    public static String getUserLiteratureAuthor(Scanner scanner, PrintStream printStream) {
        printStream.println("Enter author: (Names must start with a capital letter)");
        String author = scanner.nextLine();
        LOGGER.debug("author = " + author);
        boolean validationResult = isValidLiteratureName(author);
        LOGGER.debug("validationResult: " + validationResult);
        if (validationResult) {
            return author.trim();
        }
        printStream.println("Wrong input for literature name. Try again");
        return getUserLiteratureName(scanner, printStream);
    }

    public static boolean isValidLiteratureAuthor(String name) {
        if (name == null) {
            return false;
        }
        Matcher m = patternForAuthor.matcher(name.trim());
        return m.matches();
    }

    public static Date getUserDateOfIssue(Scanner scanner, PrintStream printStream) {
        int year = getUserYear(scanner, printStream);
        int month = getUserMonth(scanner, printStream);
        int day = getUserDay(scanner, printStream);
        return new Date(year,month,day); // TODO
    }

    public static int getUserMonth(Scanner scanner, PrintStream printStream) {
        printStream.println("Enter the month number of issue:");
        if(scanner.hasNextInt()) {
            int input = scanner.nextInt();
            if (input >= 1 && input <= 12) {
                return input;
            }
        }
        printStream.println("Wrong input month (from 1 to 12) try again");
        return getUserMonth(scanner, printStream);
    }

    //TODO test
    public static int getUserDay(Scanner scanner, PrintStream printStream) {
        printStream.println("Enter the number of month of issue:");
        if(scanner.hasNextInt()){
            int input = scanner.nextInt();
            if(isValidLiteratureDay(input)){
                return input;
            }
        }
        printStream.println("Wrong input number of day (from 1 to 31) try again");
        return getUserDay(scanner, printStream);
    }

    public static boolean isValidLiteratureYear(int input) {
        return input >= Year.MIN_VALUE && input <= Year.now().getValue();
    }

    public static boolean isValidLiteratureMonth(int input) {
        return input >= 1 && input <= 12;
    }

    public static boolean isValidLiteratureDay(int input) {
        return input >= 1 && input <= 31; // TODO validation for day input
    }

    public static int getUserYear(Scanner scanner, PrintStream printStream) {
        printStream.println("Enter the year of issue:");
        if(scanner.hasNextInt()){
            int input = scanner.nextInt();
            if(input >= 1 && input <= LocalDate.now().getYear()){
                return input;
            }
        }
        printStream.println("Wrong input year (from 1 to current year) try again");
        return getUserYear(scanner, printStream);
    }
}
