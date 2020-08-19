package com.rightdarkdoc.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void sendEmailTest() throws Exception{
        emailService.sendVerifyCode("123456","2846175443@qq.com");
    }

}
