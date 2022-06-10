package org.ShmaliukhVlad;

import org.ShmaliukhVlad.Bookshelf_objects.Book;
import org.ShmaliukhVlad.Bookshelf_objects.Literature;
import org.ShmaliukhVlad.Bookshelf_objects.Magazine;

import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        ArrayList<Literature> literatureInShelf = new ArrayList<>();

        Book book1 = new Book("1",1,false,"NoAutho1",new Date(1));
        Book book2 = new Book("2",2,false,"NoAuthor2",new Date(2));
        Book book3 = new Book("3",3,false,"NoAuthor3",new Date(3));

        literatureInShelf.add(book3);
        literatureInShelf.add(book1);
        literatureInShelf.add(book2);


        System.out.println(literatureInShelf);

        System.out.println("Сортування за назвою");
        System.out.println("Sort by name");
        literatureInShelf.sort((s1, s2) -> {
            if(s1.getName().length() != s2.getName().length())
                return s1.getName().length() - s2.getName().length();
            else
                return s1.compareTo(s2);
        });
        System.out.println(literatureInShelf);
    }
}