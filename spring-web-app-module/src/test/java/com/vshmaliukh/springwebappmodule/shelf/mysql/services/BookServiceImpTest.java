package com.vshmaliukh.springwebappmodule.shelf.mysql.services;

import com.vshmaliukh.springwebappmodule.shelf.ItemEntityServiceProvider;
import com.vshmaliukh.springwebappmodule.shelf.convertors.ItemEntityConvertor;
import com.vshmaliukh.springwebappmodule.shelf.entities.BookEntity;
import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlBookRepository;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@Import({MysqlItemServiceImp.class})
class BookServiceImpTest {

    public static final int USER_ID = 11;
    static Book book1;
    static Book book2;
    static BookEntity book1Entity;

    static {
        try {
            book1 = new Book(1, "testBook", 111, false, "testBookAuthor", new SimpleDateFormat(ConfigFile.DATE_FORMAT_STR).parse("2022-09-08"));
            book2 = new Book(2, "testBook2", 222, true, "testBookAuthor2", new SimpleDateFormat(ConfigFile.DATE_FORMAT_STR).parse("2022-12-12"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        ItemEntityConvertor convertorByItemClass = ItemEntityServiceProvider.getConvertorByItemClass(book1.getClass());
        book1Entity = (BookEntity) convertorByItemClass.getConvertedEntityFromItem(book1, USER_ID);
    }


    @Autowired
    MysqlItemServiceImp service;

    @MockBean
    MysqlBookRepository repository;


    @BeforeEach
    public void before(){
//        service = new BookServiceImp(repository);
    }

    @Test
    void addItemTest() {
        service.addItem(book1, USER_ID);
        assertEquals(1, service.readAllItemsByUserId(USER_ID).size());
    }

    @Test
    void addTwoItemsTest() {
        service.addItem(book1, USER_ID);
        service.addItem(book2, USER_ID);
        List<Book> bookList = Arrays.asList(book1, book2);
        List<Item> readAllItemsByUserId = service.readAllItemsByUserId(USER_ID);
        assertEquals(2, readAllItemsByUserId.size());
        assertEquals(bookList.toString(), readAllItemsByUserId.toString());
    }

    @Test
    void readItemTest() {
        org.mockito.BDDMockito
                .given(this.repository.findAllByUserId(USER_ID))
                .willReturn(Collections.singletonList(book1Entity));
        List<Item> items = service.readAllItemsByUserId(USER_ID);
        assertNotNull(items);
        assertEquals(1, items.size());
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