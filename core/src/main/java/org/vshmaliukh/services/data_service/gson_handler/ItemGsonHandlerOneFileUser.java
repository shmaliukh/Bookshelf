package org.vshmaliukh.services.data_service.gson_handler;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.shelf.literature_items.Item;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ItemGsonHandlerOneFileUser extends ItemGsonHandlerHandler {

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

    @Override
    public <T extends Item> List<T> readItemsByClass(Class<T> classType) {
        return readItemList().stream()
                .filter(o -> o.getClass().equals(classType))
                .map(classType::cast)
                .collect(Collectors.toList());
    }
}
