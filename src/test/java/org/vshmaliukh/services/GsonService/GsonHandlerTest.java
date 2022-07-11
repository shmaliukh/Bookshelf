package org.vshmaliukh.services.GsonService;

import org.junit.jupiter.api.Test;
import org.vshmaliukh.bookshelf.Shelf;
import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.vshmaliukh.constants.ConstantsForGsonHandler.*;

class GsonHandlerTest {
    String userName = "test";
    PrintWriter printWriter = new PrintWriter(System.out , true);
    GsonHandler gsonHandler = new GsonHandler(userName, printWriter);

    Book book1 = new Book("noNameBook1",1,false,"NoAuthor1", new Date(System.currentTimeMillis() - 60*60*64*1000));
    Book book2 = new Book("noNameBook2",2,true,"NoAuthor2",new Date());

    Book expectedBook1 = new Book("noNameBook1",1,false,"NoAuthor1", new Date(System.currentTimeMillis() - 60*60*64*1000));
    Book expectedBook2 = new Book("noNameBook2",2,true,"NoAuthor2",new Date());

    Magazine magazine1 = new Magazine("noNameMagazine1",1,false);
    Magazine magazine2 = new Magazine("noNameMagazine2",2,true);

    Magazine expectedMagazine1 = new Magazine("noNameMagazine1",1,false);
    Magazine expectedMagazine2 = new Magazine("noNameMagazine2",2,true);

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
    void readShelfFromGsonFile() throws IOException {
        shelf.addLiteratureObject(book1);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(magazine1);
        shelf.addLiteratureObject(magazine2);

        gsonHandler.saveInOneGsonFile(shelf);
        Shelf shelf2 = gsonHandler.readShelfFromGsonFile();

        assertEquals(expectedMagazine1.toString(), shelf2.getMagazines().get(0).toString());
        assertEquals(expectedMagazine2.toString(), shelf2.getMagazines().get(1).toString());
        assertEquals(expectedBook1.toString(), shelf2.getBooks().get(0).toString());
        assertEquals(expectedBook2.toString(), shelf2.getBooks().get(1).toString());
        //assertTrue(Files.deleteIfExists(Paths.get(HOME_PROPERTY, (userName + SHELF_FILE_NAME_PREFIX + FILE_TYPE))));
    }

    @Test
    void saveShelfInOneGsonFileTest() throws IOException {
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(book1);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(magazine1);
        shelf.addLiteratureObject(magazine2);

        gsonHandler.saveInOneGsonFile(shelf);
        //assertTrue(Files.deleteIfExists(Paths.get(HOME_PROPERTY, (userName + SHELF_FILE_NAME_PREFIX + FILE_TYPE))));

    }

    @Test
    void saveShelfInTwoGsonFilesTest() throws IOException {
        shelf.addLiteratureObject(new Magazine("name1", 1, true));
        shelf.addLiteratureObject(new Magazine("name2", 2, false));
        shelf.addLiteratureObject(new Book("name3", 3, false, "author1", new Date()));
        shelf.addLiteratureObject(new Book("name4", 4, true, "author2", new Date()));

        gsonHandler.saveInTwoGsonFiles(shelf);
        // TODO add test
        //assertTrue(Files.deleteIfExists(Paths.get(HOME_PROPERTY, (userName + BOOKS_FILE_NAME_PREFIX + FILE_TYPE))));
        //assertTrue(Files.deleteIfExists(Paths.get(HOME_PROPERTY, (userName + MAGAZINES_FILE_NAME_PREFIX+ FILE_TYPE))));
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