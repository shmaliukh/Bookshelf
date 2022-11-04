package org.vshmaliukh.services;

import org.junit.jupiter.api.Test;
import org.vshmaliukh.shelf.literature_items.book_item.Book;
import org.vshmaliukh.shelf.literature_items.book_item.BookHandler;
import org.vshmaliukh.shelf.literature_items.magazine_item.Magazine;
import org.vshmaliukh.shelf.literature_items.magazine_item.MagazineHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.vshmaliukh.BaseAppConfig.DATE_FORMAT_STR;

class ConvertorToStringForItemTest {

    BookHandler bookHandler = new BookHandler();
    MagazineHandler magazineHandler = new MagazineHandler();
    
    Book book1 = new Book("noNameBook1",1,false,"NoAuthor1", new SimpleDateFormat(DATE_FORMAT_STR).parse("2022-07-10"));
    Book book2 = new Book("noNameBook2___",22,true,"NoAuthor2___", new SimpleDateFormat(DATE_FORMAT_STR).parse("2022-07-13"));

    Magazine magazine1 = new Magazine("noNameMagazine1",1,false);
    Magazine magazine2 = new Magazine("noNameMagazine2___",222222222,true);


    ConvertorToStringForItemTest() throws ParseException {
    }

    @Test
    void testConvertLiteratureObjectToListOfString_magazine1() {
        String expectedStr = "{PAGES=1, BORROWED=no, TYPE=Magazine, NAME=noNameMagazine1}";
        assertEquals(expectedStr, magazineHandler.convertItemToMapOfString(magazine1).toString());
    }
    @Test
    void testConvertLiteratureObjectToListOfString_magazine2() {
        String expectedStr = "{PAGES=222222222, BORROWED=yes, TYPE=Magazine, NAME=noNameMagazine2___}";
        assertEquals(expectedStr, magazineHandler.convertItemToMapOfString(magazine2).toString());
    }

    @Test
    void testConvertLiteratureObjectToListOfString_book1() {
        String expectedStr = "{PAGES=1, DATE=2022-07-10, BORROWED=no, AUTHOR=NoAuthor1, TYPE=Book, NAME=noNameBook1}";
        assertEquals(expectedStr, bookHandler.convertItemToMapOfString(book1).toString());
    }

    @Test
    void testConvertLiteratureObjectToListOfString_book2() {
        String expectedStr = "{PAGES=22, DATE=2022-07-13, BORROWED=yes, AUTHOR=NoAuthor2___, TYPE=Book, NAME=noNameBook2___}";
        assertEquals(expectedStr, bookHandler.convertItemToMapOfString(book2).toString());
    }
}