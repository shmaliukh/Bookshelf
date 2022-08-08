package org.vshmaliukh.console_terminal.services.input_services;

import java.util.Date;
import java.util.regex.Pattern;

import static org.vshmaliukh.console_terminal.ShelfHandler.*;

public final class ConstantsForConsoleUserInputHandler {

    private ConstantsForConsoleUserInputHandler() {
    }

    public static final int MAX_RECURSION_LEVEL = 3;

    public static final Pattern PATTERN_FOR_IS_BORROWED = Pattern.compile("[yn]", Pattern.CASE_INSENSITIVE);
    public static final Pattern PATTERN_FOR_PAGES = Pattern.compile("^[1-9]+[0-9]*$");
    public static final Pattern PATTERN_FOR_TYPE_OF_WORK_WITH_FILES = Pattern.compile("^[1-3]*$");
    public static final Pattern PATTERN_FOR_USER_NAME = Pattern.compile("^(.{1,100}$)");
    public static final Pattern PATTERN_FOR_NAME = Pattern.compile("^(.{1,100}$)");
    public static final Pattern PATTERN_FOR_AUTHOR = Pattern.compile("^(.{1,100}$)");
    public static final Pattern PATTERN_FOR_PUBLISHER = Pattern.compile("^(.{1,100}$)");
    //TODO create another regular expression for 'author' and 'user name' input

    public static final String MESSAGE_ENTER_USER_NAME = "Enter user name (not empty one line text):";
    public static final String MESSAGE_ENTER_LITERATURE_NAME = "Enter literature object's name (not empty one line text):";
    public static final String MESSAGE_ENTER_LITERATURE_AUTHOR = "Enter author (not empty one line text):";
    public static final String MESSAGE_ENTER_LITERATURE_PUBLISHER = "Enter publisher (not empty one line text):";
    public static final String MESSAGE_ENTER_LITERATURE_IS_BORROWED = "Enter 'Y' if Literature object is borrowed OR 'N' if not borrowed";
    public static final String MESSAGE_ENTER_LITERATURE_PAGES_NUMBER = "Enter number of pages more than '0': (program ignores all not number symbols, max 8 symbols)";
    public static final String MESSAGE_ENTER_LITERATURE_DATE = "Enter book's date of issue 'DD-MM-YYYY' (28-06-2022),"  +
            "DD - day, MM - month, YYYY -year (numbers), use '-' between numbers";
    public static final String MESSAGE_ENTER_TYPE_OF_WORK_WITH_FILES = "Enter type number of work with files: (program ignores all not number symbols)" + System.lineSeparator() +
            FILE_MODE_WORK_WITH_ONE_FILE + " - work with one file" + System.lineSeparator() +
            FILE_MODE_WORK_WITH_FILE_PER_TYPE + " - work with file per type files";


    public static final String MESSAGE_WRONG_INPUT_TRY_AGAIN = "Wrong input. Try again.";
    public static final String MESSAGE_DEFAULT_VALUE_SET = "Default set value: ";

    public static final String DATE_FORMAT_FOR_INPUT_HANDLER = DATE_FORMAT_STR;

    public static final int DEFAULT_INTEGER = 1;
    public static final Date DEFAULT_DATE = new Date();
    public static final String DEFAULT_STRING = "-";
    public static final boolean DEFAULT_BOOLEAN = false;
}