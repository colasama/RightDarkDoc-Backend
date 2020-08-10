package com.rightdarkdoc.dao;


import com.rightdarkdoc.entity.Team;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TeamDao {

    @Insert("insert into Team values(#{teamId},#{teamName},#{creator},#{info})")
    public int addTeam(Team team);


    @Update("update Team set teamname=#{teamName},creatorid=#{creatorId},teaminfo=#{teamInfo}")
    public int updateTeamByTeamId(Team team);


    @Select("select * from Team where teamid=#{teamId}")
    public Team selectTeambyTeamId(@Param("teamId") String teamId);

}
