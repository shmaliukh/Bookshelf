package org.ShmaliukhVlad;

import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Book;
import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Literature;
import org.ShmaliukhVlad.Bookshelf.Bookshelf_objects.Magazine;
import org.ShmaliukhVlad.Bookshelf.Shelf;

import java.util.*;

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
        ArrayList<Literature> sortedShelf;

        while (play){
            printMainMenu();
            userChoice = getUserChoice();

            if (userChoice == 9){
                System.out.println(shelf);
            }
            else if (userChoice == 5){
                printMenuForBooksSorting();
                userChoice =getUserChoice();

                if(userChoice == 1){
                    System.out.println("Sorted by name");
                    shelf.printSortedBooksByName();
                }
                else if(userChoice == 2){
                    System.out.println("Sorted by author");
                    shelf.printSortedBooksByAuthor();
                }
                else if(userChoice == 3){
                    System.out.println("Sorted by pages");
                    shelf.printSortedBooksByPages();
                }
                else if(userChoice == 4){
                    System.out.println("Sorted by date of issue");
                    shelf.printSortedBooksByDate();
                }

            }
            else if (userChoice == 6){
                printMenuForMagazinesSorting();
                userChoice =getUserChoice();

                if(userChoice == 1){
                    System.out.println("Sorted by name");
                    shelf.printSortedMagazinesByName();
                }
                if(userChoice == 2){
                    System.out.println("Sorted by pages");
                    shelf.printSortedMagazinesByPages();
                }

            }
            else if (userChoice == 2){
                System.out.println("Enter index of Literature object to delete one:");
                int index = getUserChoice();
                shelf.deleteLiteratureObjectByIndex(index);
            }
            else if (userChoice == 1){
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
                    author = scanner.next();
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
                else if(userChoice == 0){
                    //play = false;
                }
                System.out.println("Shelf state:");
                System.out.println(shelf);
            }
            else if (userChoice == 7){
                shelf.saveShelfToFile();
                System.out.println("Shelf state:");
            }
            else if(userChoice == 0){
                play = false;
            }




        }
    }
    public String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_ ";
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
                "Enter number of  command you wand to execute:\n" +
                "1 - Add new Literature object to Shelf\n" +
                "2 - Delete  Literature object by index from Shelf\n" +
                "3 - Borrow  Literature object by index from Shelf\n" +
                "4 - Arrive  Literature object by index back to Shelf\n" +
                "5 - Print list of available Books sorted by parameter...\n" +
                "6 - Print list of available Magazines sorted by parameter...\n" +
                "7 - Save file\n" +
                "9 - Print current state of Shelf\n" +
                "0 - Вихід");
    }
    private void printMenuForBooksSorting(){
        System.out.println(
                "1 - Sort by 'name' value\n" +
                "2 - Sort by 'author' value\n" +
                "3 - Sort by 'page number' value\n" +
                "4 - Sort by 'date' value\n" +
                "0 - Back");
    }
    private void printMenuForMagazinesSorting(){
        System.out.println(
                "1 - Sort by 'name' value\n" +
                "2 - Sort by 'page' value\n" +
                "0 - Back");
    }
}
