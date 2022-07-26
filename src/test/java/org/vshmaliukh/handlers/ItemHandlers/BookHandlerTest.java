package org.vshmaliukh.handlers.ItemHandlers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.services.input_services.InputHandlerForLiterature;
import org.vshmaliukh.services.print_table_service.ConvertorToString;

import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.vshmaliukh.Terminal.DATE_FORMAT;
import static org.vshmaliukh.services.input_services.ConstantsForUserInputHandler.*;

class BookHandlerTest {

    PrintWriter printWriter = new PrintWriter(System.out, true);

    BookHandler bookHandler = new BookHandler();

    Book book1 = new Book("noNameBook1", 1, false, "NoAuthor1", new Date(System.currentTimeMillis() - 60 * 60 * 64 * 1000));
    Book book2 = new Book("noNameBook2", 2, true, "NoAuthor2", new Date());

    List<Book> books = Arrays.asList(book1, book2);

    @Test
    void testGetConvertorToString() {
        ConvertorToString<Book> convertorToString = bookHandler.getConvertorToString();
        List<String> convertObjectToListOfString = convertorToString.convertObjectToListOfString(book1);

        List<String> stringList = new ArrayList<>();
        stringList.add(book1.getClass().getSimpleName());
        stringList.add(book1.getName());
        stringList.add(book1.getAuthor());
        stringList.add(String.valueOf(book1.getPagesNumber()));
        stringList.add(DATE_FORMAT.format(book1.getIssuanceDate()));
        stringList.add(String.valueOf(book1.isBorrowed()));

        assertEquals(stringList.size(), convertObjectToListOfString.size());
        assertTrue(stringList.containsAll(convertObjectToListOfString));
    }

    @ParameterizedTest(name = "{index} ==> input string ''{0}''")
    @ValueSource(ints = {1, 2, 3, 4,})
    void getSortedItems(int typeOfSorting) {
        List<Book> sortedItems = bookHandler.getSortedItems(typeOfSorting, books);

        assertTrue(books.containsAll(sortedItems));
        assertFalse(book2.equals(sortedItems.get(0)));
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
        assertFalse(book2.toString().equals(sortedItems.get(0).toString()));
    }

    @Test
    void getItemByUserInput() {
        Scanner scanner = new Scanner(System.lineSeparator());
        InputHandlerForLiterature inputHandlerForLiterature = new InputHandlerForLiterature(scanner, printWriter);
        Book itemByUserInput = bookHandler.getItemByUserInput(inputHandlerForLiterature, printWriter);
        System.out.println(itemByUserInput);

        assertNotNull(itemByUserInput);
        assertNotNull(itemByUserInput.toString());
        assertEquals(DEFAULT_STRING, itemByUserInput.getName());
        assertEquals(DEFAULT_INTEGER, itemByUserInput.getPagesNumber());
        assertEquals(DEFAULT_BOOLEAN, itemByUserInput.isBorrowed());
        assertEquals(DEFAULT_DATE, itemByUserInput.getIssuanceDate());
        assertEquals(DEFAULT_STRING, itemByUserInput.getAuthor());
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