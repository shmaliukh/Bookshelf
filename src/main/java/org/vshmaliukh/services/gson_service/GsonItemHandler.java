package org.vshmaliukh.services.gson_service;

import com.google.gson.*;
import org.vshmaliukh.bookshelf.bookshelfObjects.Item;
import org.vshmaliukh.handlers.ItemHandlerProvider;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GsonItemHandler extends FilesHandler {

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    String fileName;
    String userName;
    String handlerDirectoryStr = "gson_handler";
    List<?> listToSave;

    String fileType = ".json";

    public GsonItemHandler(String homeDir, String userName, List<?> listToSave) {
        super(homeDir);
        this.userName = userName;
        this.listToSave = listToSave;

        createDirectoriesIfNotExists(generatePathForGson());
    }

    public Path generatePathForGson() {
        return Paths.get(programDirectoryStr, userName, handlerDirectoryStr);
    }

    public Path generatePathForGsonFile() {
        return Paths.get(programDirectoryStr);
    }

    boolean createDirectoriesIfNotExists(Path dir) {
        try {
            Path directories = Files.createDirectories(dir);
            if(!directories.toString().isEmpty()){
                return true;
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    public String generateFullFileName() {
        return fileName + fileType;
    }

    public String generateFullFileName(String suffix) {
        return fileName + "_" + suffix + fileType;
    }

    public void crateDirIfNotExists() {
        //Files.createDirectories(directoryToSave,)
        //directoryToSave
    }


    //public void saveListToFile() {
    //    try (FileWriter fileWriter = new FileWriter(generatePathForGsonFile().toFile(), true)) {
    //        for (Container container : getContainerListForObjects()) {
    //            gson.toJson(container, fileWriter);
    //        }
    //    } catch (IOException ioe) {
    //        //throw new RuntimeException(e);
    //    }
    //}

    public List<Item> readItemListFromFile() {
        List<Item> itemListFromFile = new ArrayList<>();
        for (JsonElement jsonElement : getJsonArrayFromFile()) {
            Item itemFromJsonElement = getItemFromJsonElement(jsonElement);
            if (itemFromJsonElement != null) {
                itemListFromFile.add(itemFromJsonElement);
            }
        }
        return itemListFromFile;
    }

    public Item getItemFromJsonElement(JsonElement jsonElement) {
        String classOfItem = getClassTypeFromJsonElement(jsonElement);
        JsonObject jsonObject = getJsonObjectFromJsonElement(jsonElement);
        if (classOfItem == null || jsonObject == null) {
            return null;
        } else {
            return gson.fromJson(jsonObject, ItemHandlerProvider.getClassByName(classOfItem));
        }
    }

    public JsonObject getJsonObjectFromJsonElement(JsonElement element) {
        return Optional.ofNullable(element)
                .map(JsonElement::getAsJsonObject)
                .map(o -> o.getAsJsonObject("item"))
                .orElse(null);
    }

    private String getClassTypeFromJsonElement(JsonElement element) {
        return Optional.ofNullable(element)
                .map(JsonElement::getAsJsonObject)
                .map(o -> o.get("classOfLiterature"))
                .map(JsonElement::getAsString)
                .orElse(null);
    }

    private JsonArray getJsonArrayFromFile() {
        try (FileReader fileReader = new FileReader(generatePathForGsonFile().toFile())) {
            return gson.fromJson(fileReader, JsonArray.class);
        } catch (Exception e) {
            //throw new RuntimeException(e);
        }
        return new JsonArray();
    }

    public List<Container> getContainerListForObjects() {
        return listToSave.stream()
                .map(Container::new)
                .collect(Collectors.toList());
    }

}
