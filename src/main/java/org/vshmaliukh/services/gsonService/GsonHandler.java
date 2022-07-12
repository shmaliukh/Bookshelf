package org.vshmaliukh.services.gsonService;

import com.google.gson.*;
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

public class GsonHandler {

    private final Gson gson = new Gson();
    private FileReader fr;
    private FileWriter fw;

    private JsonArray jsonArray;
    private PrintWriter printWriter;
    private List<Literature> literatureList;

    private Path path;
    private File file;
    private String userName;

    private String fileNameForAll;
    private String fileNameForBooks;
    private String fileNameForMagazines;

    private int typeOfWorkWithFiles;

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

        //switch (typeOfWorkWithFiles){
        //    case WORK_WITH_ONE_FILE:
        //        fileNameForAll = userName + SHELF_FILE_NAME_PREFIX;
        //        break;
        //    case WORK_WITH_TWO_FILES:
        //        fileNameForBooks = userName + BOOKS_FILE_NAME_PREFIX;
        //        fileNameForMagazines = userName + MAGAZINES_FILE_NAME_PREFIX;
        //        break;
        //    default:
        //        break;
        //}
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
        path = Paths.get(HOME_PROPERTY, (fileName + FILE_TYPE));
        file = new File(String.valueOf(path));
        try {
            fw = new FileWriter(file);
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .toJson(getContainerForLiteratureObjects((List<Literature>) literatureList), fw);
            fw.flush();
            fw.close();
        }
        catch (IOException e) {
            informAboutErr(("Problem to write shelf to file"));
            throw new RuntimeException(e);
        }
    }

    public Shelf readShelfFromGson(){ // TODO make feature to try read from another typeOfWorkWithFiles when file not exists
        Shelf shelf = new Shelf(printWriter);
        switch (typeOfWorkWithFiles){
            case WORK_WITH_ONE_FILE:
                try {
                    readShelfFromGsonFile(fileNameForAll).forEach(shelf::addLiteratureObject);
                }
                catch (FileNotFoundException e) {
                    informAboutErr("(FileNotFoundException");
                    throw new RuntimeException(e);
                }
                break;
            case WORK_WITH_TWO_FILES:
                try {
                readShelfFromGsonFile(fileNameForBooks).forEach(shelf::addLiteratureObject);
                readShelfFromGsonFile(fileNameForMagazines).forEach(shelf::addLiteratureObject);
                }
                catch (FileNotFoundException e) {
                    informAboutErr("(FileNotFoundException");
                    throw new RuntimeException(e);
                }
                break;
            default:
                break;
        }
        return shelf;
    }

    private List<Literature> readShelfFromGsonFile(String fileName) throws FileNotFoundException {
        path = Paths.get(HOME_PROPERTY, (fileName + FILE_TYPE));
        List<Literature> literatureList = new ArrayList();

        if(Files.exists(path)){
            fr = new FileReader(String.valueOf(path));
            Gson gson = new Gson();

            JsonArray jsonArray = getJsonArr(new File(String.valueOf(path)));

            if(jsonArray != null){
                for (JsonElement element : jsonArray) {
                    JsonObject itemObject = element.getAsJsonObject().getAsJsonObject("literature");
                    String typeOfClass;
                    try {
                        typeOfClass = element.getAsJsonObject().get("classOfLiterature").getAsString();
                    }
                    catch (NullPointerException e){
                        informAboutErr("Problem to read shelf from file when read shelf from one file");
                        return literatureList;
                    }
                    List<Book> books = new ArrayList<>();
                    List<Magazine> magazines = new ArrayList<>();

                    if (typeOfClass.equals(Book.class.toString())) {
                        books.add(gson.fromJson(itemObject, Book.class));
                    }
                    else if (typeOfClass.equals(Magazine.class.toString())) {
                        magazines.add(gson.fromJson(itemObject, Magazine.class));
                    }
                    literatureList.addAll(books);
                    literatureList.addAll(magazines);
                }
                return literatureList;
            }
        }
        else{
            informAboutErr("File not found when read shelf from one file");
            return literatureList;
        }
        try {
            fr.close();
        } catch (IOException e) {
            informAboutErr("Problem to close file when read shelf from one file");
            throw new RuntimeException(e);
        }
        return literatureList;
    }

    private Shelf readShelfFromTwoGsonFiles(){
        Shelf shelf = new Shelf(printWriter);
        for (Literature magazine : readLiteratureTypeFromGson(Magazine.class, fileNameForMagazines)) {
            shelf.addLiteratureObject(magazine);
        }
        for (Literature book : readLiteratureTypeFromGson(Book.class, fileNameForBooks)) {
            shelf.addLiteratureObject(book);
        }
        return shelf;
    }

    private List<Literature> readLiteratureTypeFromGson(Class classType, String fileName){
        path = Paths.get(HOME_PROPERTY, (fileName + FILE_TYPE));
        file = new File(String.valueOf(path));
        literatureList = new ArrayList<>();
        if(isFileExists(file)){
            for (JsonElement element : getJsonArr(file)) {
                literatureList.add((Literature) gson.fromJson(element,classType));
            }
        }
        else {
            informAboutErr("Problem to read shelf from file (file not exists)");
        }
        return literatureList;
    }

    private JsonArray getJsonArr(File file){
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            informAboutErr("Problem to read shelf from file (file not found)");
            return null;
        }
        try{
            jsonArray = gson.fromJson(fr, JsonArray.class);
            if(jsonArray == null){
                informAboutErr("Problem to read shelf from file (jsonArray == null)");
                return new JsonArray();
            }
        }
        catch (JsonSyntaxException e){
            informAboutErr("Problem to read shelf from file (JsonSyntaxException)");
            return null;
        }
        return jsonArray;
    }

    private List<Container> getContainerForLiteratureObjects(List<Literature> literatureList) {
        List<Container> containerArrayList= new ArrayList<>();
        literatureList.forEach(o -> containerArrayList.add(new Container(o)));
        return containerArrayList;
    }

    private boolean isFileExists(File gsonFile){
        return Files.exists(gsonFile.toPath());
    }

    private void informAboutErr(String problemMessage) {
        printWriter.println("[GsonHandler] problem: " + problemMessage);
        // TODO use LOGGER to inform about problem
    }
}
