package org.vshmaliukh.constants;

public final class ConstantsForGsonHandler {

    private ConstantsForGsonHandler(){}

    public static final String FILE_TYPE = ".json";

    public static final String SHELF_FILE_NAME_PREFIX = "ShelfAll";
    public static final String BOOKS_FILE_NAME_PREFIX = "ShelfBooks";
    public static final String MAGAZINES_FILE_NAME_PREFIX = "ShelfMagazines";

    public static final String HOME_PROPERTY = System.getProperty("user.home");

    public static final int WORK_WITH_ONE_FILE = 1;
    public static final int WORK_WITH_TWO_FILES = 2;
}
