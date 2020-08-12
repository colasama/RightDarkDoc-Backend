package com.rightdarkdoc.controller;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.rightdarkdoc.entity.User;
import com.rightdarkdoc.service.UserService;
import com.rightdarkdoc.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 通过token获取用户id，再通过数据库查询用户相关信息，返回前端
     * note :  需要 token
     * @param request
     * @return
     */
    @GetMapping("info")
    public User getUserInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        DecodedJWT decoder = JWTUtils.verify(token);
        String userTemp = decoder.getClaim("userid").asString();
        Integer userid = Integer.valueOf(userTemp);
        System.out.println(userid);
        User user = userService.selectUserByUserId(userid);
        return user;
    }


    /**
     * 通过token获取用户id，并更新信息，返回前端相应信息
     * note ：需要 token,  且不能更改密码，
     * 密码修改通过另一个接口进行
     * @param user 封装更新信息的user对象
     * @return message，包含信息
     */
    @PutMapping("mod_info")
    public Map<String,Object> updateUserInfo(@RequestBody User user,HttpServletRequest request){
        HashMap<String,Object> m = new HashMap<>();
        try {
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);
            user.setUserid(userid);
            userService.updateUser(user);
            m.put("success",true);
            m.put("message","modify user info successfully");
        } catch (Exception ex){
            m.put("success",false);
            m.put("message","token error");
        }
        return m;
    }


    /**
     * 更改用户的密码,需要提供旧密码以及新密码
     * @param m 封装json格式的map，需要两个参数：
     *          1.key="oldpassword":value=String类型
     *          2.key="newpassword":value=String类型
     * @param request 需要其中的token来取出用户名信息
     * @return
     */
    @PutMapping("mod_password")
    public Map<String,Object> updateUserPassword(@RequestBody Map<String,Object> m,HttpServletRequest request){

        HashMap<String, Object> remap = new HashMap<>();
        try {
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);
            String oldPassword = m.get("oldpassword").toString();
            String newPassword = m.get("newpassword").toString();
            User user = userService.selectUserByUserId(userid);
            if(user == null){
                remap.put("success",false);
                remap.put("message","user does not exist");
            }
            else if(!user.getPassword().contentEquals(oldPassword)){
                remap.put("success",false);
                remap.put("message","the old password is wrong");
            }else{
                userService.updateUserPassword(userid,newPassword);
                remap.put("success",true);
                remap.put("message","modify user password successfully");
            }
        } catch (Exception ex){
            remap.put("success",false);
            remap.put("message","token error");
        }
        return remap;
    }




}
