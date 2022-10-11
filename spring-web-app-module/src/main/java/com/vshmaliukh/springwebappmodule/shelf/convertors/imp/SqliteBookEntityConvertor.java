package com.vshmaliukh.springwebappmodule.shelf.convertors.imp;

import com.vshmaliukh.springwebappmodule.shelf.entities.SqliteBookEntity;
import com.vshmaliukh.springwebappmodule.shelf.convertors.ItemEntityConvertor;
import org.vshmaliukh.ConfigFile;
import org.vshmaliukh.shelf.literature_items.book_item.Book;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SqliteBookEntityConvertor implements ItemEntityConvertor<Book, SqliteBookEntity> {

    @Override
    public Book getConvertedItemFromEntity(SqliteBookEntity entityToConvert) {
        try {
            return new Book(entityToConvert.getId(),
                    entityToConvert.getName(),
                    entityToConvert.getPages(),
                    entityToConvert.isBorrowed(),
                    entityToConvert.getAuthor(),
                    new SimpleDateFormat(ConfigFile.DATE_FORMAT_STR).parse(entityToConvert.getDateOfIssue()));
        } catch (ParseException e) {
            throw new RuntimeException(e); // todo
        }
    }

    @Override
    public SqliteBookEntity getConvertedEntityFromItem(Book itemToConvert, Integer userId) {
        SqliteBookEntity bookEntity = new SqliteBookEntity();
        bookEntity.setId(itemToConvert.getId());
        bookEntity.setUserId(userId);
        bookEntity.setName(itemToConvert.getName());
        bookEntity.setPages(itemToConvert.getPagesNumber());
        bookEntity.setBorrowed(itemToConvert.isBorrowed());
        bookEntity.setAuthor(itemToConvert.getAuthor());
        bookEntity.setDateOfIssue(new SimpleDateFormat(ConfigFile.DATE_FORMAT_STR).format(itemToConvert.getIssuanceDate()));
        return bookEntity;
    }
}
