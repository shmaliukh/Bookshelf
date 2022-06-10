package org.ShmaliukhVlad.Bookshelf_objects;

import java.util.Date;

public class Book extends Literature{
    private String author;
    private Date issuanceDate;

    public Book(String name, int pagesNumber, boolean isBorrowed, String author, Date issuanceDate) {
        super(name, pagesNumber, isBorrowed);
        this.author = author;
        this.issuanceDate = issuanceDate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", issuanceDate=" + issuanceDate +
                ", name='" + name + '\'' +
                ", pagesNumber=" + pagesNumber +
                ", isBorrowed=" + isBorrowed +
                "}\n";
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
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Literature o) {
        return 0;
    }
}
