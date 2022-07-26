package org.vshmaliukh.handlers.ItemHandlers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.vshmaliukh.bookshelf.bookshelfObjects.Book;
import org.vshmaliukh.services.print_table_service.ConvertorToString;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.vshmaliukh.Terminal.DATE_FORMAT;

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

    //@ParameterizedTest
    //@MethodSource("providedArgsForSorting")
    //void clarificationForSortingItems(int typeOfSorting, boolean result, Arrays arrayList) {
    //    List<Book> sortedItems = bookHandler.clarificationForSortingItems((List<Book>) arrayList, typeOfSorting, printWriter);
    //    assertEquals(result, sortedItems.isEmpty());
    //    assertEquals(result, book1.equals(sortedItems.get(0)));
    //}

    private static Stream<Arguments> providedArgsForSorting(){
        return Stream.of(
                Arguments.of(1, true, Arrays.asList()),
                Arguments.of(1, true, Arrays.asList(new Book("noNameBook1", 1, false, "NoAuthor1", new Date(System.currentTimeMillis() - 60 * 60 * 64 * 1000)))),
                Arguments.of(1, true, Arrays.asList(new Book("noNameBook1", 1, false, "NoAuthor1", new Date(System.currentTimeMillis() - 60 * 60 * 64 * 1000)),  new Book("noNameBook2", 2, true, "NoAuthor2", new Date()))),
                Arguments.of(2, true, Arrays.asList()),
                Arguments.of(2, true, Arrays.asList(new Book("noNameBook1", 1, false, "NoAuthor1", new Date(System.currentTimeMillis() - 60 * 60 * 64 * 1000)))),
                Arguments.of(2, true, Arrays.asList(new Book("noNameBook1", 1, false, "NoAuthor1", new Date(System.currentTimeMillis() - 60 * 60 * 64 * 1000)),  new Book("noNameBook2", 2, true, "NoAuthor2", new Date()))),
                Arguments.of(3, true, Arrays.asList()),
                Arguments.of(3, true, Arrays.asList(new Book("noNameBook1", 1, false, "NoAuthor1", new Date(System.currentTimeMillis() - 60 * 60 * 64 * 1000)))),
                Arguments.of(3, true, Arrays.asList(new Book("noNameBook1", 1, false, "NoAuthor1", new Date(System.currentTimeMillis() - 60 * 60 * 64 * 1000)),  new Book("noNameBook2", 2, true, "NoAuthor2", new Date()))),
                Arguments.of(4, true, Arrays.asList()),
                Arguments.of(4, true, Arrays.asList(new Book("noNameBook1", 1, false, "NoAuthor1", new Date(System.currentTimeMillis() - 60 * 60 * 64 * 1000)))),
                Arguments.of(4, true, Arrays.asList(new Book("noNameBook1", 1, false, "NoAuthor1", new Date(System.currentTimeMillis() - 60 * 60 * 64 * 1000)),  new Book("noNameBook2", 2, true, "NoAuthor2", new Date())))
        );
    }

    @Test
    void getItemByUserInput() {
    }

    @Test
    void getRandomItem() {
    }
}