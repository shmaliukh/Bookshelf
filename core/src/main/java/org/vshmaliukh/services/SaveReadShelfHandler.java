package org.vshmaliukh.services;

import lombok.NoArgsConstructor;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.shelf_handler.AbstractShelfHandler;

import java.util.List;

@NoArgsConstructor
public abstract class SaveReadShelfHandler extends AbstractShelfHandler {

    public static final int MODE_WORK_WITH_ONE_FILE = 1;
    public static final int MODE_WORK_WITH_FILE_PER_TYPE = 2;
    public static final int MODE_WORK_WITH_SQLITE = 3;
    public static final int MODE_WORK_WITH_MYSQL = 4;
    public static final int OLD_MODE_WORK_WITH_SQLITE = 5;
    public static final int OLD_MODE_WORK_WITH_MYSQL = 6;

    protected SaveReadShelfHandler(String userName, int typeOfWorkWithFiles) {
        setUpDataService(userName, typeOfWorkWithFiles);
    }

    public abstract void saveShelfItems();

    public abstract void readShelfItems();

    public abstract void setUpDataService(String userName, int typeOfWorkWithFiles);

    public abstract <T extends Item> List<T> getSortedItemsByClass(Class<T> classType);
}
