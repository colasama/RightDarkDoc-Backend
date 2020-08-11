package com.rightdarkdoc.service.Impl;

import com.rightdarkdoc.dao.UserDao;
import com.rightdarkdoc.entity.User;
import com.rightdarkdoc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
//@Transactional      //控制事务
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    /**
     * 注册新用户的功能
     *
     * @param user
     */
    @Override
    public void registerNewUser(User user) {
        userDao.registerNewUser(user);
    }

    /**
     * 根据用户名和密码登录
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        return userDao.findUserByUsernameAndPassword(username, password);
    }
}