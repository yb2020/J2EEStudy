package com.gebilaoyi.j2ee.lesson9.dao;

import com.gebilaoyi.j2ee.lesson9.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    UserEntity findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

}
