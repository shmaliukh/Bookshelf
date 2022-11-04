package com.vshmaliukh.spring_shelf_core.shelf.convertors.imp;

import com.vshmaliukh.spring_shelf_core.shelf.entities.BookEntity;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.vshmaliukh.shelf.literature_items.book_item.Book;

import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookEntityConvertorTest {

    public static final Date ISSUANCE_DATE_1 = new Date(System.currentTimeMillis() - 60 * 60 * 64 * 1000);
    public static final Date ISSUANCE_DATE_2 = new Date();

    static Book book1 = new Book("someName1", 1, false, "someAuthor1", ISSUANCE_DATE_1);
    static Book book2 = new Book("someName2", 2, true, "someAuthor2", ISSUANCE_DATE_2);
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

    static BookEntityConvertor convertor = new BookEntityConvertor();

    private static Stream<Arguments> providedArgsToConvert() {
        return Stream.of(
                Arguments.of(bookEntity1, book1),
                Arguments.of(bookEntity2, book2)
        );
    }

    @ParameterizedTest
    @MethodSource("providedArgsToConvert")
    void getConvertedItemFromEntity(BookEntity toConvert, Book expectedItem) {
        Book itemFromEntity = convertor.getConvertedItemFromEntity(toConvert);
        assertEquals(expectedItem.getName(), toConvert.getName());
        assertEquals(expectedItem.getPagesNumber(), toConvert.getPages());
        assertEquals(expectedItem.isBorrowed(), toConvert.isBorrowed());
        assertEquals(expectedItem.getAuthor(), toConvert.getAuthor());
        assertEquals(expectedItem.getIssuanceDate(), toConvert.getDateOfIssue());
        assertEquals(expectedItem.toString(), itemFromEntity.toString());
    }

    @ParameterizedTest
    @MethodSource("providedArgsToConvert")
    void getConvertedEntityFromItem(BookEntity expectedEntity, Book toConvert) {
        BookEntity entityFromItem = convertor.getConvertedEntityFromItem(toConvert, null);
        assertEquals(expectedEntity.getId(), entityFromItem.getId());
        assertEquals(expectedEntity.getName(), entityFromItem.getName());
        assertEquals(expectedEntity.getPages(), entityFromItem.getPages());
        assertEquals(expectedEntity.isBorrowed(), entityFromItem.isBorrowed());
        assertEquals(expectedEntity.getAuthor(), entityFromItem.getAuthor());
        assertEquals(expectedEntity.getDateOfIssue(), entityFromItem.getDateOfIssue());
    }

}