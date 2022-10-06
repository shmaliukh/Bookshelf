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
@Table(name= COMICS_TABLE)
public class ComicsEntity extends ItemEntity{

    @Column(name = PUBLISHER_COLUMN, nullable = false)
    String publisher;

}
