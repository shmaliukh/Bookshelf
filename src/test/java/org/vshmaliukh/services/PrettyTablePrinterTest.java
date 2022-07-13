package org.vshmaliukh.services;

import org.junit.jupiter.api.Test;
import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Literature;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrettyTablePrinterTest {

    PrettyTablePrinter prettyTablePrinter = new PrettyTablePrinter(new PrintWriter(System.out, true));

    Book book1 = new Book("noNameBook1",1,false,"NoAuthor1", new Date(System.currentTimeMillis() - 60*60*64*1000));
    Book book2 = new Book("noNameBook2___",22,true,"NoAuthor2___",new Date());

    Magazine magazine1 = new Magazine("noNameMagazine1",1,false);
    Magazine magazine2 = new Magazine("noNameMagazine2___",222222222,true);


    @Test
    void getPrettyStringOfLiteratureTest_magazine1() {
        String expectedStr = "| 1 | Magazine | noNameMagazine1 | 1     | false  |        |      |";
        List<Literature> literatureList = new ArrayList<>();
        literatureList.add(magazine1);

        prettyTablePrinter.setFormat(literatureList);
        assertEquals(expectedStr, prettyTablePrinter.getPrettyStringOfLiterature(1, magazine1));
    }

    @Test
    void getPrettyStringOfLiteratureTest_magazine2() {
        String expectedStr = "| 2 | Magazine | noNameMagazine2___ | 222222222 | true   |        |      |";
        List<Literature> literatureList = new ArrayList<>();
        literatureList.add(magazine2);

        prettyTablePrinter.setFormat(literatureList);
        assertEquals(expectedStr, prettyTablePrinter.getPrettyStringOfLiterature(2, magazine2));
    }

    @Test
    void getPrettyStringOfLiteratureTest_twoMagazines() {
        String expectedStr1 = "| 1 | Magazine | noNameMagazine1    | 1         | false  |        |      |";
        String expectedStr2 = "| 2 | Magazine | noNameMagazine2___ | 222222222 | true   |        |      |";
        List<Literature> literatureList = new ArrayList<>();
        literatureList.add(magazine1);
        literatureList.add(magazine2);

        prettyTablePrinter.setFormat(literatureList);
        assertEquals(expectedStr1, prettyTablePrinter.getPrettyStringOfLiterature(1, magazine1));
        assertEquals(expectedStr2, prettyTablePrinter.getPrettyStringOfLiterature(2, magazine2));
    }

    @Test
    void getPrettyStringOfLiteratureTest_book1_magazine1() {
        String expectedStr1 = "| 1 | Book     | noNameBook1     | 1     | false  | NoAuthor1 | 10-07-2022 |";
        String expectedStr2 = "| 2 | Magazine | noNameMagazine1 | 1     | false  |           |            |";
        List<Literature> literatureList = new ArrayList<>();
        literatureList.add(book1);
        literatureList.add(magazine1);

        prettyTablePrinter.setFormat(literatureList);
        assertEquals(expectedStr1, prettyTablePrinter.getPrettyStringOfLiterature(1, book1));
        assertEquals(expectedStr2, prettyTablePrinter.getPrettyStringOfLiterature(2, magazine1));
    }

    @Test
    void getPrettyStringOfLiteratureTest_book1_magazine2() {
        String expectedStr1 = "| 1 | Book     | noNameBook1        | 1         | false  | NoAuthor1 | 10-07-2022 |";
        String expectedStr2 = "| 2 | Magazine | noNameMagazine2___ | 222222222 | true   |           |            |";
        List<Literature> literatureList = new ArrayList<>();
        literatureList.add(book1);
        literatureList.add(magazine2);

        prettyTablePrinter.setFormat(literatureList);
        assertEquals(expectedStr1, prettyTablePrinter.getPrettyStringOfLiterature(1, book1));
        assertEquals(expectedStr2, prettyTablePrinter.getPrettyStringOfLiterature(2, magazine2));
    }

    @Test
    void getPrettyStringOfLiteratureTest_twoBooks_twoMagazinesTest() {
        String expectedStr1 = "| 1 | Book     | noNameBook1        | 1         | false  | NoAuthor1    | 10-07-2022 |";
        String expectedStr2 = "| 2 | Book     | noNameBook2___     | 22        | true   | NoAuthor2___ | 13-07-2022 |";
        String expectedStr3 = "| 3 | Magazine | noNameMagazine1    | 1         | false  |              |            |";
        String expectedStr4 = "| 4 | Magazine | noNameMagazine2___ | 222222222 | true   |              |            |";
        List<Literature> literatureList = new ArrayList<>();
        literatureList.add(book1);
        literatureList.add(book2);
        literatureList.add(magazine1);
        literatureList.add(magazine2);

        prettyTablePrinter.setFormat(literatureList);
        assertEquals(expectedStr1, prettyTablePrinter.getPrettyStringOfLiterature(1, book1));
        assertEquals(expectedStr2, prettyTablePrinter.getPrettyStringOfLiterature(2, book2));
        assertEquals(expectedStr3, prettyTablePrinter.getPrettyStringOfLiterature(3, magazine1));
        assertEquals(expectedStr4, prettyTablePrinter.getPrettyStringOfLiterature(4, magazine2));
    }

    @Test
    void getPrettyStringOfLiteratureTest_indexResizeTest() {
        String expectedStr1 =  "| 1  | Book     | noNameBook1 | 1     | false  | NoAuthor1 | 10-07-2022 |";
        String expectedStr10 = "| 10 | Book     | noNameBook1 | 1     | false  | NoAuthor1 | 10-07-2022 |";
        List<Literature> literatureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            literatureList.add(book1);
        }

        prettyTablePrinter.setFormat(literatureList);
        assertEquals(expectedStr1, prettyTablePrinter.getPrettyStringOfLiterature(1, book1));
        assertEquals(expectedStr10, prettyTablePrinter.getPrettyStringOfLiterature(10, book1));
    }

    @Test
    void getPrettyStringOfLiteratureTest_book1() {
        String expectedStr = "| 1 | Book     | noNameBook1 | 1     | false  | NoAuthor1 | 10-07-2022 |";
        List<Literature> literatureList = new ArrayList<>();
        literatureList.add(book1);

        prettyTablePrinter.setFormat(literatureList);
        assertEquals(expectedStr, prettyTablePrinter.getPrettyStringOfLiterature(1, book1));
    }

    @Test
    void getPrettyStringOfLiteratureTest_book2() {
        String expectedStr = "| 2 | Book     | noNameBook2___ | 22    | true   | NoAuthor2___ | 13-07-2022 |";
        List<Literature> literatureList = new ArrayList<>();
        literatureList.add(book2);

        prettyTablePrinter.setFormat(literatureList);
        assertEquals(expectedStr, prettyTablePrinter.getPrettyStringOfLiterature(2, book2));
    }

    @Test
    void getPrettyStringOfLiteratureTest_twoBooks() {
        String expectedStr1 = "| 1 | Book     | noNameBook1    | 1     | false  | NoAuthor1    | 10-07-2022 |";
        String expectedStr2 = "| 2 | Book     | noNameBook2___ | 22    | true   | NoAuthor2___ | 13-07-2022 |";
        List<Literature> literatureList = new ArrayList<>();
        literatureList.add(book1);
        literatureList.add(book2);

        prettyTablePrinter.setFormat(literatureList);
        assertEquals(expectedStr1, prettyTablePrinter.getPrettyStringOfLiterature(1, book1));
        assertEquals(expectedStr2, prettyTablePrinter.getPrettyStringOfLiterature(2, book2));
    }
}