package com.rightdarkdoc.dao;

import com.rightdarkdoc.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserDao {

    /**
     * 注册新用户
     * @param user
     * @return
     */
      public void registerNewUser(User user);


    /**
     * 通过用户名和密码查找用户
     * @param username
     * @param password
     * @return 返回一个User对象
     */
      public User findUserByUsernameAndPassword(String username, String password);


    /**
     * 更新用户信息
     * @param user 一个类型为User的参数
     */
    public void updateUser(User user);


    /**
     * 重设用户密码
     */
    public void updateUserPassword(@Param("userid") Integer userid,@Param("password") String password);


    /**
     * 根据用户id查询用户信息
     * @param userid
     */
    public User selectUserByUserId(int userid);

    /**
     * 更具用户username查询用户信息
     */
    public User selectUserByUsername(String username);

}
