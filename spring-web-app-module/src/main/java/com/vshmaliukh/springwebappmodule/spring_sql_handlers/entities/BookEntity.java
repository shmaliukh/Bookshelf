package com.vshmaliukh.springwebappmodule.spring_sql_handlers.entities;

import com.vshmaliukh.springwebappmodule.spring_sql_handlers.ConstantsForDataBase;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = ConstantsForDataBase.BOOK_TABLE)
public class BookEntity extends ItemEntity {

    @Column(name = ConstantsForDataBase.AUTHOR_COLUMN, nullable = false)
    String author;

    @Column(name = ConstantsForDataBase.DATE_COLUMN, nullable = false)
    Date dateOfIssue;

}
