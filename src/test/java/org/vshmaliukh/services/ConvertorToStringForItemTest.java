package org.vshmaliukh.services;

import org.junit.jupiter.api.Test;
import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Gazette;
import org.vshmaliukh.bookshelf.bookshelfObjects.Item;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;
import org.vshmaliukh.services.print_table_service.ConvertorToStringForBook;
import org.vshmaliukh.services.print_table_service.ConvertorToStringForGazette;
import org.vshmaliukh.services.print_table_service.ConvertorToStringForMagazine;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.vshmaliukh.constants.ConstantsForTerminal.DATE_FORMAT;

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
        String expectedStr = "[Magazine, noNameMagazine1, 1, false]";
        assertEquals(expectedStr, convertorMagazine.convertObjectToListOfString(magazine1).toString());
    }
    @Test
    void testConvertLiteratureObjectToListOfString_magazine2() {
        String expectedStr = "[Magazine, noNameMagazine2___, 222222222, true]";
        assertEquals(expectedStr, convertorMagazine.convertObjectToListOfString(magazine2).toString());
    }

    @Test
    void testConvertLiteratureObjectToListOfString_gazette1() {
        String expectedStr = "[Gazette, noNameGazette1, 1, false]";
        assertEquals(expectedStr, convertorGazette.convertObjectToListOfString(gazette1).toString());
    }
    @Test
    void testConvertLiteratureObjectToListOfString_gazette2() {
        String expectedStr = "[Gazette, noNameGazette2___, 222222222, true]";
        assertEquals(expectedStr, convertorGazette.convertObjectToListOfString(gazette2).toString());
    }

    @Test
    void testConvertLiteratureObjectToListOfString_book1() {
        String expectedStr = "[Book, noNameBook1, 1, false, NoAuthor1, 10-07-2022]";
        assertEquals(expectedStr, convertorBook.convertObjectToListOfString(book1).toString());
    }

    @Test
    void testConvertLiteratureObjectToListOfString_book2() {
        String expectedStr = "[Book, noNameBook2___, 22, true, NoAuthor2___, 13-07-2022]";
        assertEquals(expectedStr, convertorBook.convertObjectToListOfString(book2).toString());
    }

    @Test
    void testForClass_methods(){
        System.out.println("Literature.class.getClass() = " + Item.class.getPackage().getName());
        System.out.println("Literature.class.getClass() = " + Item.class.getClass());
        System.out.println("Literature.class.getName()  = " + Item.class.getName());
        System.out.println("Literature.class.getCanonicalName()  = " + Item.class.getCanonicalName());
        System.out.println("Literature.class.getSimpleName()  = " + Item.class.getSimpleName());
        System.out.println("Literature.class.getTypeName()  = " + Item.class.getTypeName());
        System.out.println("Literature.class.getName()  = " + Item.class.getName());
        System.out.println("Literature.class.getName()  = " + Item.class.getName());
        Item.class.getClass();
    }

    //@Test
    //void testGetConvertedToStringList() {
    //    String expectedStr = "[[Book, noNameBook1, 1, false, NoAuthor1, 10-07-2022], " +
    //            "[Book, noNameBook2___, 22, true, NoAuthor2___, 13-07-2022], " +
    //            "[Magazine, noNameMagazine1, 1, false], " +
    //            "[Magazine, noNameMagazine2___, 222222222, true]]";
    //    List<Literature> stringList = new ArrayList<>();
    //    stringList.add(book1);
    //    stringList.add(book2);
    //    stringList.add(magazine1);
    //    stringList.add(magazine2);
//
    //    assertEquals(expectedStr, convertor.convertObjectToListOfStringt(stringList).toString());
    //}
}