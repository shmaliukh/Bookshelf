package com.vshmaliukh.springwebappmodule.spring_sql_handlers.mysql.services;

import com.vshmaliukh.springwebappmodule.spring_sql_handlers.convertors.imp.BookEntityConvertor;
import com.vshmaliukh.springwebappmodule.spring_sql_handlers.entities.BookEntity;
import com.vshmaliukh.springwebappmodule.spring_sql_handlers.mysql.MysqlItemEntityRepositoryActionsProvider;
import com.vshmaliukh.springwebappmodule.spring_sql_handlers.mysql.repositories.MysqlBookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.vshmaliukh.ConfigFile;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.book_item.Book;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@Import({MysqlItemServiceImp.class, MysqlItemEntityRepositoryActionsProvider.class})
class BookServiceImpTest {

    public static final int USER_ID = 11;
    static Book book1;
    static Book book2;
    static BookEntity book1Entity;
    static BookEntity book2Entity;

    static {
        try {
            book1 = new Book(1, "testBook", 111, false, "testBookAuthor", new SimpleDateFormat(ConfigFile.DATE_FORMAT_STR).parse("2022-09-08"));
            book2 = new Book(2, "testBook2", 222, true, "testBookAuthor2", new SimpleDateFormat(ConfigFile.DATE_FORMAT_STR).parse("2022-12-12"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        BookEntityConvertor bookEntityConvertor = new BookEntityConvertor();
        book1Entity = bookEntityConvertor.getConvertedEntityFromItem(book1, USER_ID);
        book2Entity = bookEntityConvertor.getConvertedEntityFromItem(book2, USER_ID);
    }

    @Autowired
    MysqlItemEntityRepositoryActionsProvider mysqlItemEntityRepositoryActionsProvider;

    @Autowired
    MysqlItemServiceImp service;

    @MockBean
    MysqlBookRepository mockRepository;

    @Autowired
    MysqlBookRepository bookRepository;

    @Test
    void addItemTest() {
        Integer userId = 691;
        service.insertItemByUserId(book1, userId);
        List<Item> items = service.readAllItemListByUserId(USER_ID);
        assertNotNull(items);
        assertEquals(1, items.size());
        assertEquals(book1.toString(), items.get(0).toString());
    }

    @Test
    void addTwoItemsTest() {
    }

    @Test
    void readItemTest() {
        List<BookEntity> bookEntityList = Collections.singletonList(book1Entity);
        org.mockito.BDDMockito
                .given(this.mockRepository.findAllByUserId(USER_ID))
                .willReturn(bookEntityList);
        List<Item> items = service.readAllItemListByUserId(USER_ID);
        assertNotNull(items);
        assertEquals(bookEntityList.size(), items.size());
        assertEquals(book1.toString(), items.get(0).toString());
    }

    @Test
    void deleteItemByUserIdTest() {
    }

    @Test
    void changeBorrowedStateTest() {
    }

    @Test
    void readAllItemsByUserIdTest() {
    }
}