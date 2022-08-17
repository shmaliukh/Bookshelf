package org.vshmaliukh.console_terminal_app;

import org.vshmaliukh.services.file_service.SaveReadUserFilesHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.shelf_handler.BaseShelfHandler;

import java.util.List;

public abstract class SaveReadShelfHandler extends BaseShelfHandler {

    public static final int FILE_MODE_WORK_WITH_ONE_FILE = 1;
    public static final int FILE_MODE_WORK_WITH_FILE_PER_TYPE = 2;
    public static final int FILE_MODE_WORK_WITH_SQLLITE = 3;

    protected SaveReadUserFilesHandler gsonItemHandler;

    public abstract void saveShelfItems();

    public abstract void readShelfItems();

    public abstract void setUpDataSaver(String userName, int typeOfWorkWithFiles);

    public abstract <T extends Item> List<T> getSortedItemsByClass(Class<T> classType);
}
