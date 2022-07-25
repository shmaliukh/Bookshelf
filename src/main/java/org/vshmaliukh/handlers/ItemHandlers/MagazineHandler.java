package org.vshmaliukh.handlers.ItemHandlers;

import org.vshmaliukh.services.Utils;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;
import org.vshmaliukh.constants.enums_for_menu.MenuForSortingMagazines;
import org.vshmaliukh.handlers.ItemHandler;
import org.vshmaliukh.services.ItemSorterHandler;
import org.vshmaliukh.services.input_services.InputHandlerForLiterature;
import org.vshmaliukh.services.print_table_service.ConvertorToString;
import org.vshmaliukh.services.print_table_service.convertors.ConvertorToStringForMagazine;

import java.io.PrintWriter;
import java.util.*;

public class MagazineHandler implements ItemHandler<Magazine> {

    private static final List<String> titleListForMagazine = new ArrayList<>(Arrays.asList("TYPE", "NAME", "PAGES", "IS BORROWED"));
    public static final Comparator<Magazine> MAGAZINE_COMPARATOR_BY_PAGES = Comparator.comparing(Magazine::getPagesNumber);
    public static final Comparator<Magazine> MAGAZINE_COMPARATOR_BY_NAME = Comparator.comparing(o -> o.getName().toLowerCase());

    @Override
    public ConvertorToString getConvertorToString() {
        return new ConvertorToStringForMagazine();
    }

    @Override
    public List<Magazine> getSortedItems(int typeOfSorting, List<Magazine> inputList) {
        MenuForSortingMagazines byIndex = MenuForSortingMagazines.getByIndex(typeOfSorting);
        switch (byIndex) {
            case SORT_MAGAZINES_BY_NAME:
                return new ArrayList<>(new ItemSorterHandler<>(inputList)
                        .getSortedLiterature(MAGAZINE_COMPARATOR_BY_NAME));
            case SORT_MAGAZINES_BY_PAGES:
                return new ArrayList<>(new ItemSorterHandler<>(inputList)
                        .getSortedLiterature(MAGAZINE_COMPARATOR_BY_PAGES));
            default:
                return Collections.emptyList();
        }
    }

    @Override
    public void printSortingMenu(PrintWriter printWriter) {
        MenuForSortingMagazines.printMenu(printWriter);
    }

    @Override
    public List<String> getTitlesList() {
        return new ArrayList<>(titleListForMagazine);
    }

    @Override
    public List<Magazine> clarificationForSortingItems(List<Magazine> magazines, int userChoice, PrintWriter printWriter) {
        if (magazines.isEmpty()) {
            printWriter.println("No available magazines IN shelf for sorting");
        } else {
            printSortingMenu(printWriter);
            return getSortedItems(userChoice, magazines);
        }
        return magazines;
    }

    @Override
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

    @Override
    public Magazine getRandomItem(Random random) {
        Magazine randomMagazine = new Magazine(
                Utils.getRandomString(random.nextInt(20), random),
                random.nextInt(1000),
                false);
        return randomMagazine;
    }
}
