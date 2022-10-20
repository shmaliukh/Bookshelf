package com.vshmaliukh.springbootshelfcore.shelf.entities;

import com.vshmaliukh.springbootshelfcore.shelf.ConstantsForDataBase;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name= ConstantsForDataBase.NEWSPAPER_TABLE)
public class NewspaperEntity extends ItemEntity{

}
