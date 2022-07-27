package org.vshmaliukh.terminal.services.gson_service;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.terminal.bookshelf.literature_items.Item;
import org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandlerProvider;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public abstract class ItemGsonHandler extends FilesHandler {

    public static final int MAX_PROBLEM_FILES = 5;
    public static final String JSON_FILE_TYPE = ".json";

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    String logFileFullName = "log.txt";
    String problemFilesFolderStr = "problem_files";

    ItemGsonHandler(String homeDir, String userName) {
        super(homeDir, userName);
    }

    abstract String generateFullFileName();

    abstract Path generatePathForGson();

    Path generatePathForGsonFile() {
        return Paths.get(String.valueOf(generatePathForGson()), generateFullFileName());
    }

    Path generatePathForProblemFiles() { // TODO refactor methods
        Path path = Paths.get(String.valueOf(generatePathForGson()), problemFilesFolderStr);
        createDirectoryIfNotExists(path);
        return path;
    }

    List<Item> readItemListFromGsonFile(File gsonFileToRead) {
        List<Item> itemListFromFile = new ArrayList<>();
        JsonArray jsonArrayFromFile = getJsonArrayFromFile(gsonFileToRead);
        if (jsonArrayFromFile != null) {
            for (JsonElement jsonElement : jsonArrayFromFile) {
                Item itemFromJsonElement = getItemFromJsonElement(jsonElement);
                if (itemFromJsonElement != null) {
                    itemListFromFile.add(itemFromJsonElement);
                }
            }
        }
        return itemListFromFile;
    }

    Item getItemFromJsonElement(JsonElement jsonElement) {
        String classOfItem = getClassTypeFromJsonElement(jsonElement);
        JsonObject jsonObject = getJsonObjectFromJsonElement(jsonElement);
        if (classOfItem == null || jsonObject == null) {
            return null;
        } else {
            return gson.fromJson(jsonObject, ItemHandlerProvider.getClassByName(classOfItem));
        }
    }

    JsonObject getJsonObjectFromJsonElement(JsonElement element) {
        return Optional.ofNullable(element)
                .map(JsonElement::getAsJsonObject)
                .map(o -> o.getAsJsonObject("item"))
                .orElse(null);
    }

    String getClassTypeFromJsonElement(JsonElement element) {
        return Optional.ofNullable(element)
                .map(JsonElement::getAsJsonObject)
                .map(o -> o.get("classOfLiterature"))
                .map(JsonElement::getAsString)
                .orElse(null);
    }

    JsonArray getJsonArrayFromFile(File gsonFile) {
        try (FileReader fileReader = new FileReader(gsonFile)) {
            return gson.fromJson(fileReader, JsonArray.class);
        } catch (Exception e) {
            saveProblemFile(gsonFile, "Problem to get Json arr from file '" + gsonFile.getAbsolutePath() + "'.", e);
        }
        return new JsonArray();
    }


    boolean saveListToFile(File gsonFileToSave, List listToSave) {
        try (FileWriter fileWriter = new FileWriter(gsonFileToSave)) {
            gson.toJson(getContainerListForObjects(listToSave), fileWriter);
        } catch (IOException ioe) {
            informAboutErr("Problem to save list of containers to file " + gsonFileToSave.getAbsolutePath() + "'.", ioe);
            return false;
        }
        return true;
    }

    List<ContainerForGson> getContainerListForObjects(List<?> listToSave) {
        return listToSave.stream()
                .map(ContainerForGson::new)
                .collect(Collectors.toList());
    }

    void saveProblemFile(File gsonFile, String problemMessage, Exception exception) {
        File problemFile = getOldestProblemFile(gsonFile);
        try {
            Files.copy(gsonFile.toPath(), problemFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            long time = new Date().getTime();
            problemFile.setLastModified(time);
            saveErrorToLogFile(problemMessage, exception, new File(String.valueOf(generatePathForProblemFiles()), logFileFullName));
            log.info("[GsonHandler]: save problem file as '" + problemFile.getAbsolutePath() + "' last mod time: " + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(problemFile.lastModified()));
        } catch (IOException ioe) {
            informAboutErr("Problem to save copy of problem file'" + problemFile.getAbsolutePath() + "'", ioe);
        }
    }

    void saveErrorToLogFile(String problemMessage, Exception exception, File logFile) {
        informAboutErr(problemMessage, exception);
        try (FileWriter fileWriter = new FileWriter(logFile, true)) {
            fileWriter.append("\t" + new Date() + System.lineSeparator()
                    + problemMessage + System.lineSeparator()
                    + exception.getMessage() + System.lineSeparator());
            fileWriter.flush();
        } catch (IOException ioe) {
            informAboutErr("Problem to add message to log file '" + logFile.getAbsolutePath() + "'.", ioe);
        }
    }

    File getOldestProblemFile(File gsonFile) {
        List<File> filesList = new ArrayList<>();
        for (int i = 0; i < MAX_PROBLEM_FILES; i++) {
            File problemFile = new File(String.valueOf(generatePathForProblemFiles()), ("problemFileToRead_" + i + "_" + gsonFile.getName()));
            if (!problemFile.exists()) {
                return problemFile;
            }
            filesList.add(problemFile);
        }
        return getTheOldestFile(filesList);
    }

    File getTheOldestFile(List<File> filesList) {
        long oldestTime = new Date().getTime();
        File oldestFile = null;
        for (File file : filesList) {
            if (file.lastModified() < oldestTime) {
                oldestTime = file.lastModified();
                oldestFile = file;
            }
        }
        if (oldestFile != null) {
            return oldestFile;
        }
        return filesList.get(0);
    }

    public abstract void saveToFile(List<Item> listToSave);

    public abstract List<Item> readListFromFile();
}
