package org.ShmaliukhVlad.serices;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ShmaliukhVlad.bookshelf.Shelf;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Book;
import org.ShmaliukhVlad.bookshelf.bookshelfObjects.Magazine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShelfContainer {
    private List<MyBook> bookList = new ArrayList<>();
    private List<MyMagazine> magazineList = new ArrayList<>();

    public ShelfContainer(Shelf shelf){
        for (Magazine magazine : shelf.getMagazines()) {
            magazineList.add(new MyMagazine(magazine.getName(), magazine.getPagesNumber(), magazine.isBorrowed()));
        }
        for (Book book : shelf.getBooks()) {
            bookList.add(new MyBook(book.getName(), book.getPagesNumber(), book.isBorrowed(), book.getAuthor(), book.getIssuanceDate()));
        }
    }

    public List<Book> getBooks(){
        List<Book> books = new ArrayList<>();
        for (MyBook b : bookList) {
            books.add(new Book(b.getName(), b.getPages(), b.isBorrowed(), b.getAuthor(), b.getDate()));
        }
        return books;
    }

    public List<Magazine> getMagazines(){
        List<Magazine> magazines = new ArrayList<>();
        for (MyMagazine m : magazineList) {
            magazines.add(new Magazine(m.getName(), m.getPages(), m.isBorrowed()));
        }
        return magazines;
    }
}

@Data
@AllArgsConstructor
class MyBook {
    private String name;
    private int pages;
    private boolean isBorrowed;
    private String author;
    private Date date;

    public Book convert(){
        return new Book(name,pages,isBorrowed,author,date);
    }
}

@Data
@AllArgsConstructor
class MyMagazine {
    private String name;
    private int pages;
    private boolean isBorrowed;

    public Magazine convert(){
        return new Magazine(name,pages,isBorrowed);
    }
}