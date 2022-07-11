package org.vshmaliukh;

import org.vshmaliukh.bookshelf.Shelf;
import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;
import org.vshmaliukh.services.GsonService.ReadFromGsonService;
import org.vshmaliukh.services.GsonService.SaveToGsonService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonWriteAndReadTwoFilesTest {
    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(System.out));
    
    ReadFromGsonService readFromGsonService = new ReadFromGsonService(printWriter);
    SaveToGsonService saveToGsonService = new SaveToGsonService();

    Book book1 = new Book("noNameBook1",1,false,"NoAuthor1", new Date("Jun 28, 2022 10:47:50 PM"));
    Book book2 = new Book("noNameBook2",2,true,"NoAuthor2",new Date("Jul 1, 2022 2:47:50 PM"));

    Magazine magazine1 = new Magazine("noNameMagazine1",1,false);
    Magazine magazine2 = new Magazine("noNameMagazine2",2,true);

    public JsonWriteAndReadTwoFilesTest() throws ParseException {
    }


    @Test
    void saveBooksToFile() throws IOException {
        String expectedBooksFileName = "testInDiffFileBooks.json";
        String expectedMagazinesFileName = "testInDiffFileMagazines.json";

        Shelf shelf = new Shelf(printWriter);
        shelf.addLiteratureObject(book1);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(magazine1);
        shelf.addLiteratureObject(magazine2);

        saveToGsonService.saveShelfInTwoFiles(shelf,"testInDiffFile");
        Path path1 = Paths.get(expectedBooksFileName);
        Path path2 = Paths.get(expectedMagazinesFileName);

        assertTrue(Files.exists(path1));
        assertTrue(Files.exists(path2));
        //assertEquals( Files.readAllBytes(Paths.get("src/test/resources/shelfInDiffFileBooks.json")).length, Files.readAllBytes(path1).length);
        //assertEquals( Files.readAllBytes(Paths.get("src/test/resources/shelfInDiffFileMagazines.json")).length, Files.readAllBytes(path2).length);
        // FIXME
        // TODO new assert


    }

    //@Test
    //void readFromFiles_twoArg() throws FileNotFoundException {
    //    Shelf shelf = readFromGsonService.readShelfFromTwoFiles("src/test/resources/shelfInDiffFileBooks.json", "src/test/resources/shelfInDiffFileMagazines.json");
//
    //    assertEquals(book1.toString(),shelf.getBooks().get(0).toString());
    //    assertEquals(book2.toString(),shelf.getBooks().get(1).toString());
    //    assertEquals(magazine1.toString(),shelf.getMagazines().get(0).toString());
    //    assertEquals(magazine2.toString(),shelf.getMagazines().get(1).toString());
    //}
    //@Test
    //void readFromFiles_oneArg() throws FileNotFoundException {
    //    Shelf shelf = readFromGsonService.readShelfFromTwoFiles("src/test/resources/shelfInDiffFile");
//
    //    assertEquals(book1.toString(),shelf.getBooks().get(0).toString());
    //    assertEquals(book2.toString(),shelf.getBooks().get(1).toString());
    //    assertEquals(magazine1.toString(),shelf.getMagazines().get(0).toString());
    //    assertEquals(magazine2.toString(),shelf.getMagazines().get(1).toString());
    //}



}
