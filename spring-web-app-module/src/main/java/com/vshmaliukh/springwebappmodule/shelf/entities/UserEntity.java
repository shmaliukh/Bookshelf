package com.vshmaliukh.springwebappmodule.shelf.entities;

import com.vshmaliukh.springwebappmodule.shelf.ConstantsForDataBase;
import lombok.Data;

import javax.persistence.*;

import static com.vshmaliukh.springwebappmodule.shelf.ConstantsForDataBase.USER_NAME_COLUMN;
import static com.vshmaliukh.springwebappmodule.shelf.ConstantsForDataBase.USER_TABLE;

@Data
@Entity
@Table(name = USER_TABLE)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ConstantsForDataBase.ID_COLUMN, nullable = false)
    private Long id;

    @Column(name = USER_NAME_COLUMN, nullable = false, unique = true)
    private String userName;

}
