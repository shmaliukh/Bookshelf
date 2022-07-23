package org.vshmaliukh.bookshelf.bookshelfObjects;

public class Gazette extends Item {

    /**
     * Base Constructor for creating Book and Magazine object
     *
     * @param name
     * @param pagesNumber
     * @param isBorrowed
     */
    public Gazette(String name, int pagesNumber, boolean isBorrowed) {
        super(name, pagesNumber, isBorrowed);
    }

    @Override
    public String toString() {
        return "Gazette {" +
                " name='" + name + '\'' +
                ", pagesNumber=" + pagesNumber +
                ", isBorrowed=" + isBorrowed +
                " }";
    }
}
