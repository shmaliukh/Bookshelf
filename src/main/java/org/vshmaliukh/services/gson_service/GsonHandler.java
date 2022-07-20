package org.vshmaliukh.services.gson_service;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.bookshelf.Shelf;
import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Item;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.vshmaliukh.constants.ConstantsForGsonHandler.*;
import static org.vshmaliukh.services.print_table_service.ConvertorToStringForLiterature.*;

@Slf4j
public class GsonHandler {

    private final String userName;
    private final PrintWriter printWriter;
    private final int typeOfWorkWithFiles;
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private Path userDirectory;
    private File gsonFile;

    private String fileNameForAll;
    private String fileNameForBooks;
    private String fileNameForMagazines;
    private String fileNameForGazettes;

    public GsonHandler(int typeOfWorkWithFiles, String userName, PrintWriter printWriter) {
        this.userName = userName;
        this.printWriter = printWriter;
        this.typeOfWorkWithFiles = typeOfWorkWithFiles;

        generateFileNames();
    }

    private void generateFileNames() {
        userDirectory = Paths.get(PROGRAM_HOME_PROPERTY, userName);

        fileNameForAll = userName + "_" + SHELF_FILE_NAME_PREFIX;
        fileNameForBooks = userName + "_" + BOOKS_FILE_NAME_PREFIX;
        fileNameForMagazines = userName + "_" + MAGAZINES_FILE_NAME_PREFIX;

        fileNameForGazettes = userName + "_" + GAZETTES_FILE_NAME_PREFIX;
    }

    public void saveShelfInGson(Shelf shelf) {
        switch (typeOfWorkWithFiles) {
            case WORK_WITH_ONE_FILE:
                saveInOneGsonFile(shelf);
                break;
            case WORK_WITH_FILE_PER_TYPE:
                saveInTwoGsonFiles(shelf);
                break;
            default:
                break;
        }
    }

    private void saveInOneGsonFile(Shelf shelf) {
        saveListToGsonFile(fileNameForAll, shelf.getAllLiteratureObjects());
    }

    private void saveInTwoGsonFiles(Shelf shelf) {
        saveListToGsonFile(fileNameForBooks, shelf.getBooks());
        saveListToGsonFile(fileNameForMagazines, shelf.getMagazines());
        saveListToGsonFile(fileNameForGazettes, shelf.getGazettes());
    }

    private <T extends Item> void saveListToGsonFile(String fileName, List<T> literatureList) {
        initFile(fileName);
        List<Container> containerList = getContainerForLiteratureObjects(literatureList);
        try (FileWriter fw = new FileWriter(gsonFile)) {
            gson.toJson(containerList, fw);
            fw.flush();
        } catch (IOException ioe) {
            informAboutErr("problem to write shelf to file '" + gsonFile.getAbsolutePath() + "'. Exception: " + ioe);
        }
    }

    private void initFile(String fileName) {
        createUserDirectoryIfNoExist();
        Path gsonFilePath = Paths.get(String.valueOf(userDirectory), (fileName + FILE_TYPE));
        gsonFile = new File(String.valueOf(gsonFilePath));
    }

    private void createUserDirectoryIfNoExist() {
        File dirForProgram = new File(PROGRAM_HOME_PROPERTY);
        createDirIfNotExists(dirForProgram);
        File dirForUser = new File(String.valueOf(userDirectory));
        createDirIfNotExists(dirForUser);
    }

    public Shelf readShelfFromGson() { // TODO make feature to try read from another typeOfWorkWithFiles when file not exists
        Shelf shelf = new Shelf(printWriter);
        switch (typeOfWorkWithFiles) {
            case WORK_WITH_ONE_FILE:
                readShelfItemsFromGsonFile(fileNameForAll).forEach(shelf::addLiteratureObject);
                break;
            case WORK_WITH_FILE_PER_TYPE:
                readShelfItemsFromGsonFile(fileNameForBooks).forEach(shelf::addLiteratureObject);
                readShelfItemsFromGsonFile(fileNameForMagazines).forEach(shelf::addLiteratureObject);
                readShelfItemsFromGsonFile(fileNameForGazettes).forEach(shelf::addLiteratureObject);
                break;
            default:
                break;
        }
        return shelf;
    }

    private List<Item> readShelfItemsFromGsonFile(String fileName) {
        initFile(fileName);
        List<Item> itemList = new ArrayList<>();

        String typeOfClass;
        JsonObject itemObject;

        for (JsonElement jsonElement : getJsonArr()) {
            typeOfClass = getClassTypeFromJsonElement(jsonElement);
            itemObject = getJsonObjectFromJsonElement(jsonElement);
            if (itemObject == null || typeOfClass == null) {
                String problemMessage = "problem to read shelf from '" + gsonFile.getAbsolutePath()
                        + "' file when read shelf from file (NullPointerException)";
                informAboutErr(problemMessage);
                saveProblemFile(gsonFile, problemMessage);
                return itemList;
            } else {
                itemList.add(getLiteratureObjectFromJson(typeOfClass, itemObject));
            }
        }
        return itemList;
    }

