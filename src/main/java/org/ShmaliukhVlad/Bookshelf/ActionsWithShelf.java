package org.ShmaliukhVlad.Bookshelf;

import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Literature;

public interface ActionsWithShelf {
    default void addLiteratureObject(Literature literature){
    }
    default void deleteLiteratureObjectByIndex(int index){
    }
    default void saveShelfToFile() {
    }
    default void printSortedMagazinesByName(){
    }
    default void printSortedMagazinesByPages(){
    }
    default void printSortedBooksByName(){
    }
    default void printSortedBooksByPages(){
    }
    default void printSortedBooksByAuthor(){
    }
    default void printSortedBooksByDate(){
    }





}
