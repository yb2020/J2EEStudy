package com.gebilaoyi.j2ee.lesson9.servcie;

import com.gebilaoyi.j2ee.lesson9.dao.UserDao;
import com.gebilaoyi.j2ee.lesson9.entity.UserEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserDao userDao;

    public UserEntity isLogon(String username, String password) {
        UserEntity userEntity = userDao.findByUsernameAndPassword(username, password);
        if(userEntity == null) {
            return null;
        }
        userEntity.setPassword("");
        return userEntity;
    }
}
