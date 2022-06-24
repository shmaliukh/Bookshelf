package org.ShmaliukhVlad;

import jdk.jfr.Description;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Literature;
import org.ShmaliukhVlad.bookshelf.Shelf;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Magazine;
import org.junit.jupiter.api.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ShelfTest {

    Book book1 = new Book("noNameBook1", 1, false, "NoAuthor1", new Date(System.currentTimeMillis() - 60 * 60 * 24 * 1000));
    Book book2 = new Book("noNameBook2", 2, false, "NoAuthor2", new Date());
    Book book3 = new Book("noNameBook3", 3, true, "NoAuthor3", new Date());

    Magazine magazine1 = new Magazine("noNameMagazine1", 1, false);
    Magazine magazine2 = new Magazine("noNameMagazine2", 2, false);
    Magazine magazine3 = new Magazine("noNameMagazine3", 3, true);


    @Test
    @DisplayName("Add one Book and one Magazine (are not borrowed) to empty Shelf")
    @Description("Simple add one Book and one Magazine which are NOT borrowed (isBorrowed = false) to empty Shelf")
    void addBookAndMagazineToShelf_1() {
        Shelf shelf1 = new Shelf();

        Magazine magazine1 = new Magazine("4", 4, false);
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
        Book book1 = new Book("1", 1, true, "NoAuthor1", new Date());
        Magazine magazine1 = new Magazine("4", 4, false);
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
        Book book1 = new Book("1", 1, false, "NoAuthor1", new Date());
        Magazine magazine1 = new Magazine("4", 4, false);
        shelfTest.addLiteratureObject(book1);
        shelfExpect.addLiteratureObject(magazine1);

        assertEquals(shelfTest.getLiteratureInShelf().size(), shelfExpect.getLiteratureInShelf().size());
    }

    @Test
    @DisplayName("Serialization / deserialization test")
    @Description("Generate Shelf, add 6 objects of Literature (3 - Book and 3 - Magazine) with different parameters. " +
            "Than try to Serialize" +
            "Than try to Deserialize")
    public void saveShelfFile() throws IOException, ClassNotFoundException, EOFException {
        Book book1 = new Book("1", 1, false, "NoAutho1", new Date());
        Book book2 = new Book("2", 2, true, "NoAuthor2", new Date());
        Book book3 = new Book("3", 3, true, "NoAuthor3", new Date());

        Magazine magazine1 = new Magazine("4", 4, false);
        Magazine magazine2 = new Magazine("5", 5, false);
        Magazine magazine3 = new Magazine("6", 6, true);

        Shelf shelf = new Shelf();

        shelf.addLiteratureObject(book1);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(book3);

        shelf.addLiteratureObject(magazine1);
        shelf.addLiteratureObject(magazine2);
        shelf.addLiteratureObject(magazine3);

        final String fileName = "shelf.out";
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(fileName);
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));

            ObjectOutputStream finalObjectOutputStream = objectOutputStream;
            shelf.getLiteratureInShelf().stream().forEach(literature -> {
                try {
                    finalObjectOutputStream.writeObject(literature);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            shelf.getLiteratureOutShelf().stream().forEach(literature -> {
                try {
                    finalObjectOutputStream.writeObject(literature);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            System.out.println("File '" + fileName + "' has been written");
        } finally {
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }

        Shelf shelf2 = new Shelf();
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            fileInputStream = new FileInputStream(fileName);
            objectInputStream = new ObjectInputStream(fileInputStream);
            try {
                while (true) {
                    Literature literatureBuff = (Literature) objectInputStream.readObject();
                    shelf2.addLiteratureObject(literatureBuff);
                }
            } catch (EOFException e) {
                //eof - no error in this case
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            System.out.println(shelf2);
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (objectInputStream != null) {
                objectInputStream.close();
            }
        }

        assertEquals(shelf.getLiteratureInShelf().get(0).getPagesNumber(), shelf2.getLiteratureInShelf().get(0).getPagesNumber());
        assertEquals(shelf.getLiteratureInShelf().get(0).getName(), shelf2.getLiteratureInShelf().get(0).getName());
        assertEquals(shelf.getLiteratureOutShelf().get(2).getPagesNumber(), shelf2.getLiteratureOutShelf().get(2).getPagesNumber());
        assertEquals(shelf.getLiteratureOutShelf().get(2).getName(), shelf2.getLiteratureOutShelf().get(2).getName());

        assertEquals(shelf.getLiteratureOutShelf().get(0).getPagesNumber(), shelf2.getLiteratureOutShelf().get(0).getPagesNumber());
        assertEquals(shelf.getLiteratureOutShelf().get(0).getName(), shelf2.getLiteratureOutShelf().get(0).getName());
        assertEquals(shelf.getLiteratureOutShelf().get(2).getPagesNumber(), shelf2.getLiteratureOutShelf().get(2).getPagesNumber());
        assertEquals(shelf.getLiteratureOutShelf().get(2).getName(), shelf2.getLiteratureOutShelf().get(2).getName());

    }

    @Test
    @DisplayName("Get sorted only Magazine objects by name")
    @Description("Test method which gives user list of Magazines inside Shelf by next rule:\n" +
            "Magazines which are sorted by Name")
    void sortedMagazinesByName() {
        String expectedLastName = "noNameMagazine2";
        int expectedLastPagesNumber = 2;
        int expectedArraySize = 2;

        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(magazine3);
        shelf.addLiteratureObject(magazine2);
        shelf.addLiteratureObject(magazine1);
        shelf.printSortedMagazinesByName();

        ArrayList<Magazine> sortedMagazinesByName = shelf.getSortedMagazinesByName();

        assertEquals(expectedArraySize, sortedMagazinesByName.size());
        assertEquals(expectedLastName, sortedMagazinesByName.get(1).getName());
        assertEquals(expectedLastPagesNumber, sortedMagazinesByName.get(1).getPagesNumber());
    }

    @Test
    @DisplayName("Get sorted only Magazine objects by pages")
    @Description("Test method which gives user list of Magazines inside Shelf by next rule:\n" +
            "Magazines which are sorted by Pages")
    void sortedMagazinesByPages() {
        String expectedLastName = "noNameMagazine2";
        int expectedLastPagesNumber = 2;
        int expectedArraySize = 2;

        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(magazine3);
        shelf.addLiteratureObject(magazine2);
        shelf.addLiteratureObject(magazine1);

        ArrayList<Magazine> sortedMagazinesByPages = shelf.getSortedMagazinesByPages();

        assertEquals(expectedArraySize, sortedMagazinesByPages.size());
        assertEquals(expectedLastName, sortedMagazinesByPages.get(1).getName());
        assertEquals(expectedLastPagesNumber, sortedMagazinesByPages.get(1).getPagesNumber());
    }

    @Test
    @DisplayName("Get sorted only Book objects by name")
    @Description("Test method which gives user list of Books inside Shelf by next rule:\n" +
            "Books which are sorted by Name")
    void sortedBooksByName() {
        String expectedLastName = "noNameBook2";
        int expectedLastPagesNumber = 2;
        int expectedArraySize = 2;

        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(book3);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(book1);

        ArrayList<Book> sortedBooksByName = shelf.getSortedBooksByName();

        assertEquals(expectedArraySize, sortedBooksByName.size());
        assertEquals(expectedLastName, sortedBooksByName.get(1).getName());
        assertEquals(expectedLastPagesNumber, sortedBooksByName.get(1).getPagesNumber());
    }

    @Test
    @DisplayName("Get sorted only Book objects by pages")
    @Description("Test method which gives user list of Books inside Shelf by next rule:\n" +
            "Books which are sorted by Pages")
    void sortedBooksByPages() {
        String expectedLastName = "noNameBook2";
        int expectedLastPagesNumber = 2;
        int expectedArraySize = 2;

        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(book3);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(book1);

        ArrayList<Book> sortedBooksByPages = shelf.getSortedBooksByPages();

        assertEquals(expectedArraySize, sortedBooksByPages.size());
        assertEquals(expectedLastName, sortedBooksByPages.get(1).getName());
        assertEquals(expectedLastPagesNumber, sortedBooksByPages.get(1).getPagesNumber());
    }

    @Test
    @DisplayName("Get sorted only Book objects by author")
    @Description("Test method which gives user list of Books inside Shelf by next rule:\n" +
            "Books which are sorted by Author")
    void sortedBooksByAuthor() {
        String expectedLastName = "noNameBook2";
        String expectedLastAuthor = "NoAuthor2";
        int expectedLastPagesNumber = 2;
        int expectedArraySize = 2;

        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(book3);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(book1);

        ArrayList<Book> sortedBooksByAuthor = shelf.getSortedBooksByAuthor();

        assertEquals(expectedArraySize, sortedBooksByAuthor.size());
        assertEquals(expectedLastName, sortedBooksByAuthor.get(1).getName());
        assertEquals(expectedLastPagesNumber, sortedBooksByAuthor.get(1).getPagesNumber());
        assertEquals(expectedLastAuthor, sortedBooksByAuthor.get(1).getAuthor());
    }
}