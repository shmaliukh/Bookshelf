package org.vshmaliukh.services.GsonService;

import org.junit.jupiter.api.Test;
import org.vshmaliukh.bookshelf.Shelf;
import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;

import java.io.PrintWriter;
import java.util.Date;

class GsonHandlerTest {
    PrintWriter printWriter = new PrintWriter(System.out , true);
    GsonHandler gsonHandler = new GsonHandler("test", printWriter);

    Shelf shelf = new Shelf(printWriter);


    @Test
    void readLiteratureTypeFromGson() {

    }

    @Test
    void getJsonArr() {
    }

    @Test
    void isFileExists() {
    }

    @Test
    void readShelfFromGsonFile() {
    }

    @Test
    void saveShelfInOneGsonFileTest(){
        shelf.addLiteratureObject(new Magazine("name1", 1, true));
        shelf.addLiteratureObject(new Magazine("name2", 2, false));
        shelf.addLiteratureObject(new Book("name3", 3, false, "author1", new Date()));
        shelf.addLiteratureObject(new Book("name4", 4, true, "author2", new Date()));

        gsonHandler.saveInOneGsonFile(shelf);
    }

    @Test
    void saveShelfInTwoGsonFilesTest() {
        shelf.addLiteratureObject(new Magazine("name1", 1, true));
        shelf.addLiteratureObject(new Magazine("name2", 2, false));
        shelf.addLiteratureObject(new Book("name3", 3, false, "author1", new Date()));
        shelf.addLiteratureObject(new Book("name4", 4, true, "author2", new Date()));

        gsonHandler.saveInTwoGsonFiles(shelf);
        //gsonHandler.saveListToGsonFile();
        //gsonHandler.saveInTwoGsonFiles(shelf, "test");
    }

    public void writeToGson(){

    }

    @Test
    void readShelfFromTwoFiles() {
    }

    @Test
    void saveShelfInTwoFiles() {
    }
}