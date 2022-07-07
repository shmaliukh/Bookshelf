package org.ShmaliukhVlad.services.GsonService;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import org.ShmaliukhVlad.bookshelf.Shelf;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Literature;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class GsonHandler implements SaveReadOneFile, SaveReadTwoFiles{
    private FileReader fr;
    private Gson gson = new Gson();
    private List<Literature> literatureList;
    private JsonArray jsonArray;

    public boolean isFileExists(File gsonFile){
        return Files.exists(gsonFile.toPath());
    }

    public List<Literature> readLiteratureTypeFromGson(Class type, File gsonFile){
        literatureList = new ArrayList<>();
        if(isFileExists(gsonFile)){
            for (JsonElement element : getJsonArr(gsonFile)) {
                literatureList.add((Literature) gson.fromJson(element,type));}
        }
        else {
            informAboutErr("File not exists");
        }
        return literatureList;
    }

    private void informAboutErr(String file_not_exists) {
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
    @Override
    public Shelf readShelfFromGsonFile() {
       return new Shelf();
    }



    @Override
    public void saveShelfInGsonFile() {

    }

    @Override
    public Shelf readShelfFromTwoFiles() {
        return null;
    }

    @Override
    public void saveShelfInTwoFiles() {

    }
}
