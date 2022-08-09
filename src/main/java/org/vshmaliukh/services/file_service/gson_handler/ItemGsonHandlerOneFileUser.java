package org.vshmaliukh.services.file_service.gson_handler;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.shelf.literature_items.Item;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class ItemGsonHandlerOneFileUser extends ItemGsonHandlerUser {

    public String gsonHandlerFolderStr = "gson_handler_one_file";

    public ItemGsonHandlerOneFileUser(String homeDir, String userName) {
        super(homeDir, userName);
    }

    @Override
    public Path generatePathForFileHandler() {
        Path path = Paths.get(String.valueOf(generatePathForUser()), gsonHandlerFolderStr);
        createDirectoryIfNotExists(path);
        return path;
    }

    @Override
    public String generateFullFileName() {
        return userName + JSON_FILE_TYPE;
    }

    @Override
    public void saveItemList(List<Item> listToSave) {
        saveListToFile(generatePathForGsonFile(), listToSave);
    }

    @Override
    public List<Item> readItemList() {
        return readItemListFromGsonFile(generatePathForGsonFile());
    }
}
