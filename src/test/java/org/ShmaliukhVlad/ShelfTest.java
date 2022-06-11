package org.ShmaliukhVlad;

import jdk.jfr.Description;
import org.ShmaliukhVlad.Bookshelf.Shelf;
import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Book;
import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Magazine;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ShelfTest {


    @BeforeClass
    public static void globalSetUp() {
        System.out.println("Initial setup...");
        System.out.println("Code executes only once");
    }
    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Add one NOT borrowed Book to empty Shelf")
    @Description("Simple add one Book which is NOT borrowed (isBorrowed = false) to empty Shelf")
    void addBookToShelf_0() {
        Shelf shelf1 = new Shelf();
        Book book1 = new Book("1",1,false,"NoAuthor1",new Date(1));
        shelf1.addLiteratureObject(book1);

        assertTrue(shelf1.getLiteratureInShelf().get(0) instanceof Book
            && shelf1.getLiteratureOutShelf().isEmpty());
    }

    @Test
    @DisplayName("Add one borrowed Book to empty  Shelf")
    @Description("Simple add one Book which is borrowed (isBorrowed = true) to empty Shelf")
    void addBookToShelf_1() {
        Shelf shelf1 = new Shelf();
        Book book1 = new Book("1",1,true,"NoAuthor1",new Date(1));
        shelf1.addLiteratureObject(book1);

        assertTrue(shelf1.getLiteratureOutShelf().get(0) instanceof Book
                && shelf1.getLiteratureInShelf().isEmpty());
    }

    @Test
    @DisplayName("Add one NOT borrowed Magazine to empty  Shelf")
    @Description("Simple add one Magazine which is NOT borrowed (isBorrowed = false) to empty Shelf")
    void addMagazineToShelf_0() {
        Shelf shelf1 = new Shelf();
        Magazine magazine1 = new Magazine("1",1,false);
        shelf1.addLiteratureObject(magazine1);

        assertTrue(shelf1.getLiteratureInShelf().get(0) instanceof Magazine
                && shelf1.getLiteratureOutShelf().isEmpty());
    }

    @Test
    @DisplayName("Add one borrowed Magazine to empty Shelf")
    @Description("Simple add one Magazine which is borrowed (isBorrowed = true) to empty Shelf")
    void addMagazineToShelf_1() {
        Shelf shelf1 = new Shelf();
        Magazine magazine1 = new Magazine("1",1,true);
        shelf1.addLiteratureObject(magazine1);

        assertTrue(shelf1.getLiteratureOutShelf().get(0) instanceof Magazine
                && shelf1.getLiteratureInShelf().isEmpty());
    }

    @Test
    @DisplayName("Add one Book and one Magazine (are borrowed) to empty Shelf")
    @Description("Simple add one Book and one Magazine which are borrowed (isBorrowed = true) to empty Shelf")
    void addBookAndMagazineToShelf_0() {
        Shelf shelf1 = new Shelf();
        Book book1 = new Book("1",1,true,"NoAuthor1",new Date(1));
        Magazine magazine1 = new Magazine("4",4,true);
        shelf1.addLiteratureObject(book1);
        shelf1.addLiteratureObject(magazine1);

        assertTrue(shelf1.getLiteratureOutShelf().get(0) instanceof Book
            && shelf1.getLiteratureOutShelf().get(1) instanceof Magazine);
    }

    @Test
    @DisplayName("Add one Book and one Magazine (are not borrowed) to empty Shelf")
    @Description("Simple add one Book and one Magazine which are NOT borrowed (isBorrowed = false) to empty Shelf")
    void addBookAndMagazineToShelf_1() {
        Shelf shelf1 = new Shelf();
        Book book1 = new Book("1",1,false,"NoAuthor1",new Date(1));
        Magazine magazine1 = new Magazine("4",4,false);
        shelf1.addLiteratureObject(book1);
        shelf1.addLiteratureObject(magazine1);

        assertTrue(shelf1.getLiteratureInShelf().get(0) instanceof Book
                && shelf1.getLiteratureInShelf().get(1) instanceof Magazine);
    }

    @Test
    @DisplayName("Add one Book (borrowed) and one Magazine (not borrowed) to empty Shelf")
    @Description("Add one Book which is borrowed (isBorrowed = true) " +
            "and one Magazine which is NOT borrowed (isBorrowed = false) to empty Shelf")
    void addBookAndMagazineToShelf_2() {
        Shelf shelf1 = new Shelf();
        Book book1 = new Book("1",1,true,"NoAuthor1",new Date(1));
        Magazine magazine1 = new Magazine("4",4,false);
        shelf1.addLiteratureObject(book1);
        shelf1.addLiteratureObject(magazine1);

        assertTrue(shelf1.getLiteratureOutShelf().get(0) instanceof Book
                && shelf1.getLiteratureInShelf().get(0) instanceof Magazine);
    }

    @Test
    @DisplayName("Generating Shelf with one Book and one Magazine")
    @Description("Try to generate Shelf and add one Book and one Magazine which are NOT borrowed (isBorrowed = false) ")
    void generateShelf() {
        Shelf shelfTest = new Shelf();
        Shelf shelfExpect = new Shelf();
        Book book1 = new Book("1",1,false,"NoAuthor1",new Date(1));
        Magazine magazine1 = new Magazine("4",4,false);
        shelfTest.addLiteratureObject(book1);
        shelfExpect.addLiteratureObject(magazine1);

        assertEquals(shelfTest.getLiteratureInShelf().size(),shelfExpect.getLiteratureInShelf().size());
    }

    @Test
    public void whenSerializingAndDeserializing_ThenObjectIsTheSame()
            throws IOException, ClassNotFoundException {

        Shelf shelf1 = new Shelf();
        Magazine magazine1 = new Magazine("1",1,false);
        shelf1.addLiteratureObject(magazine1);

        final String fileName = "shelf.out";
        ObjectOutputStream objectOutputStream =
                new ObjectOutputStream(new FileOutputStream(fileName));

        //objectOutputStream.writeObject(shelf1);
        objectOutputStream.writeObject(shelf1);
        //ObjectOutputStream finalObjectOutputStream = objectOutputStream;
//
        //shelf1.getLiteratureInShelf().stream().forEach(literature -> {
        //    try {
        //        finalObjectOutputStream.writeObject(literature);
        //    } catch (IOException ex) {
        //        throw new RuntimeException(ex);
        //    }
        //});
        objectOutputStream.close();
        System.out.println("File '" + fileName + "' has been written");

        FileInputStream fileInputStream
                = new FileInputStream(fileName);
        ObjectInputStream objectInputStream
                = new ObjectInputStream(fileInputStream);

        Shelf shelf2 = new Shelf();
        objectInputStream.close();

        assertEquals(shelf1.getLiteratureInShelf().get(0).getName(), shelf2.getLiteratureInShelf().get(0).getName());
        assertEquals(shelf1.getLiteratureInShelf().get(0).getPagesNumber(), shelf2.getLiteratureInShelf().get(0).getPagesNumber());
        assertEquals(shelf1.getLiteratureInShelf().get(0).isBorrowed(), shelf2.getLiteratureInShelf().get(0).isBorrowed());
    }

//ToDo
    @Test
    @DisplayName("Save all info into .txt file")
    @Description("Generate Shelf, add 6 objects of Literature (3 - Book and 3 - Magazine) with different parameters. " +
            "Than try to save info into .txt file")
    void saveShelfFile() {
        Book book1 = new Book("1",1,false,"NoAutho1",new Date(1));
        Book book2 = new Book("2",2,false,"NoAuthor2",new Date(2));
        Book book3 = new Book("3",3,true,"NoAuthor3",new Date(3));

        Magazine magazine1 = new Magazine("4",4,false);
        Magazine magazine2 = new Magazine("5",5,false);
        Magazine magazine3 = new Magazine("6",6,true);

        Shelf shelf = new Shelf();

        shelf.addLiteratureObject(book1);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(book3);

        shelf.addLiteratureObject(magazine1);
        shelf.addLiteratureObject(magazine2);
        shelf.addLiteratureObject(magazine3);

        shelf.saveShelfToFile();
        //assertTrue("The files differ!", FileUtils.contentEquals(file1, file2));
    }

    @AfterClass
    public static void tearDown() {
        System.out.println("Tests finished");
    }
}