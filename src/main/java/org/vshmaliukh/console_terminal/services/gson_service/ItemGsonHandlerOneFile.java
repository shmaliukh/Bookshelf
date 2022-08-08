package org.vshmaliukh.console_terminal.services.gson_service;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.bookshelf.literature_items.Item;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class ItemGsonHandlerOneFile extends ItemGsonHandler {

    public String gsonHandlerFolderStr = "gson_handler_one_file";

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
    public void saveItemListToFile(List<Item> listToSave) {
        saveListToFile(generatePathForGsonFile(), listToSave);
    }

    @Override
    public List<Item> readItemListFromFile() {
        return readItemListFromGsonFile(generatePathForGsonFile());
    }
}
