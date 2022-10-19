package com.vshmaliukh.springwebappmodule.spring_sql_handlers.convertors.imp;

import com.vshmaliukh.springwebappmodule.spring_sql_handlers.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.vshmaliukh.shelf.literature_items.book_item.Book;

import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookEntityConvertorTest {

    BookEntityConvertor convertor = new BookEntityConvertor();

    public static final Date ISSUANCE_DATE_1 = new Date(System.currentTimeMillis() - 60 * 60 * 64 * 1000);
    public static final Date ISSUANCE_DATE_2 = new Date();

    static Book book1 = new Book("noNameBook1", 1, false, "NoAuthor1", ISSUANCE_DATE_1);
    static Book book2 = new Book("noNameBook2", 2, true, "NoAuthor2", ISSUANCE_DATE_2);
    static BookEntity bookEntity1 = new BookEntity();
    static BookEntity bookEntity2 = new BookEntity();

    static {
        bookEntity1.setId(null);
        bookEntity1.setUserId(1);
        bookEntity1.setName(book1.getName());
        bookEntity1.setPages(book1.getPagesNumber());
        bookEntity1.setBorrowed(book1.isBorrowed());
        bookEntity1.setAuthor(book1.getAuthor());
        bookEntity1.setDateOfIssue(ISSUANCE_DATE_1);

        bookEntity2.setId(null);
        bookEntity2.setUserId(1);
        bookEntity2.setName(book2.getName());
        bookEntity2.setPages(book2.getPagesNumber());
        bookEntity2.setBorrowed(book2.isBorrowed());
        bookEntity2.setAuthor(book2.getAuthor());
        bookEntity2.setDateOfIssue(ISSUANCE_DATE_2);
    }

    private static Stream<Arguments> providedArgsToConvert() {
        return Stream.of(
                Arguments.of(book1, true),
                Arguments.of(book1, false),
                Arguments.of(book2, true)
        );
    }

    static boolean isCorrectItemEntity(Book item, BookEntity entity) {
        return item.getName().equals(entity.getName()) &&
                item.getPagesNumber() == entity.getPages() &&
                item.isBorrowed() == entity.isBorrowed() &&
                item.getAuthor().equals(entity.getAuthor()) &&
                item.getIssuanceDate().equals(entity.getDateOfIssue());
    }

    @ParameterizedTest
    @MethodSource("providedArgsToConvert")
    void getConvertedItemFromEntity(Book bookToConvert, boolean expectedEqualsResult) {
        BookEntity convertedEntity = convertor.getConvertedEntityFromItem(bookToConvert, null);
        assertEquals(expectedEqualsResult, isCorrectItemEntity(bookToConvert, convertedEntity));
    }

    @Test
    void getConvertedEntityFromItem() {
    }
}