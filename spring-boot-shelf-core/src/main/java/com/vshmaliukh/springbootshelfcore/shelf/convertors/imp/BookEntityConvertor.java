package com.vshmaliukh.springbootshelfcore.shelf.convertors.imp;


import com.vshmaliukh.springbootshelfcore.shelf.convertors.ItemEntityConvertor;
import com.vshmaliukh.springbootshelfcore.shelf.entities.BookEntity;
import org.vshmaliukh.shelf.literature_items.book_item.Book;

import java.util.Date;

public class BookEntityConvertor implements ItemEntityConvertor<Book, BookEntity> {

    @Override
    public Book getConvertedItemFromEntity(BookEntity entityToConvert) {
        return new Book(entityToConvert.getId(),
                entityToConvert.getName(),
                entityToConvert.getPages(),
                entityToConvert.isBorrowed(),
                entityToConvert.getAuthor(),
                new Date(entityToConvert.getDateOfIssue().getTime())
        );
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
        bookEntity.setDateOfIssue(new java.sql.Date(itemToConvert.getIssuanceDate().getTime()));
        return bookEntity;
    }

}
