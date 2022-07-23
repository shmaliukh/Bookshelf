package org.vshmaliukh.handlers.ItemHandlers;

import org.vshmaliukh.bookshelf.bookshelfObjects.Gazette;
import org.vshmaliukh.constants.enums_for_menu.MenuForSortingGazettes;
import org.vshmaliukh.handlers.ItemHandler;
import org.vshmaliukh.services.ItemSorterHandler;
import org.vshmaliukh.services.input_services.InputHandlerForLiterature;
import org.vshmaliukh.services.print_table_service.ConvertorToString;
import org.vshmaliukh.services.print_table_service.convertors.ConvertorToStringForGazette;

import java.io.PrintWriter;
import java.util.*;

import static org.vshmaliukh.Utils.getRandomString;
import static org.vshmaliukh.services.ConstantsItemSorterHandler.*;

public class GazetteHandler implements ItemHandler<Gazette> {

    private List<String> titleListForGazettes = new ArrayList<>(Arrays.asList("TYPE", "NAME", "PAGES", "IS BORROWED"));

    @Override
    public ConvertorToString getConvertorToString() {
        return new ConvertorToStringForGazette();
    }

    @Override
    public List<Gazette> getSortedItems(int typeOfSorting, List<Gazette> inputList) {
        MenuForSortingGazettes byIndex = MenuForSortingGazettes.getByIndex(typeOfSorting);
        switch (byIndex) {
            case SORT_GAZETTES_BY_NAME:
                return new ArrayList<>(new ItemSorterHandler<>(inputList)
                        .getSortedLiterature(GAZETTE_COMPARATOR_BY_NAME));
            case SORT_GAZETTES_BY_PAGES:
                return new ArrayList<>(new ItemSorterHandler<>(inputList)
                        .getSortedLiterature(GAZETTE_COMPARATOR_BY_PAGES));
            default:
                return Collections.emptyList();
        }
    }

    @Override
    public void printSortingMenu(PrintWriter printWriter) {
        MenuForSortingGazettes.printMenu(printWriter);
    }

    @Override
    public List<String> getTitlesList() {
        return new ArrayList<>(titleListForGazettes);
    }

    @Override
    public List<Gazette> clarificationForSortingItems(List<Gazette> items, int userChoice, PrintWriter printWriter) {
        if (items.isEmpty()) {
            printWriter.println("No available gazettes IN shelf for sorting");
        } else {
            printSortingMenu(printWriter);
            return getSortedItems(userChoice, items);
        }
        return items;
    }

    @Override
    public Gazette getByUserInput(InputHandlerForLiterature inputHandlerForLiterature, PrintWriter printWriter) {
        Gazette userGazette;
        String name;
        int pages;
        boolean isBorrowed;

        name = inputHandlerForLiterature.getUserLiteratureName();
        pages = inputHandlerForLiterature.getUserLiteraturePages();
        isBorrowed = inputHandlerForLiterature.getUserLiteratureIsBorrowed();

        userGazette = new Gazette(name, pages, isBorrowed);
        return userGazette;
    }

    @Override
    public Gazette getRandomItem(Random random) {
        return new Gazette(
                getRandomString(random.nextInt(20), random),
                random.nextInt(1000),
                false);
    }
}
