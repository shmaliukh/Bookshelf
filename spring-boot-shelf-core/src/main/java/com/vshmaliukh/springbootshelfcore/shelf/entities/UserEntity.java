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
@Table(name = ConstantsForDataBase.USER_TABLE)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ConstantsForDataBase.ID_COLUMN, nullable = false)
    private Integer id;

    @Column(name = ConstantsForDataBase.USER_NAME_COLUMN, nullable = false, unique = true)
    private String userName;

}
