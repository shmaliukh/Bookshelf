package com.vshmaliukh.springwebappmodule.shelf.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static com.vshmaliukh.springwebappmodule.shelf.ConstantsForDataBase.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = BOOK_TABLE)
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN, nullable = false)
    private Integer id;

    @Column(name = USER_ID_COLUMN, nullable = false)
    private Integer userId;

    @Column(name = NAME_COLUMN, nullable = false)
    String name;

    @Column(name = PAGES_COLUMN, nullable = false)
    int pages;

    @Column(name = BORROWED_COLUMN, nullable = false)
    boolean isBorrowed;

    @Column(name = AUTHOR_COLUMN, nullable = false)
    String author;

    @Column(name = DATE_COLUMN, nullable = false)
    String dateOfIssue;

}
