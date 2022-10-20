package org.vshmaliukh.services.save_read_services.gson_handler;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class ItemGsonHandlerPerTypeForUser extends ItemGsonHandlerHandler {

    public String gsonHandlerFolderStr = "gson_handler_per_type";
    protected String typeStr;

    public ItemGsonHandlerPerTypeForUser(String userName) {
        super(userName);
    }

    @Override
    public Path generatePathForFileHandler() {
        Path path = Paths.get(String.valueOf(generatePathForUser(System.getProperty("user_home"), this.userName)), gsonHandlerFolderStr);
        createDirectoryIfNotExists(path, this.userName);
        return path;
    }

    @Override
    public String generateFullFileName() {
        return userName + "_" + typeStr + JSON_FILE_TYPE;
    }

    @Override
    public void saveItemList(List<Item> listToSave) {
        for (Class<? extends Item> classType : getClassTypes()) {
            typeStr = classType.getSimpleName();
            List<? extends Item> listPerType = listToSave.stream()
                    .filter(o -> o.getClass().equals(classType))
                    .collect(Collectors.toList());
            saveListToFile(generatePathForGsonFile(), listPerType);
        }
    }

    @Override
    public List<Item> readItemList() {
        List<Item> resultList = new ArrayList<>();
        for (Class<? extends Item> typeName : ItemHandlerProvider.uniqueTypeNames) {
            typeStr = typeName.getSimpleName();
            List<Item> itemList = readItemListFromGsonFile(generatePathForGsonFile());
            resultList.addAll(itemList);
        }
        return resultList;
    }

    private Set<Class<? extends Item>> getClassTypes() {
        return ItemHandlerProvider.uniqueTypeNames;
    }

    @Override
    public <T extends Item> List<T> readItemsByClass(Class<T> classType) {
        typeStr = classType.getSimpleName();
        return (List<T>) readItemListFromGsonFile(generatePathForGsonFile());
    }
}
