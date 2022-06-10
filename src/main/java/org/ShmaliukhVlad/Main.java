package org.ShmaliukhVlad;

import org.ShmaliukhVlad.Bookshelf.Shelf;
import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Book;
import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Magazine;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Book book1 = new Book("1",1,false,"NoAutho1",new Date(1));
        Book book2 = new Book("2",2,false,"NoAuthor2",new Date(2));
        Book book3 = new Book("3",3,true,"NoAuthor3",new Date(3));

        Magazine magazine1 = new Magazine("4",4,false);
        Magazine magazine2 = new Magazine("5",5,false);
        Magazine magazine3 = new Magazine("6",6,true);

        Shelf shelf = new Shelf();

        shelf.addLiteratureObject(book1);
        shelf.addLiteratureObject(book2);
        shelf.addLiteratureObject(book3);

        shelf.addLiteratureObject(magazine1);
        shelf.addLiteratureObject(magazine2);
        shelf.addLiteratureObject(magazine3);

        System.out.println(shelf);

        shelf.saveShelfToFile();





        //ArrayList<Literature> literatureInShelf = new ArrayList<>();
//
        //Book book1 = new Book("1",1,false,"NoAutho1",new Date(1));
        //Book book2 = new Book("2",2,false,"NoAuthor2",new Date(2));
        //Book book3 = new Book("3",3,false,"NoAuthor3",new Date(3));
//
        //literatureInShelf.add(book3);
        //literatureInShelf.add(book1);
        //literatureInShelf.add(book2);
//
//
        //System.out.println(literatureInShelf);
//
        //System.out.println("Сортування за назвою");
        //System.out.println("Sort by name");
        //literatureInShelf.sort((s1, s2) -> {
        //    if(s1.getName().length() != s2.getName().length())
        //        return s1.getName().length() - s2.getName().length();
        //    else
        //        return s1.compareTo(s2);
        //});
        //System.out.println(literatureInShelf);
    }
}