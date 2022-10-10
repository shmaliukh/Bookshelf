package org.vshmaliukh.services.file_service;

import lombok.NoArgsConstructor;
import org.vshmaliukh.shelf.literature_items.Item;

import java.nio.file.Path;
import java.util.List;

@NoArgsConstructor
public abstract class SaveReadUserFilesHandler extends UserFilesHandler {

    protected SaveReadUserFilesHandler(String homeDir, String userName) {
        super(homeDir, userName);
    }

    protected String generateFullFileName(){return "";}

    protected Path generatePathForFileHandler(){return null;}

    public abstract void saveItemList(List<Item> listToSave);

    public abstract List<Item> readItemList();

    public abstract <T extends Item> List<T> readItemsByClass(Class<T> classType);
}
