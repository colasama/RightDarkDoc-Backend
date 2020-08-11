package com.rightdarkdoc.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserTeamDao {

    /**
     * 用户创建表时调用
     */
    public void userCreateTeam(Integer userid, Integer teamid);

    /**
     * 创建者邀请新的成员加入团队
     * @param teamid    加入的团队id
     * @param userid    受邀请者id
     */
    public void inviteTeamMember(Integer teamid, Integer userid);

}
