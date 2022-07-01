package org.ShmaliukhVlad;

import org.ShmaliukhVlad.bookshelf.Shelf;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Magazine;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonWriteAndReadTwoFilesTest {
    Book book1 = new Book("noNameBook1",1,false,"NoAuthor1", new Date(System.currentTimeMillis() - 60*60*64*1000));
    Book book2 = new Book("noNameBook2",2,true,"NoAuthor2",new Date());

    Magazine magazine1 = new Magazine("noNameMagazine1",1,false);
    Magazine magazine2 = new Magazine("noNameMagazine2",2,true);


    @Test
    void saveBooksToFile() throws IOException {
        Path path = Paths.get("my_temp/");
        String expectedBooksFileName = "testInDiffFileBooks.json";
        String expectedMagazinesFileName = "testInDiffFileMagazines.json";

        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(book1);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(magazine1);
        shelf.addLiteratureObject(magazine2);

        shelf.saveShelfToDifferentFiles("testInDiffFile");
        Path path1 = Paths.get(expectedBooksFileName);
        Path path2 = Paths.get(expectedMagazinesFileName);

        assertTrue(Files.exists(path1));
        assertTrue(Files.exists(path2));
        //TODO test files inside
        //assertTrue(Arrays.equals(, Files.readAllBytes(Paths.get("src/test/resources/shelfInDiffFileBooks.json"))));

    }



}
