package org.ShmaliukhVlad;

import org.ShmaliukhVlad.bookshelf.Shelf;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Magazine;
import org.ShmaliukhVlad.services.GsonService.ReadFromGsonService;
import org.ShmaliukhVlad.services.GsonService.SaveToGsonService;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonWriteAndReadTwoFilesTest {

    ReadFromGsonService readFromGsonService = new ReadFromGsonService();
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

        Shelf shelf = new Shelf();
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
    //    assertEquals(book1.getPrintableLineOfLiteratureObject(),shelf.getBooks().get(0).getPrintableLineOfLiteratureObject());
    //    assertEquals(book2.getPrintableLineOfLiteratureObject(),shelf.getBooks().get(1).getPrintableLineOfLiteratureObject());
    //    assertEquals(magazine1.getPrintableLineOfLiteratureObject(),shelf.getMagazines().get(0).getPrintableLineOfLiteratureObject());
    //    assertEquals(magazine2.getPrintableLineOfLiteratureObject(),shelf.getMagazines().get(1).getPrintableLineOfLiteratureObject());
    //}
    //@Test
    //void readFromFiles_oneArg() throws FileNotFoundException {
    //    Shelf shelf = readFromGsonService.readShelfFromTwoFiles("src/test/resources/shelfInDiffFile");
//
    //    assertEquals(book1.getPrintableLineOfLiteratureObject(),shelf.getBooks().get(0).getPrintableLineOfLiteratureObject());
    //    assertEquals(book2.getPrintableLineOfLiteratureObject(),shelf.getBooks().get(1).getPrintableLineOfLiteratureObject());
    //    assertEquals(magazine1.getPrintableLineOfLiteratureObject(),shelf.getMagazines().get(0).getPrintableLineOfLiteratureObject());
    //    assertEquals(magazine2.getPrintableLineOfLiteratureObject(),shelf.getMagazines().get(1).getPrintableLineOfLiteratureObject());
    //}



}
