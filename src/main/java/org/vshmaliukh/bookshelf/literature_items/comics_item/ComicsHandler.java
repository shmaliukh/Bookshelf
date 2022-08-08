package org.vshmaliukh.bookshelf.literature_items.comics_item;

import org.vshmaliukh.bookshelf.literature_items.ItemHandler;
import org.vshmaliukh.bookshelf.literature_items.ItemTitles;
import org.vshmaliukh.console_terminal.menus.menu_items.MenuItemForSorting;
import org.vshmaliukh.console_terminal.services.Utils;
import org.vshmaliukh.console_terminal.services.input_services.ConsoleInputHandlerForLiterature;
import org.vshmaliukh.console_terminal.services.input_services.WebInputHandler;

import java.io.PrintWriter;
import java.util.*;

import static org.vshmaliukh.bookshelf.literature_items.ItemTitles.*;
import static org.vshmaliukh.console_terminal.services.Utils.getRandomString;
import static org.vshmaliukh.console_terminal.services.input_services.ConstantsForConsoleUserInputHandler.*;
import static org.vshmaliukh.console_terminal.services.input_services.InputHandler.isValidInputInteger;
import static org.vshmaliukh.console_terminal.services.input_services.InputHandler.isValidInputString;

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
    public Comics getItemByUserInput(ConsoleInputHandlerForLiterature consoleInputHandlerForLiterature, PrintWriter printWriter) {
        String name = consoleInputHandlerForLiterature.getUserLiteratureName();
        int pages = consoleInputHandlerForLiterature.getUserLiteraturePages();
        boolean isBorrowed = consoleInputHandlerForLiterature.getUserLiteratureIsBorrowed();
        String publisher = consoleInputHandlerForLiterature.getUserLiteraturePublisher();
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
                Utils.generateHTMLFormItem(ItemTitles.NAME, "text") +
                Utils.generateHTMLFormItem(ItemTitles.PAGES, "number") +
                Utils.generateHTMLFormRadio(ItemTitles.BORROWED) +
                Utils.generateHTMLFormItem(PUBLISHER, "text") +
                "   <br>\n" +
                "   <input type = \"submit\" value = \"Submit\" />\n" +
                "   <br>\n" +
                "   <br>\n" +
                "</form>\n";
    }

    @Override
    public String generateHTMLFormBodyToCreateItem(Random random) {
        return "" +
                Utils.generateHTMLFormItem(ItemTitles.NAME, "text", Utils.getRandomString(random.nextInt(20), random)) +
                Utils.generateHTMLFormItem(ItemTitles.PAGES, "number", String.valueOf(random.nextInt(1000))) +
                Utils.generateHTMLFormRadio(ItemTitles.BORROWED) +
                Utils.generateHTMLFormItem(PUBLISHER, "text", getRandomString(random.nextInt(20), random)) +
                "   <br>\n" +
                "   <input type = \"submit\" value = \"Submit\" />\n" +
                "   <br>\n" +
                "   <br>\n" +
                "</form>\n";
    }

    @Override
    public boolean isValidHTMLFormData(Map<String, String> mapFieldValue) {
        String nameParameter = mapFieldValue.get(NAME);
        String pagesParameter = mapFieldValue.get(PAGES);
        String borrowedParameter = mapFieldValue.get(BORROWED);
        String publisherParameter = mapFieldValue.get(PUBLISHER);
        return isValidUserParametersInput(nameParameter, pagesParameter, borrowedParameter, publisherParameter);
    }

    public boolean isValidUserParametersInput(String name, String pages, String borrowed, String publisherParameter) {
        return isValidInputString(name, PATTERN_FOR_NAME) &&
                isValidInputInteger(pages, PATTERN_FOR_PAGES) &&
                isValidInputString(borrowed, PATTERN_FOR_IS_BORROWED) &&
                isValidInputString(publisherParameter, PATTERN_FOR_PUBLISHER);
    }

    @Override
    public Comics generateItemByHTMLFormData(Map<String, String> mapFieldValue) {
        WebInputHandler webInputHandler = new WebInputHandler();

        String name = webInputHandler.getUserString(mapFieldValue.get(NAME), PATTERN_FOR_NAME);
        Integer pages = webInputHandler.getUserInteger(mapFieldValue.get(PAGES), PATTERN_FOR_PAGES);
        Boolean isBorrowed = webInputHandler.getUserBoolean(mapFieldValue.get(BORROWED), PATTERN_FOR_IS_BORROWED);
        String publisher = webInputHandler.getUserString(mapFieldValue.get(PUBLISHER), PATTERN_FOR_PUBLISHER);

        if (name != null && pages != null && isBorrowed != null && publisher != null) {
            return new Comics(name, pages, isBorrowed, publisher);
        }
        return null;
    }
}
