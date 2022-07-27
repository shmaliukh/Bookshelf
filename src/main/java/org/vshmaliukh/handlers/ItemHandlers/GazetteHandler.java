package org.vshmaliukh.handlers.ItemHandlers;

import org.vshmaliukh.bookshelf.bookshelfObjects.Gazette;
import org.vshmaliukh.handlers.ItemHandler;
import org.vshmaliukh.menus.MenuItemForSorting;
import org.vshmaliukh.services.ItemSorterService;
import org.vshmaliukh.services.input_services.InputHandlerForLiterature;

import java.io.PrintWriter;
import java.util.*;

import static org.vshmaliukh.services.Utils.getRandomString;
import static org.vshmaliukh.handlers.ItemTitles.*;
import static org.vshmaliukh.handlers.ItemTitles.BORROWED;

public class GazetteHandler implements ItemHandler<Gazette> {

    public static final Comparator<Gazette> GAZETTE_COMPARATOR_BY_PAGES = Comparator.comparing(Gazette::getPagesNumber);
    public static final Comparator<Gazette> GAZETTE_COMPARATOR_BY_NAME = Comparator.comparing(Gazette::getName, String.CASE_INSENSITIVE_ORDER);

    @Override
    public List<Gazette> getSortedItems(int typeOfSorting, List<Gazette> inputList) {
        ItemSorterService<Gazette> gazetteItemSorterService = new ItemSorterService<>(inputList);
        for (MenuItemForSorting menuItem : getSortingMenuList()) {
            if (typeOfSorting == menuItem.getIndex()) {
                return new ArrayList<>(gazetteItemSorterService.getSortedLiterature(menuItem.getComparator()));
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<MenuItemForSorting> getSortingMenuList() {
        return Collections.unmodifiableList(Arrays.asList(
                new MenuItemForSorting(1, "Sort by 'name' value", GAZETTE_COMPARATOR_BY_NAME),
                new MenuItemForSorting(2, "Sort by 'pages' value", GAZETTE_COMPARATOR_BY_PAGES)
        ));
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

    @Override
    public Map<String, String> convertItemToListOfString(Gazette gazette) {
        Map<String, String> map = new HashMap<>();
        map.put(TYPE, gazette.getClass().getSimpleName());
        map.put(NAME, gazette.getName());
        map.put(PAGES, String.valueOf(gazette.getPagesNumber()));
        map.put(BORROWED, String.valueOf(gazette.isBorrowed()));
        return new HashMap<>(map);
    }
}
