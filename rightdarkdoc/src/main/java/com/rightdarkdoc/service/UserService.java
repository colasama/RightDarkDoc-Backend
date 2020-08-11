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

}
