package com.gebilaoyi.j2ee.lesson9.api;

import com.gebilaoyi.j2ee.lesson9.entity.UserEntity;
import com.gebilaoyi.j2ee.lesson9.model.UserLoginModel;
import com.gebilaoyi.j2ee.lesson9.servcie.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserApi {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public UserEntity login(@RequestBody UserLoginModel userLoginModel) {
        return userService.isLogon(userLoginModel.getUsername(), userLoginModel.getPassword()) ;
    }
}
