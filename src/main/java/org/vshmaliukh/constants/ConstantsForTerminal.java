package org.vshmaliukh.constants;

import java.text.SimpleDateFormat;

public final class ConstantsForTerminal {

    private ConstantsForTerminal() {
    }

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public static final int FILE_MODE_NO_WORK_WITH_FILES = 1;
    public static final int FILE_MODE_WORK_WITH_ONE_FILE = 2;
    public static final int FILE_MODE_WORK_WITH_FILE_PER_TYPE = 3;
    public static final int DEFAULT_MODE_WORK_WITH_FILES = FILE_MODE_NO_WORK_WITH_FILES;

    public static final int WRONG_INPUT = -1;
}
