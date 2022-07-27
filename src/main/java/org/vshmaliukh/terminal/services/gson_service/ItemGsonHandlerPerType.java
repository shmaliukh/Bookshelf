package org.vshmaliukh.terminal.services.gson_service;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.terminal.bookshelf.literature_items.Item;
import org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandlerProvider;

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
        return userName + "_" + typeStr + gsonFileType;
    }

    @Override
    public void saveToFile(List<Item> listToSave) {
        for (Class<? extends Item> classType : getClassTypes(listToSave)) {
            typeStr = classType.getSimpleName();
            List<? extends Item> listPerType = listToSave.stream()
                    .filter(o -> o.getClass().equals(classType))
                    .collect(Collectors.toList());

            generatePathForGsonFile().toFile().toPath().toFile().toPath();
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

    private Set<Class<? extends Item>> getClassTypes(List<Item> listToSave) {
        return listToSave.stream()
                .map(Item::getClass)
                .collect(Collectors.toSet());
    }
}
