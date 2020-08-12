package com.rightdarkdoc.dao;


import com.rightdarkdoc.entity.Team;
import org.apache.ibatis.annotations.*;

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

//
//
//    @Update("update Team set teamname=#{teamName},creatorid=#{creatorId},teaminfo=#{teamInfo}")
//    public int updateTeamByTeamId(Team team);
//
//
//    @Select("select * from Team where teamid=#{teamId}")
//    public Team selectTeambyTeamId(@Param("teamId") String teamId);

}
