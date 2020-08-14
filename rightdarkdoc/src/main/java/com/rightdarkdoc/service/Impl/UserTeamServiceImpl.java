package com.rightdarkdoc.service.Impl;

import com.rightdarkdoc.dao.UserTeamDao;
import com.rightdarkdoc.entity.Team;
import com.rightdarkdoc.service.UserTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void inviteTeamMember(Integer teamid, Integer userid) {
        userTeamDao.inviteTeamMember(teamid, userid);
    }

    /**
     * 用户删除团队成员
     *
     * @param teamid
     * @param userid
     */
    @Override
    public void deleteTeamMember(Integer teamid, Integer userid) {
        userTeamDao.deleteTeamMember(teamid, userid);
    }

    /**
     * 根据团队id删除记录
     *
     * @param teamid
     */
    @Override
    public void deleteTeamByTeamid(Integer teamid) {
        userTeamDao.deleteTeamByTeamid(teamid);
    }

    /**
     * 用户申请加入团队
     *
     * @param teamid
     * @param userid
     */
    @Override
    public void applyToBeATeamMember(Integer teamid, Integer userid) {
        userTeamDao.inviteTeamMember(teamid, userid);
    }

    /**
     * 用户申请退出团队
     * @param teamid
     * @param userid
     */
    @Override
    public void exitTeam(Integer teamid, Integer userid) {
        userTeamDao.deleteTeamMember(teamid, userid);
    }

    /**
     * 查找团队成员的id
     *
     * @param teamid
     * @return
     */
    @Override
    public List<Integer> findTeamMembers(Integer teamid) {
        return userTeamDao.findTeamMembers(teamid);
    }

    /**
     * 查找我加入的团队
     *
     * @param userid
     * @return
     */
    @Override
    public List<Integer> findMyAttendTeams(Integer userid) {
        return userTeamDao.findMyAttendTeams(userid);
    }

    /**
     * 判断成员是不是某团队的成员
     *
     * @param teamid 团队id
     * @param userid 成员id
     * @return true：是该团队的成员
     */
    @Override
    public Boolean isTeamMember(Integer teamid, Integer userid) {
        Integer temp = userTeamDao.isTeamMember(teamid, userid);
        if (temp != null) {
            return true;        //找到了对应的记录
        }
        else {
            return false;       //没有找到对应的记录
        }
    }
}