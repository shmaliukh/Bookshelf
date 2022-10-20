package com.vshmaliukh.springbootshelfcore.shelf.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import static com.vshmaliukh.springbootshelfcore.shelf.ConstantsForDataBase.*;

@Getter
@Setter
@ToString
@Entity
@Table(name= MAGAZINE_TABLE,
        uniqueConstraints = {@UniqueConstraint(columnNames = {
                USER_ID_COLUMN, NAME_COLUMN, PAGES_COLUMN, BORROWED_COLUMN})})
public class MagazineEntity extends ItemEntity {

}
