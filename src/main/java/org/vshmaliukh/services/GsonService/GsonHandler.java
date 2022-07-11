package org.vshmaliukh.services.GsonService;

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

    private Gson gson = new Gson();
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

    public GsonHandler(String userName, PrintWriter printWriter){
        this.userName = userName;
        this.printWriter = printWriter;

        fileNameForAll = userName + SHELF_FILE_NAME_PREFIX;
        fileNameForBooks = userName + BOOKS_FILE_NAME_PREFIX;
        fileNameForMagazines = userName + MAGAZINES_FILE_NAME_PREFIX;
    }

    public void saveInOneGsonFile(Shelf shelf) {
        saveListToGsonFile(fileNameForAll, shelf.getAllLiteratureObjects());
    }

    public void saveInTwoGsonFiles(Shelf shelf) {
        saveListToGsonFile(fileNameForBooks, shelf.getBooks());
        saveListToGsonFile(fileNameForMagazines, shelf.getMagazines());
    }

    public <T> void saveListToGsonFile(String fileName, List<T> literatureList){
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

    public Shelf readShelfFromGsonFile() throws FileNotFoundException {
        path = Paths.get(HOME_PROPERTY, (fileNameForAll + FILE_TYPE));
        if(Files.exists(path)){
            fr = new FileReader(String.valueOf(path));
            Gson gson = new Gson();

            List<Literature> literatureList = new ArrayList();
            JsonArray jsonArray;
            try{
                jsonArray = gson.fromJson(fr, JsonArray.class);
            }
            catch (JsonSyntaxException e){
                informAboutErr("Problem to read shelf from file when read shelf from one file");
                return new Shelf(printWriter);
            }
            if(jsonArray != null){
                for (JsonElement element : jsonArray) {
                    JsonObject itemObject = element.getAsJsonObject().getAsJsonObject("literature");
                    String typeOfClass;
                    try {
                        typeOfClass = element.getAsJsonObject().get("classOfLiterature").getAsString();
                    }
                    catch (NullPointerException e){
                        informAboutErr("Problem to read shelf from file when read shelf from one file");
                        return new Shelf(printWriter);
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
                return new Shelf(literatureList, printWriter);
            }
        }
        else{
            informAboutErr("File not found when read shelf from one file");
            return new Shelf(printWriter);
        }
        try {
            fr.close();
        } catch (IOException e) {
            informAboutErr("Problem to close file when read shelf from one file");
            throw new RuntimeException(e);
        }
        return new Shelf(printWriter);
    }

//    public Shelf readShelfFromGsonFile(){
//        Shelf shelf = new Shelf(printWriter);
//        for (Literature magazine : readLiteratureTypeFromGson(Magazine.class, fileNameForAll)) {
//            shelf.addLiteratureObject(magazine);
//        }
//        return shelf;
//    }

    public List<Literature> readLiteratureTypeFromGson(Class type, String fileName){
        path = Paths.get(HOME_PROPERTY, (fileName + FILE_TYPE));
        file = new File(String.valueOf(path));
        literatureList = new ArrayList<>();
        if(isFileExists(file)){
            for (JsonElement element : getJsonArr(file)) {
                literatureList.add((Literature) gson.fromJson(element,type));
            }
        }
        else {
            informAboutErr("File not exists");
        }
        return literatureList;
    }

    public JsonArray getJsonArr(File file){
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            informAboutErr("Problem to read shelf from file");
            return null;
        }
        try{
            jsonArray = gson.fromJson(fr, JsonArray.class);
            if(jsonArray == null){
                informAboutErr("Problem to read shelf from file");
                return new JsonArray();
            }
        }
        catch (JsonSyntaxException e){
            informAboutErr("Problem to read shelf from file");
            return null;
        }
        return jsonArray;
    }

    private List<Container> getContainerForLiteratureObjects(List<Literature> literatureList) {
        List<Container> containerArrayList= new ArrayList<>();
        literatureList.forEach(o -> containerArrayList.add(new Container(o)));
        return containerArrayList;
    }

    public boolean isFileExists(File gsonFile){
        return Files.exists(gsonFile.toPath());
    }

    private void informAboutErr(String problemMessage) {
        printWriter.println("[GsonHandler] problem: " + problemMessage);
    }
}
