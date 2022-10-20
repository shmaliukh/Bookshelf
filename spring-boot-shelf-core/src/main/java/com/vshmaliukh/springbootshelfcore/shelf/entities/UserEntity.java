package com.vshmaliukh.springbootshelfcore.shelf.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static com.vshmaliukh.springbootshelfcore.shelf.ConstantsForDataBase.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = USER_TABLE)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN, nullable = false)
    private Integer id;

    @Column(name = USER_NAME_COLUMN, nullable = false, unique = true)
    private String userName;

}
