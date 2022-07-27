package org.vshmaliukh.handlers.ItemHandlers;

import org.vshmaliukh.menus.MenuItemForSorting;
import org.vshmaliukh.services.Utils;
import org.vshmaliukh.bookshelf.bookshelfObjects.Magazine;
import org.vshmaliukh.handlers.ItemHandler;
import org.vshmaliukh.services.ItemSorterService;
import org.vshmaliukh.services.input_services.InputHandlerForLiterature;

import java.io.PrintWriter;
import java.util.*;

import static org.vshmaliukh.handlers.ItemTitles.*;
import static org.vshmaliukh.handlers.ItemTitles.BORROWED;

public class MagazineHandler implements ItemHandler<Magazine> {

    public static final Comparator<Magazine> MAGAZINE_COMPARATOR_BY_PAGES = Comparator.comparing(Magazine::getPagesNumber);
    public static final Comparator<Magazine> MAGAZINE_COMPARATOR_BY_NAME = Comparator.comparing(Magazine::getName, String.CASE_INSENSITIVE_ORDER);

    @Override
    public List<Magazine> getSortedItems(int typeOfSorting, List<Magazine> inputList) {
        ItemSorterService<Magazine> magazineItemSorterService = new ItemSorterService<>(inputList);
        for (MenuItemForSorting menuItem : getSortingMenuList()) {
            if (typeOfSorting == menuItem.getIndex()) {
                return new ArrayList<>(magazineItemSorterService.getSortedLiterature(menuItem.getComparator()));
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<MenuItemForSorting> getSortingMenuList() {
        return Collections.unmodifiableList(Arrays.asList(
                new MenuItemForSorting(1, "Sort by 'name' value", MAGAZINE_COMPARATOR_BY_NAME),
                new MenuItemForSorting(2, "Sort by 'pages' value", MAGAZINE_COMPARATOR_BY_PAGES)
        ));
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
        return new Magazine(
                Utils.getRandomString(random.nextInt(20), random),
                random.nextInt(1000),
                false);
    }

    @Override
    public Map<String, String> convertItemToListOfString(Magazine magazine) {
        Map<String, String> map = new HashMap<>();
        map.put(TYPE, magazine.getClass().getSimpleName());
        map.put(NAME, magazine.getName());
        map.put(PAGES, String.valueOf(magazine.getPagesNumber()));
        map.put(BORROWED, String.valueOf(magazine.isBorrowed()));
        return new HashMap<>(map);
    }
}
