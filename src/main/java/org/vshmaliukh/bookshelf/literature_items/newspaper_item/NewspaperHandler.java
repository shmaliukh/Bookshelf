package org.vshmaliukh.bookshelf.literature_items.newspaper_item;

import org.vshmaliukh.bookshelf.literature_items.ItemHandler;
import org.vshmaliukh.bookshelf.literature_items.ItemTitles;
import org.vshmaliukh.console_terminal.menus.menu_items.MenuItemForSorting;
import org.vshmaliukh.console_terminal.services.Utils;
import org.vshmaliukh.console_terminal.services.input_services.InputHandlerForLiterature;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.*;

import static org.vshmaliukh.console_terminal.services.input_services.ConstantsForUserInputHandler.*;
import static org.vshmaliukh.console_terminal.services.input_services.InputHandler.isValidInputInteger;
import static org.vshmaliukh.console_terminal.services.input_services.InputHandler.isValidInputString;

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

    @Override
    public String generateHTMLFormBodyToCreateItem() {
        return "" +
                Utils.generateHTMLFormItem(ItemTitles.NAME, "text") +
                Utils.generateHTMLFormItem(ItemTitles.PAGES, "number") +
                Utils.generateHTMLFormRadio(ItemTitles.BORROWED) +
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
                "   <br>\n" +
                "   <input type = \"submit\" value = \"Submit\" />\n" +
                "   <br>\n" +
                "   <br>\n" +
                "</form>\n";
    }

    @Override
    public boolean isValidHTMLFormData(Map<String, String> mapFieldValue) {
        String nameParameter = mapFieldValue.get(ItemTitles.NAME);
        String pagesParameter = mapFieldValue.get(ItemTitles.PAGES);
        String borrowedParameter = mapFieldValue.get(ItemTitles.BORROWED);

        return isValidBookInput(nameParameter, pagesParameter, borrowedParameter);
    }

    public boolean isValidBookInput(String name, String pages, String borrowed) {
        return isValidInputString(name, PATTERN_FOR_NAME) &&
                isValidInputInteger(pages, PATTERN_FOR_PAGES) &&
                isValidInputString(borrowed, PATTERN_FOR_IS_BORROWED);
    }

    @Override
    public Newspaper generateItemByHTMLFormData(Map<String, String> mapFieldValue) {
        String nameParameter = mapFieldValue.get(ItemTitles.NAME);
        String pagesParameter = mapFieldValue.get(ItemTitles.PAGES);
        String borrowedParameter = mapFieldValue.get(ItemTitles.BORROWED);

        String join = String.join(System.lineSeparator(), nameParameter, pagesParameter, borrowedParameter);
        InputHandlerForLiterature inputHandlerForLiterature = new InputHandlerForLiterature(new Scanner(join), new PrintWriter(new ByteArrayOutputStream()));

        String name = inputHandlerForLiterature.getUserLiteratureName();
        int pages = inputHandlerForLiterature.getUserLiteraturePages();
        boolean isBorrowed = inputHandlerForLiterature.getUserLiteratureIsBorrowed();

        return new Newspaper(name, pages, isBorrowed);
    }
}
