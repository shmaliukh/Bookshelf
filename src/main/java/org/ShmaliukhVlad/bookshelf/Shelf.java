package org.ShmaliukhVlad.bookshelf;

import com.google.gson.*;
import jdk.jfr.Description;
import org.ShmaliukhVlad.bookshelf.actionsWithShelf.ActionsWithBooks;
import org.ShmaliukhVlad.bookshelf.actionsWithShelf.ActionsWithMagazines;
import org.ShmaliukhVlad.bookshelf.actionsWithShelf.BaseActionsWithShelf;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Literature;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Magazine;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.ShmaliukhVlad.constants.ConstantValues.*;

/**
 * @author ShmaliukhVlad
 * @version 1.0.0
 * This is Shelf class which simulates real shelf with books and magazines
 */
public class Shelf implements BaseActionsWithShelf, ActionsWithBooks, ActionsWithMagazines {

    private List<Literature> literatureInShelf;
    private List<Literature> literatureOutShelf;

    Gson gson;

    public Shelf(){
        literatureInShelf = new ArrayList<>();
        literatureOutShelf= new ArrayList<>();
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
            informAboutAddedLiteratureObject(literature);
        }
        else {
            System.out.println("The literature object (book or magazine) is empty");
            System.err.println("The literature object (book or magazine) is NULL");
        }
    }

    /**
     * Method simply inform user about added Literature object
     */
    private void informAboutAddedLiteratureObject(Literature literature) {
        System.out.println(literature + " has added to shelf");
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
                                o -> Long.parseLong(((Book) o).getIssuanceDate()))
                        .thenComparing(
                                o -> ((Book) o).getAuthor().toLowerCase())
                        .thenComparing(
                                o -> ((Book) o).getName().toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    @Description("Serialization Shelf and it's Literature objects")
    public void saveShelfToFile() throws IOException {
        final String fileName = FILE_NAME;

        try {
            Writer writer = new FileWriter(fileName);

            gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(Book.class, new BookSerializer())
                    .registerTypeAdapter(DateSerializer.class, new DateSerializer())
                    .create();
            new Gson().toJson(getBooks(), writer);

            gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            new Gson().toJson(getMagazines(), writer);
            writer.close();
            System.out.println("File '" + fileName + "' has been written");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private List<Book> getBooks() {
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

    private List<Magazine> getMagazines() {
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
    public void deserialize() throws IOException, ClassNotFoundException {
        final String fileName = FILE_NAME;

        Gson gson = new GsonBuilder().create();
        Path path = new File(fileName).toPath();

        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            this.literatureInShelf = new ArrayList<>();
            this.literatureInShelf = new ArrayList<>();

            Book arrBooks [] = gson.fromJson(reader, Book[].class);
            Magazine arrMagazine [] = gson.fromJson(reader, Magazine[].class);

            Arrays.stream(arrBooks).forEach(e -> {
                this.addLiteratureObject(e);
            });
            Arrays.stream(arrMagazine).forEach(e -> {
                this.addLiteratureObject(e);
            });
        }
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

    //getters and setters
    public List<Literature> getLiteratureInShelf() {
        return literatureInShelf;
    }

    public void setLiteratureInShelf(List<Literature> literatureInShelf) {
        this.literatureInShelf = literatureInShelf;
    }

    public List<Literature> getLiteratureOutShelf() {
        return literatureOutShelf;
    }

    public void setLiteratureOutShelf(List<Literature> literatureOutShelf) {
        this.literatureOutShelf = literatureOutShelf;
    }
}


class CustomConverter implements JsonSerializer<Literature>, JsonDeserializer<Literature> {
    @Override
    public JsonElement serialize(Literature literature,
                                 Type type,
                                 JsonSerializationContext jsonSerializationContext) {

        JsonObject object = new JsonObject();

        object.addProperty("name", literature.getName());
        object.addProperty("pagesNumber", literature.getPagesNumber());
        object.addProperty("isBorrowed", literature.isBorrowed());

        if(literature instanceof Book){
            object.addProperty("author", ((Book) literature).getAuthor());
            object.addProperty("year", ((Book) literature).getYear());
            object.addProperty("month", ((Book) literature).getMonth());
            object.addProperty("day", ((Book) literature).getDay());
        }
        return object;
    }

    @Override
    public Literature deserialize(JsonElement jsonElement,
                                  Type type,
                                  JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        LocalDate localDate = LocalDate.of(
                jsonObject.get("year").getAsInt(),
                jsonObject.get("month").getAsInt(),
                jsonObject.get("day").getAsInt()
        );

        return new Magazine(
                jsonObject.get("name").getAsString(),
                jsonObject.get("pagesNumber").getAsInt(),
                jsonObject.get("isBorrowed").getAsBoolean());
    }
}



