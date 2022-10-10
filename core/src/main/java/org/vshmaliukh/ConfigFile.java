package org.vshmaliukh;

import java.util.HashMap;
import java.util.Map;

import static org.vshmaliukh.services.SaveReadShelfHandler.*;

public final class ConfigFile {

    private ConfigFile(){}

    public static final String DATE_FORMAT_STR = "yyyy-MM-dd";
    public static final String HOME_PROPERTY = System.getProperty("user.home");

    public static final Map<Integer, String> typeOfWorkMap = new HashMap<>();

    static {
        typeOfWorkMap.put(MODE_WORK_WITH_ONE_FILE, "work with one file");
        typeOfWorkMap.put(MODE_WORK_WITH_FILE_PER_TYPE, "work with file per type files");
        typeOfWorkMap.put(MODE_WORK_WITH_SQLITE, "work with SqlLite database");
        typeOfWorkMap.put(MODE_WORK_WITH_MYSQL, "work with MySql database");
    }


}
