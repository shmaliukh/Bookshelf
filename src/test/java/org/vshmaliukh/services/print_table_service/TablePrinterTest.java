package org.vshmaliukh.services.print_table_service;

import org.junit.jupiter.api.Test;
import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;

import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.vshmaliukh.constants.ConstantsForTerminal.DATE_FORMAT;
import static org.vshmaliukh.services.print_table_service.TablePrinter.*;

class TablePrinterTest {

    PrintWriter printWriter = new PrintWriter(System.out, true);
    TablePrinter tablePrinter = new TablePrinter(printWriter);

    @Test
    void testGetOneItemLine(){//TODO rename test name
        List<String> stringList = new ArrayList<>(Arrays.asList("0 item"));

        String expectedStr = ITEM_SEPARATOR + ITEM_SPACE + stringList.get(0) + ITEM_SPACE + ITEM_SEPARATOR;

        assertEquals(expectedStr, tablePrinter.getLineString(stringList));
    }

    @Test
    void testGetTwoItemsLine(){//TODO rename test name
        List<String> stringList = new ArrayList<>(Arrays.asList("0 item", "1 item"));

        String expectedStr = ITEM_SEPARATOR
                + ITEM_SPACE + stringList.get(0) + ITEM_SPACE + ITEM_SEPARATOR
                + ITEM_SPACE + stringList.get(1) + ITEM_SPACE + ITEM_SEPARATOR;

        assertEquals(expectedStr, tablePrinter.getLineString(stringList));
    }

    @Test
    void testGetThreeItemsLine(){//TODO rename test name
        List<String> stringList = new ArrayList<>(Arrays.asList("0 item", "1 item", "2 item"));

        String expectedStr = ITEM_SEPARATOR
                + ITEM_SPACE + stringList.get(0) + ITEM_SPACE + ITEM_SEPARATOR
                + ITEM_SPACE + stringList.get(1) + ITEM_SPACE + ITEM_SEPARATOR
                + ITEM_SPACE + stringList.get(2) + ITEM_SPACE + ITEM_SEPARATOR;

        assertEquals(expectedStr, tablePrinter.getLineString(stringList));
    }

    @Test
    void testGetThreeItemsLine_format(){//TODO rename test name
        List<String> stringList = new ArrayList<>(Arrays.asList("0 item", "1 item", "2 item"));

        String expectedStr = ITEM_SEPARATOR
                + ITEM_SPACE + stringList.get(0) + ITEM_SPACE + ITEM_SEPARATOR
                + ITEM_SPACE + stringList.get(1) + ITEM_SPACE + ITEM_SEPARATOR
                + ITEM_SPACE + stringList.get(2) + ITEM_SPACE + ITEM_SEPARATOR;

        assertEquals(expectedStr, tablePrinter.titleList.toString());
    }

    @Test
    void testTableSort(){
        String expectedStr = "[[0, 1, 2, 3], [0, 1, 2], [0, 1]]";
        List<String> stringList1 = new ArrayList<>(Arrays.asList("0","1"));
        List<String> stringList2 = new ArrayList<>(Arrays.asList("0","1","2"));
        List<String> stringList3 = new ArrayList<>(Arrays.asList("0","1","2","3"));
        List<List<String>> tableList = new ArrayList<>();
        tableList.add(stringList1);
        tableList.add(stringList2);
        tableList.add(stringList3);
        tablePrinter = new TablePrinter(printWriter, new ArrayList<>(), tableList);

        tablePrinter.sortTable();
        assertEquals(expectedStr, tablePrinter.tableList.toString());
    }

    @Test
    void testFillSortedTableWithEmptyValuesIfNecessary(){
        String expectedStr = "[[0, 1], [0, "+ EMPTY_VALUE +"]]";
        List<String> stringList1 = new ArrayList<>(Arrays.asList("0"));
        List<String> stringList2 = new ArrayList<>(Arrays.asList("0","1"));
        List<List<String>> tableList = new ArrayList<>();
        tableList.add(stringList1);
        tableList.add(stringList2);
        tablePrinter = new TablePrinter(printWriter, new ArrayList<>(), tableList);

        tablePrinter.sortTable();
        tablePrinter.fillAllWithDefaultValues();
        assertEquals(expectedStr, tablePrinter.tableList.toString());
    }

    @Test
    void testCountTableMaxSpaceWidth(){
        String expectedStr = "[[0, 1], [0, "+ EMPTY_VALUE +"]]";
        List<String> stringList1 = new ArrayList<>(Arrays.asList("0_","1__"));
        List<String> stringList2 = new ArrayList<>(Arrays.asList("0__","1","2"));
        List<String> stringList3 = new ArrayList<>(Arrays.asList("0","1","2","3"));
        List<List<String>> tableList = new ArrayList<>();
        tableList.add(stringList1);
        tableList.add(stringList2);
        tableList.add(stringList3);
        tablePrinter = new TablePrinter(printWriter, new ArrayList<>(), tableList);

        tablePrinter.printTable();
        // TODO end the test
        //assertEquals(expectedStr, tablePrinter.sizeList);
    }


    @Test
    void test() throws ParseException {
        ConvertorToStringForMagazine convertorMagazine = new ConvertorToStringForMagazine();
        ConvertorToStringForBook convertorBook = new ConvertorToStringForBook();

        List<String> titleList = new ArrayList<>();
        List<List<String>> tableList = new ArrayList<>();

        Book book1 = new Book("noNameBook1",1,false,"NoAuthor1", DATE_FORMAT.parse("10-07-2022"));
        Book book2 = new Book("noNameBook2___",22,true,"NoAuthor2___",DATE_FORMAT.parse("13-07-2022"));

        Magazine magazine1 = new Magazine("noNameMagazine1",1,false);
        Magazine magazine2 = new Magazine("noNameMagazine2___",222222222,true);

        tableList.add(convertorMagazine.convertObjectToListOfString(magazine1));
        tableList.add(convertorMagazine.convertObjectToListOfString(magazine2));
        tableList.add(convertorBook.convertObjectToListOfString(book1));
        tableList.add(convertorBook.convertObjectToListOfString(book2));

        //titleList.addAll(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));

        tablePrinter = new TablePrinter(printWriter, titleList, tableList);
        tablePrinter.printTable();

    }


    @Test
    void testChar(){
        for (int i = 0; i < Character.MAX_VALUE; i++) {
            System.out.println(i + " = " + (char) i);
        }
    }
}