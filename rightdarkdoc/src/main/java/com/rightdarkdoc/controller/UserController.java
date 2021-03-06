package com.rightdarkdoc.controller;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.rightdarkdoc.entity.User;
import com.rightdarkdoc.service.UserService;
import com.rightdarkdoc.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Keymap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("user")
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
     * 请求方法：Post
     * 请求Url：/user/fuzSearch
     * 功能：模糊匹配user信息并返回前端
     * @param map 参数：1. String: username
     * @return
     */
    @PostMapping("fuzSearch")
    public Map<String,Object> getUserByUserNameFuz(@RequestBody Map<String,Object> map){
        HashMap<String,Object> remap = new HashMap<>();
        try{
            String username = map.get("username").toString();
            List<User> users = userService.selectUserByUsernameFuz(username);
            int len = users.size();
            for(int i=0;i<len;i++){
                users.get(i).setPassword("");
            }
            remap.put("success",true);
            remap.put("message","successfully get users");
            remap.put("contents",users);
        }catch (Exception ex){
            remap.put("success",false);
            remap.put("message","fail to get users");
        }
        return remap;
    }


    /**
     * 请求方法：Put
     * 请求Url：/user/mod_info
     * 功能：通过token获取用户id，并更新信息，返回前端相应信息
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
            User usertemp  = userService.selectUserByUserId(userid);
            if(userTemp==null){
                m.put("success",false);
                m.put("message","user doesn't exists");
                return m;
            }
            if(user.getPhone()==null){
                user.setPhone(usertemp.getPhone());
            }
            if(user.getBirthday()==null){
                user.setBirthday(usertemp.getBirthday());
            }
            if(user.getDescription()==null){
                user.setDescription(usertemp.getDescription());
            }
            if(user.getAvatar()==null){
                user.setAvatar(usertemp.getAvatar());
            }
            if(user.getEmail()==null){
                user.setEmail(usertemp.getEmail());
            }
            user.setUserid(userid);
            userService.updateUser(user);
            m.put("success",true);
            m.put("message","modify user info successfully");
        } catch (Exception ex){
            m.put("success",false);
            m.put("message","failed to mod");
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
                return  remap;
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
            ex.printStackTrace();
            remap.put("success",false);
            remap.put("message","修改密码失败!");
        }
        return remap;
    }

    /**
     * 更改邮箱
     * @param remap
     * @return
     */
    @PostMapping("/updateEmail")
    public Map<String, Object> updateEmail(@RequestBody Map<String, String> remap,HttpServletRequest request) {
        System.out.println("接收到一个修改邮箱的请求");
        String email = remap.get("email");
        String code = remap.get("code");
        Map<String, Object> map = new HashMap<>();
        try {

            //获取用户的userid
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);
            String myCode = LoginController.codeMap.get(email);

            //验证码是否通过
            if (myCode.equals(code)) {
                User user2 = userService.selectUserByEmail(email);

                //验证是否重复邮箱并且不是之前可用邮箱
                if(user2!=null&&user2.getUserid()!=userid){
                    map.put("success", false);
                    map.put("message", "此邮箱已被使用");
                    LoginController.codeMap.remove(email);
                    return map;
                }
                User user = userService.selectUserByUserId(userid);
                user.setEmail(email);
                userService.updateUser(user);
                map.put("success", true);
                map.put("message", "修改成功！");
                LoginController.codeMap.remove(email);
            } else {
                map.put("success", false);
                map.put("message", "验证码错误！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "修改失败！");
        }
        return map;
    }



    /**
     * 根据用户id获取用户信息
     * @param useridString
     * @return
     */
    @GetMapping("/{useridString}")
    public Map<String, Object> getUserInfo(@PathVariable String useridString) {
        System.out.println("用户你好呀");
        Map<String, Object> map = new HashMap<>();
        Integer userid = Integer.valueOf(useridString);

        User user = userService.selectUserByUserId(userid);
        if (user != null) {
            user.setPassword("");
            map.put("user", user);
            map.put("success", true);
            map.put("message", "用户获取成功！");
        } else {
            map.put("success", false);
            map.put("message", "用户不存在或者已注销！");
        }
        return map;
    }
}
