package com.vshmaliukh.springwebappmodule.shelf.mysql.services;

import com.vshmaliukh.springwebappmodule.shelf.entities.BookEntity;
import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class MysqlBookService {

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
                logInfo(book, "book record created successfully");
            } else {
                logInfo(book, "book already exists in the database");
            }
        } catch (Exception e) {
            logError(e);
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
                logInfo(book, "book record updated");
            } catch (Exception e) {
                logError(e);
            }
        } else {
            logInfo(book, "book does not exists in the database");
        }
    }

    @Transactional
    public void deleteBook(BookEntity book) {
        if (bookRepository.existsByName(book.getName())) {
            try {
                List<BookEntity> books = bookRepository.findByName(book.getName());
                bookRepository.deleteAll(books);
                logInfo(book, "book record deleted successfully");
            } catch (Exception e) {
                logError(e);
            }

        } else {
            logInfo(book, "book does not exist");
        }
    }

    private static void logInfo(String message) {
        log.info("[MysqlBookService] info: " + message);
    }

    private static void logInfo(BookEntity bookEntity, String message) {
        logInfo("'" + bookEntity + "' " + message);
    }

    private static void logError(Exception e) {
        log.error("[MysqlBookService] error: " + e.getMessage(), e);
    }
}
