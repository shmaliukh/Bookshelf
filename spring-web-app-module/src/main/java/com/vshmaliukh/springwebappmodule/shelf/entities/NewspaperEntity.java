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
@Table(name= NEWSPAPER_TABLE)
public class NewspaperEntity extends ItemEntity{

}
