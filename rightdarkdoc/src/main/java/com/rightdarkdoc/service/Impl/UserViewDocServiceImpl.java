package com.rightdarkdoc.service.Impl;


import com.rightdarkdoc.dao.UserViewDocDao;
import com.rightdarkdoc.service.UserViewDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserViewDocServiceImpl implements UserViewDocService {


    @Autowired
    private UserViewDocDao userViewDocDao;


    @Override
    public void addUserViewDoc(Integer userid, Integer docid, Date viewtime) {
        userViewDocDao.addUserViewDoc(userid,docid,viewtime);
    }


    @Override
    public void updateViewtime(Integer userid, Integer docid, Date viewtime) {
        userViewDocDao.updateViewtime(userid,docid,viewtime);
    }

    @Override
    public void delUserViewDoc(Integer userid, Integer docid) {
        userViewDocDao.delUserViewDoc(userid,docid);
    }

    @Override
    public void delUserViewDocByDocid(Integer docid) {
        userViewDocDao.delUserViewDocByDocid(docid);
    }

    @Override
    public List<Integer> selectUserViewDoc(Integer userid) {
        return userViewDocDao.selectUserViewDoc(userid);
    }


    @Override
    public Integer selectUserViewDocByUidAndDid(Integer userid, Integer docid) {
        return userViewDocDao.selectUserViewDocByUidAndDid(userid,docid);
    }

}
