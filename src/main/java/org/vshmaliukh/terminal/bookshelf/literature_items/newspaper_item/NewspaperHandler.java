package org.vshmaliukh.terminal.bookshelf.literature_items.newspaper_item;

import org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandler;
import org.vshmaliukh.terminal.bookshelf.literature_items.ItemTitles;
import org.vshmaliukh.terminal.bookshelf.literature_items.newspaper_item.Newspaper;
import org.vshmaliukh.terminal.menus.menu_items.MenuItemForSorting;
import org.vshmaliukh.terminal.services.Utils;
import org.vshmaliukh.terminal.services.input_services.InputHandlerForLiterature;

import java.io.PrintWriter;
import java.util.*;

public class NewspaperHandler implements ItemHandler<Newspaper> {

    public static final Comparator<Newspaper> NEWSPAPER_COMPARATOR_BY_PAGES = Comparator.comparing(Newspaper::getPagesNumber);
    public static final Comparator<Newspaper> NEWSPAPER_COMPARATOR_BY_NAME = Comparator.comparing(Newspaper::getName, String.CASE_INSENSITIVE_ORDER);

    @Override
    public List<Newspaper> getSortedItems(int typeOfSorting, List<Newspaper> inputList) {
        for (MenuItemForSorting menuItem : getSortingMenuList()) {
            if (typeOfSorting == menuItem.getIndex()) {
                return new ArrayList<>(Utils.getSortedLiterature(inputList, menuItem.getComparator()));
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<MenuItemForSorting> getSortingMenuList() {
        return Collections.unmodifiableList(Arrays.asList(
                new MenuItemForSorting(1, "Sort by 'name' value", NEWSPAPER_COMPARATOR_BY_NAME),
                new MenuItemForSorting(2, "Sort by 'pages' value", NEWSPAPER_COMPARATOR_BY_PAGES)
        ));
    }

    @Override
    public Newspaper getItemByUserInput(InputHandlerForLiterature inputHandlerForLiterature, PrintWriter printWriter) {
        String name = inputHandlerForLiterature.getUserLiteratureName();
        int pages = inputHandlerForLiterature.getUserLiteraturePages();
        boolean isBorrowed = inputHandlerForLiterature.getUserLiteratureIsBorrowed();
        return new Newspaper(name, pages, isBorrowed);
    }

    @Override
    public Newspaper getRandomItem(Random random) {
        return new Newspaper(
                Utils.getRandomString(random.nextInt(20), random),
                random.nextInt(1000),
                false);
    }

    @Override
    public Map<String, String> convertItemToListOfString(Newspaper newspaper) {
        Map<String, String> map = new HashMap<>();
        map.put(ItemTitles.TYPE, newspaper.getClass().getSimpleName());
        map.put(ItemTitles.NAME, newspaper.getName());
        map.put(ItemTitles.PAGES, String.valueOf(newspaper.getPagesNumber()));
        map.put(ItemTitles.BORROWED, String.valueOf(newspaper.isBorrowed()));
        return new HashMap<>(map);
    }
}
