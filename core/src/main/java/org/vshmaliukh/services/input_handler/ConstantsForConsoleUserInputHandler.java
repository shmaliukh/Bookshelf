package org.vshmaliukh.services.input_handler;

import org.vshmaliukh.BaseAppConfig;

import java.util.Date;

import static org.vshmaliukh.BaseAppConfig.TYPE_OF_WORK_MAP;

public final class ConstantsForConsoleUserInputHandler {

    private ConstantsForConsoleUserInputHandler() {
    }

    public static final int MAX_RECURSION_LEVEL = 3;

    //TODO create another regular expression for 'author' and 'user name' input

    public static final String MESSAGE_ENTER_USER_NAME = "Enter user name (not empty one line text):";
    public static final String MESSAGE_ENTER_LITERATURE_NAME = "Enter literature object's name (not empty one line text):";
    public static final String MESSAGE_ENTER_LITERATURE_AUTHOR = "Enter author (not empty one line text):";
    public static final String MESSAGE_ENTER_LITERATURE_PUBLISHER = "Enter publisher (not empty one line text):";
    public static final String MESSAGE_ENTER_LITERATURE_IS_BORROWED = "Enter 'Y' if Literature object is borrowed OR 'N' if not borrowed";
    public static final String MESSAGE_ENTER_LITERATURE_PAGES_NUMBER = "Enter number of pages more than '0': (program ignores all not number symbols, max 8 symbols)";
    public static final String MESSAGE_ENTER_LITERATURE_DATE = "Enter book's date of issue 'DD-MM-YYYY' (28-06-2022)," + System.lineSeparator() +
            "DD - day, MM - month, YYYY -year (numbers), use '-' between numbers";
    public static String MESSAGE_ENTER_TYPE_OF_WORK_WITH_FILES = "Enter type number of work with files: (program ignores all not number symbols)" + System.lineSeparator();

    static { // todo refactor
        TYPE_OF_WORK_MAP.forEach((k, v) -> MESSAGE_ENTER_TYPE_OF_WORK_WITH_FILES += (k + " - " + v + System.lineSeparator()));
        MESSAGE_ENTER_TYPE_OF_WORK_WITH_FILES = MESSAGE_ENTER_TYPE_OF_WORK_WITH_FILES.trim();
    }

    public static final String MESSAGE_WRONG_INPUT_TRY_AGAIN = "Wrong input. Try again.";
    public static final String MESSAGE_DEFAULT_VALUE_SET = "Default set value: ";

    public static final String DATE_FORMAT_FOR_INPUT_HANDLER = BaseAppConfig.DATE_FORMAT_STR;

    public static final int DEFAULT_INT = 1;
    public static final Date DEFAULT_DATE = new Date();
    public static final String DEFAULT_STRING = "-";
    public static final boolean DEFAULT_BOOLEAN = false;
}