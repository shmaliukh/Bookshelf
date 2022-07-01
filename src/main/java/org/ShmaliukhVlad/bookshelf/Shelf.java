package org.ShmaliukhVlad.bookshelf;

import com.google.gson.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.ShmaliukhVlad.bookshelf.actionsWithShelf.ActionsWithBooks;
import org.ShmaliukhVlad.bookshelf.actionsWithShelf.ActionsWithMagazines;
import org.ShmaliukhVlad.bookshelf.actionsWithShelf.BaseActionsWithShelf;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Literature;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Magazine;
import org.ShmaliukhVlad.serices.ContainerForLiteratureObject;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.ShmaliukhVlad.constants.ConstantValues.*;

/**
 * @author ShmaliukhVlad
 * @version 1.0.0
 * This is Shelf class which simulates real shelf with books and magazines
 */
@Data
@AllArgsConstructor
public class Shelf implements BaseActionsWithShelf, ActionsWithBooks, ActionsWithMagazines {

    private List<Literature> literatureInShelf  = new ArrayList<>();
    private List<Literature> literatureOutShelf = new ArrayList<>();

    public List<List<Object>> allLiterature = new ArrayList<>();

    public Shelf(){
    }

    public Shelf(List<Literature> literatureList){
        for (Literature literature : literatureList) {
            this.addLiteratureObject(literature);
        }
    }

    public Shelf(List<Book> books, List<Magazine> magazines) {
        if(!books.isEmpty()){
            for (Book book : books) {
                this.addLiteratureObject(book);
            }
        }
        if(!magazines.isEmpty()){
            for (Magazine magazine : magazines) {
                this.addLiteratureObject(magazine);
            }
        }
    }

    /**
     * Method returns all available books which are inside (not borrowed) Shelf
     * @return list of Book objects inside Shelf
     */
    public List<Book> getAvailableBooks(){
        return this.getBooks().stream()
                .filter(o -> o.isBorrowed() == false)
                .collect(Collectors.toList());
    }

    /**
     * Method returns all available magazines which are inside (not borrowed) Shelf
     * @return list of Magazine objects inside Shelf
     */
    public List<Magazine> getAvailableMagazines(){
        return this.getMagazines().stream()
                .filter(o -> o.isBorrowed() == false)
                .collect(Collectors.toList());
    }

    @Deprecated
    public List<List<Object>> getAllLiterature(){
        allLiterature.add(Collections.singletonList(literatureInShelf));// TODO ???
        allLiterature.add(Collections.singletonList(literatureOutShelf));
        return allLiterature;
    }

    @Override
    public void addLiteratureObject(Literature literature) {
        if(literature != null){
            if(literature.isBorrowed()){
                getLiteratureOutShelf().add(literature);
            }
            else{
                getLiteratureInShelf().add(literature);
            }
        }
        else {
            System.out.println("The literature object (book or magazine) is empty");
            System.err.println("The literature object (book or magazine) is NULL");
        }
    }

    @Override
    public void deleteLiteratureObjectByIndex(int index) {
        if(!this.getLiteratureInShelf().isEmpty()){
            if(index >0 && index <= this.getLiteratureInShelf().size()){
                informAboutDeletedLiteratureObject(this.getLiteratureInShelf().remove(index-1));
            }
            else {
                System.out.println("Wrong index");
            }
        }
        else System.out.println("Empty shelf");
    }

    /**
     * Method simply inform user about deleted Literature object
     */
    private void informAboutDeletedLiteratureObject(Literature literature) {
        System.out.println(literature + " has deleted from shelf");
    }

    @Override
    public void borrowLiteratureObjectFromShelfByIndex(int index) {
        Literature buffer;
        if(!this.getLiteratureInShelf().isEmpty()){
            if(index > 0 && index <= this.getLiteratureInShelf().size()){
                buffer = this.getLiteratureInShelf().remove(index-1);
                buffer.setBorrowed(true);
                this.getLiteratureOutShelf().add(buffer);
                informAboutBorrowedLiteratureObject(buffer);
            }
            else {
                System.out.println("Wrong index");
            }
        }
        else System.out.println("No available literature");
    }

    /**
     * Method simply inform user about borrowed Literature object
     */
    private void informAboutBorrowedLiteratureObject(Literature literature) {
        System.out.println(literature + " has borrowed from shelf");
    }

    @Override
    public void arriveLiteratureObjectFromShelfByIndex(int index) {
        Literature buffer;
        if(!this.getLiteratureOutShelf().isEmpty()){
            if(index > 0 && index <= this.getLiteratureOutShelf().size()){
                buffer = this.getLiteratureOutShelf().remove(index-1);
                buffer.setBorrowed(false);
                this.getLiteratureInShelf().add(buffer);
                informAboutArrivedLiteratureObject(buffer);
            }
            else {
                System.out.println("Wrong index");
            }
        }
        else System.out.println("Literature is not borrowed");
    }

