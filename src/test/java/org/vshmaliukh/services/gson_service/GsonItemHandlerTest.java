package org.vshmaliukh.services.gson_service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.vshmaliukh.Utils;
import org.vshmaliukh.bookshelf.Shelf;
import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Gazette;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.vshmaliukh.services.gson_service.ConstantsForGsonHandler.*;

class GsonItemHandlerTest {
    public static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    //String userName = "test";
    //PrintWriter printWriter = new PrintWriter(System.out, true);
//
    //Path filePathAll;
    //Path filePathBooks;
    //Path filePathMagazines;
    //Path filePathGazettes;
//
    //Book book1 = new Book("noNameBook1", 1, false, "NoAuthor1", new Date(System.currentTimeMillis() - 60 * 60 * 64 * 1000));
    //Book book2 = new Book("noNameBook2", 2, true, "NoAuthor2", new Date());
//
    //Book expectedBook1 = new Book("noNameBook1", 1, false, "NoAuthor1", new Date(System.currentTimeMillis() - 60 * 60 * 64 * 1000));
    //Book expectedBook2 = new Book("noNameBook2", 2, true, "NoAuthor2", new Date());
//
    //Magazine magazine1 = new Magazine("noNameMagazine1", 1, false);
    //Magazine magazine2 = new Magazine("noNameMagazine2", 2, true);
//
    //Magazine expectedMagazine1 = new Magazine("noNameMagazine1", 1, false);
    //Magazine expectedMagazine2 = new Magazine("noNameMagazine2", 2, true);
//
    //Gazette gazette1 = new Gazette("noNameGazette1", 1, false);
    //Gazette gazette2 = new Gazette("noNameGazette2", 2, true);
//
    //Gazette expectedGazette1 = new Gazette("noNameGazette1", 1, false);
    //Gazette expectedGazette2 = new Gazette("noNameGazette2", 2, true);
//
    //@Test
    //void readWriteOneFileTest_brokenFile() throws IOException {
    //    String userName = "testProblemToReadBrokenShelfFile";
    //    setPaths(userName);
    //    GsonHandler_ gsonHandlerOneFile = new GsonHandler_(WORK_WITH_ONE_FILE, userName, printWriter, true);
//
    //    Shelf shelf = new Shelf(printWriter);
    //    initShelfWithItems(shelf);
//
    //    gsonHandlerOneFile.saveShelfInGson(shelf);
    //    FileWriter fw = new FileWriter(String.valueOf(filePathAll), true);
    //    fw.append("someStr");
    //    fw.flush();
    //    fw.close();
    //    Shelf shelf2 = gsonHandlerOneFile.readShelfFromGson();
//
    //    assertTrue(Utils.getItemsByType(Magazine.class, shelf2.getAllLiteratureObjects()).isEmpty());
    //    assertTrue(Utils.getItemsByType(Book.class, shelf2.getAllLiteratureObjects()).isEmpty());
    //    assertTrue(Utils.getItemsByType(Gazette.class, shelf2.getAllLiteratureObjects()).isEmpty());
//
    //    assertTrue(deleteDirectory(Paths.get(SYSTEM_TEMP_PROPERTY, userName).toFile()));
    //}
//
//
    //@Test
    //void workWithFilesPerTypeTest_brokenFilePerType() throws IOException {
    //    String userName = "testProblemToReadBrokenFilePerType";
    //    setPaths(userName);
    //    GsonHandler_ gsonHandlerFilePerType = new GsonHandler_(WORK_WITH_FILE_PER_TYPE, userName, printWriter, true);
//
    //    Shelf shelf = new Shelf(printWriter);
    //    initShelfWithItems(shelf);
    //    gsonHandlerFilePerType.saveShelfInGson(shelf);
//
    //    FileWriter fw;
    //    fw = new FileWriter(String.valueOf(filePathBooks), true);
    //    fw.append("someStr");
    //    fw.flush();
    //    fw.close();
    //    fw = new FileWriter(String.valueOf(filePathMagazines), true);
    //    fw.append("someStr");
    //    fw.flush();
    //    fw.close();
    //    fw = new FileWriter(String.valueOf(filePathGazettes), true);
    //    fw.append("someStr");
    //    fw.flush();
    //    fw.close();
    //    Shelf shelf2 = gsonHandlerFilePerType.readShelfFromGson();
//
    //    assertTrue(Utils.getItemsByType(Magazine.class, shelf2.getAllLiteratureObjects()).isEmpty());
    //    assertTrue(Utils.getItemsByType(Book.class, shelf2.getAllLiteratureObjects()).isEmpty());
    //    assertTrue(Utils.getItemsByType(Gazette.class, shelf2.getAllLiteratureObjects()).isEmpty());
//
    //    assertTrue(deleteDirectory(Paths.get(SYSTEM_TEMP_PROPERTY, userName).toFile()));
    //}
//
    //@Test
    //void workWithFilesPerTypeTest_brokenBooksFile() throws IOException {
    //    String userName = "testProblemToReadBrokenBooksFile";
    //    setPaths(userName);
    //    GsonHandler_ gsonHandlerFilesPerType = new GsonHandler_(WORK_WITH_FILE_PER_TYPE, userName, printWriter, true);
//
    //    Shelf shelf = new Shelf(printWriter);
    //    initShelfWithItems(shelf);
    //    gsonHandlerFilesPerType.saveShelfInGson(shelf);
//
    //    FileWriter fw = new FileWriter(String.valueOf(filePathBooks), true);
    //    fw.append("someStr");
    //    fw.flush();
    //    fw.close();
    //    Shelf shelf2 = gsonHandlerFilesPerType.readShelfFromGson();
//
    //    assertEquals(expectedMagazine1.toString(), Utils.getItemsByType(Magazine.class, shelf2.getAllLiteratureObjects()).get(0).toString());
    //    assertEquals(expectedMagazine2.toString(), Utils.getItemsByType(Magazine.class, shelf2.getAllLiteratureObjects()).get(1).toString());
    //    assertTrue(Utils.getItemsByType(Book.class, shelf2.getAllLiteratureObjects()).isEmpty());
//
    //    assertTrue(deleteDirectory(Paths.get(SYSTEM_TEMP_PROPERTY, userName).toFile()));
    //}
//
    //@Test
    //void workWithFilesPerTypeTest_brokenMagazinesFile() throws IOException {
    //    String userName = "testProblemToReadBrokenMagazinesFile";
    //    setPaths(userName);
    //    GsonHandler_ gsonHandlerFilesPerType = new GsonHandler_(WORK_WITH_FILE_PER_TYPE, userName, printWriter, true);
    //    Shelf shelf = new Shelf(printWriter);
    //    initShelfWithItems(shelf);
    //    gsonHandlerFilesPerType.saveShelfInGson(shelf);
//
    //    FileWriter fw = new FileWriter(String.valueOf(filePathMagazines), true);
    //    fw.append("someStr");
    //    fw.flush();
    //    fw.close();
    //    Shelf shelf2 = gsonHandlerFilesPerType.readShelfFromGson();
//
    //    assertEquals(expectedBook1.toString(), Utils.getItemsByType(Book.class, shelf2.getAllLiteratureObjects()).get(0).toString());
    //    assertEquals(expectedBook2.toString(), Utils.getItemsByType(Book.class, shelf2.getAllLiteratureObjects()).get(1).toString());
    //    assertTrue(Utils.getItemsByType(Magazine.class, shelf2.getAllLiteratureObjects()).isEmpty());
//
    //    assertTrue(deleteDirectory(Paths.get(SYSTEM_TEMP_PROPERTY, userName).toFile()));
    //}
//
    //@Test
    //void workWithFilesPerTypeTest_brokenGazettesFile() throws IOException {
    //    String userName = "testProblemToReadBrokenGazettesFile";
    //    setPaths(userName);
    //    GsonHandler_ gsonHandlerFilesPerType = new GsonHandler_(WORK_WITH_FILE_PER_TYPE, userName, printWriter, true);
    //    Shelf shelf = new Shelf(printWriter);
    //    initShelfWithItems(shelf);
    //    gsonHandlerFilesPerType.saveShelfInGson(shelf);
//
    //    FileWriter fw = new FileWriter(String.valueOf(filePathGazettes), true);
    //    fw.append("someStr");
    //    fw.flush();
    //    fw.close();
    //    Shelf shelf2 = gsonHandlerFilesPerType.readShelfFromGson();
//
    //    assertEquals(expectedMagazine1.toString(), Utils.getItemsByType(Magazine.class, shelf2.getAllLiteratureObjects()).get(0).toString());
    //    assertEquals(expectedMagazine2.toString(), Utils.getItemsByType(Magazine.class, shelf2.getAllLiteratureObjects()).get(1).toString());
    //    assertTrue(Utils.getItemsByType(Gazette.class, shelf2.getAllLiteratureObjects()).isEmpty());
//
    //    assertTrue(deleteDirectory(Paths.get(SYSTEM_TEMP_PROPERTY, userName).toFile()));
    //}
//
    //@Test
    //void workWithFilesPerTypeTest_booksFileNotExists() throws IOException {
    //    String userName = "testProblemToReadBooksFile";
    //    setPaths(userName);
    //    GsonHandler_ gsonHandlerFilesPerType = new GsonHandler_(WORK_WITH_FILE_PER_TYPE, userName, printWriter, true);
//
    //    Shelf shelf = new Shelf(printWriter);
    //    initShelfWithItems(shelf);
    //    gsonHandlerFilesPerType.saveShelfInGson(shelf);
//
    //    Files.deleteIfExists(filePathBooks);
    //    Shelf shelf2 = gsonHandlerFilesPerType.readShelfFromGson();
//
    //    assertEquals(expectedMagazine1.toString(), Utils.getItemsByType(Magazine.class, shelf2.getAllLiteratureObjects()).get(0).toString());
    //    assertEquals(expectedMagazine2.toString(), Utils.getItemsByType(Magazine.class, shelf2.getAllLiteratureObjects()).get(1).toString());
//
    //    assertTrue(deleteDirectory(Paths.get(SYSTEM_TEMP_PROPERTY, userName).toFile()));
    //}
//
    //@Test
    //void workWithFilesPerTypeTest_magazinesFileNotExists() throws IOException {
    //    String userName = "testProblemToReadMagazinesFile";
    //    setPaths(userName);
    //    GsonHandler_ gsonHandlerFilesPerType = new GsonHandler_(WORK_WITH_FILE_PER_TYPE, userName, printWriter, true);
//
    //    Shelf shelf = new Shelf(printWriter);
    //    initShelfWithItems(shelf);
    //    gsonHandlerFilesPerType.saveShelfInGson(shelf);
//
    //    Files.deleteIfExists(filePathMagazines);
    //    Shelf shelf2 = gsonHandlerFilesPerType.readShelfFromGson();
//
    //    assertEquals(expectedBook1.toString(), Utils.getItemsByType(Book.class, shelf2.getAllLiteratureObjects()).get(0).toString());
    //    assertEquals(expectedBook2.toString(), Utils.getItemsByType(Book.class, shelf2.getAllLiteratureObjects()).get(1).toString());
    //    assertTrue(Utils.getItemsByType(Magazine.class, shelf2.getAllLiteratureObjects()).isEmpty());
//
    //    assertTrue(deleteDirectory(Paths.get(SYSTEM_TEMP_PROPERTY, userName).toFile()));
    //}
//
    //@Test
    //void workWithFilesPerTypeTest_gazettesFileNotExists() throws IOException {
    //    String userName = "testProblemToReadGazettesFile";
    //    setPaths(userName);
    //    GsonHandler_ gsonHandlerFilesPerType = new GsonHandler_(WORK_WITH_FILE_PER_TYPE, userName, printWriter, true);
//
    //    Shelf shelf = new Shelf(printWriter);
    //    initShelfWithItems(shelf);
    //    gsonHandlerFilesPerType.saveShelfInGson(shelf);
//
    //    Files.deleteIfExists(filePathGazettes);
    //    Shelf shelf2 = gsonHandlerFilesPerType.readShelfFromGson();
//
    //    assertEquals(expectedBook1.toString(), Utils.getItemsByType(Book.class, shelf2.getAllLiteratureObjects()).get(0).toString());
    //    assertEquals(expectedBook2.toString(), Utils.getItemsByType(Book.class, shelf2.getAllLiteratureObjects()).get(1).toString());
    //    assertEquals(expectedMagazine1.toString(), Utils.getItemsByType(Magazine.class, shelf2.getAllLiteratureObjects()).get(0).toString());
    //    assertEquals(expectedMagazine2.toString(), Utils.getItemsByType(Magazine.class, shelf2.getAllLiteratureObjects()).get(1).toString());
    //    //assertTrue(Utils.getItemsByType(Gazette.class, shelf2.getAllLiteratureObjects()).isEmpty());
//
    //    assertTrue(deleteDirectory(Paths.get(SYSTEM_TEMP_PROPERTY, userName).toFile()));
    //}
//
    //@Test
    //void readWriteOneFileTest() {
    //    userName = "testOneFile";
    //    setPaths(userName);
    //    GsonHandler_ gsonHandlerOneFile = new GsonHandler_(WORK_WITH_ONE_FILE, userName, printWriter, true);
    //    Shelf shelf = new Shelf(printWriter);
    //    initShelfWithItems(shelf);
//
    //    gsonHandlerOneFile.saveShelfInGson(shelf);
    //    Shelf shelf2 = gsonHandlerOneFile.readShelfFromGson();
//
    //    assertEquals(expectedMagazine1.toString(), Utils.getItemsByType(Magazine.class, shelf2.getAllLiteratureObjects()).get(0).toString());
    //    assertEquals(expectedMagazine2.toString(), Utils.getItemsByType(Magazine.class, shelf2.getAllLiteratureObjects()).get(1).toString());
    //    assertEquals(expectedBook1.toString(), Utils.getItemsByType(Book.class, shelf2.getAllLiteratureObjects()).get(0).toString());
    //    assertEquals(expectedBook2.toString(), Utils.getItemsByType(Book.class, shelf2.getAllLiteratureObjects()).get(1).toString());
    //    assertEquals(expectedGazette1.toString(), Utils.getItemsByType(Gazette.class, shelf2.getAllLiteratureObjects()).get(0).toString());
    //    assertEquals(expectedGazette2.toString(), Utils.getItemsByType(Gazette.class, shelf2.getAllLiteratureObjects()).get(1).toString());
//
    //    //assertTrue(deleteDirectory(Paths.get(SYSTEM_TEMP_PROPERTY, userName).toFile()));
    //}
//
    //@Test
    //void readWriteFilesPerTypeTest() {
    //    userName = "testFilesPerType";
    //    setPaths(userName);
    //    GsonHandler_ gsonHandlerFilesPerType = new GsonHandler_(WORK_WITH_FILE_PER_TYPE, userName, printWriter, true);
    //    Shelf shelf = new Shelf(printWriter);
    //    initShelfWithItems(shelf);
//
    //    gsonHandlerFilesPerType.saveShelfInGson(shelf);
    //    Shelf shelf2 = gsonHandlerFilesPerType.readShelfFromGson();
//
    //    assertEquals(expectedMagazine1.toString(), Utils.getItemsByType(Magazine.class, shelf2.getAllLiteratureObjects()).get(0).toString());
    //    assertEquals(expectedMagazine2.toString(), Utils.getItemsByType(Magazine.class, shelf2.getAllLiteratureObjects()).get(1).toString());
    //    assertEquals(expectedBook1.toString(), Utils.getItemsByType(Book.class, shelf2.getAllLiteratureObjects()).get(0).toString());
    //    assertEquals(expectedBook2.toString(), Utils.getItemsByType(Book.class, shelf2.getAllLiteratureObjects()).get(1).toString());
    //    assertEquals(expectedGazette1.toString(), Utils.getItemsByType(Gazette.class, shelf2.getAllLiteratureObjects()).get(0).toString());
    //    assertEquals(expectedGazette2.toString(), Utils.getItemsByType(Gazette.class, shelf2.getAllLiteratureObjects()).get(1).toString());
//
    //    //assertTrue(deleteDirectory(Paths.get(SYSTEM_TEMP_PROPERTY, userName).toFile()));
    //}
//
    //void setPaths(String name) {
    //    filePathAll = Paths.get(SYSTEM_TEMP_PROPERTY, name, (name + "_" + SHELF_FILE_NAME_PREFIX + FILE_TYPE));
    //    filePathBooks = Paths.get(SYSTEM_TEMP_PROPERTY, name, (name + "_" + BOOKS_FILE_NAME_PREFIX + FILE_TYPE));
    //    filePathMagazines = Paths.get(SYSTEM_TEMP_PROPERTY, name, (name + "_" + MAGAZINES_FILE_NAME_PREFIX + FILE_TYPE));
    //    filePathGazettes = Paths.get(SYSTEM_TEMP_PROPERTY, name, (name + "_" + GAZETTES_FILE_NAME_PREFIX + FILE_TYPE));
    //}
//
    //private void initShelfWithItems(Shelf shelf) {
    //    shelf.addLiteratureObject(book1);
    //    shelf.addLiteratureObject(book2);
    //    shelf.addLiteratureObject(magazine1);
    //    shelf.addLiteratureObject(magazine2);
    //    shelf.addLiteratureObject(gazette1);
    //    shelf.addLiteratureObject(gazette2);
    //}
//
    //boolean deleteDirectory(File directoryToBeDeleted) {
    //    File[] allContents = directoryToBeDeleted.listFiles();
    //    if (allContents != null) {
    //        for (File file : allContents) {
    //            deleteDirectory(file);
    //        }
    //    }
    //    return directoryToBeDeleted.delete();
    //}

