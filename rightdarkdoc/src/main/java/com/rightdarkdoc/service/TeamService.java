package com.rightdarkdoc.service;

import com.rightdarkdoc.entity.Team;

public interface TeamService {

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
}
