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


    /**
     * 更新用户信息的功能接口
     * note : 不提供密码的修改
     * @param user 封装了需要更新的用户实体
     */
    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }


    /**
     * 根据用户id查找用户
     * @param userid  用户的id
     */
    @Override
    public User selectUserByUserId(Integer userid) {
        return userDao.selectUserByUserId(userid);
    }


    /**
     * 更改用户密码
     * @param userid   用户id
     * @param password   新密码
     */
    @Override
    public void updateUserPassword(Integer userid, String password) {
        userDao.updateUserPassword(userid,password);
    }


}
