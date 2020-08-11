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

}
