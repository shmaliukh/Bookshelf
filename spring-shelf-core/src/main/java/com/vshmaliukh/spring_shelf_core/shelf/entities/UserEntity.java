package com.vshmaliukh.spring_shelf_core.shelf.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static com.vshmaliukh.spring_shelf_core.shelf.ConstantsForDataBase.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = USER_TABLE,
        uniqueConstraints = {@UniqueConstraint(columnNames = {USER_NAME_COLUMN})})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN, nullable = false)
    private Integer id;

    @Column(name = USER_NAME_COLUMN, nullable = false)
    private String userName;

    @Column(name = USER_EMAIL_COLUMN)
    private String userEmail;

    @Enumerated(EnumType.STRING)
    private LogInProvider logInProvider;

}
