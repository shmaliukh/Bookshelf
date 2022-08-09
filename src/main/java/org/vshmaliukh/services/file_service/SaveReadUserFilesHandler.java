package org.vshmaliukh.services.file_service;

import org.vshmaliukh.shelf.literature_items.Item;

import java.nio.file.Path;
import java.util.List;

public abstract class SaveReadUserFilesHandler extends UserFilesHandler {

    protected SaveReadUserFilesHandler(String homeDir, String userName) {
        super(homeDir, userName);
    }

    protected abstract String generateFullFileName();

    protected abstract Path generatePathForGson();

    public abstract void saveItemListToFile(List<Item> listToSave);

    public abstract List<Item> readItemListFromFile();
}
