package org.ShmaliukhVlad.Bookshelf.Bookshelf_objects;

import java.io.Serializable;

public abstract class Literature implements Serializable {

    protected String name;
    protected int pagesNumber;
    protected boolean isBorrowed;

    public Literature(String name, int pagesNumber, boolean isBorrowed) {
        this.name = name;
        this.pagesNumber = pagesNumber;
        this.isBorrowed = isBorrowed;
    }

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
        if(pagesNumber < 0){
            pagesNumber = 0;
        }
        this.pagesNumber = pagesNumber;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }





}
