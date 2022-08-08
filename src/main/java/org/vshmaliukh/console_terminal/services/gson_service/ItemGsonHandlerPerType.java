package org.vshmaliukh.console_terminal.services.gson_service;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.bookshelf.literature_items.Item;
import org.vshmaliukh.bookshelf.literature_items.ItemHandlerProvider;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class ItemGsonHandlerPerType extends ItemGsonHandler {

    String gsonHandlerFolderStr = "gson_handler_per_type";
    String typeStr;

    public ItemGsonHandlerPerType(String homeDir, String userName) {
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
        return userName + "_" + typeStr + JSON_FILE_TYPE;
    }

    @Override
    public void saveItemListToFile(List<Item> listToSave) {
        for (Class<? extends Item> classType : getClassTypes()) {
            typeStr = classType.getSimpleName();
            List<? extends Item> listPerType = listToSave.stream()
                    .filter(o -> o.getClass().equals(classType))
                    .collect(Collectors.toList());
            saveListToFile(generatePathForGsonFile(), listPerType);
        }
    }

    @Override
    public List<Item> readItemListFromFile() {
        List<Item> resultList = new ArrayList<>();
        for (Class<? extends Item> typeName : ItemHandlerProvider.uniqueTypeNames) {
            typeStr = typeName.getSimpleName();
            resultList.addAll(readItemListFromGsonFile(generatePathForGsonFile()));
        }
        return resultList;
    }

    private Set<Class<? extends Item>> getClassTypes() {
        return ItemHandlerProvider.uniqueTypeNames;
    }
}
