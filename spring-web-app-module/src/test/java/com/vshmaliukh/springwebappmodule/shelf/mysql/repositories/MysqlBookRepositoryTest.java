//package com.vshmaliukh.springwebappmodule.spring_sql_handlers.mysql.repositories;
//
//import com.vshmaliukh.springwebappmodule.spring_sql_handlers.entities.BookEntity;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.vshmaliukh.shelf.literature_items.book_item.Book;
//
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@DataJpaTest
//class MysqlBookRepositoryTest {
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    @Qualifier("mysqlBookRepository")
//    private MysqlBookRepository repository;
//
//    public static final Date ISSUANCE_DATE_1 = new Date(System.currentTimeMillis() - 60 * 60 * 64 * 1000);
//    public static final Date ISSUANCE_DATE_2 = new Date();
//
//    static Book book1 = new Book("noNameBook1", 1, false, "NoAuthor1", ISSUANCE_DATE_1);
//    static Book book2 = new Book("noNameBook2", 2, true, "NoAuthor2", ISSUANCE_DATE_2);
//    static BookEntity bookEntity1 = new BookEntity();
//    static BookEntity bookEntity2 = new BookEntity();
//
//    public static final int USER_ID = 1;
//
//    static {
//        bookEntity1.setId(null);
//        bookEntity1.setUserId(USER_ID);
//        bookEntity1.setName(book1.getName());
//        bookEntity1.setPages(book1.getPagesNumber());
//        bookEntity1.setBorrowed(book1.isBorrowed());
//        bookEntity1.setAuthor(book1.getAuthor());
//        bookEntity1.setDateOfIssue(ISSUANCE_DATE_1);
//
//        bookEntity2.setId(null);
//        bookEntity2.setUserId(USER_ID);
//        bookEntity2.setName(book2.getName());
//        bookEntity2.setPages(book2.getPagesNumber());
//        bookEntity2.setBorrowed(book2.isBorrowed());
//        bookEntity2.setAuthor(book2.getAuthor());
//        bookEntity2.setDateOfIssue(ISSUANCE_DATE_2);
//    }
//
//    @Test
//    void findAllByUserIdTest() {
//        entityManager.persistAndFlush(bookEntity1);
//
//        List<BookEntity> allByUserId = repository.findAllByUserId(USER_ID);
//        assertNotNull(allByUserId);
//        assertEquals(1, allByUserId.size());
//    }
//
//    @Test
//    void findAllByUserIdTest_2() {
//        entityManager.persistAndFlush(bookEntity1);
//        entityManager.persistAndFlush(bookEntity2);
//
//        List<BookEntity> allByUserId = repository.findAllByUserId(USER_ID);
//        assertNotNull(allByUserId);
//        assertEquals(2, allByUserId.size());
//    }
//
//    @Test
//    void saveTest(){
////        repository.save(bookEntity1); // todo
//        List<BookEntity> allByUserId = repository.findAllByUserId(USER_ID);
//        assertNotNull(allByUserId);
//        assertEquals(1, allByUserId.size());
//    }
//
//    @Test
//    void deleteTest(){
//        BookEntity bookEntity = bookEntity1;
//        bookEntity.setId(1);
//        entityManager.persistAndFlush(bookEntity);
//
////        bookRepository.deleteById(bookEntity); // todo
//        List<BookEntity> allByUserId = repository.findAllByUserId(USER_ID);
//        assertNotNull(allByUserId);
//        assertEquals(1, allByUserId.size());
//    }
//
//}