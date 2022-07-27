package org.vshmaliukh.terminal.services;

import org.junit.jupiter.api.Test;
import org.vshmaliukh.terminal.bookshelf.literature_items.book_item.Book;
import org.vshmaliukh.terminal.bookshelf.literature_items.gazette_item.Gazette;
import org.vshmaliukh.terminal.bookshelf.literature_items.magazine_item.Magazine;
import org.vshmaliukh.terminal.bookshelf.literature_items.book_item.BookHandler;
import org.vshmaliukh.terminal.bookshelf.literature_items.gazette_item.GazetteHandler;
import org.vshmaliukh.terminal.bookshelf.literature_items.magazine_item.MagazineHandler;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.vshmaliukh.terminal.Terminal.DATE_FORMAT;

class ConvertorToStringForItemTest {

    BookHandler bookHandler = new BookHandler();
    MagazineHandler magazineHandler = new MagazineHandler();
    GazetteHandler gazetteHandler = new GazetteHandler();
    
    Book book1 = new Book("noNameBook1",1,false,"NoAuthor1", DATE_FORMAT.parse("10-07-2022"));
    Book book2 = new Book("noNameBook2___",22,true,"NoAuthor2___",DATE_FORMAT.parse("13-07-2022"));

    Magazine magazine1 = new Magazine("noNameMagazine1",1,false);
    Magazine magazine2 = new Magazine("noNameMagazine2___",222222222,true);

    Gazette gazette1 = new Gazette("noNameGazette1",1,false);
    Gazette gazette2 = new Gazette("noNameGazette2___",222222222,true);

    ConvertorToStringForItemTest() throws ParseException {
    }

    @Test
    void testConvertLiteratureObjectToListOfString_magazine1() {
        String expectedStr = "{PAGES=1, BORROWED=false, TYPE=Magazine, NAME=noNameMagazine1}";
        assertEquals(expectedStr, magazineHandler.convertItemToListOfString(magazine1).toString());
    }
    @Test
    void testConvertLiteratureObjectToListOfString_magazine2() {
        String expectedStr = "{PAGES=222222222, BORROWED=true, TYPE=Magazine, NAME=noNameMagazine2___}";
        assertEquals(expectedStr, magazineHandler.convertItemToListOfString(magazine2).toString());
    }

    @Test
    void testConvertLiteratureObjectToListOfString_gazette1() {
        String expectedStr = "{PAGES=1, BORROWED=false, TYPE=Gazette, NAME=noNameGazette1}";
        assertEquals(expectedStr, gazetteHandler.convertItemToListOfString(gazette1).toString());
    }
    @Test
    void testConvertLiteratureObjectToListOfString_gazette2() {
        String expectedStr = "{PAGES=222222222, BORROWED=true, TYPE=Gazette, NAME=noNameGazette2___}";
        assertEquals(expectedStr, gazetteHandler.convertItemToListOfString(gazette2).toString());
    }

    @Test
    void testConvertLiteratureObjectToListOfString_book1() {
        String expectedStr = "{PAGES=1, DATE=10-07-2022, BORROWED=false, AUTHOR=NoAuthor1, TYPE=Book, NAME=noNameBook1}";
        assertEquals(expectedStr, bookHandler.convertItemToListOfString(book1).toString());
    }

    @Test
    void testConvertLiteratureObjectToListOfString_book2() {
        String expectedStr = "{PAGES=22, DATE=13-07-2022, BORROWED=true, AUTHOR=NoAuthor2___, TYPE=Book, NAME=noNameBook2___}";
        assertEquals(expectedStr, bookHandler.convertItemToListOfString(book2).toString());
    }
}