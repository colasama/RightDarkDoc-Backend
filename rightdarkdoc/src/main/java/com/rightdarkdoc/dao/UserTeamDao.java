package com.rightdarkdoc.dao;

import com.rightdarkdoc.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import java.util.List;

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

    /**
     * 根据teamid和userid删除记录
     * @param teamid
     * @param userid
     */
    public void deleteTeamMember(Integer teamid, Integer userid);

    /**
     * 删除team
     * @param teamid 团队id
     */
    public void deleteTeamByTeamid(Integer teamid);

    /**
     * 查找团队成员
     * @param teamid
     * @return
     */
    public List<Integer> findTeamMembers(Integer teamid);

    /**
     * 更具userid寻找我加入的团队
     * @param userid
     * @return
     */
    public List<Integer> findMyAttendTeams(Integer userid);


    /**
     * 判断某人是不是团队成员
     * @param teamid
     * @param userid
     * @return
     */
    public Integer isTeamMember(Integer teamid, Integer userid);

}
