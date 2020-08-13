package com.rightdarkdoc.dao;


import com.rightdarkdoc.entity.Team;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeamDao {

    /**
     * 创建新的团队
     * @param team
     */
    public void createNewTeam(Team team);

    /**
     * 根据团队名称和创建者id查询团队
     * @param teamname          团队名称
     * @param creatorid         创建者id
     * @return
     */
    public Team findTeamByTeamnameAndCreatorId(String teamname, Integer creatorid);

    /**
     * 删除team
     * @param teamid 团队id
     */
    public void deleteTeamByTeamid(Integer teamid);

    /**
     * 根据团队id查找团队
     * @param teamid
     * @return
     */
    public Team findTeamByTeamid(Integer teamid);


    /**
     * 根据team的id修改team信息
     * @param team
     * @return
     */
    public void updateTeamByTeamId(Team team);

    /**
     * 根据搜索内容去查找团队信息
     * @param searchContent
     * @return
     */
    public List<Team> findTeamsBySearchContent(String searchContent);

//
//
//    @Select("select * from Team where teamid=#{teamId}")
//    public Team selectTeambyTeamId(@Param("teamId") String teamId);

}