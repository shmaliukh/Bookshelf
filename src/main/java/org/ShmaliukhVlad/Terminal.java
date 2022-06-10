package org.ShmaliukhVlad;

import java.awt.desktop.AboutEvent;
import java.util.Scanner;

public class Terminal {
    private Scanner scanner;
    private int userChoice;

    {
        scanner = new Scanner(System.in);
    }

    public Terminal(){
    }

    public void startWork(){
        boolean play = true;

        while (true){
            printMainMenu();
            getUserChoice();



        }
    }



    private void getUserChoice(){

        userChoice = scanner.nextShort();

        if (userChoice == 1) {

        } else if (userChoice == 2) {

        } else if (userChoice == 3) {

        } else if (userChoice == 4) {

        } else if (userChoice == 5) {

        } else if (userChoice == 6) {

        } else if (userChoice == 7) {

        }
        else {
            System.out.println("Було введенно значення не вірної команди");
        }
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
