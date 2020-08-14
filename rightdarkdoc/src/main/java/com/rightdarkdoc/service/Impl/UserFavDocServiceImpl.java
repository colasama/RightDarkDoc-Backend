package com.rightdarkdoc.service.Impl;


import com.rightdarkdoc.dao.UserFavDocDao;
import com.rightdarkdoc.service.UserFavDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFavDocServiceImpl implements UserFavDocService {

    @Autowired
    private UserFavDocDao userFavDocDao;

    @Override
    public void addUserFavDoc(Integer userid,Integer docid){
        userFavDocDao.addUserFavDoc(userid,docid);
    }

    @Override
    public void deleteUserFavDoc(Integer userid, Integer docid) {
        userFavDocDao.deleteUserFavDoc(userid,docid);
    }


    @Override
    public void deleteUserFavDocByDocid(Integer docid) {
        userFavDocDao.deleteUserFavDocByDocid(docid);
    }

    @Override
    public List<Integer> selectDocByUserid(Integer userid) {
        return userFavDocDao.selectDocByUserid(userid);
    }

    @Override
    public Integer selectDocByUidAndDid(Integer userid, Integer docid) {
        return  userFavDocDao.selectDocByUidAndDid(userid,docid);
    }
}
