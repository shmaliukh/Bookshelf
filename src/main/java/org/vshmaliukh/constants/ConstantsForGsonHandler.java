package org.vshmaliukh.constants;

import java.nio.file.Paths;

import static org.vshmaliukh.constants.ConstantsForTerminal.FILE_MODE_WORK_WITH_ONE_FILE;
import static org.vshmaliukh.constants.ConstantsForTerminal.FILE_MODE_WORK_WITH_TWO_FILES;

public final class ConstantsForGsonHandler {

    private ConstantsForGsonHandler() {
    }

    public static final int MAX_PROBLEM_FILES = 5;

    public static final String FILE_TYPE = ".json";

    public static final String SHELF_FILE_NAME_PREFIX = "shelfAll";
    public static final String BOOKS_FILE_NAME_PREFIX = "shelfBooks";
    public static final String MAGAZINES_FILE_NAME_PREFIX = "shelfMagazines";

    public static final String PROGRAM_DIRECTORY_NAME = "book_shelf";
    public static final String PROGRAM_HOME_PROPERTY = String.valueOf(Paths.get(System.getProperty("user.home"), PROGRAM_DIRECTORY_NAME));

    public static final int WORK_WITH_ONE_FILE = FILE_MODE_WORK_WITH_ONE_FILE;
    public static final int WORK_WITH_TWO_FILES = FILE_MODE_WORK_WITH_TWO_FILES;

}
