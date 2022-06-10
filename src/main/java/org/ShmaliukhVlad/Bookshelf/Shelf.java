package org.ShmaliukhVlad.Bookshelf;

import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Literature;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ShmaliukhVlad
 *
 *
 */

public class Shelf implements ActionsWithShelf{

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
    @Override
    public void saveShelfToFile(){
        String filename = "books_inside_shelf_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy")) +".txt";
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))){
            oos.writeObject(this.toString());
            System.out.println("File has been written");
        }
        catch(Exception ex){
            System.err.println(ex.getMessage());
            System.out.println("Serialization Error");
        }
    }

    /**
     *
     */
    @Override
    public void getListOfBooks() {

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
}
