package org.vshmaliukh.terminal.bookshelf.literature_items.gazette_item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.vshmaliukh.terminal.bookshelf.literature_items.gazette_item.Gazette;
import org.vshmaliukh.terminal.bookshelf.literature_items.gazette_item.GazetteHandler;
import org.vshmaliukh.terminal.services.input_services.InputHandlerForLiterature;

import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.vshmaliukh.terminal.bookshelf.literature_items.ItemTitles.*;
import static org.vshmaliukh.terminal.services.input_services.ConstantsForUserInputHandler.*;

class GazetteHandlerTest {

    PrintWriter printWriter = new PrintWriter(System.out, true);

    GazetteHandler gazetteHandler = new GazetteHandler();

    Gazette gazette1 = new Gazette("noNameGazette1", 1, false);
    Gazette gazette2 = new Gazette("noNameGazette2", 2, false);

    List<Gazette> gazettes = Arrays.asList(gazette2, gazette1);

    @Test
    void testGetConvertorToString() {

        Map<String, String> convertObjectToMapOfString = new GazetteHandler().convertItemToListOfString(gazette1);

        Map<String, String> map = new HashMap<>();
        map.put(TYPE, gazette1.getClass().getSimpleName());
        map.put(NAME, gazette1.getName());
        map.put(PAGES, String.valueOf(gazette1.getPagesNumber()));
        map.put(BORROWED, String.valueOf(gazette1.isBorrowed()));

        assertEquals(map.size(), convertObjectToMapOfString.size());
        assertTrue(map.values().containsAll(convertObjectToMapOfString.values()));
        assertTrue(map.keySet().containsAll(convertObjectToMapOfString.keySet()));
    }

    @ParameterizedTest(name = "{index} ==> input string ''{0}''")
    @ValueSource(ints = {1, 2})
    void getSortedItems(int typeOfSorting) {
        List<Gazette> sortedItems = gazetteHandler.getSortedItems(typeOfSorting, gazettes);

        assertTrue(gazettes.containsAll(sortedItems));
        assertNotEquals(gazette2.toString(), sortedItems.get(0).toString());
    }

    private static Stream<Arguments> providedArgsForSorting() {
        Gazette gazette1 = new Gazette("noNameGazette1", 1, false);
        Gazette gazette2 = new Gazette("noNameGazette2", 2, false);
        return Stream.of(
                Arguments.of(1, new ArrayList<>(Arrays.asList(gazette1, gazette2))),
                Arguments.of(2, new ArrayList<>(Arrays.asList(gazette1, gazette2))),
                Arguments.of(1, new ArrayList<>(Arrays.asList(gazette2, gazette1))),
                Arguments.of(2, new ArrayList<>(Arrays.asList(gazette2, gazette1)))
        );
    }

    @ParameterizedTest
    @MethodSource("providedArgsForSorting")
    void clarificationForSortingItems(int typeOfSorting, ArrayList arrayList) {
        List<Gazette> sortedItems = gazetteHandler.clarificationForSortingItems(arrayList, typeOfSorting, printWriter);

        List<String> stringList = sortedItems.stream()
                .map(Gazette::toString)
                .collect(Collectors.toList());

        List<String> stringList1 = gazettes.stream()
                .map(Gazette::toString)
                .collect(Collectors.toList());

        assertTrue(stringList.containsAll(stringList1));
        assertNotEquals(gazette2.toString(), sortedItems.get(0).toString());
    }

    @Test
    void getItemByUserInput() {
        Scanner scanner = new Scanner(System.lineSeparator());
        InputHandlerForLiterature inputHandlerForLiterature = new InputHandlerForLiterature(scanner, printWriter);
        Gazette itemByUserInput = gazetteHandler.getItemByUserInput(inputHandlerForLiterature, printWriter);
        System.out.println(itemByUserInput);

        assertNotNull(itemByUserInput);
        assertNotNull(itemByUserInput.toString());
        assertEquals(DEFAULT_STRING, itemByUserInput.getName());
        assertEquals(DEFAULT_INTEGER, itemByUserInput.getPagesNumber());
        assertEquals(DEFAULT_BOOLEAN, itemByUserInput.isBorrowed());
    }

    @Test
    void getRandomItem() {
        Gazette randomItem = gazetteHandler.getRandomItem(new Random());
        assertNotNull(randomItem);
        assertNotNull(randomItem.getName());
        assertNotNull(randomItem.toString());
    }
}