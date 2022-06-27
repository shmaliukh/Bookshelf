package org.ShmaliukhVlad.bookshelf.serices;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class UserInput {

    public static boolean getUserLiteratureIsBorrowed(Scanner scanner, PrintStream printStream) {
        printStream.println("Enter 'Y' if Literature object is borrowed OR 'N' if no borrowed");
        String answer = scanner.nextLine();
        if(isValidLiteratureIsBorrowed(answer.trim())){
            return true;
        }
        printStream.println("Wrong input. Try again");
        return getUserLiteratureIsBorrowed(scanner, printStream);
    }

    public static boolean isValidLiteratureIsBorrowed(String answer) {
        String regex = "[yn]";
        Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        if (answer == null) {
            return false;
        }
        Matcher m = p.matcher(answer);
        return m.matches();
    }

    public static int getUserLiteraturePages(Scanner scanner, PrintStream printStream) {
        printStream.println("Enter pages number: (number without spaces which bigger than 0)");
        String pages = scanner.nextLine();
        if(isValidLiteraturePages(pages.trim())){
            return Integer.getInteger(pages);
        }
        printStream.println("Wrong input for literature pages. Try again");
        return getUserLiteraturePages(scanner, printStream);
    }

    public static boolean isValidLiteraturePages(String pages){
        String regex = "/\\d+/g";
        Pattern p = Pattern.compile(regex);
        if (pages == null) {
            return false;
        }
        Matcher m = p.matcher(pages);
        return m.matches();
    }

    public static String getUserLiteratureName(Scanner scanner, PrintStream printStream) {
        printStream.println("Enter name:");
        String name = scanner.nextLine();
        if(isValidLiteratureName(name)){
            return name.trim();
        }
        printStream.println("Wrong input for literature name. Try again");
        return getUserLiteratureName(scanner, printStream);
    }

    public static boolean isValidLiteratureName(String name){
        String regex = "^( .{1,100}$)";
        Pattern p = Pattern.compile(regex);
        if (name == null) {
            return false;
        }
        Matcher m = p.matcher(name);
        return m.matches();
    }
}
