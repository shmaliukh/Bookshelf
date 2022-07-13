package org.vshmaliukh.constants;

import java.util.regex.Pattern;

public final class ConstantsForUserInputHandler {

    private ConstantsForUserInputHandler(){}

    public static final Pattern PATTERN_FOR_IS_BORROWED = Pattern.compile("[yn]", Pattern.CASE_INSENSITIVE);
    public static final Pattern PATTERN_FOR_PAGES = Pattern.compile("^[1-9]+[0-9]*$");
    public static final Pattern PATTERN_FOR_USER_NAME = Pattern.compile("^(.{1,100}$)");
    public static final Pattern PATTERN_FOR_NAME = Pattern.compile("^(.{1,100}$)");
    public static final Pattern PATTERN_FOR_AUTHOR = Pattern.compile("^(.{1,100}$)");
    //TODO create another regular expression for 'author' and 'user name' input

    public static final String MESSAGE_ENTER_USER_NAME = "Enter user name (not empty one line text):";
    public static final String MESSAGE_ENTER_LITERATURE_NAME = "Enter literature object's name (not empty one line text):";
    public static final String MESSAGE_ENTER_LITERATURE_AUTHOR = "Enter author (not empty one line text):";
    public static final String MESSAGE_ENTER_LITERATURE_IS_BORROWED = "Enter 'Y' if Literature object is borrowed OR 'N' if not borrowed";
    public static final String MESSAGE_ENTER_LITERATURE_PAGES_NUMBER = "Enter pages number: (program ignores all not number symbols, max 8 symbols)";
    public static final String MESSAGE_ENTER_LITERATURE_DATE = "Enter book's date of issue 'DD-MM-YYYY' (28-06-2022),\n" +
            "DD - day, MM - month, YYYY -year (numbers), use '-' between numbers";

    public static final String MESSAGE_WRONG_INPUT_FOR_LITERATURE_PAGES = "Wrong input for literature pages (must be bigger than '0' and not start with '0'). Try again";
    public static final String MESSAGE_WRONG_INPUT_TRY_AGAIN = "Wrong input. Try again";
}
