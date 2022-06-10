package org.ShmaliukhVlad.Bookshelf_objects;

import java.util.Comparator;

public abstract class Literature implements Comparable<Literature> {
    protected String name;
    protected int pagesNumber;
    protected boolean isBorrowed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPagesNumber() {
        return pagesNumber;
    }

    public void setPagesNumber(int pagesNumber) {
        this.pagesNumber = pagesNumber;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public Literature(String name, int pagesNumber, boolean isBorrowed) {
        this.name = name;
        this.pagesNumber = pagesNumber;
        this.isBorrowed = isBorrowed;
    }
}
