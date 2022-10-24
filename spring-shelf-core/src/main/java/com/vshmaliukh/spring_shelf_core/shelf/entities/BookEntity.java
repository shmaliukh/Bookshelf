package com.vshmaliukh.spring_shelf_core.shelf.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

import static com.vshmaliukh.spring_shelf_core.shelf.ConstantsForDataBase.*;

@Getter
@Setter
@Entity
@Table(name = BOOK_TABLE,
        uniqueConstraints = {@UniqueConstraint(columnNames = {
                USER_ID_COLUMN, NAME_COLUMN, PAGES_COLUMN, BORROWED_COLUMN, AUTHOR_COLUMN, DATE_COLUMN})})
public class BookEntity extends ItemEntity {

    @Column(name = AUTHOR_COLUMN, nullable = false)
    String author;

    @Column(name = DATE_COLUMN, nullable = false)
    Date dateOfIssue;

    @Override
    public String toString() {
        return "BookEntity{" +
                "name='" + name + '\'' +
                ", pages=" + pages +
                ", isBorrowed=" + isBorrowed +
                ", author='" + author + '\'' +
                ", dateOfIssue=" + dateOfIssue +
                '}';
    }
}