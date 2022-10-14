package com.gebilaoyi.j2ee.lesson9.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gebilaoyi.j2ee.lesson9.entity.UserEntity;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class UserUtil {
    static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 通过request.getCookies获取用户信息
     * @param request
     * @return
     */
    public static UserEntity getUser(HttpServletRequest request) {
        Cookie[] cookieArray = request.getCookies();
        UserEntity userEntity = null;

        for(Cookie cookie : cookieArray) {
            if(cookie.getName().equals("user")) {
                try {
                    String userString = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    userEntity = objectMapper.readValue(userString, UserEntity.class) ;
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return userEntity;
    }
}
