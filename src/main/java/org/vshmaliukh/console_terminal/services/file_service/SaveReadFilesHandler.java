package org.vshmaliukh.console_terminal.services.file_service;

import org.vshmaliukh.bookshelf.literature_items.Item;

import java.nio.file.Path;
import java.util.List;

public abstract class SaveReadFilesHandler extends FilesHandler {

    protected SaveReadFilesHandler(String homeDir, String userName) {
        super(homeDir, userName);
    }

    abstract String generateFullFileName();

    abstract Path generatePathForGson();

    public abstract void saveItemListToFile(List<Item> listToSave);

    public abstract List<Item> readItemListFromFile();
}
