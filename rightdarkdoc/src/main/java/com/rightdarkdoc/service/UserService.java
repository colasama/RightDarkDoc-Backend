package com.rightdarkdoc.service;


import com.rightdarkdoc.dao.UserDao;
import com.rightdarkdoc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {


    @Autowired
    private UserDao userDao;

    public void addUser(User user){
        userDao.addUser(user);
    }
}
