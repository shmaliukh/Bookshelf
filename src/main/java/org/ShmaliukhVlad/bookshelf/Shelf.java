package org.ShmaliukhVlad.bookshelf;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
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
                                o -> Long.parseLong(String.valueOf(((Book) o).getIssuanceDate().toEpochDay())))
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

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Literature.class, new CustomConverter());
        Gson gson = builder.setPrettyPrinting().create();

        try (FileOutputStream fos = new FileOutputStream(fileName);
             OutputStreamWriter isr = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8)) {

            for (Literature literature : this.literatureInShelf) {
                gson.toJson(literature, isr);
            }
            for (Literature literature : this.literatureOutShelf) {
                gson.toJson(literature, isr);
            }
            //gson.toJson(this.getLiteratureInShelf(), isr);
            //gson.toJson(this.getLiteratureOutShelf(), isr);

            System.out.println("File '" + fileName + "' has been written");
        }
    }

    /**
     * Deserialization Shelf and it's Literature objects
     */
    public void deserialize() throws IOException, ClassNotFoundException {
        final String fileName = FILE_NAME;
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
             this.setLiteratureInShelf(new ArrayList<>());
             this.setLiteratureOutShelf(new ArrayList<>());

             Gson gson = new Gson();
             Magazine magazine = gson.fromJson(FILE_NAME, Magazine.class);
             this.addLiteratureObject(magazine);

             //try {
             //    while (true) {
             //        Literature literatureBuff = (Literature) objectInputStream.readObject();
             //        this.addLiteratureObject(literatureBuff);
             //    }
             //} catch (EOFException e) {
             //    //eof - no error in this case
             //} catch (IOException e) {
             //    e.printStackTrace();
             //}
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
            object.addProperty("year", ((Book) literature).getIssuanceDate().getYear());
            object.addProperty("month", ((Book) literature).getIssuanceDate().getMonthValue());
            object.addProperty("day", ((Book) literature).getIssuanceDate().getDayOfMonth());
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