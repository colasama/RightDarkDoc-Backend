package com.rightdarkdoc.service;


import com.rightdarkdoc.dao.UserDao;
import com.rightdarkdoc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface UserService {

    /**
     * 注册新用户的功能
     * @param user
     */
    public void registerNewUser(User user);

    /**
     * 根据用户名和密码登录
     * @param username
     * @param password
     * @return
     */
    public User findUserByUsernameAndPassword(String username, String password);

    /**
     * 更新用户的信息
     * note:不提供密码的修改功能
     * @param user 封装了需要更新的用户实体
     */
    public void updateUser(User user);

    /**
     * 根据用户id查找用户
     * @param userid 需要查找的用户id
     * @return  user对象
     */
    public User selectUserByUserId(Integer userid);


    /**
     * 根据用户名查找用户
     * @param username 需要查找的用户名
     * @return 对应的用户
     */
    public User selectUesrByUsername(String username);


    /**
     * 修改密码的功能
     * @param userid   用户id
     * @param password   新密码
     */
    public void updateUserPassword(Integer userid,String password);

}
