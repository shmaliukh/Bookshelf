package com.vshmaliukh.springwebappmodule.shelf.entities;

import com.vshmaliukh.springwebappmodule.shelf.ConstantsForDataBase;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = ConstantsForDataBase.BOOK_TABLE)
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity extends ItemEntity {

    @Column(name = ConstantsForDataBase.AUTHOR_COLUMN, nullable = false)
    String author;

    @Column(name = ConstantsForDataBase.DATE_COLUMN, nullable = false)
    Date dateOfIssue;

}