    /**
     * Method simply inform user about arrived Literature object
     */
    private void informAboutArrivedLiteratureObject(Literature literature) {
        System.out.println(literature + " has arrived back to shelf");
    }

    @Override
    public void printSortedMagazinesByName() {
        if(getSortedMagazinesByName().isEmpty()){
            System.out.println("No available magazines in Shelf");
        }
        else {
            System.out.println("Available magazines sorted by name:");
            for (Magazine magazine : getSortedMagazinesByName()) {
                System.out.print(magazine.getPrintableLineOfLiteratureObject(SORT_MAGAZINES_BY_NAME));
            }
        }
    }

    @Override
    public void printSortedMagazinesByPages() {
        if(getSortedMagazinesByPages().isEmpty()){
            System.out.println("No available magazines in Shelf");
        }
        else {
            System.out.println("Available magazines sorted by pages:");
            for (Magazine magazine : getSortedMagazinesByPages()) {
                System.out.print(magazine.getPrintableLineOfLiteratureObject(SORT_MAGAZINES_BY_PAGES_NUMBER));
            }
        }
    }

    @Override
    public void printSortedBooksByName() {
        if(getSortedBooksByName().isEmpty()){
            System.out.println("No available books in Shelf");
        }
        else {
            System.out.println("Available books sorted by name:");
            for (Book book : getSortedBooksByName()) {
                System.out.print(book.getPrintableLineOfLiteratureObject(SORT_BOOKS_BY_NAME));
            }
        }
    }

    @Override
    public void printSortedBooksByPages() {
        if(getSortedBooksByPages().isEmpty()){
            System.out.println("No available books in Shelf");
        }
        else {
            System.out.println("Available books sorted by pages:");
            for (Book book : getSortedBooksByPages()) {
                System.out.print(book.getPrintableLineOfLiteratureObject(SORT_BOOKS_BY_PAGES_NUMBER));
            }
        }
    }

    @Override
    public void printSortedBooksByAuthor() {
        if(getSortedBooksByAuthor().isEmpty()){
                 System.out.println("No available books in Shelf");
        }
        else {
            System.out.println("Available books sorted by author:");
            for (Book book : getSortedBooksByAuthor()) {
                        System.out.print(book.getPrintableLineOfLiteratureObject(SORT_BOOKS_BY_AUTHOR));
            }
        }
    }

    @Override
    public void printSortedBooksByDate() {
        if(getSortedBooksByDate().isEmpty()){
            System.out.println("No available books in Shelf");
        }
        else {
            System.out.println("Available books sorted by date of issue:");
            for (Book book : getSortedBooksByDate()) {
                System.out.print(book.getPrintableLineOfLiteratureObject(SORT_BOOKS_BY_DATE_OF_ISSUE));
            }
        }
    }

    /**
     * Method returns sorted ArrayList of Magazines by Name number from current Shelf
     * @return ArrayList<Magazine>
     *     firstly sorted by Name
     *     than sorted by Pages number
     */
    public ArrayList<Magazine> getSortedMagazinesByName(){
        return (ArrayList<Magazine>) this.getLiteratureInShelf().stream()
                .filter((Literature o) -> o instanceof Magazine)
                .map(o -> (Magazine) o)
                .sorted(Comparator.comparing(
                                o -> ((Magazine) o).getName().toLowerCase())
                        .thenComparing(
                                o -> ((Magazine) o).getPagesNumber()))
                .collect(Collectors.toList());
    }

