package com.vshmaliukh.springwebappmodule.spring_sql_handlers.entities;

import com.vshmaliukh.springwebappmodule.spring_sql_handlers.ConstantsForDataBase;
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
