package org.ShmaliukhVlad.Bookshelf;

import jdk.jfr.Description;
import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Book;
import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Literature;
import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Magazine;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ShmaliukhVlad
 *
 *
 */

public class Shelf implements ActionsWithShelf, Serializable {

    private List<Literature> literatureInShelf;
    private List<Literature> literatureOutShelf;

    {
        literatureInShelf = new ArrayList<>();
        literatureOutShelf= new ArrayList<>();
    }

    public void addSomeLite(){

    }

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

    public Shelf(){

    }



    public void removeLiteratureFromShelf(Literature literature){
        if(literature != null){
            if(literature.isBorrowed() == true){
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

    /**
     * @param literature
     */
    @Override
    public void addLiteratureObject(Literature literature) {
        if(literature != null){
            if(literature.isBorrowed() == true){
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

    /**
     * @param literature
     */
    @Override
    public void removeLiteratureObject(Literature literature) {

    }

    /**
     * @param literature
     */
    @Override
    public void borrowLiteratureObject(Literature literature) {

    }

    /**
     * @param literature
     */
    @Override
    public void arriveLiteratureObject(Literature literature) {

    }
    /**
     *
     */

    public void sortShelfBooksByName() {
        this.setLiteratureInShelf(
                (ArrayList<Literature>) this.getLiteratureInShelf().stream()
                        .sorted(Comparator.comparing((Literature o) -> {
                            if(o instanceof Book){
                                return o.getName();
                            }
                            return "";
                        }).thenComparing((Literature o) -> {
                            if (o instanceof Book) {
                                return o.getPagesNumber();
                            }
                            return 0;
                        }))
                        .collect(Collectors.toList()));
    }

    public void sortShelfBooksByAuthor() {
        this.setLiteratureInShelf(
                (ArrayList<Literature>) this.getLiteratureInShelf().stream()
                        .sorted(Comparator.comparing((Literature o) -> {
                            if(o instanceof Book){
                                return ((Book) o).getAuthor();
                            }
                            return "";
                        }).thenComparing((Literature o) -> {
                            if (o instanceof Book) {
                                return o.getPagesNumber();
                            }
                            return 0;
                        }))
                        .collect(Collectors.toList()));
    }

    public void sortShelfBooksByPages() {
        this.setLiteratureInShelf(
                (ArrayList<Literature>) this.getLiteratureInShelf().stream()
                        .sorted(Comparator.comparingInt((Literature o) -> {
                            if(o instanceof Book){
                                return o.getPagesNumber();
                            }
                            return 0;
                        }))
                        .collect(Collectors.toList()));
    }

    public void sortShelfMagazinesByName() {
        this.setLiteratureInShelf((
                (ArrayList<Literature>) this.getLiteratureInShelf().stream()
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
                .collect(Collectors.toList())));
    }

    public void sortShelfMagazinesByPages() {
        this.setLiteratureInShelf(
                (ArrayList<Literature>) this.getLiteratureInShelf().stream()
                .sorted(Comparator.comparingInt((Literature o) -> {
                    if(o instanceof Magazine){
                        return o.getPagesNumber();
                    }
                    return 0;
                }))
                .collect(Collectors.toList()));
    }

    public void sortShelfBooksByDate() {
        //Todo
    }



    @Override
    @Description("Serialization Shelf and it's Literature objects")
    public void saveShelfToFile(){
        ObjectOutputStream objectOutputStream = null;
        final String fileName = "shelf.out";
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));
            ObjectOutputStream finalObjectOutputStream = objectOutputStream;
            this.literatureInShelf.stream().forEach(literature -> {
                assert finalObjectOutputStream != null;
                try {
                    finalObjectOutputStream.writeObject(literature);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            objectOutputStream.close();
            System.out.println("File '" + fileName + "' has been written");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.out.println("Serialization Error");
        }
    }



    @Override
    public String toString() {
        String tab1 = "\n";
        String tab2 = "\n\t";
        return  tab1 + "Shelf{" +
                tab2 + "\n\tliteratureInShelf=" + literatureInShelf +
                tab2 + "\n\tliteratureOutShelf=" + literatureOutShelf +
                tab1 + "}";
    }

    @Deprecated
    @Description("Simple save all info about Shelf and it's objects in .txt file (with current date)")
    public void saveShelfToTXTFile(){
        String filename = "books_inside_shelf_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy")) +".txt";
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))){
            oos.writeObject(this.toString());
            System.out.println("File has been written");
        }
        catch(Exception ex){
            System.err.println(ex.getMessage());
            System.out.println("Save file Error");
        }
    }
}
