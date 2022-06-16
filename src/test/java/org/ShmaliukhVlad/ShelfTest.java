package org.ShmaliukhVlad;

import jdk.jfr.Description;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Literature;
import org.ShmaliukhVlad.bookshelf.Shelf;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Magazine;
import org.junit.jupiter.api.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ShelfTest {

    Book book1 = new Book("noNameBook1",1,false,"NoAuthor1",LocalDate.now());
    Book book2 = new Book("noNameBook2",2,false,"NoAuthor2",LocalDate.now());
    Book book3 = new Book("noNameBook3",3,true,"NoAuthor3",LocalDate.now());

    Magazine magazine1 = new Magazine("noNameMagazine1",1,false);
    Magazine magazine2 = new Magazine("noNameMagazine2",2,false);
    Magazine magazine3 = new Magazine("noNameMagazine3",3,true);


    @Test
    @DisplayName("Add one NOT borrowed Book to empty Shelf")
    @Description("Simple add one Book which is NOT borrowed (isBorrowed = false) to empty Shelf")
    void addBookToShelf_0() {
        String expectedName = "noNameBook1";
        Shelf shelf1 = new Shelf();
        shelf1.addLiteratureObject(book1);

        assertTrue(shelf1.getLiteratureOutShelf().isEmpty());
        assertTrue(shelf1.getLiteratureInShelf().get(0) instanceof Book);
        assertEquals(expectedName, shelf1.getLiteratureInShelf().get(0).getName());
    }

    @Test
    @DisplayName("Add one borrowed Book to empty  Shelf")
    @Description("Simple add one Book which is borrowed (isBorrowed = true) to empty Shelf")
    void addBookToShelf_1() {
        String expectedName = "noNameBook3";
        Shelf shelf1 = new Shelf();
        shelf1.addLiteratureObject(book3);

        assertTrue(shelf1.getLiteratureInShelf().isEmpty());
        assertTrue(shelf1.getLiteratureOutShelf().get(0) instanceof Book);
        assertEquals(expectedName, shelf1.getLiteratureOutShelf().get(0).getName());
    }

    @Test
    @DisplayName("Add one NOT borrowed Magazine to empty Shelf")
    @Description("Simple add one Magazine which is NOT borrowed (isBorrowed = false) to empty Shelf")
    void addMagazineToShelf_0() {
        String expectedName = "noNameMagazine1";
        Shelf shelf1 = new Shelf();
        shelf1.addLiteratureObject(magazine1);

        assertTrue(shelf1.getLiteratureOutShelf().isEmpty());
        assertTrue(shelf1.getLiteratureInShelf().get(0) instanceof Magazine);
        assertEquals(expectedName, shelf1.getLiteratureInShelf().get(0).getName());
    }

    @Test
    @DisplayName("Add one borrowed Magazine to empty Shelf")
    @Description("Simple add one Magazine which is borrowed (isBorrowed = true) to empty Shelf")
    void addMagazineToShelf_1() {
        String expectedName = "noNameMagazine3";
        Shelf shelf1 = new Shelf();
        shelf1.addLiteratureObject(magazine3);

        assertTrue(shelf1.getLiteratureInShelf().isEmpty());
        assertTrue(shelf1.getLiteratureOutShelf().get(0) instanceof Magazine);
        assertEquals(expectedName, shelf1.getLiteratureOutShelf().get(0).getName());
    }

    @Test
    @DisplayName("Add one Book and one Magazine (are borrowed) to empty Shelf")
    @Description("Simple add one Book and one Magazine which are borrowed (isBorrowed = true) to empty Shelf")
    void addBookAndMagazineToShelf_0() {
        Shelf shelf1 = new Shelf();
        Book book1 = new Book("1",1,true,"NoAuthor1",LocalDate.now());
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
        Book book1 = new Book("1",1,true,"NoAuthor1",LocalDate.now());
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
        Book book1 = new Book("1",1,false,"NoAuthor1",LocalDate.now());
        Magazine magazine1 = new Magazine("4",4,false);
        shelfTest.addLiteratureObject(book1);
        shelfExpect.addLiteratureObject(magazine1);

        assertEquals(shelfTest.getLiteratureInShelf().size(),shelfExpect.getLiteratureInShelf().size());
    }

    @Test
    @DisplayName("Serialization / deserialization test")
    @Description("Generate Shelf, add 6 objects of Literature (3 - Book and 3 - Magazine) with different parameters. " +
            "Than try to Serialize"+
            "Than try to Deserialize")
    public void saveShelfFile() throws IOException, ClassNotFoundException, EOFException {
        Book book1 = new Book("1",1,false,"NoAutho1",LocalDate.now());
        Book book2 = new Book("2",2,true,"NoAuthor2",LocalDate.now());
        Book book3 = new Book("3",3,true,"NoAuthor3",LocalDate.now());

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

        final String fileName = "shelf.out";
        FileOutputStream   fileOutputStream   = null;
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
        }finally {
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
            try
            {
                while(true){
                    Literature literatureBuff = (Literature) objectInputStream.readObject();
                    shelf2.addLiteratureObject(literatureBuff);
                }
            }
            catch(EOFException e) {
                //eof - no error in this case
            }
            catch(IOException e) {
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
        shelf.addLiteratureObject(new Book("2",2,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Magazine("5",5,false));
        shelf.addLiteratureObject(new Book("1",1,false,"1",LocalDate.now()));
        shelf.addLiteratureObject(new Magazine("3",3,false));
        shelf.addLiteratureObject(new Book("4",4,false,"4",LocalDate.now()));



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
        shelf.addLiteratureObject(new Book("2",2,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Magazine("5",5,false));
        shelf.addLiteratureObject(new Book("1",1,false,"1",LocalDate.now()));
        shelf.addLiteratureObject(new Magazine("3",3,false));
        shelf.addLiteratureObject(new Book("4",4,false,"4",LocalDate.now()));

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
        shelf.addLiteratureObject(new Book("LAST",55,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Book("last",100,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Book("last",55,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Book("las",55,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Book("la",55,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Book("55",55,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Book("b",6,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Book("a",5,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Book("7",7,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Book("6",6,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Book("2",2,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Magazine("5",5,false));
        shelf.addLiteratureObject(new Book("1",1,false,"1",LocalDate.now()));
        shelf.addLiteratureObject(new Magazine("3",3,false));
        shelf.addLiteratureObject(new Book("4",4,false,"4",LocalDate.now()));

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
        shelf.addLiteratureObject(new Book("LAST",55,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Book("last",100,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Book("last",55,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Book("las",55,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Book("la",55,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Book("55",55,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Book("b",6,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Book("a",5,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Book("7",7,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Book("6",6,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Book("2",2,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Magazine("5",5,false));
        shelf.addLiteratureObject(new Book("1",1,false,"1",LocalDate.now()));
        shelf.addLiteratureObject(new Magazine("3",3,false));
        shelf.addLiteratureObject(new Book("4",4,false,"4",LocalDate.now()));

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
        shelf.addLiteratureObject(new Book("LAST",55,false,"LAST",LocalDate.now()));
        shelf.addLiteratureObject(new Book("last",100,false,"last",LocalDate.now()));
        shelf.addLiteratureObject(new Book("last",55,false,"last",LocalDate.now()));
        shelf.addLiteratureObject(new Book("las",55,false,"las",LocalDate.now()));
        shelf.addLiteratureObject(new Book("la",55,false,"la",LocalDate.now()));
        shelf.addLiteratureObject(new Book("55",55,false,"55",LocalDate.now()));
        shelf.addLiteratureObject(new Book("b",6,false,"b",LocalDate.now()));
        shelf.addLiteratureObject(new Book("a",5,false,"a",LocalDate.now()));
        shelf.addLiteratureObject(new Book("7",7,false,"7",LocalDate.now()));
        shelf.addLiteratureObject(new Book("6",6,false,"6",LocalDate.now()));
        shelf.addLiteratureObject(new Book("2",2,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Magazine("5",5,false));
        shelf.addLiteratureObject(new Book("1",1,false,"1",LocalDate.now()));
        shelf.addLiteratureObject(new Magazine("3",3,false));
        shelf.addLiteratureObject(new Book("4",4,false,"4",LocalDate.now()));

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
    @DisplayName("Get sorted only Book objects by Date Of Issue")
    @Description("Test method which gives user list of Books inside Shelf by next rule:\n" +
            "Books which are sorted by DateOfIssue")
    void sortedBooksByDateOfIssue() {
        LocalDate expectedDate = LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth());

        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(new Book("LAST",55,false,"LAST",LocalDate.of(2020,12,1)));
        shelf.addLiteratureObject(new Book("last",100,false,"last",LocalDate.of(1965,12,1)));
        shelf.addLiteratureObject(new Book("last",55,false,"last",LocalDate.of(2020,12,1)));
        shelf.addLiteratureObject(new Book("las",55,false,"las",LocalDate.of(2020,12,15)));
        shelf.addLiteratureObject(new Book("la",55,false,"la",LocalDate.of(1968,12,14)));
        shelf.addLiteratureObject(new Book("55",55,false,"55",LocalDate.of(1965,12,13)));
        shelf.addLiteratureObject(new Book("b",6,false,"b",LocalDate.of(1967,12,12)));
        shelf.addLiteratureObject(new Book("a",5,false,"a",LocalDate.of(1995,12,11)));
        shelf.addLiteratureObject(new Book("7",7,false,"7",LocalDate.of(2020,12,10)));
        shelf.addLiteratureObject(new Book("6",6,false,"6",LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth())));
        shelf.addLiteratureObject(new Book("2",2,false,"2",LocalDate.of(1985,12,1)));
        shelf.addLiteratureObject(new Magazine("5",5,false));
        shelf.addLiteratureObject(new Book("1",1,false,"1",LocalDate.of(1975,12,5)));
        shelf.addLiteratureObject(new Magazine("3",3,false));
        shelf.addLiteratureObject(new Book("4",4,false,"4",LocalDate.of(1965,12,1)));

        ArrayList<Literature> sortedShelf =
                (ArrayList<Literature>) shelf.getLiteratureInShelf().stream()
                        .filter((Literature o)-> o instanceof Book)
                        .sorted(Comparator.comparingLong(
                                        o ->  ((Book) o).getIssuanceDate().toEpochDay())
                                .thenComparing(
                                        o -> ((Book) o).getAuthor().toLowerCase())
                                .thenComparing(
                                        o -> ((Book) o).getName().toLowerCase()))
                        .collect(Collectors.toList());

        shelf.setLiteratureInShelf(sortedShelf);
        System.out.println(sortedShelf);

        Book lastBook = (Book) shelf.getLiteratureInShelf().get(shelf.getLiteratureInShelf().size()-1);

        assertEquals(lastBook.getIssuanceDate(), expectedDate);
    }

    @Test
    @Deprecated
    @DisplayName("Sort Shelf Book objects by author")
    @Description("""
            Test Method which swap Literature object inside Shelf by next rule:
            firstly Magazines not in order
            than Books which are sorted by Author""")
    void sortBooksByAuthor() {
        String expectedLastAuthor = "last";
        int expectedLastPagesNumber = 100;

        Shelf shelf = new Shelf();
        shelf.addLiteratureObject(new Book("LAST",55,false,"LAST", LocalDate.now()));
        shelf.addLiteratureObject(new Book("last",100,false,"last",LocalDate.now()));
        shelf.addLiteratureObject(new Book("last",55,false,"last",LocalDate.now()));
        shelf.addLiteratureObject(new Book("las",55,false,"55",LocalDate.now()));
        shelf.addLiteratureObject(new Book("la",55,false,"55",LocalDate.now()));
        shelf.addLiteratureObject(new Book("55",55,false,"55",LocalDate.now()));
        shelf.addLiteratureObject(new Book("b",6,false,"6",LocalDate.now()));
        shelf.addLiteratureObject(new Book("a",5,false,"4",LocalDate.now()));
        shelf.addLiteratureObject(new Magazine("7",7,false));
        shelf.addLiteratureObject(new Magazine("6",6,false));
        shelf.addLiteratureObject(new Book("2",2,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Magazine("5",5,false));
        shelf.addLiteratureObject(new Book("1",1,false,"1",LocalDate.now()));
        shelf.addLiteratureObject(new Magazine("3",3,false));
        shelf.addLiteratureObject(new Book("4",4,false,"4",LocalDate.now()));


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
        shelf.addLiteratureObject(new Book("LAST",55,false,"55",LocalDate.now()));
        shelf.addLiteratureObject(new Book("last",100,false,"55",LocalDate.now()));
        shelf.addLiteratureObject(new Book("last",55,false,"55",LocalDate.now()));
        shelf.addLiteratureObject(new Book("las",55,false,"55",LocalDate.now()));
        shelf.addLiteratureObject(new Book("la",55,false,"55",LocalDate.now()));
        shelf.addLiteratureObject(new Book("55",55,false,"55",LocalDate.now()));
        shelf.addLiteratureObject(new Book("b",6,false,"6",LocalDate.now()));
        shelf.addLiteratureObject(new Book("a",5,false,"4",LocalDate.now()));
        shelf.addLiteratureObject(new Magazine("7",7,false));
        shelf.addLiteratureObject(new Magazine("6",6,false));
        shelf.addLiteratureObject(new Book("2",2,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Magazine("5",5,false));
        shelf.addLiteratureObject(new Book("1",1,false,"1",LocalDate.now()));
        shelf.addLiteratureObject(new Magazine("3",3,false));
        shelf.addLiteratureObject(new Book("4",4,false,"4",LocalDate.now()));


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
        shelf.addLiteratureObject(new Book("55",55,false,"55",LocalDate.now()));
        shelf.addLiteratureObject(new Book("6",6,false,"6",LocalDate.now()));
        shelf.addLiteratureObject(new Book("5",5,false,"4",LocalDate.now()));

        shelf.addLiteratureObject(new Magazine("7",7,false));
        shelf.addLiteratureObject(new Magazine("6",6,false));
        shelf.addLiteratureObject(new Book("2",2,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Magazine("5",5,false));
        shelf.addLiteratureObject(new Book("1",1,false,"1",LocalDate.now()));
        shelf.addLiteratureObject(new Magazine("3",3,false));
        shelf.addLiteratureObject(new Book("4",4,false,"4",LocalDate.now()));


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
        shelf.addLiteratureObject(new Book("2",2,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Magazine("5",5,false));
        shelf.addLiteratureObject(new Book("1",1,false,"1",LocalDate.now()));
        shelf.addLiteratureObject(new Magazine("3",3,false));
        shelf.addLiteratureObject(new Book("4",4,false,"4",LocalDate.now()));


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
        shelf.addLiteratureObject(new Book("2",2,false,"2",LocalDate.now()));
        shelf.addLiteratureObject(new Magazine("5",5,false));
        shelf.addLiteratureObject(new Book("1",1,false,"1",LocalDate.now()));
        shelf.addLiteratureObject(new Magazine("3",3,false));
        shelf.addLiteratureObject(new Book("4",4,false,"4",LocalDate.now()));


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

}