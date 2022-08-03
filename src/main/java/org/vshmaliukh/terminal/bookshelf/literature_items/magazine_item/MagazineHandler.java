package org.vshmaliukh.terminal.bookshelf.literature_items.magazine_item;

import org.vshmaliukh.terminal.bookshelf.literature_items.ItemHandler;
import org.vshmaliukh.terminal.bookshelf.literature_items.ItemTitles;
import org.vshmaliukh.terminal.bookshelf.literature_items.newspaper_item.Newspaper;
import org.vshmaliukh.terminal.menus.menu_items.MenuItemForSorting;
import org.vshmaliukh.terminal.services.Utils;
import org.vshmaliukh.terminal.services.input_services.InputHandlerForLiterature;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.*;

import static org.vshmaliukh.terminal.bookshelf.literature_items.ItemTitles.*;
import static org.vshmaliukh.terminal.services.input_services.ConstantsForUserInputHandler.*;

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
    public String generateHTMLFormBodyToCreateItem(HttpServletRequest request) {
        return "" +
                Utils.generateHTMLFormItem(NAME) +
                Utils.generateHTMLFormItem(PAGES) +
                Utils.generateHTMLFormItem(BORROWED) +
                "   <input type = \"submit\" value = \"Submit\" />\n" +
                "</form>";
    }

    @Override
    public boolean isValidHTMLFormData(HttpServletRequest request) {
        String nameParameter = request.getParameter(NAME);
        String pagesParameter = request.getParameter(PAGES);
        String borrowedParameter = request.getParameter(BORROWED);
        if (InputHandlerForLiterature.isValidInputString(nameParameter, PATTERN_FOR_NAME) &&
                InputHandlerForLiterature.isValidInputInteger(pagesParameter, PATTERN_FOR_PAGES) &&
                InputHandlerForLiterature.isValidInputString(borrowedParameter, PATTERN_FOR_IS_BORROWED)) {
            return true;
        }
        return false;
    }

    @Override
    public Magazine generateItemByHTMLFormData(HttpServletRequest request, PrintWriter printWriter) {
        String nameParameter = request.getParameter(NAME);
        String pagesParameter = request.getParameter(PAGES);
        String borrowedParameter = request.getParameter(BORROWED);

        InputHandlerForLiterature inputHandlerForLiterature = new InputHandlerForLiterature(null, printWriter);

        inputHandlerForLiterature.scanner = new Scanner(nameParameter);
        String name = inputHandlerForLiterature.getUserLiteratureName();
        inputHandlerForLiterature.scanner = new Scanner(pagesParameter);
        int pages = inputHandlerForLiterature.getUserLiteraturePages();
        inputHandlerForLiterature.scanner = new Scanner(borrowedParameter);
        boolean isBorrowed = inputHandlerForLiterature.getUserLiteratureIsBorrowed();

        return new Magazine(name, pages, isBorrowed);
    }
}
