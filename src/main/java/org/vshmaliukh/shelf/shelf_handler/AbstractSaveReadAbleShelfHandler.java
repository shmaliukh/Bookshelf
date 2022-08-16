package org.vshmaliukh.shelf.shelf_handler;

import org.vshmaliukh.services.file_service.SaveReadUserFilesHandler;
import org.vshmaliukh.shelf.Shelf;

public abstract class AbstractSaveReadAbleShelfHandler implements ShelfHandlerInterface {
    public static final int FILE_MODE_WORK_WITH_SQLLITE = 3;

    protected User user;
    protected Shelf shelf;
    protected int typeOfWorkWithFiles;

    protected SaveReadUserFilesHandler saveReadUserFilesHandler;

    protected AbstractSaveReadAbleShelfHandler() {
        this.shelf = new Shelf();
    }

    abstract void saveShelfItems();

    abstract void readShelfItems();

    public Shelf getShelf() {
        return shelf;
    }
}
