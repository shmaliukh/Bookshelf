package org.ShmaliukhVlad;

import org.ShmaliukhVlad.bookshelf.Shelf;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Magazine;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Date;

public class JsonWriteAndReadTwoFilesTest {
    Book book1 = new Book("noNameBook1",1,false,"NoAuthor1", new Date(System.currentTimeMillis() - 60*60*64*1000));
    Book book2 = new Book("noNameBook2",2,true,"NoAuthor2",new Date());

    Magazine magazine1 = new Magazine("noNameMagazine1",1,false);
    Magazine magazine2 = new Magazine("noNameMagazine2",2,true);


    @Test
    void create() throws IOException {

    }

    @Test
    void saveBooksToFile() throws IOException {
        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(book1);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(magazine1);
        shelf.addLiteratureObject(magazine2);

        shelf.saveShelfToDifferentFiles("shelfInDiffFile");
        //Files.
    }



}
