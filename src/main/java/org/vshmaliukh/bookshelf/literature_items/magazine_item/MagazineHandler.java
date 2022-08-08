package org.vshmaliukh.bookshelf.literature_items.magazine_item;

import org.vshmaliukh.bookshelf.literature_items.ItemHandler;
import org.vshmaliukh.bookshelf.literature_items.ItemTitles;
import org.vshmaliukh.console_terminal.menus.menu_items.MenuItemForSorting;
import org.vshmaliukh.console_terminal.services.Utils;
import org.vshmaliukh.console_terminal.services.input_services.InputHandlerForLiterature;

import java.io.PrintWriter;
import java.util.*;

import static org.vshmaliukh.console_terminal.services.input_services.ConstantsForUserInputHandler.*;

public class MagazineHandler implements ItemHandler<Magazine> {

    public static final Comparator<Magazine> MAGAZINE_COMPARATOR_BY_PAGES = Comparator.comparing(Magazine::getPagesNumber);
    public static final Comparator<Magazine> MAGAZINE_COMPARATOR_BY_NAME = Comparator.comparing(Magazine::getName, String.CASE_INSENSITIVE_ORDER);

    @Override
    public List<Magazine> getSortedItems(int typeOfSorting, List<Magazine> inputList) {
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
                new MenuItemForSorting(1, "Sort by 'name' value", MAGAZINE_COMPARATOR_BY_NAME),
                new MenuItemForSorting(2, "Sort by 'pages' value", MAGAZINE_COMPARATOR_BY_PAGES)
        ));
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
        map.put(ItemTitles.TYPE, magazine.getClass().getSimpleName());
        map.put(ItemTitles.NAME, magazine.getName());
        map.put(ItemTitles.PAGES, String.valueOf(magazine.getPagesNumber()));
        map.put(ItemTitles.BORROWED, String.valueOf(magazine.isBorrowed()));
        return new HashMap<>(map);
    }

    @Override
    public String generateHTMLFormBodyToCreateItem() {
        return "" +
                Utils.generateHTMLFormItem(ItemTitles.NAME) +
                Utils.generateHTMLFormItem(ItemTitles.PAGES) +
                Utils.generateHTMLFormItem(ItemTitles.BORROWED) +
                "   <input type = \"submit\" value = \"Submit\" />\n" +
                "</form>";
    }

    @Override
    public String generateHTMLFormBodyToCreateItem(Random random) {
        return "" +
                Utils.generateHTMLFormItem(ItemTitles.NAME, Utils.getRandomString(random.nextInt(20), random)) +
                Utils.generateHTMLFormItem(ItemTitles.PAGES, String.valueOf(random.nextInt(1000))) +
                Utils.generateHTMLFormItem(ItemTitles.BORROWED, "n") +
                "   <input type = \"submit\" value = \"Submit\" />\n" +
                "</form>";
    }

    @Override
    public boolean isValidHTMLFormData(Map<String, String> mapFieldValue) {
        String nameParameter = mapFieldValue.get(ItemTitles.NAME);
        String pagesParameter = mapFieldValue.get(ItemTitles.PAGES);
        String borrowedParameter = mapFieldValue.get(ItemTitles.BORROWED);

        if (isValidBookInput(nameParameter, pagesParameter, borrowedParameter)) {
            return true;
        }
        return false;
    }

    public boolean isValidBookInput(String name, String pages, String borrowed) {
        return InputHandlerForLiterature.isValidInputString(name, PATTERN_FOR_NAME) &&
                InputHandlerForLiterature.isValidInputInteger(pages, PATTERN_FOR_PAGES) &&
                InputHandlerForLiterature.isValidInputString(borrowed, PATTERN_FOR_IS_BORROWED);
    }

    @Override
    public Magazine generateItemByHTMLFormData(Map<String, String> mapFieldValue, PrintWriter printWriter) {
        String nameParameter = mapFieldValue.get(ItemTitles.NAME);
        String pagesParameter = mapFieldValue.get(ItemTitles.PAGES);
        String borrowedParameter = mapFieldValue.get(ItemTitles.BORROWED);

        String join = String.join(System.lineSeparator(), nameParameter, pagesParameter, borrowedParameter);
        InputHandlerForLiterature inputHandlerForLiterature = new InputHandlerForLiterature(new Scanner(join), printWriter);

        String name = inputHandlerForLiterature.getUserLiteratureName();
        int pages = inputHandlerForLiterature.getUserLiteraturePages();
        boolean isBorrowed = inputHandlerForLiterature.getUserLiteratureIsBorrowed();

        return new Magazine(name, pages, isBorrowed);
    }
}
