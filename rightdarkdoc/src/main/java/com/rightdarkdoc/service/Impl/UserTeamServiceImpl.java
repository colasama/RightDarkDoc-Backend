package com.rightdarkdoc.service.Impl;

import com.rightdarkdoc.dao.UserTeamDao;
import com.rightdarkdoc.service.UserTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTeamServiceImpl implements UserTeamService {

    @Autowired
    private UserTeamDao userTeamDao;

    @Override
    public void userCreateTeam(Integer userid, Integer teamid) {
        userTeamDao.userCreateTeam(userid, teamid);
    }
}
