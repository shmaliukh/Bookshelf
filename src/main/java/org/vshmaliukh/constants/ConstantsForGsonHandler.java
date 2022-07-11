package org.vshmaliukh.constants;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ConstantsForGsonHandler {

    private ConstantsForGsonHandler(){}

    public static final String FILE_TYPE = ".json";

    public static final String SHELF_FILE_NAME = "ShelfAll";
    public static final String BOOKS_FILE_NAME = "ShelfBooks";
    public static final String MAGAZINES_FILE_NAME = "ShelfMagazines";

    public static final String HOME_PROPERTY = System.getProperty("user.home");

    public static final Path SHELF_GSON_FILE_PATH = Paths.get(HOME_PROPERTY);
    public static final Path BOOKS_GSON_FILE_PATH = Paths.get(HOME_PROPERTY, BOOKS_FILE_NAME, FILE_TYPE);
    public static final Path MAGAZINES_GSON_FILE_PATH = Paths.get(HOME_PROPERTY, MAGAZINES_FILE_NAME, FILE_TYPE);

    public static final File SHELF_GSON_FILE = new File(String.valueOf(SHELF_GSON_FILE_PATH));
    public static final File BOOKS_GSON_FILE = new File(String.valueOf(BOOKS_GSON_FILE_PATH));
    public static final File MAGAZINES_GSON_FILE = new File(String.valueOf(MAGAZINES_GSON_FILE_PATH));

}
