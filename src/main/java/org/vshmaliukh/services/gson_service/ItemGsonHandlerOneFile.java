package org.vshmaliukh.services.gson_service;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.bookshelf.bookshelfObjects.Item;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class ItemGsonHandlerOneFile extends ItemGsonHandler {

    String gsonHandlerFolderStr = "gson_item_one_file";

    public ItemGsonHandlerOneFile(String homeDir, String userName) {
        super(homeDir, userName);
    }

    @Override
    public Path generatePathForGson() {
        Path path = Paths.get(String.valueOf(generatePathForUser()), gsonHandlerFolderStr);
        createDirectoryIfNotExists(path);
        return path;
    }

    @Override
    public String generateFullFileName() {
        return userName + gsonFileType;
    }

    @Override
    public void saveToFile(List<Item> listToSave) {
        saveListToFile(new File(String.valueOf(generatePathForGsonFile())), listToSave);
    }

    @Override
    public List<Item> readListFromFile() {
        return readItemListFromGsonFile(new File(String.valueOf(generatePathForGsonFile())));
    }
}