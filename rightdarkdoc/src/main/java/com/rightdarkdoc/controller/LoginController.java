package com.rightdarkdoc.controller;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.rightdarkdoc.entity.User;
import com.rightdarkdoc.service.EmailService;
import com.rightdarkdoc.service.UserService;
import com.rightdarkdoc.utils.JWTUtils;
import com.rightdarkdoc.utils.PayloadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.Keymap;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController                 //返回json数据
public class LoginController {

    //存储验证码
    //String : 邮箱
    //String ：验证码
    public static Map<String, String> codeMap = new HashMap<>();

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    /**
     * 注册用户
     * @param user
     * @return
     */
    @PostMapping("register")
    public Map<String, Object> registerNewUser(@RequestBody Map<String, String> remap) {
        System.out.println("接收到一个注册请求");
        String username = remap.get("username");
        String password = remap.get("password");
        String phone = remap.get("phone");
        String email = remap.get("email");
        String code = remap.get("code");
        Map<String, Object> map = new HashMap<>();
        try {
            User user = new User(username, password, phone, email);
            User user1 = userService.selectUserByUsername(user.getUsername());
            User user2 = userService.selectUserByEmail(user.getEmail());
            if (user1 != null) {
                map.put("success", false);
                map.put("message", "用户名已注册！");
            } else if (user2 != null) {
                map.put("success", false);
                map.put("message", "该邮箱已注册");
            }
            else {
                String myCode = codeMap.get(email);
                if (myCode.equals(code)) {
                    userService.registerNewUser(user);
                    map.put("success", true);
                    map.put("message", "用户注册成功！");
                    codeMap.remove(email);                          //从本地存储中删除刚刚的验证码
                }
                else {
                    map.put("success", false);
                    map.put("message", "注册码错误！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "用户注册失败！");
        }
        return map;
    }

    /**
     * 获取验证码
     * @return
     */
    @PostMapping("/code")
    public Map<String, Object> registerCode(@RequestBody Map<String, String> remap) {
        System.out.println("接收到一个注册码的请求");
        String username = remap.get("username");
        String email = remap.get("email");
        Map<String, Object> map = new HashMap<>();
        try {
            User user1 = userService.selectUserByUsername(username);
            User user2 = userService.selectUserByEmail(email);
            if (user1 != null) {
                map.put("success", false);
                map.put("message", "用户名已注册！");
            } else if (user2 != null) {
                map.put("success", false);
                map.put("message", "该邮箱已注册");
            }
            else {
                String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
                emailService.sendVerifyCode(checkCode, email);
                if (codeMap.containsKey(email)) {
                    codeMap.replace(email, checkCode);
                }
                else {
                    codeMap.put(email, checkCode);
                }
                map.put("success", true);
                map.put("message", "验证码发送成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "验证码发送失败！");
        }
        return map;
    }

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("login")
    public Map<String, Object> findUserByUsernameAndPassword(@RequestBody User user) {
        System.out.println("接收到一个登录请求");

        //获得传入的username和password
        String username = user.getUsername();
        String password = user.getPassword();

        Map<String, Object> map = new HashMap<>();

        //数据库中查询
        try {
            User user1 = userService.findUserByUsernameAndPassword(username, password);
            if(user1 == null) {
                //登录失败
                map.put("success", false);
                map.put("message", "用户登录失败，用户名或者密码错误！");
            } else {
                //登录成功
                map.put("success", true);
                map.put("message", "用户登录成功！");

                //生成一个token并返回
                Map<String, String> payload = PayloadUtils.payloadCreator(user1);       //把查询到的user1的内容注入payload
                String token = JWTUtils.getToken(payload);                              //生成token
                map.put("token", token);                                                //放入返回json中
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "用户登录失败，用户名或者密码错误！");
        }
        return map;
    }

    /**
     * 测试连接
     * @return
     */
    @PostMapping("test")
    public Map<String, Object> testGet() {
        System.out.println("接收到一个测试请求");
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", "这是一个测试接口");
        return map;
    }
}
