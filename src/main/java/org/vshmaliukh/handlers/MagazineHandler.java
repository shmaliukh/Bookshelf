package org.vshmaliukh.handlers;

import org.vshmaliukh.Utils;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;
import org.vshmaliukh.constants.enums_for_menu.MenuForSortingMagazines;
import org.vshmaliukh.services.LiteratureSorterHandler;
import org.vshmaliukh.services.input_services.InputHandlerForLiterature;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.vshmaliukh.constants.ConstantsLiteratureSorterHandler.MAGAZINE_COMPARATOR_BY_NAME;
import static org.vshmaliukh.constants.ConstantsLiteratureSorterHandler.MAGAZINE_COMPARATOR_BY_PAGES;

public class MagazineHandler implements ItemHandler<Magazine>, HasCrossword {

    private List<String> titleListForMagazine = new ArrayList<>(Arrays.asList("TYPE", "NAME", "PAGES", "IS BORROWED"));


    @Override
    public List<Magazine> getSortedItems(int typeOfSorting, List<Magazine> inputList) {
        List<Magazine> magazineList = new ArrayList<>();
        MenuForSortingMagazines byIndex = MenuForSortingMagazines.getByIndex(typeOfSorting);
        switch (byIndex) {
            case SORT_MAGAZINES_BY_NAME:
                magazineList.addAll( new LiteratureSorterHandler<>(inputList)
                        .getSortedLiterature(MAGAZINE_COMPARATOR_BY_NAME));
                break;
            case SORT_MAGAZINES_BY_PAGES:
                magazineList.addAll( new LiteratureSorterHandler<>(inputList)
                        .getSortedLiterature(MAGAZINE_COMPARATOR_BY_PAGES));
                break;
            default:
                break;
        }
        return magazineList;

    }

    @Override
    public void printSortingMenu(PrintWriter printWriter) {
        MenuForSortingMagazines.printMenu(printWriter);
    }
    public List<String> getTitlesList(){
        return new ArrayList<>(titleListForMagazine);
    }

    public List<Magazine> clarificationForSortingItems(List<Magazine> magazines, int userChoice, PrintWriter printWriter) {
        if (magazines.isEmpty()) {
            printWriter.println("No available magazines IN shelf for sorting");
        } else {
            printSortingMenu(printWriter);
            return getSortedItems(userChoice, magazines);
        }
        return magazines;
    }

    public Magazine getByUserInput(InputHandlerForLiterature inputHandlerForLiterature, PrintWriter printWriter) {
        Magazine userMagazine;
        String name;
        int pages;
        boolean isBorrowed;

        name = inputHandlerForLiterature.getUserLiteratureName();
        pages = inputHandlerForLiterature.getUserLiteraturePages();
        isBorrowed = inputHandlerForLiterature.getUserLiteratureIsBorrowed();

        userMagazine = new Magazine(name, pages, isBorrowed);
        return userMagazine;
    }

    public Magazine getRandomItem(Random random) {
        Magazine randomMagazine = new Magazine(
                Utils.getRandomString(random.nextInt(20), random),
                random.nextInt(1000),
                false);
        return randomMagazine;
    }

    public String getCrossword(){

    };
}
