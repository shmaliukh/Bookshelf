package org.ShmaliukhVlad.services.GsonService;

import com.google.gson.*;
import org.ShmaliukhVlad.bookshelf.Shelf;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Magazine;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.ShmaliukhVlad.constants.ConstantValues.SAVE_READ_ONE_FILE;
import static org.ShmaliukhVlad.constants.ConstantValues.SAVE_READ_TWO_FILES;

public abstract class SaveToGsonService {

    private SaveToGsonService(){}

    public static void saveShelfToGsonFile(Shelf shelf, String fileName, int typeOfWorkWithFiles) throws IOException {
        switch (typeOfWorkWithFiles){
            case SAVE_READ_ONE_FILE:
                SaveToGsonService.saveShelfInOneFile(shelf ,fileName);
                break;
            case SAVE_READ_TWO_FILES:
                SaveToGsonService.saveShelfInTwoFiles(shelf ,fileName);
                break;
        }
    }

    public static void saveShelfInOneFile(Shelf shelf, String fileName) throws IOException {
        Writer fw = new FileWriter(fileName);
        new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(getContainerForLiteratureObjects(shelf), fw);
        fw.flush();
        fw.close();
    }

    public static void saveShelfInTwoFiles(Shelf shelf, String fileName) throws IOException {
        saveBooksToGsonFile(shelf, fileName+"Books"+".json");
        saveMagazinesToGsonFile(shelf, fileName+"Magazines"+".json");
    }


    public static void saveBooksToGsonFile(Shelf shelf, String fileName) throws IOException {
        FileWriter fw = new FileWriter(fileName);
        new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(shelf.getBooks(), fw);
        fw.flush();
        fw.close();
    }

    public static void saveMagazinesToGsonFile(Shelf shelf, String fileName) throws IOException {
        FileWriter fw = new FileWriter(fileName);
        new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(shelf.getMagazines(), fw);
        fw.flush();
        fw.close();
    }

    private static List<ContainerForLiteratureObject> getContainerForLiteratureObjects(Shelf shelf) {
        List<ContainerForLiteratureObject> containerArrayList= new ArrayList<>();
        for (Book book : shelf.getBooks()) {
            containerArrayList.add(new ContainerForLiteratureObject(book));
        }
        for (Magazine magazine : shelf.getMagazines()) {
            containerArrayList.add(new ContainerForLiteratureObject(magazine));
        }
        return containerArrayList;
    }
}
