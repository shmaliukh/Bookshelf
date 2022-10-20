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
@Table(name= ConstantsForDataBase.MAGAZINE_TABLE)
public class MagazineEntity extends ItemEntity {

}
