package org.ShmaliukhVlad.Bookshelf;

import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Literature;

public interface ActionsWithShelf {
    default void addLiteratureObject(Literature literature){
    }
    default  void removeLiteratureObject(Literature literature){
    }
    default void borrowLiteratureObject(Literature literature){
    }
    default void arriveLiteratureObject(Literature literature){
    }
    default void saveShelfToFile() {
    }
    default void getListOfBooks() {
    }


}
