package com.rightdarkdoc.service.Impl;

import com.rightdarkdoc.dao.UserTeamDao;
import com.rightdarkdoc.service.UserTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTeamServiceImpl implements UserTeamService {

    @Autowired
    private UserTeamDao userTeamDao;

    /**
     * 用户创建团队时调用
     *
     * @param userid
     * @param teamid
     */
    @Override
    public void userCreateTeam(Integer userid, Integer teamid) {
        userTeamDao.userCreateTeam(userid, teamid);
    }

    /**
     * 用户邀请团队新成员时调用
     *
     * @param userid
     * @param teamid
     */
    @Override
    public void inviteTeamMember(Integer userid, Integer teamid) {
        userTeamDao.inviteTeamMember(userid, teamid);
    }
}
