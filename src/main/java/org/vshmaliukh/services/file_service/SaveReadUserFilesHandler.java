package org.vshmaliukh.services.file_service;

import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class SaveReadUserFilesHandler extends UserFilesHandler {

    protected SaveReadUserFilesHandler(String homeDir, String userName) {
        super(homeDir, userName);
    }

    protected abstract String generateFullFileName();

    protected abstract Path generatePathForFileHandler();

    public abstract void saveItemList(List<Item> listToSave);

    public abstract List<Item> readItemList();

    public abstract <T extends Item> List<T> readItemsByClass(Class<T> classType);
}
