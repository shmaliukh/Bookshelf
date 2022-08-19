package org.vshmaliukh.console_terminal_app;

import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.shelf_handler.AbstractShelfHandler;

import java.util.List;

public abstract class SaveReadShelfHandler extends AbstractShelfHandler {

    public static final int MODE_WORK_WITH_ONE_FILE = 1;
    public static final int MODE_WORK_WITH_FILE_PER_TYPE = 2;
    public static final int MODE_WORK_WITH_SQLLITE = 3;
    public static final int MODE_WORK_WITH_MYSQL = 4;

    protected SaveReadShelfHandler(String userName, int typeOfWorkWithFiles) {
        setUpDataSaver(userName, typeOfWorkWithFiles);
    }

    public abstract void saveShelfItems();

    public abstract void readShelfItems();

    public abstract void setUpDataSaver(String userName, int typeOfWorkWithFiles);

    public abstract <T extends Item> List<T> getSortedItemsByClass(Class<T> classType);
}
