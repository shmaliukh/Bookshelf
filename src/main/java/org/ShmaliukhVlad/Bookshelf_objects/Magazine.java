package org.ShmaliukhVlad.Bookshelf_objects;

import java.util.Comparator;

public class Magazine
        extends Literature
        implements Comparator<Magazine> {

    public Magazine(String name, int pagesNumber, boolean isBorrowed) {
        super(name, pagesNumber, isBorrowed);
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "name='" + name + '\'' +
                ", pagesNumber=" + pagesNumber +
                ", isBorrowed=" + isBorrowed +
                '}';
    }

    /**
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return
     */
    @Override
    public int compare(Magazine o1, Magazine o2) {
        return 0;
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
