package com.vshmaliukh.springwebappmodule.shelf.mysql.services;

import com.vshmaliukh.springwebappmodule.shelf.ActionsWithItemEntities;
import com.vshmaliukh.springwebappmodule.shelf.DbEntityService;
import com.vshmaliukh.springwebappmodule.shelf.entities.BookEntity;
import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.vshmaliukh.shelf.shelf_handler.User;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class MysqlBookService extends DbEntityService implements ActionsWithItemEntities<BookEntity> {

    static String serviceName = "MysqlBookEntityService";

    private final MysqlBookRepository bookRepository;

    public MysqlBookService(MysqlBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void addBook(BookEntity book) {
        try {
            if (!bookRepository.existsByName(book.getName())) {
                book.setId(bookRepository.findMaxId() == null ? 1 : bookRepository.findMaxId() + 1);
                bookRepository.save(book);
                logInfo(book, serviceName, "book record created successfully");
            } else {
                logInfo(book, serviceName, "book already exists in the database");
            }
        } catch (Exception e) {
            logError(serviceName, e);
        }
    }

    public List<BookEntity> readBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public void updateBook(BookEntity book) {
        if (bookRepository.existsByName(book.getName())) {
            try {
                List<BookEntity> books = bookRepository.findByName(book.getName());
                for (BookEntity bookEntity : books) {
                    BookEntity bookToUpdate = bookRepository.findById(bookEntity.getId()).get();
                    bookToUpdate.setName(book.getName());
                    bookToUpdate.setName(book.getName());
                    bookRepository.save(bookToUpdate);
                }
                logInfo(book, serviceName, "book record updated");
            } catch (Exception e) {
                logError(serviceName, e);
            }
        } else {
            logInfo(book, serviceName, "book does not exists in the database");
        }
    }

    @Transactional
    public void deleteBook(BookEntity book) {
        if (bookRepository.existsByName(book.getName())) {
            try {
                List<BookEntity> books = bookRepository.findByName(book.getName());
                bookRepository.deleteAll(books);
                logInfo(book, serviceName, "book record deleted successfully");
            } catch (Exception e) {
                logError(serviceName, e);
            }
        } else {
            logInfo(book, serviceName, "book does not exist");
        }
    }


    @Override
    public void saveItemList(List<BookEntity> listToSave) {

    }

    @Override
    public void saveItemToDB(BookEntity item) {

    }

    @Override
    public void insertUser(String userName) {

    }

    @Override
    public boolean isUserExist(String userName) {
        return false;
    }

    @Override
    public void createUser() {

    }

    @Override
    public void readUserId(User user) {

    }
}
