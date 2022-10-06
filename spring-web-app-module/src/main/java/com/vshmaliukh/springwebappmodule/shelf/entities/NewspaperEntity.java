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
@Table(name= NEWSPAPER_TABLE)
public class NewspaperEntity {

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

}
