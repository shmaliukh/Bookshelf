package org.vshmaliukh.services.GsonService;

import com.google.gson.*;
import org.vshmaliukh.bookshelf.Shelf;
import org.vshmaliukh.bookshelf.bookshelfObjects.Literature;

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
    }

    public void saveInOneGsonFile(Shelf shelf) {
        fileNameForAll = userName + "ShelfAll";

        saveListToGsonFile(fileNameForAll, shelf.getAllLiteratureObjects());
    }

    public void saveInTwoGsonFiles(Shelf shelf) {
        fileNameForBooks = userName + "ShelfBooks";
        fileNameForMagazines = userName + "ShelfMagazines";

        saveListToGsonFile(fileNameForBooks, shelf.getBooks());
        saveListToGsonFile(fileNameForMagazines, shelf.getMagazines());
    }

    public <T> void saveListToGsonFile(String fileName, List<T> literatureList){
        path = Paths.get(HOME_PROPERTY, (fileName + FILE_TYPE) );
        file = new File(String.valueOf(path));
        try {
            fw = new FileWriter(file);
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .toJson(getContainerForLiteratureObjects((List<Literature>) literatureList), fw);// TODO set all literature
            fw.flush();
            fw.close();
        }
        catch (IOException e) {
            informAboutErr(("Problem to write shelf to file"));
            throw new RuntimeException(e);
        }
    }



    private List<ContainerForLiteratureObject> getContainerForLiteratureObjects(List<Literature> literatureList) {
        List<ContainerForLiteratureObject> containerArrayList= new ArrayList<>();
        literatureList.forEach(o -> containerArrayList.add(new ContainerForLiteratureObject(o)));
        return containerArrayList;
    }

    public List<Literature> readLiteratureTypeFromGson(Class type, File gsonFile){
        literatureList = new ArrayList<>();
        if(isFileExists(gsonFile)){
            for (JsonElement element : getJsonArr(gsonFile)) {
                literatureList.add((Literature) gson.fromJson(element,type));
            }
        }
        else {
            informAboutErr("File not exists");
        }
        return literatureList;
    }

    private void informAboutErr(String problemMessage) {
        printWriter.println("[GsonHandler] problem: " + problemMessage);
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

    public boolean isFileExists(File gsonFile){
        return Files.exists(gsonFile.toPath());
    }


}
