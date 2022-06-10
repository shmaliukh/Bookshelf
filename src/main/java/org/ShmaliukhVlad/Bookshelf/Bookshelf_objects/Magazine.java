package org.ShmaliukhVlad.Bookshelf.Bookshelf_objects;

import java.util.Comparator;

public class Magazine
        extends Literature
        implements Comparator<Magazine> {

    public Magazine(String name, int pagesNumber, boolean isBorrowed) {
        super(name, pagesNumber, isBorrowed);
    }

    @Override
    public String toString() {
        String tab2 = "\n\t\t";
        String tab3 = "\n\t\t\t";
        return  tab2 + "Magazine{" +
                tab3 + "name='" + name + '\'' +
                tab3 + "pagesNumber=" + pagesNumber +
                tab3 + "isBorrowed=" + isBorrowed +
                tab2 + '}';
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
