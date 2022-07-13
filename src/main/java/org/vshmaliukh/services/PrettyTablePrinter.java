package org.vshmaliukh.services;

import org.vshmaliukh.bookshelf.Shelf;
import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.bookshelf.bookshelfObjects.Literature;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.vshmaliukh.constants.ConstantsForTerminal.*;

public class PrettyTablePrinter {

    private PrintWriter printWriter;

    String format;

    int spaceForIndex;
    int spaceForType;
    int spaceForName;
    int spaceForPages;
    int spaceForIsBorrowed;
    int spaceForAuthor;
    int spaceForDate;

    String titleIndex = "№";
    String titleType = "Type";
    String titleName = "Name";
    String titlePages = "Pages";
    String titleBorrow = "Borrow";
    String titleAuthor = "Author";
    String titleDate = "Date";

    String bookType = "Book";
    String magazineType = "Magazine";

    public PrettyTablePrinter(PrintWriter printWriter){
        this.printWriter = printWriter;
        setDefaultSpaces();
    }

    public void printTable(List<Literature> literatureList){
        setFormat(literatureList);
        printTitle();
        for (int i = 1 ; literatureList.size() +1 > i; i++) {
            printLiteratureObject(i, literatureList.get(i-1));
        }
    }

    private void printTitle() {
        printWriter.println(String.format(format,
                titleIndex, titleType, titleName, titlePages, titleBorrow, titleAuthor, titleDate));
    }

    public void printLiteratureObject(int i, Literature literature){
        printWriter.println(getPrettyStringOfLiterature(i, literature));
    }

    private void setDefaultSpaces() {
        spaceForIndex = titleIndex.length();
        spaceForType = titleType.length();
        spaceForName = titleName.length();
        spaceForPages = titlePages.length();
        spaceForIsBorrowed = titleBorrow.length();
        spaceForAuthor = titleAuthor.length();
        spaceForDate = titleDate.length();
    }

    public String getPrettyStringOfLiterature (int i ,Literature literature){
        if (literature instanceof Book){
            return getPrettyString(i, (Book) literature);
        }
        else if (literature instanceof Magazine){
            return getPrettyString(i, (Magazine) literature);
        }
        return "";
    }

    public String getPrettyString(int i, Magazine magazine){
        return String.format(format, i, magazineType, magazine.getName(), magazine.getPagesNumber(), magazine.isBorrowed(), "", "");
    }

    public String getPrettyString(int i, Book book){
        return String.format(format, i, bookType, book.getName(), book.getPagesNumber(), book.isBorrowed(), book.getAuthor(), DATE_FORMAT.format(book.getIssuanceDate()));
    }

    private void setFormat(List<Literature> literatureList){
        getSpacesValue(literatureList);
        format = "| %-"
                + spaceForIndex+"s | %-"
                + spaceForType+"s | %-"
                + spaceForName+"s | %-"
                + spaceForPages +"s | %-"
                + spaceForIsBorrowed+"s | %-"
                + spaceForAuthor +"s | %-"
                + spaceForDate +"s |";
    }

    private void getSpacesValue(List<Literature> literatureList) {
        int gotNameLength = spaceForName;
        int gotPagesLength = spaceForPages;
        int gotAuthorLength = spaceForAuthor;
        int gotForDateLength = spaceForPages;

        spaceForIndex = String.valueOf(literatureList.size()).length();

        for (Literature literature : literatureList) {
            if (literature instanceof Magazine) {
                spaceForType = magazineType.length();
                gotNameLength = literature.getName().length();
                gotPagesLength = String.valueOf(literature.getPagesNumber()).length();
                if (gotNameLength > spaceForName) {
                    spaceForName = gotNameLength;
                }
                if (gotPagesLength > spaceForPages) {
                    spaceForPages = gotPagesLength;
                }
            }
            if (literature instanceof Book) {
                spaceForType = bookType.length();
                Book book = (Book) literature;
                gotNameLength = book.getName().length();
                gotPagesLength = String.valueOf(book.getPagesNumber()).length();
                gotAuthorLength = book.getAuthor().length();
                gotForDateLength = DATE_FORMAT.format(book.getIssuanceDate()).length();
                if (gotNameLength > spaceForName) {
                    spaceForName = gotNameLength;
                }
                if (gotPagesLength > spaceForPages) {
                    spaceForPages = gotPagesLength;
                }
                if (gotAuthorLength > spaceForAuthor) {
                    spaceForAuthor = gotAuthorLength;
                }
                if (gotForDateLength > spaceForPages) {
                    spaceForDate = gotForDateLength;
                }
            }
        }
    }

    public void printSortedBooks(int typeOfSorting, Shelf shelf){
        List<Literature> bookList = new ArrayList<>();
        switch (typeOfSorting){
            case SORT_BOOKS_BY_NAME:
                bookList.addAll(shelf.getSortedBooksByName());
                break;
            case SORT_BOOKS_BY_PAGES_NUMBER:
                bookList.addAll(shelf.getSortedBooksByPages());
                break;
            case SORT_BOOKS_BY_AUTHOR:
                bookList.addAll(shelf.getSortedBooksByAuthor());
                break;
            case SORT_BOOKS_BY_DATE_OF_ISSUE:
                bookList.addAll(shelf.getSortedBooksByDate());
                break;
            default:
                break;
        }
        printTable(bookList);
    }


    public void printSortedMagazines(int typeOfSorting, Shelf shelf) {
        List<Literature> magazineList = new ArrayList<>();
        switch (typeOfSorting){
            case SORT_MAGAZINES_BY_NAME:
                magazineList.addAll(shelf.getSortedMagazinesByName());
                break;
            case SORT_MAGAZINES_BY_PAGES_NUMBER:
                magazineList.addAll(shelf.getSortedMagazinesByPages());
                break;
            default:
                break;
        }
        printTable(magazineList);
    }

    //public void printPrettyTable(){
////
//
    //    final String format = "| %-"
    //            + spaceForName+"s | %-"
    //            + spaceForPages +"s | %-"
    //            + spaceForIsBorrowed+"s | %-"
    //            + spaceForAuthor +"s | %-"
    //            + spaceForDate +"s |\n";
//
    //    printWriter.printf(format, "Name", "Pages", "Borrowed", "Author", "Date");
    //    for (Book book : shelf.getBooks()) {
    //        printWriter.printf(format,
    //                book.getName(),
    //                book.getPagesNumber(),
    //                book.isBorrowed(),
    //                book.getAuthor(),
    //                book.getIssuanceDate().toString());
    //    }
    //    for (Magazine magazine : shelf.getMagazines()) {
    //        printWriter.printf(format,
    //                magazine.getName(),
    //                magazine.getPagesNumber(),
    //                magazine.isBorrowed(),
    //                "",
    //                "");
    //    }
    //}
}
