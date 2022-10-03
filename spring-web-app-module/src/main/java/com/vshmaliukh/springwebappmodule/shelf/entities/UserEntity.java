package com.vshmaliukh.springwebappmodule.shelf.entities;

import com.vshmaliukh.springwebappmodule.shelf.ConstantsForDataBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.vshmaliukh.springwebappmodule.shelf.ConstantsForDataBase.*;

@Getter
@Setter
@Entity
@Table(name = USER_TABLE)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = ConstantsForDataBase.ID_COLUMN, nullable = false)
    private Long id;

    @Column(name = USER_NAME_COLUMN, nullable = false, unique = true)
    private String userName;

}
