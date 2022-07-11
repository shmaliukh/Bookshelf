package org.vshmaliukh.constants;

import java.text.SimpleDateFormat;

/**
 * @author ShmaliukhVlad
 * @version 1.0.0
 * This is a special abstract class which contains all constants value in project
 */
public final class ConstantValues {

    private ConstantValues(){}


    public static final String FILE_TYPE = ".json";
    public static final String FILE_NAME = "/shelfGson";
    public static final String SYSTEM_FILE_PATH = System.getProperty("user.home");

    public static final int SAVE_READ_ONE_FILE = 1;
    public static final int SAVE_READ_TWO_FILES = 2;


}
