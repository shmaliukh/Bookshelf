package org.ShmaliukhVlad.bookshelf.bookshelfObjects;

import java.io.Serializable;

/**
 * @author ShmaliukhVlad
 * @version 1.0.0
 * This is Literature class which is abstraction class for generating extended classes like Book and Magazine
 */
public abstract class Literature implements Serializable {

    protected String name;
    protected int pagesNumber;
    protected boolean isBorrowed;

    /**
     * Base Constructor for creating Book and Magazine object
     */
    public Literature(String name, int pagesNumber, boolean isBorrowed) {
        this.name = name;
        this.pagesNumber = pagesNumber;
        this.isBorrowed = isBorrowed;
    }

    public String getPrintableLineOfLiterature(){
        //Todo
        String tab2 = "\n\t\t";
        String tab3 = "\n\t\t\t";
        return tab2 + "Book{" +
                tab3 + "name='" + name + '\'' +
                tab3 + "pagesNumber=" + pagesNumber +
                tab3 + "isBorrowed=" + isBorrowed +
                tab2 + "}";
    }


    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPagesNumber() {
        return pagesNumber;
    }

    /**
     * Method validate information about pages value
     * @param pagesNumber must be greater than 0
     */
    public void setPagesNumber(int pagesNumber) {
        if(pagesNumber < 0){
            pagesNumber = 1;
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
