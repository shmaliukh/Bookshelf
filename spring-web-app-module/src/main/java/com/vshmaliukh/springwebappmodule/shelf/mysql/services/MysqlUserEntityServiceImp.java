package com.vshmaliukh.springwebappmodule.shelf.mysql.services;

import com.vshmaliukh.springwebappmodule.shelf.entities.UserEntity;
import com.vshmaliukh.springwebappmodule.shelf.mysql.repositories.MysqlUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MysqlUserEntityServiceImp {

    @Autowired
    MysqlUserRepository userRepository;

//    public UserEntityServiceImp(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

//    public UserEntityServiceImp(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    public Integer readUserIdByName(String userName){
        UserEntity byUserName = userRepository.findByUserName(userName);
        return byUserName.getId();
    }


}
