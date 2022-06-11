package org.ShmaliukhVlad;

import jdk.jfr.Description;
import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Literature;
import org.ShmaliukhVlad.Bookshelf.Shelf;
import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Book;
import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Magazine;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Test
    @DisplayName("Get sorted only Magazine objects by author")
    @Description("Test method which gives user list of Books inside Shelf by next rule:\n" +
            "Magazines which are sorted by Author")
    void sortedMagazinesByName() {
        String expectedLastAuthor = "last";
        int expectedLastPagesNumber = 100;

        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(new Magazine("LAST",55,false));
        shelf.addLiteratureObject(new Magazine("last",100,false));
        shelf.addLiteratureObject(new Magazine("last",55,false));
        shelf.addLiteratureObject(new Magazine("las",55,false));
        shelf.addLiteratureObject(new Magazine("la",55,false));
        shelf.addLiteratureObject(new Magazine("55",55,false));
        shelf.addLiteratureObject(new Magazine("b",6,false));
        shelf.addLiteratureObject(new Magazine("a",5,false));
        shelf.addLiteratureObject(new Magazine("7",7,false));
        shelf.addLiteratureObject(new Magazine("6",6,false));
        shelf.addLiteratureObject(new Book("2",2,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Magazine("5",5,false));
        shelf.addLiteratureObject(new Book("1",1,false,"1",new Date(1)));
        shelf.addLiteratureObject(new Magazine("3",3,false));
        shelf.addLiteratureObject(new Book("4",4,false,"4",new Date(4)));

        ArrayList<Literature> sortedShelf =
                (ArrayList <Literature>) shelf.getLiteratureInShelf().stream()
                        .filter((Literature o)-> o instanceof Magazine)
                        .sorted(Comparator.comparing(
                                        (Literature o) -> o.getName())
                                .thenComparing(
                                        (Literature o) -> o.getPagesNumber()))
                        .collect(Collectors.toList());

        shelf.setLiteratureInShelf(sortedShelf);
        System.out.println(sortedShelf);

        assertEquals(shelf.getLiteratureInShelf().get(shelf.getLiteratureInShelf().size()-1).getName(), expectedLastAuthor);
        assertEquals(shelf.getLiteratureInShelf().get(shelf.getLiteratureInShelf().size()-1).getPagesNumber(), expectedLastPagesNumber);
    }

    @Test
    @DisplayName("Get sorted only Magazine objects by pages")
    @Description("Test method which gives user list of Books inside Shelf by next rule:\n" +
            "Magazines which are sorted by Pages")
    void sortedMagazinesByPages() {
        String expectedLastAuthor = "last";
        int expectedLastPagesNumber = 100;

        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(new Magazine("LAST",55,false));
        shelf.addLiteratureObject(new Magazine("last",100,false));
        shelf.addLiteratureObject(new Magazine("last",55,false));
        shelf.addLiteratureObject(new Magazine("las",55,false));
        shelf.addLiteratureObject(new Magazine("la",55,false));
        shelf.addLiteratureObject(new Magazine("55",55,false));
        shelf.addLiteratureObject(new Magazine("b",6,false));
        shelf.addLiteratureObject(new Magazine("a",5,false));
        shelf.addLiteratureObject(new Magazine("7",7,false));
        shelf.addLiteratureObject(new Magazine("6",6,false));
        shelf.addLiteratureObject(new Book("2",2,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Magazine("5",5,false));
        shelf.addLiteratureObject(new Book("1",1,false,"1",new Date(1)));
        shelf.addLiteratureObject(new Magazine("3",3,false));
        shelf.addLiteratureObject(new Book("4",4,false,"4",new Date(4)));

        ArrayList<Literature> sortedShelf =
                (ArrayList <Literature>) shelf.getLiteratureInShelf().stream()
                        .filter((Literature o)-> o instanceof Magazine)
                        .sorted(Comparator.comparing(
                                        (Literature o) -> o.getPagesNumber())
                                .thenComparing(
                                        (Literature o) -> o.getName()))
                        .collect(Collectors.toList());

        shelf.setLiteratureInShelf(sortedShelf);
        System.out.println(sortedShelf);

        assertEquals(shelf.getLiteratureInShelf().get(shelf.getLiteratureInShelf().size()-1).getName(), expectedLastAuthor);
        assertEquals(shelf.getLiteratureInShelf().get(shelf.getLiteratureInShelf().size()-1).getPagesNumber(), expectedLastPagesNumber);
    }

    @Test
    @DisplayName("Get sorted only Book objects by name")
    @Description("Test method which gives user list of Books inside Shelf by next rule:\n" +
            "Books which are sorted by Name")
    void sortedBooksByName() {
        String expectedLastAuthor = "last";
        int expectedLastPagesNumber = 100;

        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(new Book("LAST",55,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Book("last",100,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Book("last",55,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Book("las",55,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Book("la",55,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Book("55",55,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Book("b",6,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Book("a",5,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Book("7",7,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Book("6",6,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Book("2",2,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Magazine("5",5,false));
        shelf.addLiteratureObject(new Book("1",1,false,"1",new Date(1)));
        shelf.addLiteratureObject(new Magazine("3",3,false));
        shelf.addLiteratureObject(new Book("4",4,false,"4",new Date(4)));

        ArrayList<Literature> sortedShelf =
                (ArrayList <Literature>) shelf.getLiteratureInShelf().stream()
                        .filter((Literature o)-> o instanceof Book)
                        .sorted(Comparator.comparing(
                                        (Literature o) -> o.getName())
                                .thenComparing(
                                        (Literature o) -> o.getPagesNumber()))
                        .collect(Collectors.toList());

        shelf.setLiteratureInShelf(sortedShelf);
        System.out.println(sortedShelf);

        assertEquals(shelf.getLiteratureInShelf().get(shelf.getLiteratureInShelf().size()-1).getName(), expectedLastAuthor);
        assertEquals(shelf.getLiteratureInShelf().get(shelf.getLiteratureInShelf().size()-1).getPagesNumber(), expectedLastPagesNumber);
    }

    @Test
    @DisplayName("Get sorted only Book objects by pages")
    @Description("Test method which gives user list of Books inside Shelf by next rule:\n" +
            "Books which are sorted by Pages")
    void sortedBooksByPages() {
        String expectedLastAuthor = "last";
        int expectedLastPagesNumber = 100;

        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(new Book("LAST",55,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Book("last",100,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Book("last",55,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Book("las",55,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Book("la",55,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Book("55",55,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Book("b",6,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Book("a",5,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Book("7",7,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Book("6",6,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Book("2",2,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Magazine("5",5,false));
        shelf.addLiteratureObject(new Book("1",1,false,"1",new Date(1)));
        shelf.addLiteratureObject(new Magazine("3",3,false));
        shelf.addLiteratureObject(new Book("4",4,false,"4",new Date(4)));

        ArrayList<Literature> sortedShelf =
                (ArrayList <Literature>) shelf.getLiteratureInShelf().stream()
                        .filter((Literature o)-> o instanceof Book)
                        .sorted(Comparator.comparing(
                                        (Literature o) -> o.getPagesNumber())
                                .thenComparing(
                                        (Literature o) -> o.getName()))
                        .collect(Collectors.toList());

        shelf.setLiteratureInShelf(sortedShelf);
        System.out.println(sortedShelf);

        assertEquals(shelf.getLiteratureInShelf().get(shelf.getLiteratureInShelf().size()-1).getName(), expectedLastAuthor);
        assertEquals(shelf.getLiteratureInShelf().get(shelf.getLiteratureInShelf().size()-1).getPagesNumber(), expectedLastPagesNumber);
    }

    @Test
    @DisplayName("Get sorted only Book objects by author")
    @Description("Test method which gives user list of Books inside Shelf by next rule:\n" +
            "Books which are sorted by Author")
    void sortedBooksByAuthor() {
        String expectedLastAuthor = "last";
        int expectedLastPagesNumber = 100;

        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(new Book("LAST",55,false,"LAST",new Date(2)));
        shelf.addLiteratureObject(new Book("last",100,false,"last",new Date(2)));
        shelf.addLiteratureObject(new Book("last",55,false,"last",new Date(2)));
        shelf.addLiteratureObject(new Book("las",55,false,"las",new Date(2)));
        shelf.addLiteratureObject(new Book("la",55,false,"la",new Date(2)));
        shelf.addLiteratureObject(new Book("55",55,false,"55",new Date(2)));
        shelf.addLiteratureObject(new Book("b",6,false,"b",new Date(2)));
        shelf.addLiteratureObject(new Book("a",5,false,"a",new Date(2)));
        shelf.addLiteratureObject(new Book("7",7,false,"7",new Date(2)));
        shelf.addLiteratureObject(new Book("6",6,false,"6",new Date(2)));
        shelf.addLiteratureObject(new Book("2",2,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Magazine("5",5,false));
        shelf.addLiteratureObject(new Book("1",1,false,"1",new Date(1)));
        shelf.addLiteratureObject(new Magazine("3",3,false));
        shelf.addLiteratureObject(new Book("4",4,false,"4",new Date(4)));

        ArrayList<Literature> sortedShelf =
                (ArrayList<Literature>) shelf.getLiteratureInShelf().stream()
                        .filter((Literature o)-> o instanceof Book)
                        .sorted(Comparator.comparing((Literature o) -> {
                            if(o instanceof Book){
                                return ((Book) o).getAuthor();
                            }
                            return "";
                        }).thenComparingInt((Literature o) -> {
                            if(o instanceof Book){
                                return o.getPagesNumber();
                            }
                            return 0;
                        }))
                        .collect(Collectors.toList());

        shelf.setLiteratureInShelf(sortedShelf);
        System.out.println(sortedShelf);

        assertEquals(shelf.getLiteratureInShelf().get(shelf.getLiteratureInShelf().size()-1).getName(), expectedLastAuthor);
        assertEquals(shelf.getLiteratureInShelf().get(shelf.getLiteratureInShelf().size()-1).getPagesNumber(), expectedLastPagesNumber);
    }

    @Test
    @Deprecated
    @DisplayName("Sort Shelf Book objects by author")
    @Description("Test Method which swap Literature object inside Shelf by next rule:\n" +
            "firstly Magazines not in order\n" +
            "than Books which are sorted by Author")
    void sortBooksByAuthor() {
        String expectedLastAuthor = "last";
        int expectedLastPagesNumber = 100;

        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(new Book("LAST",55,false,"LAST",new Date(55)));
        shelf.addLiteratureObject(new Book("last",100,false,"last",new Date(55)));
        shelf.addLiteratureObject(new Book("last",55,false,"last",new Date(55)));
        shelf.addLiteratureObject(new Book("las",55,false,"55",new Date(55)));
        shelf.addLiteratureObject(new Book("la",55,false,"55",new Date(55)));
        shelf.addLiteratureObject(new Book("55",55,false,"55",new Date(55)));
        shelf.addLiteratureObject(new Book("b",6,false,"6",new Date(6)));
        shelf.addLiteratureObject(new Book("a",5,false,"4",new Date(5)));
        shelf.addLiteratureObject(new Magazine("7",7,false));
        shelf.addLiteratureObject(new Magazine("6",6,false));
        shelf.addLiteratureObject(new Book("2",2,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Magazine("5",5,false));
        shelf.addLiteratureObject(new Book("1",1,false,"1",new Date(1)));
        shelf.addLiteratureObject(new Magazine("3",3,false));
        shelf.addLiteratureObject(new Book("4",4,false,"4",new Date(4)));


        ArrayList<Literature> sortedShelf =
                (ArrayList<Literature>) shelf.getLiteratureInShelf().stream()
                        .sorted(Comparator.comparing((Literature o) -> {
                            if(o instanceof Book){
                                return ((Book) o).getAuthor();
                            }
                            return "";
                        }).thenComparingInt((Literature o) -> {
                            if(o instanceof Book){
                                return o.getPagesNumber();
                            }
                            return 0;
                        }))
                        .collect(Collectors.toList());
        shelf.setLiteratureInShelf(sortedShelf);
        System.out.println(sortedShelf);

        assertEquals(shelf.getLiteratureInShelf().get(shelf.getLiteratureInShelf().size()-1).getName(), expectedLastAuthor);
        assertEquals(shelf.getLiteratureInShelf().get(shelf.getLiteratureInShelf().size()-1).getPagesNumber(), expectedLastPagesNumber);
    }

    @Test
    @Deprecated
    @DisplayName("Sort Shelf Book objects by name")
    @Description("Test Method which swap Literature object inside Shelf by next rule:\n" +
            "firstly Magazines not in order\n" +
            "than Books which are sorted by Name")
    void sortBooksByName() {
        String expectedLastName = "last";
        int expectedLastPagesNumber = 100;

        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(new Book("LAST",55,false,"55",new Date(55)));
        shelf.addLiteratureObject(new Book("last",100,false,"55",new Date(55)));
        shelf.addLiteratureObject(new Book("last",55,false,"55",new Date(55)));
        shelf.addLiteratureObject(new Book("las",55,false,"55",new Date(55)));
        shelf.addLiteratureObject(new Book("la",55,false,"55",new Date(55)));
        shelf.addLiteratureObject(new Book("55",55,false,"55",new Date(55)));
        shelf.addLiteratureObject(new Book("b",6,false,"6",new Date(6)));
        shelf.addLiteratureObject(new Book("a",5,false,"4",new Date(5)));
        shelf.addLiteratureObject(new Magazine("7",7,false));
        shelf.addLiteratureObject(new Magazine("6",6,false));
        shelf.addLiteratureObject(new Book("2",2,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Magazine("5",5,false));
        shelf.addLiteratureObject(new Book("1",1,false,"1",new Date(1)));
        shelf.addLiteratureObject(new Magazine("3",3,false));
        shelf.addLiteratureObject(new Book("4",4,false,"4",new Date(4)));


        ArrayList<Literature> sortedShelf =
                (ArrayList<Literature>) shelf.getLiteratureInShelf().stream()
                        .sorted(Comparator.comparing((Literature o) -> {
                            if(o instanceof Book){
                                return o.getName();
                            }
                            return "";
                        }).thenComparingInt((Literature o) -> {
                            if(o instanceof Book){
                                return o.getPagesNumber();
                            }
                            return 0;
                        }))
                        .collect(Collectors.toList());
        shelf.setLiteratureInShelf(sortedShelf);
        System.out.println(shelf);

        assertEquals(shelf.getLiteratureInShelf().get(shelf.getLiteratureInShelf().size()-1).getName(), expectedLastName);
        assertEquals(shelf.getLiteratureInShelf().get(shelf.getLiteratureInShelf().size()-1).getPagesNumber(), expectedLastPagesNumber);
    }

    @Test
    @Deprecated
    @DisplayName("Sort Shelf Book objects by pages")
    @Description("Test Method which swap Literature object inside Shelf by next rule:\n" +
            "firstly Magazines not in order\n" +
            "than Books which are sorted by Pages"
    )
    void sortBooksByPages() {
        int expectedLastLiteraturePages = 55;

        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(new Book("55",55,false,"55",new Date(55)));
        shelf.addLiteratureObject(new Book("6",6,false,"6",new Date(6)));
        shelf.addLiteratureObject(new Book("5",5,false,"4",new Date(5)));

        shelf.addLiteratureObject(new Magazine("7",7,false));
        shelf.addLiteratureObject(new Magazine("6",6,false));
        shelf.addLiteratureObject(new Book("2",2,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Magazine("5",5,false));
        shelf.addLiteratureObject(new Book("1",1,false,"1",new Date(1)));
        shelf.addLiteratureObject(new Magazine("3",3,false));
        shelf.addLiteratureObject(new Book("4",4,false,"4",new Date(4)));


        ArrayList<Literature> sortedShelf =
                (ArrayList<Literature>) shelf.getLiteratureInShelf().stream()
                        .sorted(Comparator.comparingInt((Literature o) -> {
                            if(o instanceof Book){
                                return o.getPagesNumber();
                            }
                            return 0;
                        }))
                        .collect(Collectors.toList());
        shelf.setLiteratureInShelf(sortedShelf);
        System.out.println(shelf);

        assertEquals(shelf.getLiteratureInShelf().get(shelf.getLiteratureInShelf().size()-1).getPagesNumber(), expectedLastLiteraturePages);
    }

    @Test
    @Deprecated
    @DisplayName("Sort Shelf Magazine objects by pages")
    @Description("Test Method which swap Literature object inside Shelf by next rule:\n" +
            "firstly Books not in order\n" +
            "than Magazines which are sorted by Pages"
    )
    void sortMagazineByPages() {
        int expectedLastLiteraturePages = 7;

        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(new Magazine("7",7,false));
        shelf.addLiteratureObject(new Magazine("6",6,false));
        shelf.addLiteratureObject(new Book("2",2,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Magazine("5",5,false));
        shelf.addLiteratureObject(new Book("1",1,false,"1",new Date(1)));
        shelf.addLiteratureObject(new Magazine("3",3,false));
        shelf.addLiteratureObject(new Book("4",4,false,"4",new Date(4)));


        ArrayList<Literature> result =
                (ArrayList<Literature>) shelf.getLiteratureInShelf().stream()
                        .sorted(Comparator.comparingInt((Literature o) -> {
                            if(o instanceof Magazine){
                                return o.getPagesNumber();
                            }
                            return 0;
                        }))
                        .collect(Collectors.toList());
        shelf.setLiteratureInShelf(result);
        System.out.println(shelf);

        assertEquals(shelf.getLiteratureInShelf().get(shelf.getLiteratureInShelf().size()-1).getPagesNumber(), expectedLastLiteraturePages);
    }

    @Test
    @Deprecated
    @DisplayName("Sort Shelf Magazine objects by name")
    @Description("Test Method which swap Literature object inside Shelf by next rule:\n" +
            "firstly Books not in order\n" +
            "than Magazines which are sorted by Name"
    )
    void sortMagazineByName() {
        int expectedLastLiteraturePages = 13;

        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(new Magazine("ca",13,false));
        shelf.addLiteratureObject(new Magazine("ca",12,false));
        shelf.addLiteratureObject(new Magazine("ca",11,false));
        shelf.addLiteratureObject(new Magazine("C",10,false));
        shelf.addLiteratureObject(new Magazine("c",9,false));
        shelf.addLiteratureObject(new Magazine("b",8,false));
        shelf.addLiteratureObject(new Magazine("a",7,false));
        shelf.addLiteratureObject(new Magazine("612121212",6,false));
        shelf.addLiteratureObject(new Book("2",2,false,"2",new Date(2)));
        shelf.addLiteratureObject(new Magazine("5",5,false));
        shelf.addLiteratureObject(new Book("1",1,false,"1",new Date(1)));
        shelf.addLiteratureObject(new Magazine("3",3,false));
        shelf.addLiteratureObject(new Book("4",4,false,"4",new Date(4)));


        ArrayList<Literature> result =
                (ArrayList<Literature>) shelf.getLiteratureInShelf().stream()
                        .sorted(Comparator.comparing((Literature o) -> {
                            if(o instanceof Magazine){
                                return o.getName();
                            }
                            return "";
                        }).thenComparing((Literature o) -> {
                            if (o instanceof Magazine) {
                                return o.getPagesNumber();
                            }
                            return 0;
                        }))
                        .collect(Collectors.toList());
        shelf.setLiteratureInShelf(result);
        System.out.println(shelf);

        assertEquals(shelf.getLiteratureInShelf().get(shelf.getLiteratureInShelf().size()-1).getPagesNumber(), expectedLastLiteraturePages);
    }



    @AfterClass
    public static void tearDown() {
        System.out.println("Tests finished");
    }
}