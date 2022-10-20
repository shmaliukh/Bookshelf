package com.vshmaliukh.springbootshelfcore.shelf.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

import static com.vshmaliukh.springbootshelfcore.shelf.ConstantsForDataBase.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = BOOK_TABLE,
        uniqueConstraints = {@UniqueConstraint(columnNames = {
                USER_ID_COLUMN, NAME_COLUMN, PAGES_COLUMN, BORROWED_COLUMN, AUTHOR_COLUMN, DATE_COLUMN})})
public class BookEntity extends ItemEntity {

    @Column(name = AUTHOR_COLUMN, nullable = false)
    String author;

    @Column(name = DATE_COLUMN, nullable = false)
    Date dateOfIssue;

}