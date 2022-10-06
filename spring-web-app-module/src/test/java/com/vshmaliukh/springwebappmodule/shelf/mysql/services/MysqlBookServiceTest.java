package com.vshmaliukh.springwebappmodule.shelf.mysql.services;

import com.vshmaliukh.springwebappmodule.shelf.ItemEntityConvertorsProvider;
import com.vshmaliukh.springwebappmodule.shelf.convertors.ItemEntityConvertor;
import com.vshmaliukh.springwebappmodule.shelf.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.vshmaliukh.ConfigFile;
import org.vshmaliukh.shelf.literature_items.book_item.Book;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import({MysqlBookService.class})
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MysqlBookServiceTest {

    public static final int USER_ID = 11;
    static Book book;
    static BookEntity bookEntity;

    static {
        try {
            book = new Book(1, "testBook", 111, false, "testBookAuthor", new SimpleDateFormat(ConfigFile.DATE_FORMAT_STR).parse("2022-09-08"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        ItemEntityConvertor convertorByItemClass = ItemEntityConvertorsProvider.getConvertorByItemClass(book.getClass());
        bookEntity = (BookEntity) convertorByItemClass.getConvertedEntityFromItem(book, USER_ID);
    }

    @Autowired
    MysqlBookService mysqlBookService;

    @Test
    void saveBookAsItemToDbTest(){
        mysqlBookService.setUserName("Vlad");
        mysqlBookService.saveItemToDB(book);
        List<BookEntity> entityList = mysqlBookService.readItemEntityList();
        assertEquals(1, entityList.size());
        assertEquals(bookEntity.toString(), entityList.get(0).toString());
    }

    @Test
    void addBookTest() {
        mysqlBookService.addBook(bookEntity);
        List<BookEntity> entityList = mysqlBookService.readItemEntityList();
        assertEquals(1, entityList.size());
        assertEquals(bookEntity.toString(), entityList.get(0).toString());
    }

    @Test
    void readBooksTest() {
        List<BookEntity> entityList = mysqlBookService.readItemEntityList();
        assertEquals(1, entityList.size());
        assertEquals(bookEntity.toString(), entityList.get(0).toString());
    }

    @Test
    void updateBookTest() {
    }

    @Test
    void deleteBook() {
    }
}