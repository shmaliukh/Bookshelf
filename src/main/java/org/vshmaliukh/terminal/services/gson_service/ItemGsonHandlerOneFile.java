package org.vshmaliukh.terminal.services.gson_service;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.terminal.bookshelf.literature_items.Item;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class ItemGsonHandlerOneFile extends ItemGsonHandler {

    String gsonHandlerFolderStr = "gson_handler_one_file";

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
        return userName + JSON_FILE_TYPE;
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
