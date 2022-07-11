package org.vshmaliukh.bookshelf.bookshelfObjects;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ShmaliukhVlad
 * @version 1.0.0
 * This is Literature class which is abstraction class for generating extended classes like Book and Magazine
 */
@Data
public abstract class Literature implements Serializable {

    protected String name;
    protected int pagesNumber;
    protected boolean isBorrowed;

    private Literature(){}

    /**
     * Base Constructor for creating Book and Magazine object
     */
    protected Literature(String name, int pagesNumber, boolean isBorrowed) { // TODO is private wright?
        this.name = name;
        setPagesNumber(pagesNumber);
        this.isBorrowed = isBorrowed;
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
}
