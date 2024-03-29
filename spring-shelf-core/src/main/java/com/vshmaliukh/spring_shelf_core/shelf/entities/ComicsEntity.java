package com.vshmaliukh.spring_shelf_core.shelf.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import static com.vshmaliukh.spring_shelf_core.shelf.ConstantsForDataBase.*;

@Getter
@Setter
@Entity
@Table(name= COMICS_TABLE,
        uniqueConstraints = {@UniqueConstraint(columnNames = {
                USER_ID_COLUMN, NAME_COLUMN, PAGES_COLUMN, BORROWED_COLUMN, PUBLISHER_COLUMN})})
public class ComicsEntity extends ItemEntity{

    @Column(name = PUBLISHER_COLUMN, nullable = false)
    String publisher;

    @Override
    public String toString() {
        return "ComicsEntity{" +
                "name='" + name + '\'' +
                ", pages=" + pages +
                ", isBorrowed=" + isBorrowed +
                ", publisher='" + publisher + '\'' +
                '}';
    }
}
