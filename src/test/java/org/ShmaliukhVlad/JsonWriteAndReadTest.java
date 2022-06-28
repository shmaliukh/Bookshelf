package org.ShmaliukhVlad;

import com.google.gson.Gson;
import com.sun.org.glassfish.gmbal.Description;
import org.ShmaliukhVlad.bookshelf.Shelf;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Magazine;
import org.ShmaliukhVlad.serices.ShelfContainer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonWriteAndReadTest {
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
    @DisplayName("test read json file")
    void testReadLiteratureFromJson() throws IOException, ClassNotFoundException {
        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(book1);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(magazine1);
        shelf.addLiteratureObject(magazine2);

        shelf.saveShelfToFile(testFileName);

        Shelf shelf2 = new Shelf();
        shelf2.deserialize(testFileName);

        assertEquals(expectedBook1.getPrintableLineOfLiteratureObject(), shelf2.getBooks().get(0).getPrintableLineOfLiteratureObject());
        assertEquals(expectedBook2.getPrintableLineOfLiteratureObject(), shelf2.getBooks().get(1).getPrintableLineOfLiteratureObject());
        assertEquals(expectedMagazine1.getPrintableLineOfLiteratureObject(), shelf2.getMagazines().get(0).getPrintableLineOfLiteratureObject());
        assertEquals(expectedMagazine2.getPrintableLineOfLiteratureObject(), shelf2.getMagazines().get(1).getPrintableLineOfLiteratureObject());

    }

    @Test
    @DisplayName("test to write Shelf with books in file")
    @Description("Test to write info about Shelf only with books objects")
    void testWriteLiterature_books() throws IOException {
        Shelf shelf1 = new Shelf();
        Shelf shelf2 = new Shelf();

        shelf1.addLiteratureObject(book1);
        shelf1.addLiteratureObject(book2);

        shelf1.saveShelfToFile(testFileName);

        Path path = new File(testFileName).toPath();
        ShelfContainer shelfContainer = new Gson().fromJson(Files.newBufferedReader(path, StandardCharsets.UTF_8), ShelfContainer.class);

        for (Book book : shelfContainer.getBooks()) {
            shelf2.addLiteratureObject(book);
        }

        assertEquals(shelf1.getLiteratureInShelf().get(0).getName(), shelf2.getLiteratureInShelf().get(0).getName());
        assertEquals(shelf1.getLiteratureOutShelf().get(0).getName(), shelf2.getLiteratureOutShelf().get(0).getName());
        assertEquals(shelf1.getLiteratureInShelf().get(0).getPagesNumber(), shelf2.getLiteratureInShelf().get(0).getPagesNumber());
        assertEquals(shelf1.getLiteratureOutShelf().get(0).getPagesNumber(), shelf2.getLiteratureOutShelf().get(0).getPagesNumber());
    }

    @Test
    @DisplayName("test to write Shelf with magazines in file")
    @Description("Test to write info about Shelf only with magazines objects")
    void testWriteLiterature_magazines() throws IOException {
        String testFileName = "testShelf.json";
        Shelf shelf1 = new Shelf();
        Shelf shelf2 = new Shelf();

        shelf1.addLiteratureObject(magazine1);
        shelf1.addLiteratureObject(magazine2);

        shelf1.saveShelfToFile(testFileName);

        Path path = new File(testFileName).toPath();
        ShelfContainer shelfContainer = new Gson().fromJson(Files.newBufferedReader(path, StandardCharsets.UTF_8), ShelfContainer.class);

        for (Magazine magazine : shelfContainer.getMagazines()) {
            shelf2.addLiteratureObject(magazine);
        }

        assertEquals(shelf1.getLiteratureInShelf().get(0).getName(), shelf2.getLiteratureInShelf().get(0).getName());
        assertEquals(shelf1.getLiteratureOutShelf().get(0).getName(), shelf2.getLiteratureOutShelf().get(0).getName());
        assertEquals(shelf1.getLiteratureInShelf().get(0).getPagesNumber(), shelf2.getLiteratureInShelf().get(0).getPagesNumber());
        assertEquals(shelf1.getLiteratureOutShelf().get(0).getPagesNumber(), shelf2.getLiteratureOutShelf().get(0).getPagesNumber());
    }
}
