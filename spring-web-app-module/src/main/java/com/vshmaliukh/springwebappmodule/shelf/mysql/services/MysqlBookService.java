package com.vshmaliukh.springwebappmodule.shelf.mysql.services;

import com.vshmaliukh.springwebappmodule.shelf.ActionsWithItem;
import com.vshmaliukh.springwebappmodule.shelf.DbEntityService;
import com.vshmaliukh.springwebappmodule.shelf.ItemEntityConvertorsProvider;
import com.vshmaliukh.springwebappmodule.shelf.entities.BookEntity;
import com.vshmaliukh.springwebappmodule.shelf.convertors.ItemEntityConvertor;
import com.vshmaliukh.springwebappmodule.shelf.repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.vshmaliukh.shelf.literature_items.book_item.Book;
import org.vshmaliukh.shelf.shelf_handler.User;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class MysqlBookService<T> extends DbEntityService implements ActionsWithItem<Book> {

    static String serviceName = "MysqlBookEntityService";

    private final BookRepository bookRepository;


    public MysqlBookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void addBook(BookEntity book) {
        try {
            if (!(bookRepository.existsByName(book.getName()))) {
                book.setId(bookRepository.findMaxId() == null ? 1 : bookRepository.findMaxId() + 1);
                bookRepository.save(book);
                logInfo(book, serviceName, "entity record created successfully");
            } else {
                logInfo(book, serviceName, "entity already exists in the database");
            }
        } catch (Exception e) {
            logError(serviceName, e);
        }
    }

    public List<BookEntity> readItemEntityList() {
        return bookRepository.findAllByUserId(readUserIdByName(getUserName()));
    }

    @Transactional
    public void updateBook(BookEntity book) {
        String bookName = book.getName();
        if (getBookRepository().existsByName(bookName)) {
            try {
                List<BookEntity> books = bookRepository.findByName(bookName);
                for (BookEntity bookEntity : books) {
                    BookEntity bookToUpdate = bookRepository.findById(bookEntity.getId()).get();
                    bookToUpdate.setName(bookName);
                    bookToUpdate.setName(bookName);
                    bookRepository.save(bookToUpdate);
                }
                logInfo(book, serviceName, "entity record updated");
            } catch (Exception e) {
                logError(serviceName, e);
            }
        } else {
            logInfo(book, serviceName, "entity does not exists in the database");
        }
    }

    private BookRepository getBookRepository() {
        return bookRepository;
    }

    @Transactional
    public void deleteBook(BookEntity entity) {
        String entityName = entity.getName();
        if (bookRepository.existsByName(entityName)) {
            try {
                List<BookEntity> entityList = bookRepository.findByName(entityName);
                bookRepository.deleteAll(entityList);
                logInfo(entity, serviceName, "entity record deleted successfully");
            } catch (Exception e) {
                logError(serviceName, e);
            }
        } else {
            logInfo(entity, serviceName, "entity does not exist");
        }
    }

    @Override
    public void saveItemList(List<Book> listToSave) {
        listToSave.forEach(this::saveItemToDB);

    }

    @Override
    public void saveItemToDB(Book item) {
        ItemEntityConvertor convertorByItemClass = ItemEntityConvertorsProvider.getConvertorByItemClass(item.getClass());
        BookEntity entityFromItem = (BookEntity) convertorByItemClass.getConvertedEntityFromItem(item, readUserIdByName(getUserName())); // FIXME fix cast
        addBook(entityFromItem);
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
