package com.rightdarkdoc.service;

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
    public void inviteTeamMember(Integer userid, Integer teamid);

}
