package org.vshmaliukh.services.GsonService;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ConstantsForGsonFiles {

    private ConstantsForGsonFiles(){}

    public static final String FILE_TYPE = ".json";

    public static final String SHELF_FILE_NAME = "shelfAll";
    public static final String BOOKS_FILE_NAME = "shelfBooks";
    public static final String MAGAZINES_FILE_NAME = "shelfMagazines";

    public static final Path SHELF_GSON_FILE_PATH = Paths.get(System.getProperty("user.home"),SHELF_FILE_NAME,FILE_TYPE);
    public static final Path BOOKS_GSON_FILE_PATH = Paths.get(System.getProperty("user.home"),BOOKS_FILE_NAME,FILE_TYPE);
    public static final Path MAGAZINES_GSON_FILE_PATH = Paths.get(System.getProperty("user.home"),MAGAZINES_FILE_NAME,FILE_TYPE);

    public static final File SHELF_GSON_FILE = new File(String.valueOf(SHELF_GSON_FILE_PATH));
    public static final File BOOKS_GSON_FILE = new File(String.valueOf(BOOKS_GSON_FILE_PATH));
    public static final File MAGAZINES_GSON_FILE = new File(String.valueOf(MAGAZINES_GSON_FILE_PATH));

}
