package com.rightdarkdoc.service;

import com.rightdarkdoc.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;


//    @Test
//    public void addUserTest(){
//        User user = new User();
//        user.setUserId(1);
//        user.setUserName("wzk");
//        user.setPassWord("123456");
//        userService.addUser(user);
//    }

    @Test
    public void selectUserTest(){
        User user = userService.selectUserByUserId(1);
        System.out.println(user);
    }


    @Test
    public void updateUserTest(){
    }


    @Test
    public void updateUserPassword(){
        userService.updateUserPassword(1,"995111");
    }

    @Test
    public void selectUserByUsernameTest(){
        System.out.println(userService.selectUesrByUsername("wzk"));

    }
}