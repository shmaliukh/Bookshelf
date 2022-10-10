package com.vshmaliukh.springwebappmodule.shelf.convertors;

import com.vshmaliukh.springwebappmodule.shelf.entities.BookEntity;
import org.vshmaliukh.shelf.literature_items.book_item.Book;

public class BookEntityConvertor implements ItemEntityConvertor<Book, BookEntity> {

    @Override
    public Book getConvertedItemFromEntity(BookEntity entityToConvert) {
        return new Book(entityToConvert.getId(),
                entityToConvert.getName(),
                entityToConvert.getPages(),
                entityToConvert.isBorrowed(),
                entityToConvert.getAuthor(),
                entityToConvert.getDateOfIssue());
    }

    @Override
    public BookEntity getConvertedEntityFromItem(Book itemToConvert, Integer userId) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(itemToConvert.getId());
        bookEntity.setUserId(userId);
        bookEntity.setName(itemToConvert.getName());
        bookEntity.setPages(itemToConvert.getPagesNumber());
        bookEntity.setBorrowed(itemToConvert.isBorrowed());
        bookEntity.setAuthor(itemToConvert.getAuthor());
        bookEntity.setDateOfIssue(itemToConvert.getIssuanceDate());
        return bookEntity;
    }
}