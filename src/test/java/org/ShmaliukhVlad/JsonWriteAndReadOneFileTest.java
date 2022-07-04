package org.ShmaliukhVlad;

import org.ShmaliukhVlad.bookshelf.Shelf;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Magazine;
import org.ShmaliukhVlad.services.GsonService.ReadFromGsonService;
import org.ShmaliukhVlad.services.GsonService.SaveToGsonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Date;

import static org.ShmaliukhVlad.constants.ConstantValues.SAVE_READ_ONE_FILE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonWriteAndReadOneFileTest {
    String testFileName = "testShelf.json";

    Book book1 = new Book("noNameBook1",1,false,"NoAuthor1", new Date(System.currentTimeMillis() - 60*60*64*1000));
    Book book2 = new Book("noNameBook2",2,true,"NoAuthor2",new Date());

    Book expectedBook1 = new Book("noNameBook1",1,false,"NoAuthor1", new Date(System.currentTimeMillis() - 60*60*64*1000));
    Book expectedBook2 = new Book("noNameBook2",2,true,"NoAuthor2",new Date());

    Magazine magazine1 = new Magazine("noNameMagazine1",1,false);
    Magazine magazine2 = new Magazine("noNameMagazine2",2,true);

    Magazine expectedMagazine1 = new Magazine("noNameMagazine1",1,false);
    Magazine expectedMagazine2 = new Magazine("noNameMagazine2",2,true);

    @Test
    @DisplayName("test to read shelf from file")
    void readFileTest() throws IOException, ClassNotFoundException {
        Book expectedBook = new Book("book name", 111, false, "book author", new Date(1000000));
        Magazine expectedMagazine = new Magazine("book name", 222, true);

        Shelf shelf = ReadFromGsonService.readShelfFromGson("src/test/resources/testGsonReader.json",1);
        assertEquals(expectedBook.getPrintableLineOfLiteratureObject(), shelf.getBooks().get(0).getPrintableLineOfLiteratureObject());
        assertEquals(expectedMagazine.getPrintableLineOfLiteratureObject(), shelf.getMagazines().get(0).getPrintableLineOfLiteratureObject());
    }

    @Test
    @DisplayName("test to read created json shelf file by program")
    void testReadCreatedGsonFile() throws IOException{
        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(book1);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(magazine1);
        shelf.addLiteratureObject(magazine2);

        SaveToGsonService.saveShelfToGsonFile(shelf, testFileName, SAVE_READ_ONE_FILE);

        Shelf shelf2 = ReadFromGsonService.readShelfFromGson(testFileName,SAVE_READ_ONE_FILE);

        assertEquals(expectedBook1.getPrintableLineOfLiteratureObject(), shelf2.getBooks().get(0).getPrintableLineOfLiteratureObject());
        assertEquals(expectedBook2.getPrintableLineOfLiteratureObject(), shelf2.getBooks().get(1).getPrintableLineOfLiteratureObject());
        assertEquals(expectedMagazine1.getPrintableLineOfLiteratureObject(), shelf2.getMagazines().get(0).getPrintableLineOfLiteratureObject());
        assertEquals(expectedMagazine2.getPrintableLineOfLiteratureObject(), shelf2.getMagazines().get(1).getPrintableLineOfLiteratureObject());

    }

    @Test
    @DisplayName("test to write Shelf with books in file")
    void testWriteLiterature_books() throws IOException{
        Shelf shelf1 = new Shelf();
        shelf1.addLiteratureObject(book1);
        shelf1.addLiteratureObject(book2);

        SaveToGsonService.saveShelfToGsonFile(shelf1, testFileName, SAVE_READ_ONE_FILE);

        Shelf shelf2 = ReadFromGsonService.readShelfFromGson(testFileName, SAVE_READ_ONE_FILE);

        assertEquals(shelf1.getLiteratureInShelf().get(0).getName(), shelf2.getLiteratureInShelf().get(0).getName());
        assertEquals(shelf1.getLiteratureOutShelf().get(0).getName(), shelf2.getLiteratureOutShelf().get(0).getName());
        assertEquals(shelf1.getLiteratureInShelf().get(0).getPagesNumber(), shelf2.getLiteratureInShelf().get(0).getPagesNumber());
        assertEquals(shelf1.getLiteratureOutShelf().get(0).getPagesNumber(), shelf2.getLiteratureOutShelf().get(0).getPagesNumber());
    }

    @Test
    @DisplayName("test to write Shelf with magazines in file")
    void testWriteLiterature_magazines() throws IOException {
        Shelf shelf1 = new Shelf();

        shelf1.addLiteratureObject(magazine1);
        shelf1.addLiteratureObject(magazine2);

        SaveToGsonService.saveShelfToGsonFile(shelf1, testFileName, SAVE_READ_ONE_FILE);

        Shelf shelf2 = ReadFromGsonService.readShelfFromGson(testFileName, SAVE_READ_ONE_FILE);

        assertEquals(shelf1.getLiteratureInShelf().get(0).getName(), shelf2.getLiteratureInShelf().get(0).getName());
        assertEquals(shelf1.getLiteratureOutShelf().get(0).getName(), shelf2.getLiteratureOutShelf().get(0).getName());
        assertEquals(shelf1.getLiteratureInShelf().get(0).getPagesNumber(), shelf2.getLiteratureInShelf().get(0).getPagesNumber());
        assertEquals(shelf1.getLiteratureOutShelf().get(0).getPagesNumber(), shelf2.getLiteratureOutShelf().get(0).getPagesNumber());
    }
}
