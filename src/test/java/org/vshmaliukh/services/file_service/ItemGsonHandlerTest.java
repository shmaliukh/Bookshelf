package org.vshmaliukh.services.file_service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.vshmaliukh.console_terminal_app.ConsoleGsonShelfHandler;
import org.vshmaliukh.services.file_service.gson_handler.ItemGsonHandlerUser;
import org.vshmaliukh.services.file_service.gson_handler.ItemGsonHandlerOneFileUser;
import org.vshmaliukh.services.file_service.gson_handler.ItemGsonHandlerPerTypeUser;
import org.vshmaliukh.shelf.literature_items.book_item.Book;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.magazine_item.Magazine;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ItemGsonHandlerTest {

    @TempDir
    static Path tempDir;
    static String tempDirStr;

    static {
        tempDirStr = String.valueOf(tempDir);
    }

    //static String TEMP_DIR_STR = System.getProperty("java.io.tmpdir");

    Book book1 = new Book("noNameBook1", 1, false, "NoAuthor1", new Date(System.currentTimeMillis() - 60 * 60 * 64 * 1000));
    Book book2 = new Book("noNameBook2", 2, true, "NoAuthor2", new Date());

    Magazine magazine1 = new Magazine("noNameMagazine1", 1, false);
    Magazine magazine2 = new Magazine("noNameMagazine2", 2, true);


    ItemGsonHandlerOneFileUser testGsonHandlerOneFile = new ItemGsonHandlerOneFileUser(tempDirStr, "testGsonItemHandler");


    @Test
    void testGeneratePathForGson() {
        ItemGsonHandlerOneFileUser gsonHandlerOneFile = new ItemGsonHandlerOneFileUser(tempDirStr, "testGeneratePathForGson");
        assertEquals(Paths.get(tempDirStr, "shelf", "testGeneratePathForGson", gsonHandlerOneFile.gsonHandlerFolderStr), gsonHandlerOneFile.generatePathForFileHandler());
    }

    @Test
    void testGenerateFullFileName() {
        assertEquals(testGsonHandlerOneFile.userName + ItemGsonHandlerUser.JSON_FILE_TYPE, testGsonHandlerOneFile.generateFullFileName());
    }

    @Test
    void testSaveItemListToFile() {
        assertTrue(testGsonHandlerOneFile.saveListToFile(testGsonHandlerOneFile.generatePathForGsonFile(), new ArrayList<>()));
    }

    @Test
    void testReadItemListFromGsonFile() {
        ItemGsonHandlerOneFileUser gsonHandlerOneFile = new ItemGsonHandlerOneFileUser(tempDirStr, "testReadItemListFromGsonFile");
        File gsonFile = gsonHandlerOneFile.generatePathForGsonFile().toFile();
        assertTrue(gsonHandlerOneFile.saveListToFile(gsonFile.toPath(), new ArrayList<>()));
        assertTrue(gsonHandlerOneFile.readItemListFromGsonFile(gsonFile.toPath()).isEmpty());
    }

    @Test
    void testSaveReadShelf_GsonHandlerOneFile() {
        ConsoleGsonShelfHandler consoleShelfHandler = new ConsoleGsonShelfHandler(new Scanner(""),new PrintWriter(System.out, true));
        consoleShelfHandler.addLiteratureObject(book1);
        consoleShelfHandler.addLiteratureObject(book2);
        consoleShelfHandler.addLiteratureObject(magazine1);
        consoleShelfHandler.addLiteratureObject(magazine2);

        ItemGsonHandlerOneFileUser gsonHandlerOneFile = new ItemGsonHandlerOneFileUser(tempDirStr, "testReadSaveGsonHandlerOneFile");
        File gsonFile = gsonHandlerOneFile.generatePathForGsonFile().toFile();

        gsonHandlerOneFile.saveItemList(consoleShelfHandler.getShelf().getAllLiteratureObjects());
        ConsoleGsonShelfHandler consoleShelfHandler1 = new ConsoleGsonShelfHandler(new Scanner(""),new PrintWriter(System.out, true));
        gsonHandlerOneFile.readItemListFromGsonFile(gsonFile.toPath()).forEach(consoleShelfHandler1::addLiteratureObject);

        assertEquals(consoleShelfHandler1.getShelf().getAllLiteratureObjects().size(), consoleShelfHandler1.getShelf().getAllLiteratureObjects().size());
        assertTrue(consoleShelfHandler1.getShelf().getAllLiteratureObjects().stream()
                .map(Item::toString)
                .collect(Collectors.toList())
                .containsAll(consoleShelfHandler1.getShelf().getAllLiteratureObjects().stream()
                        .map(Item::toString)
                        .collect(Collectors.toList())));
    }

    @Test
    void testSaveReadShelf_GsonHandlerPerType() {
        ConsoleGsonShelfHandler consoleShelfHandler1 = new ConsoleGsonShelfHandler(new Scanner(""),new PrintWriter(System.out, true));
        ConsoleGsonShelfHandler consoleShelfHandler2 = new ConsoleGsonShelfHandler(new Scanner(""),new PrintWriter(System.out, true));

        consoleShelfHandler1 .addLiteratureObject(book1);
        consoleShelfHandler1 .addLiteratureObject(book2);
        consoleShelfHandler1 .addLiteratureObject(magazine1);
        consoleShelfHandler1 .addLiteratureObject(magazine2);

        ItemGsonHandlerPerTypeUser itemGsonHandlerPerType = new ItemGsonHandlerPerTypeUser(tempDirStr, "testGsonHandlerPerType");
        itemGsonHandlerPerType.saveItemList(consoleShelfHandler1.getShelf().getAllLiteratureObjects());

        itemGsonHandlerPerType.readItemList().forEach(consoleShelfHandler2::addLiteratureObject);

        assertEquals(consoleShelfHandler1.getShelf().getAllLiteratureObjects().size(), consoleShelfHandler2.getShelf().getAllLiteratureObjects().size());
        assertTrue(consoleShelfHandler1.getShelf().getAllLiteratureObjects().stream()
                .map(Item::toString)
                .collect(Collectors.toList())
                .containsAll(consoleShelfHandler2.getShelf().getAllLiteratureObjects().stream()
                        .map(Item::toString)
                        .collect(Collectors.toList())));
    }

    //String userName = "test";
    //PrintWriter printWriter = new PrintWriter(System.out, true);
//
    //Path filePathAll;
    //Path filePathBooks;
    //Path filePathMagazines;
    //Path filePathGazettes;
//
//
    //Book expectedBook1 = new Book("noNameBook1", 1, false, "NoAuthor1", new Date(System.currentTimeMillis() - 60 * 60 * 64 * 1000));
    //Book expectedBook2 = new Book("noNameBook2", 2, true, "NoAuthor2", new Date());
//

//
    //Magazine expectedMagazine1 = new Magazine("noNameMagazine1", 1, false);
    //Magazine expectedMagazine2 = new Magazine("noNameMagazine2", 2, true);
//

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


}