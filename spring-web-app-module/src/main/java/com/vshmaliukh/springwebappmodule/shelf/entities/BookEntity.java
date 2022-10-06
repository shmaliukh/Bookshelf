package com.vshmaliukh.springwebappmodule.shelf.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

import static com.vshmaliukh.springwebappmodule.shelf.ConstantsForDataBase.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = BOOK_TABLE)
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity extends ItemEntity {

    @Column(name = AUTHOR_COLUMN, nullable = false)
    String author;

    @Column(name = DATE_COLUMN, nullable = false)
    Date dateOfIssue;

}
