package org.ShmaliukhVlad.Bookshelf;

import jdk.jfr.Description;
import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Book;
import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Literature;
import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Magazine;

import java.io.*;
import java.time.LocalDate;
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
    private final List<Literature> literatureOutShelf;

    {
        literatureInShelf = new ArrayList<>();
        literatureOutShelf= new ArrayList<>();
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

    public Shelf(){

    }

    /**
     * @param literature
     */
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

    /**
     * @param index
     */
    @Override
    public void deleteLiteratureObjectByIndex(int index) {
        if(!this.getLiteratureInShelf().isEmpty()){
            if(index >= 0 && index < this.getLiteratureInShelf().size()){
                this.getLiteratureInShelf().remove(index);
            }
            else {
                System.out.println("Wrong index");
            }
        }
        else System.out.println("Empty");
    }

    /**
     * @param index
     */
    @Override
    public void borrowLiteratureObjectFromShelfByIndex(int index) {
        Literature buffer;
        if(!this.getLiteratureInShelf().isEmpty()){
            if(index >= 0 && index < this.getLiteratureInShelf().size()){
                buffer = this.getLiteratureInShelf().remove(index);
                buffer.setBorrowed(true);
                this.getLiteratureOutShelf().add(buffer);
            }
            else {
                System.out.println("Wrong index");
            }
        }
        else System.out.println("Empty");
    }

    /**
     *@param index
     */
    @Override
    public void arriveLiteratureObjectFromShelfByIndex(int index) {
        Literature buffer;
        if(!this.getLiteratureOutShelf().isEmpty()){
            if(index >= 0 && index < this.getLiteratureOutShelf().size()){
                buffer = this.getLiteratureOutShelf().remove(index);
                buffer.setBorrowed(false);
                this.getLiteratureInShelf().add(buffer);
            }
            else {
                System.out.println("Wrong index");
            }
        }
        else System.out.println("Empty");
    }

    /**
     *
     */
    @Override
    public void printSortedMagazinesByName() {
        System.out.println(
                this.getLiteratureInShelf().stream()
                        .filter((Literature o) -> o instanceof Magazine)
                        .sorted(Comparator.comparing(
                                        (Literature o) -> o.getName())
                                .thenComparing(
                                        (Literature o) -> o.getPagesNumber())).toList()
        );
    }

    /**
     *
     */
    @Override
    public void printSortedMagazinesByPages() {
        System.out.println(
                this.getLiteratureInShelf().stream()
                        .filter((Literature o)-> o instanceof Magazine)
                        .sorted(Comparator.comparing(
                                        (Literature o) -> o.getPagesNumber())
                                .thenComparing(
                                        (Literature o) -> o.getName()))
                        .collect(Collectors.toList())
        );
    }

    /**
     *
     */
    @Override
    public void printSortedBooksByName() {
        System.out.println(
                this.getLiteratureInShelf().stream()
                        .filter((Literature o)-> o instanceof Book)
                        .sorted(Comparator.comparing(
                                        (Literature o) -> o.getName())
                                .thenComparing(
                                        (Literature o) -> o.getPagesNumber()))
                        .collect(Collectors.toList())
        );
    }

    /**
     *
     */
    @Override
    public void printSortedBooksByPages() {
        System.out.println(
                this.getLiteratureInShelf().stream()
                        .filter((Literature o)-> o instanceof Book)
                        .sorted(Comparator.comparing(
                                        (Literature o) -> o.getPagesNumber())
                                .thenComparing(
                                        (Literature o) -> o.getName()))
                        .collect(Collectors.toList())
        );
    }

    /**
     *
     */
    @Override
    public void printSortedBooksByAuthor() {
        System.out.println(
                this.getLiteratureInShelf().stream()
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
                        .collect(Collectors.toList())
        );
    }

    /**
     *
     */
    @Override
    public void printSortedBooksByDate() {
        System.out.println(
                this.getLiteratureInShelf().stream()
                        .filter((Literature o)-> o instanceof Book)
                        .sorted(Comparator.comparingInt((Literature o) -> {
                            if(o instanceof Book){
                                return ((Book) o).getIssuanceDate().getYear();
                            }
                            return LocalDate.now().getYear();
                        }).thenComparing((Literature o) -> {
                            if(o instanceof Book){
                                return ((Book) o).getIssuanceDate().getMonthValue();
                            }
                            return LocalDate.now().getMonthValue();
                        }).thenComparing((Literature o) -> {
                            if (o instanceof Book) {
                                return ((Book) o).getIssuanceDate().getDayOfMonth();
                            }
                            return LocalDate.now().getDayOfMonth();
                        }))
                        .collect(Collectors.toList())
        );
    }

    @Deprecated
    public void sortOnlyBooksByNameInShelf() {
        this.setLiteratureInShelf(
                this.getLiteratureInShelf().stream()
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

    @Deprecated
    public void sortOnlyBooksByAuthorInShelf() {
        this.setLiteratureInShelf(
                this.getLiteratureInShelf().stream()
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

    @Deprecated
    public void sortShelfBooksByPagesInShelf() {
        this.setLiteratureInShelf(
                this.getLiteratureInShelf().stream()
                        .sorted(Comparator.comparingInt((Literature o) -> {
                            if(o instanceof Book){
                                return o.getPagesNumber();
                            }
                            return 0;
                        }))
                        .collect(Collectors.toList()));
    }

    @Deprecated
    public void sortShelfMagazinesByNameInShelf() {
        this.setLiteratureInShelf(this.getLiteratureInShelf().stream()
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
        .collect(Collectors.toList()));
    }

    @Deprecated
    public void sortShelfMagazinesByPagesInShelf() {
        this.setLiteratureInShelf(
                this.getLiteratureInShelf().stream()
                .sorted(Comparator.comparingInt((Literature o) -> {
                    if(o instanceof Magazine){
                        return o.getPagesNumber();
                    }
                    return 0;
                }))
                .collect(Collectors.toList()));
    }

    @Override
    @Description("Serialization Shelf and it's Literature objects")
    public void saveShelfToFile() throws IOException {
        final String fileName = "shelf.out";
        FileOutputStream   fileOutputStream   = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileName);
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));

            ObjectOutputStream finalObjectOutputStream = objectOutputStream;
            this.getLiteratureInShelf().stream().forEach(literature -> {
                try {
                    finalObjectOutputStream.writeObject(literature);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            this.getLiteratureOutShelf().stream().forEach(literature -> {
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
    }


    @Description("Deserialization Shelf and it's Literature objects")
    public void deserialize() throws IOException, ClassNotFoundException {
        final String fileName = "shelf.out";
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            fileInputStream = new FileInputStream(fileName);
            objectInputStream = new ObjectInputStream(fileInputStream);
            try
            {
                while(true){
                    Literature literatureBuff = (Literature) objectInputStream.readObject();
                    this.addLiteratureObject(literatureBuff);
                }
            }
            catch(EOFException e) {
                //eof - no error in this case
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        } finally {
            System.out.println(this);
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (objectInputStream != null) {
                objectInputStream.close();
            }
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
