package com.rightdarkdoc.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserFavDocServiceTest {


    @Autowired
    private UserFavDocService userFavDocService;

    @Test
    public void addUserFavDocTest(){
        userFavDocService.addUserFavDoc(1,2);
    }


    @Test
    public void deleteFavDocTest(){
        userFavDocService.deleteUserFavDoc(3,100);
    }

    @Test
    public void selectFavDocByUseridTest(){
        System.out.println(userFavDocService.selectDocByUserid(3));
    }

    @Test
    public void selectAllTest(){
        System.out.println(userFavDocService.selectDocByUidAndDid(1,2));
        System.out.println(userFavDocService.selectDocByUidAndDid(1,4));
    }

}
