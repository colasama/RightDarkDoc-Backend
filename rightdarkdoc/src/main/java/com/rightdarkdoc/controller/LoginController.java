package com.rightdarkdoc.controller;


import com.rightdarkdoc.entity.User;
import com.rightdarkdoc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.Keymap;
import java.util.HashMap;
import java.util.Map;

@RestController                 //返回json数据
@CrossOrigin                    //实现跨域请求
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public Map<String, Object> registerNewUser(@RequestBody User user) {
        System.out.println("接收到一个请求");
        Map<String, Object> map = new HashMap<>();
        try {
            userService.registerNewUser(user);
            map.put("success", true);
            map.put("message", "用户注册成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "用户保存失败！");
        }
        return map;
    }

    @GetMapping("test")
    public Map<String, Object> testGet() {
        System.out.println("接收到一个请求");
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", "用户注册成功！");
        return map;
    }


//    @PostMapping("/login")
//    @ResponseBody
//    public String loginControl(@RequestBody User user){
//        if(user.getUserName().contentEquals("wzk")&&user.getPassWord().contentEquals("123456")){
//            return "success";
//        }else{
//            return "error";
//        }
//    }
//
//    @PostMapping("/register")
//    @ResponseBody
//    public String registerControl(@RequestBody User user){
//        userService.addUser(user);
//        return "success";
//    }

}
