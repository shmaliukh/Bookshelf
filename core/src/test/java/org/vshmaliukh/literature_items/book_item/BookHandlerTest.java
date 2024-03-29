package org.vshmaliukh.literature_items.book_item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.vshmaliukh.services.input_handler.ConsoleInputHandlerForLiterature;
import org.vshmaliukh.services.input_handler.ConstantsForConsoleUserInputHandler;
import org.vshmaliukh.shelf.literature_items.ItemUtils;
import org.vshmaliukh.shelf.literature_items.book_item.Book;
import org.vshmaliukh.shelf.literature_items.book_item.BookHandler;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.vshmaliukh.BaseAppConfig.DATE_FORMAT_STR;
import static org.vshmaliukh.shelf.literature_items.ItemTitles.*;

class BookHandlerTest {

    PrintWriter printWriter = new PrintWriter(System.out, true);

    BookHandler bookHandler = new BookHandler();

    Book book1 = new Book("noNameBook1", 1, false, "NoAuthor1", new Date(System.currentTimeMillis() - 60 * 60 * 64 * 1000));
    Book book2 = new Book("noNameBook2", 2, true, "NoAuthor2", new Date());

    List<Book> books = Arrays.asList(book1, book2);

    @Test
    void testGetConvertorToString() {
        Map<String, String> convertObjectToMapOfString = new BookHandler().convertItemToMapOfString(book1);

        Map<String, String> map = new HashMap<>();
        map.put(TYPE, book1.getClass().getSimpleName());
        map.put(NAME, book1.getName());
        map.put(PAGES, String.valueOf(book1.getPagesNumber()));
        map.put(BORROWED, ItemUtils.convertBorrowed(book1.isBorrowed()));
        map.put(AUTHOR, book1.getAuthor());
        map.put(DATE, new SimpleDateFormat(DATE_FORMAT_STR).format(book1.getIssuanceDate()));

        assertEquals(map.size(), convertObjectToMapOfString.size());
        assertTrue(map.values().containsAll(convertObjectToMapOfString.values())); // FIXME problem
        assertTrue(map.keySet().containsAll(convertObjectToMapOfString.keySet()));
    }

    @ParameterizedTest(name = "{index} ==> input string ''{0}''")
    @ValueSource(ints = {1, 2, 3, 4,})
    void getSortedItems(int typeOfSorting) {
        List<Book> sortedItems = bookHandler.getSortedItems(typeOfSorting, books);

        assertTrue(books.containsAll(sortedItems));
        assertNotEquals(book2, sortedItems.get(0));
        assertNotEquals(book1, sortedItems.get(1));
    }

    private static Stream<Arguments> providedArgsForSorting() {
        Book book1 = new Book("noNameBook1", 1, false, "NoAuthor1", new Date(System.currentTimeMillis() - 60 * 60 * 64 * 1000));
        Book book2 = new Book("noNameBook2", 2, true, "NoAuthor2", new Date());
        return Stream.of(
                Arguments.of(1, new ArrayList<>(Arrays.asList(book1, book2))),
                Arguments.of(2, new ArrayList<>(Arrays.asList(book1, book2))),
                Arguments.of(3, new ArrayList<>(Arrays.asList(book1, book2))),
                Arguments.of(4, new ArrayList<>(Arrays.asList(book1, book2))),
                Arguments.of(1, new ArrayList<>(Arrays.asList(book2, book1))),
                Arguments.of(2, new ArrayList<>(Arrays.asList(book2, book1))),
                Arguments.of(3, new ArrayList<>(Arrays.asList(book2, book1))),
                Arguments.of(4, new ArrayList<>(Arrays.asList(book2, book1)))
        );
    }

    @ParameterizedTest
    @MethodSource("providedArgsForSorting")
    void clarificationForSortingItems(int typeOfSorting, ArrayList arrayList) {
        List<Book> sortedItems = bookHandler.clarificationForSortingItems(arrayList, typeOfSorting, printWriter);

        List<String> stringList = sortedItems.stream()
                .map(Book::toString)
                .collect(Collectors.toList());

        List<String> stringList1 = books.stream()
                .map(Book::toString)
                .collect(Collectors.toList());

        assertTrue(stringList.containsAll(stringList1));
        assertNotEquals(book2.toString(), sortedItems.get(0).toString());
    }

    @Test
    void getItemByUserInput() {
        Scanner scanner = new Scanner(System.lineSeparator());
        ConsoleInputHandlerForLiterature consoleInputHandlerForLiterature = new ConsoleInputHandlerForLiterature(scanner, printWriter);
        Book itemByUserInput = bookHandler.getItemByUserInput(consoleInputHandlerForLiterature, printWriter);
        System.out.println(itemByUserInput);

        assertNotNull(itemByUserInput);
        assertNotNull(itemByUserInput.toString());
        assertEquals(ConstantsForConsoleUserInputHandler.DEFAULT_STRING, itemByUserInput.getName());
        assertEquals(ConstantsForConsoleUserInputHandler.DEFAULT_INT, itemByUserInput.getPagesNumber());
        assertEquals(ConstantsForConsoleUserInputHandler.DEFAULT_BOOLEAN, itemByUserInput.isBorrowed());
        assertEquals(ConstantsForConsoleUserInputHandler.DEFAULT_DATE, itemByUserInput.getIssuanceDate());
        assertEquals(ConstantsForConsoleUserInputHandler.DEFAULT_STRING, itemByUserInput.getAuthor());
    }

    @Test
    void getRandomItem() {
        Book randomItem = bookHandler.getRandomItem(new Random());
        assertNotNull(randomItem);
        assertNotNull(randomItem.getName());
        assertNotNull(randomItem.getAuthor());
        assertNotNull(randomItem.getIssuanceDate());
        assertNotNull(randomItem.toString());
    }
}