    /**
     * Method returns sorted ArrayList of Magazines by Pages number from current Shelf
     * @return ArrayList<Magazine>
     *     firstly sorted by Pages number
     *     than sorted by Name
     */
    public ArrayList<Magazine> getSortedMagazinesByPages(){
        return (ArrayList<Magazine>) this.getLiteratureInShelf().stream()
                .filter((Literature o)-> o instanceof Magazine)
                .map(o -> (Magazine) o)
                .sorted(Comparator.comparing(
                                o -> ((Magazine) o).getPagesNumber())
                        .thenComparing(
                                o -> ((Magazine) o).getName().toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Method returns sorted ArrayList of Books by Name from current Shelf
     * @return ArrayList<Book>
     *     firstly sorted by Name
     *     than sorted by Pages number
     */
    public ArrayList<Book> getSortedBooksByName(){
        return (ArrayList<Book>) this.getLiteratureInShelf().stream()
                .filter((Literature o)-> o instanceof Book)
                .map(o -> (Book) o)
                .sorted(Comparator.comparing(
                                o -> ((Book) o).getName().toLowerCase())
                        .thenComparing(
                                o -> ((Book) o).getPagesNumber()))
                .collect(Collectors.toList());
    }

    /**
     * Method returns sorted ArrayList of Books by Pages number from current Shelf
     * @return ArrayList<Book>
     *     firstly sorted by Pages number
     *     than sorted by Name
     */
    public ArrayList<Book> getSortedBooksByPages(){
        return (ArrayList<Book>) this.getLiteratureInShelf().stream()
                .filter((Literature o)-> o instanceof Book)
                .map(o -> (Book) o)
                .sorted(Comparator.comparing(
                                Book::getPagesNumber)
                        .thenComparing(Book::getName))
                .collect(Collectors.toList());
    }

    /**
     * Method returns sorted ArrayList of Books by Author from current Shelf
     * @return ArrayList<Book>
     *     firstly sorted by Author
     *     than sorted by Pages
     */
    public ArrayList<Book> getSortedBooksByAuthor(){
        return (ArrayList<Book>) this.getLiteratureInShelf().stream()
                .filter((Literature o)-> o instanceof Book)
                .map(o -> (Book) o)
                .sorted(Comparator.comparing(
                            o -> ((Book) o).getAuthor().toLowerCase())
                        .thenComparingInt(
                            o -> ((Book) o).getPagesNumber()))
                .collect(Collectors.toList());
    }

    /**
     * Method returns sorted ArrayList of Books by Date from current Shelf
     * @return ArrayList<Book>
     *     firstly sorted by Date
     *     than sorted by Author
     *     than sorted by Name
     */
    public ArrayList<Book> getSortedBooksByDate(){
        return (ArrayList<Book>) this.getLiteratureInShelf().stream()
                .filter((Literature o)-> o instanceof Book)
                .map(o -> (Book) o)
                .sorted(Comparator.comparingLong(
                                o -> ((Book) o).getIssuanceDate().getTime())
                        .thenComparing(
                                o -> ((Book) o).getAuthor().toLowerCase())
                        .thenComparing(
                                o -> ((Book) o).getName().toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public void saveShelfToGsonFile(String fileName){
        try {
            Writer fw = new FileWriter(fileName);
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .toJson(getContainerForLiteratureObjects(), fw);
            fw.flush();
            fw.close();
            //System.out.println("File '" + fileName + "' has been written");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //TODO description
    private List<ContainerForLiteratureObject> getContainerForLiteratureObjects() {
        List<ContainerForLiteratureObject> containerArrayList= new ArrayList<>();
        for (Book book : this.getBooks()) {
            containerArrayList.add(new ContainerForLiteratureObject(book));
        }
        for (Magazine magazine : this.getMagazines()) {
            containerArrayList.add(new ContainerForLiteratureObject(magazine));
        }
        return containerArrayList;
    }

    public List<Book> getBooks() {
        List <Book> arrBooks = new ArrayList<>();
        arrBooks.addAll(this.literatureInShelf.stream()
                .filter((Literature o)-> o instanceof Book)
                .map(o -> (Book) o)
                .collect(Collectors.toList()));
        arrBooks.addAll(this.literatureOutShelf.stream()
                        .filter((Literature o)-> o instanceof Book)
                        .map(o -> (Book) o)
                        .collect(Collectors.toList()));
        return arrBooks;
    }

    public List<Magazine> getMagazines() {
        List <Magazine> arrMagazines = new ArrayList<>();
        arrMagazines.addAll(this.literatureInShelf.stream()
                .filter((Literature o)-> o instanceof Magazine)
                .map(o -> (Magazine) o)
                .collect(Collectors.toList()));
        arrMagazines.addAll(this.literatureOutShelf.stream()
                .filter((Literature o)-> o instanceof Magazine)
                .map(o -> (Magazine) o)
                .collect(Collectors.toList()));
        return arrMagazines;
    }

    /**
     * Deserialization Shelf and it's Literature objects
     */
    public static Shelf readShelfFromGsonFile(String fileName) throws IOException, ClassNotFoundException {
        FileReader fr = new FileReader(fileName);
        Gson gson = new Gson();

        List<Literature> literatureList = new ArrayList();
        JsonArray jsonArray;
        try{
            jsonArray = gson.fromJson(fr, JsonArray.class);
        }
        catch (JsonSyntaxException e){
            informAboutGsonReadErr();
            return new Shelf();
        }

        for (JsonElement element : jsonArray) {
            //System.out.println("element: " + element);
            JsonObject itemObject = element.getAsJsonObject().getAsJsonObject("Literature");
            String typeOfClass;
            try {
                typeOfClass = element.getAsJsonObject().get("ClassType").getAsString();
            }
            catch (NullPointerException e){
                informAboutGsonReadErr();
                return new Shelf();
            }
            //System.out.println("    classType: " + typeOfClass);
            //System.out.println("    itemObject: " + itemObject);
            List<Book> books = new ArrayList<>();
            List<Magazine> magazines = new ArrayList<>();

            if (typeOfClass.equals(Book.class.toString())) {
                books.add(gson.fromJson(itemObject, Book.class));
                //System.out.println("        book: " + gson.fromJson(itemObject, Book.class));
            }
            else if (typeOfClass.equals(Magazine.class.toString())) {
                magazines.add(gson.fromJson(itemObject, Magazine.class));
                //System.out.println("        magazine: " + gson.fromJson(itemObject, Magazine.class));
            }
            literatureList.addAll(books);
            literatureList.addAll(magazines);
        }
        return new Shelf(literatureList);
    }

    private static void informAboutGsonReadErr() {
        System.err.println("Problem to read shelf from file");
    }

    /**
     * Simple forming String about Book object
     * @return String about Shelf object
     */
    @Override
    public String toString() {
        return  "Shelf {" +
                "\n\tliteratureInShelf=" + literatureInShelf +
                "\n\tliteratureOutShelf=" + literatureOutShelf +
                "}";
    }

    //TODO delete method???
    void saveLiteratureToGsonFile(Shelf shelf, String fileName) throws IOException {
        FileWriter fw = new FileWriter(fileName);
        new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(shelf.getAllLiterature(), fw);
        fw.flush();
        fw.close();
    }

    void saveBooksToGsonFile(String fileName) throws IOException {
        FileWriter fw = new FileWriter(fileName);
        new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(this.getBooks(), fw);
        fw.flush();
        fw.close();
    }

    void saveMagazinesToGsonFile(String fileName) throws IOException {
        FileWriter fw = new FileWriter(fileName);
        new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(this.getMagazines(), fw);
        fw.flush();
        fw.close();
    }


    List<Magazine> getMagazinesFromGsonFile(String fileName) throws FileNotFoundException {
        FileReader fr = new FileReader(fileName);
        List<Magazine> magazineList = new ArrayList<>();

        String name;
        Integer pages;
        Boolean isBorrowed;

        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(fr, JsonArray.class);
        for (JsonElement jsonElement : jsonArray) {// TODO cover with try-catch
            name = jsonElement.getAsJsonObject().getAsJsonPrimitive("Name").getAsString();
            pages = jsonElement.getAsJsonObject().getAsJsonPrimitive("Pages number").getAsInt();
            isBorrowed = jsonElement.getAsJsonObject().getAsJsonPrimitive("Borrowed").getAsBoolean();

            magazineList.add(new Magazine(name, pages, isBorrowed));
        }
        return magazineList;
    }

    List<Book> getBooksFromGsonFile(String fileName) throws FileNotFoundException {
        FileReader fr = new FileReader(fileName);
        List<Book> bookList = new ArrayList<>();

        String name;
        Integer pages;
        Boolean isBorrowed;
        String author;
        Date dateOfIssue;

        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(fr, JsonArray.class);
        for (JsonElement jsonElement : jsonArray) { // TODO cover with try-catch
            //System.out.println("Book");
            name = jsonElement.getAsJsonObject().getAsJsonPrimitive("Name").getAsString();
            //System.out.println("    name = " + name);
            pages = jsonElement.getAsJsonObject().getAsJsonPrimitive("Number of pages").getAsInt();
            //System.out.println("    pages = " + pages);
            isBorrowed = jsonElement.getAsJsonObject().getAsJsonPrimitive("Borrowed").getAsBoolean();
            //System.out.println("    borrowed = " + isBorrowed);
            author = jsonElement.getAsJsonObject().getAsJsonPrimitive("Author").getAsString();
            //System.out.println("    author = " + author);
            dateOfIssue = new Date(jsonElement.getAsJsonObject().getAsJsonPrimitive("Date of issue").getAsString()); // TODO date adapter
            //System.out.println("    date = " + dateOfIssue);

            bookList.add(new Book(name,pages,isBorrowed,author,dateOfIssue));
        }
        return bookList;
    }

    public void saveShelfToDifferentFiles(String fileName) throws IOException {
        this.saveBooksToGsonFile(fileName+"Books");
        this.saveMagazinesToGsonFile(fileName+"Magazines");
    }
}
