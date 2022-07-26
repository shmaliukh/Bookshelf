package org.vshmaliukh.services;

import org.junit.jupiter.api.Test;
import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Gazette;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;
import org.vshmaliukh.services.print_table_service.convertors.ConvertorToStringForBook;
import org.vshmaliukh.services.print_table_service.convertors.ConvertorToStringForGazette;
import org.vshmaliukh.services.print_table_service.convertors.ConvertorToStringForMagazine;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.vshmaliukh.Terminal.DATE_FORMAT;

class ConvertorToStringForItemTest {

    ConvertorToStringForMagazine convertorMagazine = new ConvertorToStringForMagazine();
    ConvertorToStringForBook convertorBook = new ConvertorToStringForBook();
    ConvertorToStringForGazette convertorGazette = new ConvertorToStringForGazette();

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
        assertEquals(expectedStr, convertorMagazine.convertObjectToListOfString(magazine1).toString());
    }
    @Test
    void testConvertLiteratureObjectToListOfString_magazine2() {
        String expectedStr = "{PAGES=222222222, BORROWED=true, TYPE=Magazine, NAME=noNameMagazine2___}";
        assertEquals(expectedStr, convertorMagazine.convertObjectToListOfString(magazine2).toString());
    }

    @Test
    void testConvertLiteratureObjectToListOfString_gazette1() {
        String expectedStr = "{PAGES=1, BORROWED=false, TYPE=Gazette, NAME=noNameGazette1}";
        assertEquals(expectedStr, convertorGazette.convertObjectToListOfString(gazette1).toString());
    }
    @Test
    void testConvertLiteratureObjectToListOfString_gazette2() {
        String expectedStr = "{PAGES=222222222, BORROWED=true, TYPE=Gazette, NAME=noNameGazette2___}";
        assertEquals(expectedStr, convertorGazette.convertObjectToListOfString(gazette2).toString());
    }

    @Test
    void testConvertLiteratureObjectToListOfString_book1() {
        String expectedStr = "{PAGES=1, DATE=10-07-2022, BORROWED=false, AUTHOR=NoAuthor1, TYPE=Book, NAME=noNameBook1}";
        assertEquals(expectedStr, convertorBook.convertObjectToListOfString(book1).toString());
    }

    @Test
    void testConvertLiteratureObjectToListOfString_book2() {
        String expectedStr = "{PAGES=22, DATE=13-07-2022, BORROWED=true, AUTHOR=NoAuthor2___, TYPE=Book, NAME=noNameBook2___}";
        assertEquals(expectedStr, convertorBook.convertObjectToListOfString(book2).toString());
    }
}