    GsonItemHandler testGsonItemHandler = new GsonItemHandler(TEMP_DIR, "test", new ArrayList<>());

    @Test
    void testGeneratePathForGson() {
        Path expectedPath = Paths.get(TEMP_DIR, "shelf", "testGeneratePathForGson", "gson_handler");
        GsonItemHandler gsonItemHandler = new GsonItemHandler(TEMP_DIR, "testGeneratePathForGson", new ArrayList<>());
        assertEquals(expectedPath, gsonItemHandler.generatePathForGson());
    }

    @Test
    void generateFullFileName() {
    }

    @ParameterizedTest
    @MethodSource("provideArgsForCreateDirectoriesIfNotExists")
    void testCreateDirectoriesIfNotExists(boolean expectedState, Path path) {
        assertEquals(expectedState, testGsonItemHandler.createDirectoriesIfNotExists(path));
    }

    private static Stream<Arguments> provideArgsForCreateDirectoriesIfNotExists() {
        return Stream.of(
                Arguments.of(true, Paths.get(TEMP_DIR)),
                Arguments.of(true, Paths.get(TEMP_DIR, "programDir")),
                Arguments.of(true, Paths.get(TEMP_DIR, "programDir")),
                Arguments.of(true, Paths.get(TEMP_DIR, "programDir", "fodler")),
                Arguments.of(false, Paths.get(""))


        );
    }

    @Test
    void saveListToFile() {
    }

    @Test
    void readItemListFromFile() {
    }

    @Test
    void getItemFromJsonElement() {
    }

    @Test
    void getJsonObjectFromJsonElement() {
    }

    @Test
    void getContainerListForObjects() {
    }
}