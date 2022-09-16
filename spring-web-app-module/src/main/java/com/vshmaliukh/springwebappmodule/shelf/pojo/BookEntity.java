package com.vshmaliukh.springwebappmodule.shelf.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static org.vshmaliukh.shelf.literature_items.ItemTitles.*;

@Getter
@Setter
@Entity
@Table(name="Books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = NAME, nullable = false)
    String name;

    @Column(name = PAGES, nullable = false)
    int pages;

    @Column(name = BORROWED, nullable = false)
    boolean isBorrowed;

    @Column(name = AUTHOR, nullable = false)
    String author;

    @Column(name = DATE, nullable = false)
    String dateOfIssue;

}
