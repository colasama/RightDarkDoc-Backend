package com.rightdarkdoc.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserViewDocServiceTest {

    @Autowired
    private UserViewDocService userViewDocService;



    @Test
    public void addTest(){
        userViewDocService.addUserViewDoc(3,4);
    }

    @Test
    public void delTest(){
        userViewDocService.delUserViewDoc(3,4);
    }


    @Test
    public void selectByUseridTest(){
        System.out.println(userViewDocService.selectUserViewDoc(3));
    }


    @Test
    public void selectByAllTest(){
        System.out.println(userViewDocService.selectUserViewDocByUidAndDid(3,4));
        System.out.println(userViewDocService.selectUserViewDocByUidAndDid(3,5));
        System.out.println(userViewDocService.selectUserViewDocByUidAndDid(3,6));
    }


}
