package com.vshmaliukh.springbootshelfcore.shelf.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.vshmaliukh.springbootshelfcore.shelf.ConstantsForDataBase.*;

@Getter
@Setter
@MappedSuperclass
public class ItemEntity {

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
}