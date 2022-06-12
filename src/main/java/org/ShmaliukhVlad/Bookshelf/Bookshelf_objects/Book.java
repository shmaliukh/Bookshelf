package org.ShmaliukhVlad.Bookshelf.Bookshelf_objects;

import java.time.LocalDate;

public class Book extends Literature{

    private String author;
    private LocalDate issuanceDate;

    public Book(String name, int pagesNumber, boolean isBorrowed, String author, LocalDate issuanceDate) {
        super(name, pagesNumber, isBorrowed);
        this.author = author;
        this.issuanceDate = issuanceDate;
    }

    @Override
    public String toString() {
        String tab2 = "\n\t\t";
        String tab3 = "\n\t\t\t";
        return tab2 + "Book{" +
               tab3 + "author='" + author + '\'' +
               tab3 + "issuanceDate=" + issuanceDate +
               tab3 + "name='" + name + '\'' +
               tab3 + "pagesNumber=" + pagesNumber +
               tab3 + "isBorrowed=" + isBorrowed +
               tab2 + "}";
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getIssuanceDate() {
        return issuanceDate;
    }

    public void setIssuanceDate(LocalDate issuanceDate) {
        this.issuanceDate = issuanceDate;
    }
}
