package org.vshmaliukh.terminal.bookshelf.literature_items.comics_item;

import org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandler;
import org.vshmaliukh.terminal.bookshelf.literature_items.ItemTitles;
import org.vshmaliukh.terminal.menus.menu_items.MenuItemForSorting;
import org.vshmaliukh.terminal.services.Utils;
import org.vshmaliukh.terminal.services.input_services.InputHandlerForLiterature;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.*;

import static org.vshmaliukh.terminal.bookshelf.literature_items.ItemTitles.*;
import static org.vshmaliukh.terminal.services.Utils.getRandomString;
import static org.vshmaliukh.terminal.services.input_services.ConstantsForUserInputHandler.*;

public class ComicsHandler implements ItemHandler<Comics> {

    public static final Comparator<Comics> COMICS_COMPARATOR_BY_NAME = Comparator.comparing(Comics::getName, String.CASE_INSENSITIVE_ORDER);
    public static final Comparator<Comics> COMICS_COMPARATOR_BY_PUBLISHER = Comparator.comparing(Comics::getPublisher, String.CASE_INSENSITIVE_ORDER);
    public static final Comparator<Comics> COMICS_COMPARATOR_BY_PAGES = Comparator.comparing(Comics::getPagesNumber);

    @Override
    public List<Comics> getSortedItems(int typeOfSorting, List<Comics> inputList) {
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
                new MenuItemForSorting(1, "Sort by 'name' value", COMICS_COMPARATOR_BY_NAME),
                new MenuItemForSorting(2, "Sort by 'page number' value", COMICS_COMPARATOR_BY_PAGES),
                new MenuItemForSorting(3, "Sort by 'publisher' value", COMICS_COMPARATOR_BY_PUBLISHER)
        ));
    }

    @Override
    public Comics getItemByUserInput(InputHandlerForLiterature inputHandlerForLiterature, PrintWriter printWriter) {
        String name = inputHandlerForLiterature.getUserLiteratureName();
        int pages = inputHandlerForLiterature.getUserLiteraturePages();
        boolean isBorrowed = inputHandlerForLiterature.getUserLiteratureIsBorrowed();
        String publisher = inputHandlerForLiterature.getUserLiteraturePublisher();
        return new Comics(name, pages, isBorrowed, publisher);
    }

    @Override
    public Comics getRandomItem(Random random) {
        return new Comics(
                getRandomString(random.nextInt(20), random),
                random.nextInt(1000),
                false,
                getRandomString(random.nextInt(20), random));
    }

    @Override
    public Map<String, String> convertItemToListOfString(Comics comics) {
        Map<String, String> map = new HashMap<>();
        map.put(ItemTitles.TYPE, comics.getClass().getSimpleName());
        map.put(ItemTitles.NAME, comics.getName());
        map.put(ItemTitles.PAGES, String.valueOf(comics.getPagesNumber()));
        map.put(ItemTitles.BORROWED, String.valueOf(comics.isBorrowed()));
        map.put(ItemTitles.PUBLISHER, comics.getPublisher());
        return new HashMap<>(map);
    }

    @Override
    public String generateHTMLFormBodyToCreateItem() {
        return "" +
                Utils.generateHTMLFormItem(NAME) +
                Utils.generateHTMLFormItem(PAGES) +
                Utils.generateHTMLFormItem(BORROWED) +
                Utils.generateHTMLFormItem(PUBLISHER) +
                "   <input type = \"submit\" value = \"Submit\" />\n" +
                "</form>";
    }

    @Override
    public String generateHTMLFormBodyToCreateItem(Random random) {
        return "" +
                Utils.generateHTMLFormItem(NAME, getRandomString(random.nextInt(20), random)) +
                Utils.generateHTMLFormItem(PAGES, String.valueOf(random.nextInt(1000))) +
                Utils.generateHTMLFormItem(BORROWED, "n") +
                Utils.generateHTMLFormItem(PUBLISHER, getRandomString(random.nextInt(20), random)) +
                "   <input type = \"submit\" value = \"Submit\" />\n" +
                "</form>";
    }

    @Override
    public boolean isValidHTMLFormData(Map<String, String> mapFieldValue) {
        String nameParameter = mapFieldValue.get(NAME);
        String pagesParameter = mapFieldValue.get(PAGES);
        String borrowedParameter = mapFieldValue.get(BORROWED);
        String publisherParameter = mapFieldValue.get(PUBLISHER);
        if (isValidBookInput(nameParameter, pagesParameter, borrowedParameter, publisherParameter)) {
            return true;
        }
        return false;
    }

    public boolean isValidBookInput(String name, String pages, String borrowed, String publisherParameter) {
        return InputHandlerForLiterature.isValidInputString(name, PATTERN_FOR_NAME) &&
                InputHandlerForLiterature.isValidInputInteger(pages, PATTERN_FOR_PAGES) &&
                InputHandlerForLiterature.isValidInputString(borrowed, PATTERN_FOR_IS_BORROWED) &&
                InputHandlerForLiterature.isValidInputString(publisherParameter, PATTERN_FOR_PUBLISHER);
    }

    @Override
    public Comics generateItemByHTMLFormData(Map<String, String> mapFieldValue, PrintWriter printWriter) {
        String nameParameter = mapFieldValue.get(NAME);
        String pagesParameter = mapFieldValue.get(PAGES);
        String borrowedParameter = mapFieldValue.get(BORROWED);
        String publisherParameter = mapFieldValue.get(PUBLISHER);

        InputHandlerForLiterature inputHandlerForLiterature = new InputHandlerForLiterature(null, printWriter);
        String join = String.join(System.lineSeparator(), nameParameter, pagesParameter, borrowedParameter, publisherParameter);
        inputHandlerForLiterature.scanner = new Scanner(join);

        String name = inputHandlerForLiterature.getUserLiteratureName();
        int pages = inputHandlerForLiterature.getUserLiteraturePages();
        boolean isBorrowed = inputHandlerForLiterature.getUserLiteratureIsBorrowed();
        String publisher = inputHandlerForLiterature.getUserLiteraturePublisher();

        return new Comics(name, pages, isBorrowed, publisher);
    }
}
