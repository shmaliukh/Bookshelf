package org.ShmaliukhVlad.services.GsonService;

import com.google.gson.*;
import lombok.NoArgsConstructor;
import org.ShmaliukhVlad.bookshelf.Shelf;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Magazine;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.ShmaliukhVlad.constants.ConstantValues.*;

@NoArgsConstructor
public class SaveToGsonService {

    public void saveShelfToGsonFile(Shelf shelf, String fileName, int typeOfWorkWithFiles) throws IOException {
        switch (typeOfWorkWithFiles){
            case SAVE_READ_ONE_FILE:
                this.saveShelfInOneFile(shelf ,fileName);
                break;
            case SAVE_READ_TWO_FILES:
                this.saveShelfInTwoFiles(shelf ,fileName);
                break;
        }
    }

    public void saveShelfInOneFile(Shelf shelf, String fileName) throws IOException {
        Writer fw = new FileWriter((fileName+FILE_TYPE));
        new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(getContainerForLiteratureObjects(shelf), fw);
        fw.flush();
        fw.close();
    }

    public void saveShelfInTwoFiles(Shelf shelf, String fileName) throws IOException {
        String fileNameForBooks = fileName+"Books"+FILE_TYPE;
        String fileNameForMagazines = fileName+"Magazines"+FILE_TYPE;
        saveBooksToGsonFile(shelf, fileNameForBooks);
        saveMagazinesToGsonFile(shelf, fileNameForMagazines);
    }


    public void saveBooksToGsonFile(Shelf shelf, String fileName) throws IOException {
        FileWriter fw = new FileWriter(fileName);
        new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(shelf.getBooks(), fw);
        fw.flush();
        fw.close();
    }

    public void saveMagazinesToGsonFile(Shelf shelf, String fileName) throws IOException {
        FileWriter fw = new FileWriter(fileName);
        new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(shelf.getMagazines(), fw);
        fw.flush();
        fw.close();
    }

    private List<ContainerForLiteratureObject> getContainerForLiteratureObjects(Shelf shelf) {
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
