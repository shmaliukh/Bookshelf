package org.ShmaliukhVlad;
import jdk.jfr.Description;
import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Book;
import org.ShmaliukhVlad.Bookshelf.Shelf;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {



    @Test
    @DisplayName("Add one NOT borrowed Book to empty Shelf")
    @Description("Simple add one Book which is NOT borrowed (isBorrowed = false) to empty Shelf")
    void addBookToShelf_0() {
        Shelf shelf1 = new Shelf();
        Book book1 = new Book("1",1,false,"NoAuthor1", LocalDate.now());
        shelf1.addLiteratureObject(book1);

        assertTrue(shelf1.getLiteratureInShelf().get(0) instanceof Book
                && shelf1.getLiteratureOutShelf().isEmpty());
    }
}
