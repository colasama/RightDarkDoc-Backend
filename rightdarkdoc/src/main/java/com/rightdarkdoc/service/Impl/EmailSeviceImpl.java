package com.rightdarkdoc.service.Impl;

import com.rightdarkdoc.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;


@Service
public class EmailSeviceImpl implements EmailService {



    @Autowired
    JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;



    @Override
    @Async
    public void sendVerifyCode(String verifyCode,String userEmail) throws Exception {
        System.out.println("正在发送邮件");
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);

        helper.setSubject("右墨文档-邮箱验证");
        helper.setText(("您的注册验证码为：<b style='color:green'>"+verifyCode+"</b>"),true);

        helper.setTo(userEmail);
        helper.setFrom(from);

        mailSender.send(mimeMessage);
    }


}
