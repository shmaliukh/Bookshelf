package org.ShmaliukhVlad.Bookshelf.Bookshelf_objects;

import java.util.Comparator;
import java.util.Date;

public class Book extends Literature implements Comparator {

    private String author;
    private Date issuanceDate;

    public Book(String name, int pagesNumber, boolean isBorrowed, String author, Date issuanceDate) {
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

    public Date getIssuanceDate() {
        return issuanceDate;
    }

    public void setIssuanceDate(Date issuanceDate) {
        this.issuanceDate = issuanceDate;
    }

    /**
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return
     */
    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }
}
