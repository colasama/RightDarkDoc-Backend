package com.rightdarkdoc.service;

import com.rightdarkdoc.entity.Team;
import com.rightdarkdoc.entity.User;

import java.util.List;

public interface UserTeamService {

    /**
     * 用户创建团队时调用
     * @param userid
     * @param teamid
     */
    public void userCreateTeam(Integer userid, Integer teamid);

    /**
     * 用户邀请团队新成员时调用
     * @param userid
     * @param teamid
     */
    public void inviteTeamMember(Integer teamid, Integer userid);

    /**
     * 用户删除团队成员
     * @param teamid
     * @param userid
     */
    public void deleteTeamMember(Integer teamid, Integer userid);

    /**
     * 根据团队id删除记录
     * @param teamid
     */
    public void deleteTeamByTeamid(Integer teamid);

    /**
     * 用户申请加入团队
     * @param teamid
     * @param userid
     */
    public void applyToBeATeamMember(Integer teamid, Integer userid);

    /**
     * 退出团队
     * @param teamid
     * @param userid
     */
    public void exitTeam(Integer teamid, Integer userid);

    /**
     * 查找团队成员的id
     * @param teamid
     * @return
     */
    public List<Integer> findTeamMembers(Integer teamid);

    /**
     * 查找我加入的团队
     * @param userid
     * @return
     */
    public List<Integer> findMyAttendTeams(Integer userid);

    /**
     * 判断成员是不是某团队的成员
     * @param teamid    团队id
     * @param userid    成员id
     * @return          true：是该团队的成员
     */
    public Boolean isTeamMember(Integer teamid, Integer userid);

}
