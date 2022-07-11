package org.vshmaliukh.constants;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ConstantsForGsonHandler {

    private ConstantsForGsonHandler(){}

    public static final String FILE_TYPE = ".json";

    public static final String SHELF_FILE_NAME_PREFIX = "ShelfAll";
    public static final String BOOKS_FILE_NAME_PREFIX = "ShelfBooks";
    public static final String MAGAZINES_FILE_NAME_PREFIX = "ShelfMagazines";

    public static final String HOME_PROPERTY = System.getProperty("user.home");
}
