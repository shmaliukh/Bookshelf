package org.vshmaliukh.terminal.bookshelf.literature_items.comics_item;

import org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandler;
import org.vshmaliukh.terminal.bookshelf.literature_items.ItemTitles;
import org.vshmaliukh.terminal.bookshelf.literature_items.magazine_item.Magazine;
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
    public String generateHTMLFormBodyToCreateItem(HttpServletRequest request) {
        return "" +
                Utils.generateHTMLFormItem(NAME) +
                Utils.generateHTMLFormItem(PAGES) +
                Utils.generateHTMLFormItem(BORROWED) +
                Utils.generateHTMLFormItem(PUBLISHER) +
                "   <input type = \"submit\" value = \"Submit\" />\n" +
                "</form>";
    }

    @Override
    public boolean isValidHTMLFormData(HttpServletRequest request) {
        String nameParameter = request.getParameter(NAME);
        String pagesParameter = request.getParameter(PAGES);
        String borrowedParameter = request.getParameter(BORROWED);
        String publisherParameter = request.getParameter(PUBLISHER);
        if (InputHandlerForLiterature.isValidInputString(nameParameter, PATTERN_FOR_NAME) &&
                InputHandlerForLiterature.isValidInputInteger(pagesParameter, PATTERN_FOR_PAGES) &&
                InputHandlerForLiterature.isValidInputString(borrowedParameter, PATTERN_FOR_IS_BORROWED) &&
                InputHandlerForLiterature.isValidInputString(publisherParameter, PATTERN_FOR_PUBLISHER)) {
            return true;
        }
        return false;
    }

    @Override
    public Comics generateItemByHTMLFormData(HttpServletRequest request, PrintWriter printWriter) {
        String nameParameter = request.getParameter(NAME);
        String pagesParameter = request.getParameter(PAGES);
        String borrowedParameter = request.getParameter(BORROWED);
        String publisherParameter = request.getParameter(PUBLISHER);

        InputHandlerForLiterature inputHandlerForLiterature = new InputHandlerForLiterature(null, printWriter);

        inputHandlerForLiterature.scanner = new Scanner(nameParameter);
        String name = inputHandlerForLiterature.getUserLiteratureName();
        inputHandlerForLiterature.scanner = new Scanner(pagesParameter);
        int pages = inputHandlerForLiterature.getUserLiteraturePages();
        inputHandlerForLiterature.scanner = new Scanner(borrowedParameter);
        boolean isBorrowed = inputHandlerForLiterature.getUserLiteratureIsBorrowed();
        inputHandlerForLiterature.scanner = new Scanner(publisherParameter);
        String publisher = inputHandlerForLiterature.getUserLiteraturePublisher();

        return new Comics(name, pages, isBorrowed, publisher);
    }
}
