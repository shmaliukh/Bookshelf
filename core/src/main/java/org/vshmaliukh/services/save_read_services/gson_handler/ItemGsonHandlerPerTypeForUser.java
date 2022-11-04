package org.vshmaliukh.services.save_read_services.gson_handler;

import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.vshmaliukh.BaseAppConfig.HOME_PROPERTY;

@Slf4j
public class ItemGsonHandlerPerTypeForUser extends ItemGsonHandlerHandler {

    public static final String GSON_HANDLER_PER_TYPE = "gson_handler_per_type";

    protected String typeStr;

    public ItemGsonHandlerPerTypeForUser(String userName) {
        super(userName);
    }

    @Override
    public Path generatePathForFileHandler() {
        Path path = Paths.get(String.valueOf(generatePathForUser(HOME_PROPERTY, this.userName)), GSON_HANDLER_PER_TYPE);
        createDirectoryIfNotExists(path, this.userName);
        return path;
    }

    @Override
    public String generateFullFileName() {
        return userName + "_" + typeStr + JSON_FILE_TYPE;
    }

    @Override
    public void saveItemListToDB(List<Item> listToSave) {
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

    private List<Class<? extends Item>> getClassTypes() {
        return ItemHandlerProvider.uniqueTypeNames;
    }

    @Override
    public <T extends Item> List<T> readItemsByClass(Class<T> classType) {
        typeStr = classType.getSimpleName();
        return (List<T>) readItemListFromGsonFile(generatePathForGsonFile());
    }
}
