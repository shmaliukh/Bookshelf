package com.vshmaliukh.springwebappmodule.shelf.entities;

import com.vshmaliukh.springwebappmodule.shelf.ConstantsForDataBase;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name= ConstantsForDataBase.COMICS_TABLE)
public class ComicsEntity extends ItemEntity{

    @Column(name = ConstantsForDataBase.PUBLISHER_COLUMN, nullable = false)
    String publisher;

}
