package org.vshmaliukh.shelf.literature_items.magazine_item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.vshmaliukh.console_terminal_app.input_handler.ConsoleInputHandlerForLiterature;
import org.vshmaliukh.shelf.literature_items.ItemUtils;

import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.vshmaliukh.shelf.literature_items.ItemTitles.*;
import static org.vshmaliukh.shelf.literature_items.ItemTitles.BORROWED;
import static org.vshmaliukh.console_terminal_app.input_handler.ConstantsForConsoleUserInputHandler.*;

class MagazineHandlerTest {

    PrintWriter printWriter = new PrintWriter(System.out, true);

    MagazineHandler magazineHandler = new MagazineHandler();

    Magazine magazine1 = new Magazine("noNameMagazine1", 1, false);
    Magazine magazine2 = new Magazine("noNameMagazine2", 2, false);

    List<Magazine> magazines = Arrays.asList(magazine2, magazine1);

    @Test
    void testGetConvertorToString() {
        Map<String, String> convertObjectToMapOfString = new MagazineHandler().convertItemToListOfString(magazine1);

        Map<String, String> map = new HashMap<>();
        map.put(TYPE, magazine1.getClass().getSimpleName());
        map.put(NAME, magazine1.getName());
        map.put(PAGES, String.valueOf(magazine1.getPagesNumber()));
        map.put(BORROWED, ItemUtils.convertBorrowed(magazine1.isBorrowed()));

        assertEquals(map.size(), convertObjectToMapOfString.size());
        assertTrue(map.values().containsAll(convertObjectToMapOfString.values()));
        assertTrue(map.keySet().containsAll(convertObjectToMapOfString.keySet()));
    }

    @ParameterizedTest(name = "{index} ==> input string ''{0}''")
    @ValueSource(ints = {1, 2})
    void getSortedItems(int typeOfSorting) {
        List<Magazine> sortedItems = magazineHandler.getSortedItems(typeOfSorting, magazines);

        assertTrue(magazines.containsAll(sortedItems));
        assertNotEquals(magazine2.toString(), sortedItems.get(0).toString());
    }

    private static Stream<Arguments> providedArgsForSorting() {
        Magazine magazine1 = new Magazine("noNameMagazine1", 1, false);
        Magazine magazine2 = new Magazine("noNameMagazine2", 2, false);
        return Stream.of(
                Arguments.of(1, new ArrayList<>(Arrays.asList(magazine1, magazine2))),
                Arguments.of(2, new ArrayList<>(Arrays.asList(magazine1, magazine2))),
                Arguments.of(1, new ArrayList<>(Arrays.asList(magazine2, magazine1))),
                Arguments.of(2, new ArrayList<>(Arrays.asList(magazine2, magazine1)))
        );
    }

    @ParameterizedTest
    @MethodSource("providedArgsForSorting")
    void clarificationForSortingItems(int typeOfSorting, ArrayList arrayList) {
        List<Magazine> sortedItems = magazineHandler.clarificationForSortingItems(arrayList, typeOfSorting, printWriter);

        List<String> stringList = sortedItems.stream()
                .map(Magazine::toString)
                .collect(Collectors.toList());

        List<String> stringList1 = magazines.stream()
                .map(Magazine::toString)
                .collect(Collectors.toList());

        assertTrue(stringList.containsAll(stringList1));
        assertNotEquals(magazine2.toString(), sortedItems.get(0).toString());
    }

    @Test
    void getItemByUserInput() {
        Scanner scanner = new Scanner(System.lineSeparator());
        ConsoleInputHandlerForLiterature consoleInputHandlerForLiterature = new ConsoleInputHandlerForLiterature(scanner, printWriter);
        Magazine itemByUserInput = magazineHandler.getItemByUserInput(consoleInputHandlerForLiterature, printWriter);
        System.out.println(itemByUserInput);

        assertNotNull(itemByUserInput);
        assertNotNull(itemByUserInput.toString());
        assertEquals(DEFAULT_STRING, itemByUserInput.getName());
        assertEquals(DEFAULT_INTEGER, itemByUserInput.getPagesNumber());
        assertEquals(DEFAULT_BOOLEAN, itemByUserInput.isBorrowed());
    }

    @Test
    void getRandomItem() {
        Magazine randomItem = magazineHandler.getRandomItem(new Random());
        assertNotNull(randomItem);
        assertNotNull(randomItem.getName());
        assertNotNull(randomItem.toString());
    }
}