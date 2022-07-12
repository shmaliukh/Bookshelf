package org.vshmaliukh.services;

import java.io.PrintWriter;

public class PrettyTablePrinter {

    private PrintWriter printWriter;

    String format;

    int spaceForName;
    int spaceForPages;
    int spaceForIsBorrowed;
    int spaceForAuthor;
    int spaceForDate;

    public PrettyTablePrinter(PrintWriter printWriter){
        this.printWriter = printWriter;
        setDefaultSpaces();
    }

    private void setDefaultSpaces() {
        spaceForName = 4;
        spaceForPages = 5;
        spaceForIsBorrowed =8;
        spaceForAuthor = 6;
        spaceForDate = 4;
    }

    private void setFormat(){
        format = "| %-"
                + spaceForName+"s | %-"
                + spaceForPages +"s | %-"
                + spaceForIsBorrowed+"s | %-"
                + spaceForAuthor +"s | %-"
                + spaceForDate +"s |\n";
    }

    public void printLiteratureList(){
        setFormat();
    }

}
