package org.vshmaliukh.services.gson_service;

import org.junit.jupiter.api.Test;
import org.vshmaliukh.bookshelf.Shelf;
import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;

import java.io.FileWriter;
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

    Book book1 = new Book("noNameBook1",1,false,"NoAuthor1", new Date(System.currentTimeMillis() - 60*60*64*1000));
    Book book2 = new Book("noNameBook2",2,true,"NoAuthor2",new Date());

    Book expectedBook1 = new Book("noNameBook1",1,false,"NoAuthor1", new Date(System.currentTimeMillis() - 60*60*64*1000));
    Book expectedBook2 = new Book("noNameBook2",2,true,"NoAuthor2",new Date());

    Magazine magazine1 = new Magazine("noNameMagazine1",1,false);
    Magazine magazine2 = new Magazine("noNameMagazine2",2,true);

    Magazine expectedMagazine1 = new Magazine("noNameMagazine1",1,false);
    Magazine expectedMagazine2 = new Magazine("noNameMagazine2",2,true);

    @Test
    void readWriteOneFileTest_brokenFile() throws IOException {
        userName = "testProblemToReadBrokenShelfFile";
        GsonHandler gsonHandlerOneFile  = new GsonHandler(WORK_WITH_ONE_FILE, userName, printWriter);
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(book1);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(magazine1);
        shelf.addLiteratureObject(magazine2);

        gsonHandlerOneFile.saveShelfInGson(shelf);
        FileWriter fw = new FileWriter(String.valueOf(Paths.get(HOME_PROPERTY, (userName + SHELF_FILE_NAME_PREFIX + FILE_TYPE))));
        fw.append("someStr");
        fw.flush();
        fw.close();
        Shelf shelf2 = gsonHandlerOneFile.readShelfFromGson();

        assertTrue(shelf2.getMagazines().isEmpty());
        assertTrue(shelf2.getBooks().isEmpty());
    }

    @Test
    void workWithTwoFilesTest_brokenTwoFile() throws IOException {
        String userName = "testProblemToReadBrokenBooksFile";
        GsonHandler gsonHandlerTwoFiles = new GsonHandler(WORK_WITH_TWO_FILES, userName, printWriter);
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(book1);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(magazine1);
        shelf.addLiteratureObject(magazine2);
        gsonHandlerTwoFiles.saveShelfInGson(shelf);

        FileWriter fw = new FileWriter(String.valueOf(Paths.get(HOME_PROPERTY, (userName + BOOKS_FILE_NAME_PREFIX + FILE_TYPE))));
        fw.append("someStr");
        fw.flush();
        fw = new FileWriter(String.valueOf(Paths.get(HOME_PROPERTY, (userName + MAGAZINES_FILE_NAME_PREFIX + FILE_TYPE))));
        fw.append("someStr");
        fw.flush();
        fw.close();
        Shelf shelf2 = gsonHandlerTwoFiles.readShelfFromGson();

        assertTrue(shelf2.getMagazines().isEmpty());
        assertTrue(shelf2.getBooks().isEmpty());
    }

    @Test
    void workWithTwoFilesTest_brokenBooksFile() throws IOException {
        String userName = "testProblemToReadBrokenBooksFile";
        GsonHandler gsonHandlerTwoFiles = new GsonHandler(WORK_WITH_TWO_FILES, userName, printWriter);
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(book1);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(magazine1);
        shelf.addLiteratureObject(magazine2);
        gsonHandlerTwoFiles.saveShelfInGson(shelf);

        FileWriter fw = new FileWriter(String.valueOf(Paths.get(HOME_PROPERTY, (userName + BOOKS_FILE_NAME_PREFIX + FILE_TYPE))));
        fw.append("someStr");
        fw.flush();
        fw.close();
        Shelf shelf2 = gsonHandlerTwoFiles.readShelfFromGson();

        assertEquals(expectedMagazine1.toString(), shelf2.getMagazines().get(0).toString());
        assertEquals(expectedMagazine2.toString(), shelf2.getMagazines().get(1).toString());
        assertTrue(shelf2.getBooks().isEmpty());
    }

    @Test
    void workWithTwoFilesTest_brokenMagazinesFile() throws IOException {
        String userName = "testProblemToReadBrokenMagazinesFile";
        GsonHandler gsonHandlerTwoFiles = new GsonHandler(WORK_WITH_TWO_FILES, userName, printWriter);
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(book1);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(magazine1);
        shelf.addLiteratureObject(magazine2);
        gsonHandlerTwoFiles.saveShelfInGson(shelf);

        FileWriter fw = new FileWriter(String.valueOf(Paths.get(HOME_PROPERTY, (userName + MAGAZINES_FILE_NAME_PREFIX + FILE_TYPE))));
        fw.append("someStr");
        fw.flush();
        fw.close();
        Shelf shelf2 = gsonHandlerTwoFiles.readShelfFromGson();

        assertEquals(expectedBook1.toString(), shelf2.getBooks().get(0).toString());
        assertEquals(expectedBook2.toString(), shelf2.getBooks().get(1).toString());
        assertTrue(shelf2.getMagazines().isEmpty());
    }

    @Test
    void workWithTwoFilesTest_booksFileNotExists() throws IOException {
        String userName = "testProblemToReadBooksFile";
        GsonHandler gsonHandlerTwoFiles = new GsonHandler(WORK_WITH_TWO_FILES, userName, printWriter);
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(book1);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(magazine1);
        shelf.addLiteratureObject(magazine2);
        gsonHandlerTwoFiles.saveShelfInGson(shelf);

        Files.deleteIfExists(Paths.get(HOME_PROPERTY, (userName + BOOKS_FILE_NAME_PREFIX + FILE_TYPE)));
        Shelf shelf2 = gsonHandlerTwoFiles.readShelfFromGson();

        assertEquals(expectedMagazine1.toString(), shelf2.getMagazines().get(0).toString());
        assertEquals(expectedMagazine2.toString(), shelf2.getMagazines().get(1).toString());
        assertTrue(shelf2.getBooks().isEmpty());
    }

    @Test
    void workWithTwoFilesTest_magazinesFileNotExists() throws IOException {
        String userName = "testProblemToReadMagazinesFile";
        GsonHandler gsonHandlerTwoFiles = new GsonHandler(WORK_WITH_TWO_FILES, userName, printWriter);
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(book1);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(magazine1);
        shelf.addLiteratureObject(magazine2);
        gsonHandlerTwoFiles.saveShelfInGson(shelf);

        Files.deleteIfExists(Paths.get(HOME_PROPERTY, (userName + MAGAZINES_FILE_NAME_PREFIX + FILE_TYPE)));
        Shelf shelf2 = gsonHandlerTwoFiles.readShelfFromGson();

        assertEquals(expectedBook1.toString(), shelf2.getBooks().get(0).toString());
        assertEquals(expectedBook2.toString(), shelf2.getBooks().get(1).toString());
        assertTrue(shelf2.getMagazines().isEmpty());
    }

    @Test
    void readWriteOneFileTest(){
        userName = "testOneFile";
        GsonHandler gsonHandlerOneFile  = new GsonHandler(WORK_WITH_ONE_FILE, userName, printWriter);
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(book1);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(magazine1);
        shelf.addLiteratureObject(magazine2);

        gsonHandlerOneFile.saveShelfInGson(shelf);
        Shelf shelf2 = gsonHandlerOneFile.readShelfFromGson();

        assertEquals(expectedMagazine1.toString(), shelf2.getMagazines().get(0).toString());
        assertEquals(expectedMagazine2.toString(), shelf2.getMagazines().get(1).toString());
        assertEquals(expectedBook1.toString(), shelf2.getBooks().get(0).toString());
        assertEquals(expectedBook2.toString(), shelf2.getBooks().get(1).toString());
    }

    @Test
    void readWriteTwoFilesTest(){
        userName = "testTwoFiles";
        GsonHandler gsonHandlerTwoFiles = new GsonHandler(WORK_WITH_TWO_FILES,userName, printWriter);
        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(book1);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(magazine1);
        shelf.addLiteratureObject(magazine2);

        gsonHandlerTwoFiles.saveShelfInGson(shelf);
        Shelf shelf2 = gsonHandlerTwoFiles.readShelfFromGson();

        assertEquals(expectedMagazine1.toString(), shelf2.getMagazines().get(0).toString());
        assertEquals(expectedMagazine2.toString(), shelf2.getMagazines().get(1).toString());
        assertEquals(expectedBook1.toString(), shelf2.getBooks().get(0).toString());
        assertEquals(expectedBook2.toString(), shelf2.getBooks().get(1).toString());
    }
}