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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.vshmaliukh.constants.ConstantsForGsonHandler.*;
import static org.vshmaliukh.services.print_table_service.ConvertorToStringForLiterature.BOOK_CLASS_NAME;
import static org.vshmaliukh.services.print_table_service.ConvertorToStringForLiterature.MAGAZINE_CLASS_NAME;

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

    public GsonHandler(int typeOfWorkWithFiles, String userName, PrintWriter printWriter) {
        this.userName = userName;
        this.printWriter = printWriter;
        this.typeOfWorkWithFiles = typeOfWorkWithFiles;

        generateFileNames();
    }

    private void generateFileNames() {
        userDirectory = Paths.get(PROGRAM_HOME_PROPERTY, userName);

        fileNameForAll = userName + SHELF_FILE_NAME_PREFIX;
        fileNameForBooks = userName + BOOKS_FILE_NAME_PREFIX;
        fileNameForMagazines = userName + MAGAZINES_FILE_NAME_PREFIX;
    }

    public void saveShelfInGson(Shelf shelf) {
        switch (typeOfWorkWithFiles) {
            case WORK_WITH_ONE_FILE:
                saveInOneGsonFile(shelf);
                break;
            case WORK_WITH_TWO_FILES:
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
    }

    private <T extends Item> void saveListToGsonFile(String fileName, List<T> literatureList) {
        initFile(fileName);
        List<Container> containerList = getContainerForLiteratureObjects(literatureList);
        try {
            FileWriter fw = new FileWriter(gsonFile);
            gson.toJson(containerList, fw);
            fw.flush();
            fw.close();
        } catch (IOException ioe) {
            informAboutErr("problem to write shelf to file '" + gsonFile.getAbsolutePath() + "'");
        }
    }

    private void initFile(String fileName) {
        checkForDirectory(); // TODO rename method
        Path gsonFilePath = Paths.get(String.valueOf(userDirectory), (fileName + FILE_TYPE));
        gsonFile = new File(String.valueOf(gsonFilePath));
    }

    private void checkForDirectory() {
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
            case WORK_WITH_TWO_FILES:
                readShelfItemsFromGsonFile(fileNameForBooks).forEach(shelf::addLiteratureObject);
                readShelfItemsFromGsonFile(fileNameForMagazines).forEach(shelf::addLiteratureObject);
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
            }
            else {
                itemList.add(getLiteratureObjectFromJson(typeOfClass, itemObject));
            }
        }
        return itemList;
    }

    private void saveProblemFile(File gsonFile , String message) {
        try {
            boolean isSavedProblemFile = false;

            File dirForProblemFiles = new File(String.valueOf(Paths.get(String.valueOf(userDirectory), "problemFiles")));
            createDirIfNotExists(dirForProblemFiles);

            for (int i = 0; !isSavedProblemFile; i++) {// TODO create new method for rewriting ready n-max-files
                File problemFile = new File(String.valueOf(Paths.get(
                        String.valueOf(dirForProblemFiles),
                        ("problemFileToRead_" + i + "_" + gsonFile.getName()))));
                if (!Files.exists(problemFile.toPath())) {
                    Files.copy(gsonFile.toPath(), problemFile.toPath());
                    appendMessageToFile(message, problemFile);
                    isSavedProblemFile = true;
                }
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    private void appendMessageToFile(String message, File problemFile) {
        try {
            FileWriter fileWriter = new FileWriter(problemFile, true);
            fileWriter.append(message);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            // TODO catch
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
        }
        return null;
    }

    private JsonArray getJsonArr() {
        JsonArray jsonArray = new JsonArray();
        try {
            FileReader fr = new FileReader(gsonFile);
            try {
                JsonArray fromJson = gson.fromJson(fr, JsonArray.class);
                if (fromJson != null) {
                    jsonArray = fromJson;
                } else {

                    String problemMessage = "problem to read shelf from file '" + gsonFile.getAbsolutePath() + "' (jsonArray == null)";
                    informAboutErr(problemMessage);
                    saveProblemFile(gsonFile, problemMessage);
                }
            } catch (JsonSyntaxException jse) {
                String problemMessage = "problem to read shelf from file '" + gsonFile.getAbsolutePath() + "'(JsonSyntaxException)";
                informAboutErr(problemMessage);
                saveProblemFile(gsonFile, problemMessage);
            }
        } catch (FileNotFoundException fnfe) {
            //closeFileReader();
            //throw new RuntimeException(fnfe);
        }
        return jsonArray;
    }

    private <T extends Item> List<Container> getContainerForLiteratureObjects(List<T> itemList) {
        List<Container> containerArrayList = new ArrayList<>();
        itemList.forEach(o -> containerArrayList.add(new Container(o)));
        return containerArrayList;
    }

    private void informAboutErr(String problemMessage) {
        log.error("[User] name: '" + userName + "' // [GsonHandler] problem: " + problemMessage);
    }
}
