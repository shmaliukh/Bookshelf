package org.vshmaliukh.handlers.ItemHandlers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.vshmaliukh.bookshelf.bookshelfObjects.Gazette;
import org.vshmaliukh.services.input_services.InputHandlerForLiterature;
import org.vshmaliukh.services.print_table_service.ConvertorToString;

import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.vshmaliukh.services.input_services.ConstantsForUserInputHandler.*;

class GazetteHandlerTest {

    PrintWriter printWriter = new PrintWriter(System.out, true);

    GazetteHandler gazetteHandler = new GazetteHandler();

    Gazette gazette1 = new Gazette("noNameGazette1", 1, false);
    Gazette gazette2 = new Gazette("noNameGazette2", 2, false);

    List<Gazette> gazettes = Arrays.asList(gazette2, gazette1);

    @Test
    void testGetConvertorToString() {
        ConvertorToString<Gazette> convertorToString = gazetteHandler.getConvertorToString();
        Map<String, String> convertObjectToListOfString = convertorToString.convertObjectToListOfString(gazette1);

        List<String> stringList = new ArrayList<>();
        stringList.add(gazette1.getClass().getSimpleName());
        stringList.add(gazette1.getName());
        stringList.add(String.valueOf(gazette1.getPagesNumber()));
        stringList.add(String.valueOf(gazette1.isBorrowed()));

        assertEquals(stringList.size(), convertObjectToListOfString.size());
        //assertTrue(stringList.containsAll(convertObjectToListOfString));
    }

    @ParameterizedTest(name = "{index} ==> input string ''{0}''")
    @ValueSource(ints = {1, 2})
    void getSortedItems(int typeOfSorting) {
        List<Gazette> sortedItems = gazetteHandler.getSortedItems(typeOfSorting, gazettes);

        assertTrue(gazettes.containsAll(sortedItems));
        assertFalse(gazette2.toString().equals(sortedItems.get(0).toString()));
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
        assertFalse(gazette2.toString().equals(sortedItems.get(0).toString()));
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