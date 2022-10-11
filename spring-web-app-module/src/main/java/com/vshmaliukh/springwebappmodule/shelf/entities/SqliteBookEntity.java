package com.vshmaliukh.springwebappmodule.shelf.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static com.vshmaliukh.springwebappmodule.shelf.ConstantsForDataBase.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = BOOK_TABLE)
@NoArgsConstructor
@AllArgsConstructor
public class SqliteBookEntity extends ItemEntity {

    @Column(name = AUTHOR_COLUMN, nullable = false)
    String author;

    @Column(name = DATE_COLUMN, nullable = false)
    String dateOfIssue;

}
