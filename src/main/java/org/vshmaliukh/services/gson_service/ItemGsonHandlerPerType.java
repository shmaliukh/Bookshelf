package org.vshmaliukh.services.gson_service;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.bookshelf.bookshelfObjects.Item;
import org.vshmaliukh.handlers.ItemHandlerProvider;

import java.io.File;
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

    public ItemGsonHandlerPerType(String homeDir, String userName, List<?> listToSave) {
        super(homeDir, userName, listToSave);
    }

    @Override
    public Path generatePathForGson() {
        Path path = Paths.get(String.valueOf(generatePathForUser()), gsonHandlerFolderStr);
        createDirectoryIfNotExists(path);
        return path;
    }

    @Override
    public String generateFullFileName() {
        return userName + "_" + typeStr + gsonFileType;
    }

    @Override
    public void saveToFile() {
        for (Class<? extends Item> classType : getClassTypes()) {
            typeStr = classType.getSimpleName();
            List<? extends Item> listPerType = listToSave.stream()
                    .filter(o -> o.getClass().equals(classType))
                    .collect(Collectors.toList());
            saveListToFile(new File(String.valueOf(generatePathForGsonFile())), listPerType);
        }
    }

    @Override
    public List<Item> readListFromFile() {
        List<Item> resultList = new ArrayList<>();
        for (Class<? extends Item> typeName : ItemHandlerProvider.uniqueTypeNames) {
            typeStr = typeName.getSimpleName();
            resultList.addAll(readItemListFromGsonFile(new File(String.valueOf(generatePathForGsonFile()))));
        }
        return resultList;
    }

    private Set<Class<? extends Item>> getClassTypes() {
        return listToSave.stream()
                .map(Item::getClass)
                .collect(Collectors.toSet());
    }
}
