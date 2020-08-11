package com.rightdarkdoc.controller;


import com.rightdarkdoc.entity.User;
import com.rightdarkdoc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {


    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public String loginControl(@RequestBody User user){
        if(user.getUserName().contentEquals("wzk")&&user.getPassWord().contentEquals("123456")){
            return "success";
        }else{
            return "error";
        }
    }

    @PostMapping("/register")
    @ResponseBody
    public String registerControl(@RequestBody User user){
        userService.addUser(user);
        return "success";
    }




}
