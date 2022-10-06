package com.vshmaliukh.springwebappmodule.shelf;

import com.vshmaliukh.springwebappmodule.shelf.entities.UserEntity;
import com.vshmaliukh.springwebappmodule.shelf.repositories.UserRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@Slf4j
public abstract class DbEntityService {

    private String userName;

    @Autowired
    UserRepository userRepository;

    protected Integer readUserIdByName(String name){
        UserEntity userEntity = userRepository.findByUserName(name);
        if(userEntity == null){ // todo refactor
            userEntity = generateUserBy(name);
            saveUser(userEntity);
        }
        return userEntity.getId();
    }

    private UserEntity generateUserBy(String name) {
        UserEntity byUserName = new UserEntity();
        byUserName.setUserName(name);
        return byUserName;
    }

    private void saveUser(UserEntity userEntity){
        userRepository.save(userEntity);
    }


    protected static void logInfo(String serviceName, String message) {
        log.info("[" + serviceName + "Service] info: " + message);
    }

    protected static <T> void logInfo(T entity, String serviceName, String message) {
        logInfo(serviceName, "'" + entity + "' " + message);
    }

    protected static void logError(String serviceName, Exception e) {
        log.error("[" + serviceName + "] error: " + e.getMessage(), e);
    }

}
