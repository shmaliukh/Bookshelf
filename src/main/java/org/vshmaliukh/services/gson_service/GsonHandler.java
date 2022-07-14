package org.vshmaliukh.services.gson_service;

import com.google.gson.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.vshmaliukh.bookshelf.Shelf;
import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Literature;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.vshmaliukh.constants.ConstantsForGsonHandler.*;

@Slf4j
public class GsonHandler {

    private final String userName;
    private final PrintWriter printWriter;
    private final int typeOfWorkWithFiles;
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private FileReader fr;
    private Path path;
    private File file;

    private String fileNameForAll;
    private String fileNameForBooks;
    private String fileNameForMagazines;

    public GsonHandler(int typeOfWorkWithFiles, String userName, PrintWriter printWriter){
        this.userName = userName;
        this.printWriter = printWriter;
        this.typeOfWorkWithFiles = typeOfWorkWithFiles;

        generateFileNames();
    }

    private void generateFileNames() {
        fileNameForAll = userName + SHELF_FILE_NAME_PREFIX;
        fileNameForBooks = userName + BOOKS_FILE_NAME_PREFIX;
        fileNameForMagazines = userName + MAGAZINES_FILE_NAME_PREFIX;
    }

    public void saveShelfInGson(Shelf shelf){
        switch (typeOfWorkWithFiles){
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

    private <T> void saveListToGsonFile(String fileName, List<T> literatureList){
        createFile(fileName);
        List<Container> containerList = getContainerForLiteratureObjects((List<Literature>) literatureList);
        try {
            FileWriter fw = new FileWriter(file);
            gson.toJson(containerList, fw);
            fw.flush();
            fw.close();
        }
        catch (IOException e) {
            informAboutErr("problem to write shelf to file");
        }
    }

    private void createFile(String fileName) {
        path = Paths.get(HOME_PROPERTY, (fileName + FILE_TYPE));
        file = new File(String.valueOf(path));
    }

    public Shelf readShelfFromGson(){ // TODO make feature to try read from another typeOfWorkWithFiles when file not exists
        Shelf shelf = new Shelf(printWriter);
        switch (typeOfWorkWithFiles){
            case WORK_WITH_ONE_FILE:
                try {
                    readShelfFromGsonFile(fileNameForAll).forEach(shelf::addLiteratureObject);
                }
                catch (FileNotFoundException e) {
                    informAboutErr("(problem to read from one Json file (FileNotFoundException)");
                }
                break;
            case WORK_WITH_TWO_FILES:
                try {
                    readShelfFromGsonFile(fileNameForBooks).forEach(shelf::addLiteratureObject);
                    readShelfFromGsonFile(fileNameForMagazines).forEach(shelf::addLiteratureObject);
                }
                catch (FileNotFoundException e) {
                    informAboutErr("(problem to read from two Json files (FileNotFoundException)");
                }
                break;
            default:
                break;
        }
        return shelf;
    }

    private List<Literature> readShelfFromGsonFile(String fileName) throws FileNotFoundException {

        @Data
        class GsonContainer{
            private String classOfLiterature;
            private Object literature;
        }
        createFile(fileName);
        List<Literature> literatureList = new ArrayList<>();

        if(Files.exists(path)){
            JsonObject itemObject;
            String typeOfClass;
            JsonArray jsonArray = getJsonArr();

            if(jsonArray != null){
                for (JsonElement element : jsonArray) {
                    try {

                        itemObject = element.getAsJsonObject().getAsJsonObject("literature");
                        typeOfClass = element.getAsJsonObject().get("classOfLiterature").getAsString();
                    }
                    catch (NullPointerException e){ //fixme
                        informAboutErr("problem to read shelf from '"+ fileName +"' file when read shelf from file (NullPointerException)");
                        return literatureList;
                    }
                    literatureList.add(getLiteratureObjectFromJson(typeOfClass, itemObject));
                }
                closeFileReader();
                return literatureList;
            }
        }
        else{
            informAboutErr( "'"+ fileName +"' file not found when read shelf from file (file not exists)");
            return literatureList;
        }
        return literatureList;
    }

    private Literature getLiteratureObjectFromJson(String typeOfClass, JsonObject itemObject) {
        Literature literature = null;
        if (typeOfClass.equals(Book.class.toString())) {
            literature = gson.fromJson(itemObject, Book.class);
        }
        else if (typeOfClass.equals(Magazine.class.toString())) {
            literature = gson.fromJson(itemObject, Magazine.class);
        }
        return literature;
    }

    private JsonArray getJsonArr(){
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            closeFileReader();
            informAboutErr("problem to read shelf from file");
        }
        JsonArray jsonArray;
        try{
            jsonArray = gson.fromJson(fr, JsonArray.class);
            if(jsonArray == null){
                informAboutErr("problem to read shelf from file (jsonArray == null)");
                return new JsonArray();
            }
        }
        catch (JsonSyntaxException e){
            informAboutErr("problem to read shelf from file (JsonSyntaxException)");
            return null;
        }
        finally {
            closeFileReader();
        }
        return jsonArray;
    }

    private void closeFileReader() {
        try {
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Container> getContainerForLiteratureObjects(List<Literature> literatureList) {
        List<Container> containerArrayList= new ArrayList<>();
        literatureList.forEach(o -> containerArrayList.add(new Container(o)));
        return containerArrayList;
    }

    private void informAboutErr(String problemMessage) {
        log.info("  [User] name: '" + userName + "' // [GsonHandler] problem: " + problemMessage);
    }
}
