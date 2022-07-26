package org.vshmaliukh.handlers.ItemHandlers;

import org.vshmaliukh.bookshelf.bookshelfObjects.Gazette;
import org.vshmaliukh.constants.enums_for_menu.MenuForSortingGazettes;
import org.vshmaliukh.constants.enums_for_menu.MenuItem;
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
    public static final Comparator<Magazine> MAGAZINE_COMPARATOR_BY_NAME = Comparator.comparing(Magazine::getName, String.CASE_INSENSITIVE_ORDER);

    @Override
    public ConvertorToString getConvertorToString() {
        return new ConvertorToStringForMagazine();
    }

    @Override
    public List<Magazine> getSortedItems(int typeOfSorting, List<Magazine> inputList) {
        ItemSorterHandler<Magazine> magazineItemSorterHandler = new ItemSorterHandler<>(inputList);
        for (MenuItem menuItem : MenuForSortingMagazines.menuForSortingMagazinesItems) {
            if (typeOfSorting == menuItem.getIndex()) {
                return new ArrayList<>(magazineItemSorterHandler.getSortedLiterature(menuItem.getComparator()));
            }// TODO is it normal to save comparator in menu item
        }
        return Collections.emptyList();
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
    public Magazine getItemByUserInput(InputHandlerForLiterature inputHandlerForLiterature, PrintWriter printWriter) {
        String name = inputHandlerForLiterature.getUserLiteratureName();
        int pages = inputHandlerForLiterature.getUserLiteraturePages();
        boolean isBorrowed = inputHandlerForLiterature.getUserLiteratureIsBorrowed();
        return new Magazine(name, pages, isBorrowed);
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
