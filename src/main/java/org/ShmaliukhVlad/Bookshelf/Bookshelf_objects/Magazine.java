package org.ShmaliukhVlad.Bookshelf.Bookshelf_objects;

import java.util.Comparator;

public class Magazine
        extends Literature {

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



}
