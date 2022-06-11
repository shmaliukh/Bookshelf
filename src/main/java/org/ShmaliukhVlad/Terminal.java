package org.ShmaliukhVlad;

import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Book;
import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Magazine;
import org.ShmaliukhVlad.Bookshelf.Shelf;

import java.awt.desktop.AboutEvent;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

enum MenuLevel{

}

public class Terminal {
    private Scanner scanner;
    private int userChoice;
    private Shelf shelf;

    {
        scanner = new Scanner(System.in);
        shelf = new Shelf();
    }

    public Terminal(){
    }

    public void startWork(){
        boolean play = true;
        int userChoice;

        while (true){
            printMainMenu();
            userChoice = getUserChoice();
            if (userChoice == 1){
                System.out.println("Choose type of literature you want to add:");
                System.out.println("1 - Magazine");
                System.out.println("2 - Book");
                System.out.println("3 - Random Magazine");
                System.out.println("4 - Random Book");
                System.out.println("0 - Back");

                userChoice = getUserChoice();

                if(userChoice == 1){
                    Magazine userMagazineToAdd;
                    String name = "";
                    int pages = 0;
                    boolean isBorrowed = false;

                    System.out.println("Enter name:");
                    name = scanner.next();
                    System.out.println("Enter number of pages:");
                    pages = scanner.nextInt();
                    System.out.println("Enter 1 if Magazine is borrowed:");
                    System.out.println("Enter 2 if Magazine is NOT borrowed:");
                    if(getUserChoice() == 1){
                        isBorrowed = true;
                    }

                    userMagazineToAdd = new Magazine(name, pages, isBorrowed);
                    shelf.addLiteratureObject(userMagazineToAdd);
                    System.out.println("New user Magazine has added to shelf");

                }
                else if(userChoice == 2){
                    Book userBookToAdd;
                    String name = "";
                    int pages = 0;
                    boolean isBorrowed = false;
                    String author = "";
                    Date dateOfIssue = new Date(0);

                    System.out.println("Enter name:");
                    name = scanner.next();
                    System.out.println("Enter number of pages:");
                    pages = scanner.nextInt();
                    System.out.println("Enter 1 if Book is borrowed:");
                    System.out.println("Enter 2 if Book is NOT borrowed:");
                    if(getUserChoice() == 1){
                        isBorrowed = true;
                    }
                    System.out.println("Enter author:");
                    author = scanner.nextLine();
                    //Todo
                    System.out.println("Enter date of issue:");
                    dateOfIssue = new Date(scanner.nextInt());

                    userBookToAdd = new Book(name, pages, isBorrowed, author, dateOfIssue);
                    shelf.addLiteratureObject(userBookToAdd);
                    System.out.println("New user Book has added to shelf");
                }
                else if(userChoice == 3){
                    Random randomNumber = new Random();

                    shelf.addLiteratureObject(new Magazine(
                            getRandomString(randomNumber.nextInt(10)),
                            randomNumber.nextInt(1000),
                            false));
                    System.out.println("New random Magazine has added to shelf");
                }
                else if(userChoice == 4){
                    Random randomNumber = new Random();

                    shelf.addLiteratureObject(new Book(
                            getRandomString(randomNumber.nextInt(10)),
                            randomNumber.nextInt(1000),
                            false,
                            getRandomString(randomNumber.nextInt(10)),
                            new Date(randomNumber.nextInt(10000))));
                    System.out.println("New random Book has added to shelf");
                }
                else if(getUserChoice() == 0){

                }
                System.out.println("Shelf state:");
                System.out.println(shelf);
            }





        }
    }
    public String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }



    private int getUserChoice(){
        return scanner.nextInt();
    }

    private void printMainMenu(){
        System.out.println(
                "Введіть цифру пункту меню для виконання дії на полиці:" +
                "1 - Додавання\n" +
                "2 - Видалення\n" +
                "3 - Запозичення з полиці\n" +
                "4 - Повернення на полицю\n" +
                "5 - Виведення списку наявних книг з можливістю сортування за вказаним параметром\n" +
                "6 - Виведення списку наявних журналів з можливістю сортування за вказаним параметром\n" +
                "7 - Вихід");
    }
    private void printMenuForBooksSorting(){
        System.out.println(
                "1 - Сортування по автору\n" +
                "2 - Сортування по автору\n" +
                "3 - Сортування по назві\n" +
                "4 - Сортування по даті видання\n" +
                "5 - Сортування по кількості сторінок\n" +
                "0 - Повернутися до попередніх пунктів");
    }
    private void printMenuForMagazinesSorting(){
        System.out.println(
                "1 - Сортування по назві\n" +
                "2 - Сортування по кількості сторінок\n" +
                "0 - Повернутися до попередніх пунктів");
    }
}
