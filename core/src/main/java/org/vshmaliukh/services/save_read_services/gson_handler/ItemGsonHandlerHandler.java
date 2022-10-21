package org.vshmaliukh.services.save_read_services.gson_handler;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.services.save_read_services.AbstractSaveReadService;
import org.vshmaliukh.services.save_read_services.SaveReadUserFilesHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

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
public abstract class ItemGsonHandlerHandler extends AbstractSaveReadService implements SaveReadUserFilesHandler {

    public static final int MAX_PROBLEM_FILES = 5;
    public static final String JSON_FILE_TYPE = ".json";

    protected Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    protected String logFileFullName = "log.txt";
    protected String problemFilesFolderStr = "problem_files";

    protected ItemGsonHandlerHandler(String userName) {
        super(userName);
    }

    public Path generatePathForGsonFile() {
        return Paths.get(String.valueOf(generatePathForFileHandler()), generateFullFileName());
    }

    Path generatePathForProblemFiles() {
        Path path = Paths.get(String.valueOf(generatePathForFileHandler()), problemFilesFolderStr);
        createDirectoryIfNotExists(path, this.userName);
        return path;
    }

    public List<Item> readItemListFromGsonFile(Path gsonFilePathToRead) {
        List<Item> itemListFromFile = new ArrayList<>();
        JsonArray jsonArrayFromFile = getJsonArrayFromFile(gsonFilePathToRead);
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

    protected Item getItemFromJsonElement(JsonElement jsonElement) {
        String classOfItem = getClassTypeFromJsonElement(jsonElement);
        JsonObject jsonObject = getJsonObjectFromJsonElement(jsonElement);
        if (classOfItem == null || jsonObject == null) {
            return null;
        } else {
            Class<? extends Item> classByName = ItemHandlerProvider.getClassByName(classOfItem);
            if(classByName != null){
                return gson.fromJson(jsonObject, classByName);
            }
            else {
                return null;
            }
        }
    }

    protected JsonObject getJsonObjectFromJsonElement(JsonElement element) {
        return Optional.ofNullable(element)
                .map(JsonElement::getAsJsonObject)
                .map(o -> o.getAsJsonObject("item"))
                .orElse(null);
    }

    protected String getClassTypeFromJsonElement(JsonElement element) {
        return Optional.ofNullable(element)
                .map(JsonElement::getAsJsonObject)
                .map(o -> o.get("classOfLiterature"))
                .map(JsonElement::getAsString)
                .orElse(null);
    }

    protected JsonArray getJsonArrayFromFile(Path gsonFilePath) {
        File gsonFile = gsonFilePath.toFile();
        try (FileReader fileReader = new FileReader(gsonFile)) {
            return gson.fromJson(fileReader, JsonArray.class);
        } catch (Exception e) {
            saveProblemFile(gsonFilePath, "Problem to get Json arr from file '" + gsonFile.getAbsolutePath() + "'.", e);
        }
        return new JsonArray();
    }


    public boolean saveListToFile(Path gsonFilePathToSave, List<? extends Item> listToSave) {
        File gsonFileToSave = gsonFilePathToSave.toFile();
        try (FileWriter fileWriter = new FileWriter(gsonFileToSave)) {
            gson.toJson(getContainerListForObjects(listToSave), fileWriter);
        } catch (IOException ioe) {
            informAboutErr(this.userName, "Problem to save list of containers to file " + gsonFileToSave.getAbsolutePath() + "'.", ioe);
            return false;
        }
        return true;
    }

    protected List<ContainerForGson> getContainerListForObjects(List<?> listToSave) {
        return listToSave.stream()
                .map(ContainerForGson::new)
                .collect(Collectors.toList());
    }

    protected void saveProblemFile(Path gsonFilePath, String problemMessage, Exception exception) {
        Path oldestProblemFilePath = getOldestProblemFile(gsonFilePath);
        File oldestProblemFile = oldestProblemFilePath.toFile();
        try {
            Files.copy(gsonFilePath, oldestProblemFilePath, StandardCopyOption.REPLACE_EXISTING);
            long time = new Date().getTime();
            boolean isLastModified = oldestProblemFile.setLastModified(time);
            saveErrorToLogFile(problemMessage, exception, Paths.get(generatePathForProblemFiles().toString(), logFileFullName));
            if(isLastModified){
                log.info("[GsonHandler]: save problem file as '" + oldestProblemFile.getAbsolutePath() + "' last mod time: " + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(oldestProblemFile.lastModified()));
            }
        } catch (IOException ioe) {
            informAboutErr(this.userName, "Problem to save copy of problem file'" + oldestProblemFile.getAbsolutePath() + "'", ioe);
        }
    }

    protected void saveErrorToLogFile(String problemMessage, Exception exception, Path logFilePath) {
        log.warn("[User]: name: '" + userName + "'"
                + " // [FilesHandler]: " + problemMessage
                + " // [Exception]: " + exception.getMessage());
        File logFile = logFilePath.toFile();
        try (FileWriter fileWriter = new FileWriter(logFile, true)) {
            fileWriter.append("\t")
                    .append(new Date().toString())
                    .append(System.lineSeparator())
                    .append(problemMessage)
                    .append(System.lineSeparator())
                    .append(exception.getMessage())
                    .append(System.lineSeparator());
            fileWriter.flush();
        } catch (IOException ioe) {
            informAboutErr(this.userName, "Problem to add message to log file '" + logFile.getAbsolutePath() + "'.", ioe);
        }
    }

    protected Path getOldestProblemFile(Path gsonFilePath) {
        List<File> filesList = new ArrayList<>();
        for (int i = 0; i < MAX_PROBLEM_FILES; i++) {
            File problemGsonFile = gsonFilePath.toFile();
            File problemFile = new File(String.valueOf(generatePathForProblemFiles()), ("problemFileToRead_" + i + "_" + problemGsonFile.getName()));
            if (!problemFile.exists()) {
                return problemFile.toPath();
            }
            filesList.add(problemFile);
        }
        return getTheOldestFile(filesList).toPath();
    }

    protected File getTheOldestFile(List<File> filesList) {
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
}
