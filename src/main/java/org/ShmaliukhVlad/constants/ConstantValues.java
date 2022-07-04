package org.ShmaliukhVlad.constants;

import java.text.SimpleDateFormat;

/**
 * @author ShmaliukhVlad
 * @version 1.0.0
 * This is a special abstract class which contains all constants value in project
 */
public final class ConstantValues {

    private ConstantValues(){}

    public static final int ADD_NEW_LITERATURE = 1;
    public static final int DELETE_LITERATURE = 2;
    public static final int BORROW_LITERATURE = 3;
    public static final int ARRIVE_LITERATURE = 4;
    public static final int PRINT_SORTED_BOOKS = 5;
    public static final int PRINT_SORTED_MAGAZINES = 6;
    public static final int PRINT_SHELF = 9;
    public static final int EXIT = 0;

    public static final int ADD_CUSTOM_MAGAZINE = 1;
    public static final int ADD_CUSTOM_BOOK = 2;
    public static final int ADD_RANDOM_MAGAZINE = 3;
    public static final int ADD_RANDOM_BOOK = 4;

    public static final int SORT_BOOKS_BY_NAME = 1;
    public static final int SORT_BOOKS_BY_AUTHOR = 2;
    public static final int SORT_BOOKS_BY_PAGES_NUMBER = 3;
    public static final int SORT_BOOKS_BY_DATE_OF_ISSUE = 4;

    public static final int SORT_MAGAZINES_BY_NAME = 1;
    public static final int SORT_MAGAZINES_BY_PAGES_NUMBER = 2;

    public static final int WRONG_INPUT = -1;

    public static final String FILE_NAME = "\\shelfGson.json";
    public static final String SYSTEM_FILE_PATH = System.getProperty("user.home");

    public static final int SAVE_READ_ONE_FILE = 1;
    public static final int SAVE_READ_TWO_FILES = 2;

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
}
