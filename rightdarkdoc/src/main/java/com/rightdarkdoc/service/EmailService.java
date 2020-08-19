package com.rightdarkdoc.service;


public interface EmailService {


    public void sendVerifyCode(String verifyCode,String userEmail) throws  Exception;

}
