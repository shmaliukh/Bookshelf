package com.vshmaliukh.springwebappmodule.shelf.mysql.services;

import com.vshmaliukh.springwebappmodule.shelf.entities.UserEntity;
import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlUserRepository;
import com.vshmaliukh.springwebappmodule.shelf.repository_services.UserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class MysqlUserServiceImp implements UserService {

    MysqlUserRepository mysqlUserRepository;

    @Autowired
    public void setMysqlUserRepository(MysqlUserRepository mysqlUserRepository) {
        this.mysqlUserRepository = mysqlUserRepository;
    }

    @Override
    public void insertUser(String userName) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        mysqlUserRepository.save(userEntity);
    }

    @Override
    public Integer readUserIdByName(String userName) {
        System.out.println(mysqlUserRepository);
        UserEntity userEntity = mysqlUserRepository.findByUserName(userName);
        if(userEntity != null) {
            Integer id = userEntity.getId();
            return id;
        }
        return null;
    }

}