    private void saveProblemFile(File gsonFile, String problemMessage) {
        File dirForProblemFiles = new File(String.valueOf(Paths.get(String.valueOf(userDirectory), "problemFiles")));
        createDirIfNotExists(dirForProblemFiles);

        File problemFile = getOldestProblemFile(gsonFile, dirForProblemFiles);
        try {
            Files.copy(gsonFile.toPath(), problemFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            problemFile.setLastModified(new Date().getTime());
            saveErrorToLogFile(problemMessage, dirForProblemFiles);

            log.info("[GsonHandler] save problem file as '" + problemFile.getAbsolutePath() + "' last mod time: " + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(problemFile.lastModified()));
        } catch (IOException ioe) {
            informAboutErr("problem to save copy of problem file'" + problemFile.getAbsolutePath() + "'. Exception: " + ioe);
        }
    }

    private File getOldestProblemFile(File gsonFile, File dirForProblemFiles) {
        List<File> filesList = new ArrayList<>();
        for (int i = 0; i < MAX_PROBLEM_FILES; i++) {
            File problemFile = new File(String.valueOf(Paths.get(
                    String.valueOf(dirForProblemFiles),
                    ("problemFileToRead_" + i + "_" + gsonFile.getName()))));
            if (!problemFile.exists()) {
                return problemFile;
            }
            filesList.add(problemFile);
        }
        return getTheOldestFile(filesList);
    }

    private File getTheOldestFile(List<File> filesList) {
        long oldest = new Date().getTime();
        File oldestFile = null;
        for (File file : filesList) {
            if (file.lastModified() < oldest) {
                oldest = file.lastModified();
                oldestFile = file;
            }
        }
        if (oldestFile != null) {
            return oldestFile;
        }
        return filesList.get(0);
    }

    private void saveErrorToLogFile(String message, File dirForProblemFiles) {
        File logFile = new File(String.valueOf(Paths.get(
                String.valueOf(dirForProblemFiles),
                ("log.txt"))));
        try (FileWriter fileWriter = new FileWriter(logFile, true)) {
            fileWriter.append(new Date() + System.lineSeparator()
                    + "\t" + message + System.lineSeparator() + System.lineSeparator());
            fileWriter.flush();
        } catch (IOException ioe) {
            informAboutErr("problem to add message to log file'" + logFile.getAbsolutePath() + "'. "
                    + "Exception: " + ioe);
        }
    }

    private void createDirIfNotExists(File der) {
        if (!der.exists()) {
            der.mkdir();
        }
    }

    private JsonObject getJsonObjectFromJsonElement(JsonElement element) {
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

    private Item getLiteratureObjectFromJson(String typeOfClass, JsonObject itemObject) {
        if (typeOfClass.equals(BOOK_CLASS_NAME)) {
            return gson.fromJson(itemObject, Book.class);
        } else if (typeOfClass.equals(MAGAZINE_CLASS_NAME)) {
            return gson.fromJson(itemObject, Magazine.class);
        } else if (typeOfClass.equals(GAZETTE_CLASS_NAME)) {
            return gson.fromJson(itemObject, Magazine.class);
        }
        return null;
    }

    private JsonArray getJsonArr() {
        JsonArray jsonArray = new JsonArray();
        try (FileReader fr = new FileReader(gsonFile)) {
            jsonArray = getJsonElementsFromArray(jsonArray, fr);
        } catch (IOException fnfe) {
            informAboutErr("problem to read for file'" + gsonFile.getAbsolutePath() + "'. Exception: " + fnfe);
        }
        return jsonArray;
    }

    private JsonArray getJsonElementsFromArray(JsonArray jsonArray, FileReader fr) {
        try {
            JsonArray fromJson = gson.fromJson(fr, JsonArray.class);
            if (fromJson != null) {
                jsonArray = fromJson;
            } else {
                String problemMessage = "problem to read shelf from file '" + gsonFile.getAbsolutePath() + "'. Exception: jsonArray == null";
                informAboutErr(problemMessage);
                saveProblemFile(gsonFile, problemMessage);
            }
        } catch (JsonSyntaxException jse) {
            String problemMessage = "problem to read shelf from file '" + gsonFile.getAbsolutePath() + "'(JsonSyntaxException). Exception: " + jse;
            informAboutErr(problemMessage);
            saveProblemFile(gsonFile, problemMessage);
        }
        return jsonArray;
    }

    private <T extends Item> List<Container> getContainerForLiteratureObjects(List<T> itemList) {
        List<Container> containerArrayList = new ArrayList<>();
        itemList.forEach(o -> containerArrayList.add(new Container(o)));
        return containerArrayList;
    }

    private void informAboutErr(String problemMessage) {
        log.error("[User]: name: '" + userName + "' // [GsonHandler]: problem: " + problemMessage);
    }
}
