package com.vshmaliukh.spring_shelf_core.shelf.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import static com.vshmaliukh.spring_shelf_core.shelf.ConstantsForDataBase.*;

@Getter
@Setter
@Entity
@Table(name= MAGAZINE_TABLE,
        uniqueConstraints = {@UniqueConstraint(columnNames = {
                USER_ID_COLUMN, NAME_COLUMN, PAGES_COLUMN, BORROWED_COLUMN})})
public class MagazineEntity extends ItemEntity {

    @Override
    public String toString() {
        return "MagazineEntity{" +
                "name='" + name + '\'' +
                ", pages=" + pages +
                ", isBorrowed=" + isBorrowed +
                '}';
    }
}
