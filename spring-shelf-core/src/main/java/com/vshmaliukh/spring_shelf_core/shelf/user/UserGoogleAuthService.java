package com.vshmaliukh.spring_shelf_core.shelf.user;

import com.vshmaliukh.spring_shelf_core.shelf.entities.LogInProvider;
import com.vshmaliukh.spring_shelf_core.shelf.entities.UserEntity;
import com.vshmaliukh.spring_shelf_core.shelf.mysql.repositories.MysqlUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGoogleAuthService {

    private final MysqlUserRepository userRepository;

    @Autowired
    public UserGoogleAuthService(MysqlUserRepository userRepository) {
        // todo refactor to register user for current type of work
        this.userRepository = userRepository;
    }

    public void processOAuthPostLogin(String userName) {
        UserEntity existUser = userRepository.findByUserName(userName);

        if (existUser == null) {
            UserEntity newUser = new UserEntity();
            newUser.setUserName(userName);
            newUser.setLogInProvider(LogInProvider.GOOGLE);

            userRepository.save(newUser);
        }

    }


}
