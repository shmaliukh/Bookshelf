package org.vshmaliukh.handlers.ItemHandlers;

import org.vshmaliukh.bookshelf.bookshelfObjects.Gazette;
import org.vshmaliukh.constants.enums_for_menu.MenuForSortingGazettes;
import org.vshmaliukh.constants.enums_for_menu.MenuItem;
import org.vshmaliukh.handlers.ItemHandler;
import org.vshmaliukh.services.ItemSorterHandler;
import org.vshmaliukh.services.input_services.InputHandlerForLiterature;
import org.vshmaliukh.services.print_table_service.ConvertorToString;
import org.vshmaliukh.services.print_table_service.convertors.ConvertorToStringForGazette;

import java.io.PrintWriter;
import java.util.*;

import static org.vshmaliukh.services.Utils.getRandomString;

public class GazetteHandler implements ItemHandler<Gazette> {

    public static final Comparator<Gazette> GAZETTE_COMPARATOR_BY_PAGES = Comparator.comparing(Gazette::getPagesNumber);
    public static final Comparator<Gazette> GAZETTE_COMPARATOR_BY_NAME = Comparator.comparing(Gazette::getName, String.CASE_INSENSITIVE_ORDER);

    @Override
    public ConvertorToString getConvertorToString() {
        return new ConvertorToStringForGazette();
    }

    @Override
    public List<Gazette> getSortedItems(int typeOfSorting, List<Gazette> inputList) {
        ItemSorterHandler<Gazette> gazetteItemSorterHandler = new ItemSorterHandler<>(inputList);
        for (MenuItem menuItem : MenuForSortingGazettes.menuForSortingGazettesItems) {
            if (typeOfSorting == menuItem.getIndex()) {
                return new ArrayList<>(gazetteItemSorterHandler.getSortedLiterature(menuItem.getComparator()));
            }// TODO is it normal to save comparator in menu item
        }
        return Collections.emptyList();
    }

    @Override
    public void printSortingMenu(PrintWriter printWriter) {
        MenuForSortingGazettes.printMenu(printWriter);
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
    public Gazette getItemByUserInput(InputHandlerForLiterature inputHandlerForLiterature, PrintWriter printWriter) {
        String name = inputHandlerForLiterature.getUserLiteratureName();
        int pages = inputHandlerForLiterature.getUserLiteraturePages();
        boolean isBorrowed = inputHandlerForLiterature.getUserLiteratureIsBorrowed();
        return new Gazette(name, pages, isBorrowed);
    }

    @Override
    public Gazette getRandomItem(Random random) {
        return new Gazette(
                getRandomString(random.nextInt(20), random),
                random.nextInt(1000),
                false);
    }
}
