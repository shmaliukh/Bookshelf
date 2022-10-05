package com.vshmaliukh.springwebappmodule.shelf.entities;

import lombok.Data;

import javax.persistence.*;

import static com.vshmaliukh.springwebappmodule.shelf.ConstantsForDataBase.*;

@Data
@Entity
@Table(name= COMICS_TABLE)
public class ComicsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN, nullable = false)
    private Long id;

    @Column(name = USER_ID_COLUMN, nullable = false)
    private Long userId;

    @Column(name = NAME_COLUMN, nullable = false)
    String name;

    @Column(name = PAGES_COLUMN, nullable = false)
    int pages;

    @Column(name = BORROWED_COLUMN, nullable = false)
    boolean isBorrowed;

    @Column(name = PUBLISHER_COLUMN, nullable = false)
    String publisher;

}
