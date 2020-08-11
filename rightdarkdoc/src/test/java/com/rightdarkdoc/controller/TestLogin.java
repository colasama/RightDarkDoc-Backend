package com.rightdarkdoc.controller;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TestLogin {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date birthday = simpleDateFormat.parse("2000-06-16");
        System.out.println(birthday);
        String birthdayString = simpleDateFormat.format(birthday);
        System.out.println(birthdayString);
    }

}
