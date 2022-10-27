package org.vshmaliukh;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.vshmaliukh.services.SaveReadShelfHandler.*;

public final class BaseAppConfig {

    private BaseAppConfig(){}

    public static final String DATE_FORMAT_STR = "yyyy-MM-dd";
    public static final String HOME_PROPERTY = System.getProperty("user.home");

    public static final Map<Integer, String> TYPE_OF_WORK_MAP;

    static {
        HashMap<Integer, String> tempMap = new HashMap<>();
        tempMap.put(MODE_WORK_WITH_ONE_FILE, "work with one file");
        tempMap.put(MODE_WORK_WITH_FILE_PER_TYPE, "work with file per type files");
        tempMap.put(MODE_WORK_WITH_SQLITE, "work with SqlLite database");
        tempMap.put(MODE_WORK_WITH_MYSQL, "work with MySql database");
        tempMap.put(OLD_MODE_WORK_WITH_SQLITE, "work with old imp Sqlite database");
        tempMap.put(OLD_MODE_WORK_WITH_MYSQL, "work with old imp MySql database");
        TYPE_OF_WORK_MAP = Collections.unmodifiableMap(tempMap);
    }

}
