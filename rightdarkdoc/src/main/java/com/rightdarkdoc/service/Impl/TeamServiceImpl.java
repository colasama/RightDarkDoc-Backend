package com.rightdarkdoc.service.Impl;

import com.rightdarkdoc.dao.TeamDao;
import com.rightdarkdoc.entity.Team;
import com.rightdarkdoc.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamDao teamDao;

    /**
     * 创建新的团队
     *
     * @param team
     */
    @Override
    public void createNewTeam(Team team) {
        teamDao.createNewTeam(team);
    }

    /**
     * 根据团队名称和创建者id查询团队
     *
     * @param teamname  团队名称
     * @param creatorid 创建者id
     * @return
     */
    @Override
    public Team findTeamByTeamnameAndCreatorId(String teamname, Integer creatorid) {
        return teamDao.findTeamByTeamnameAndCreatorId(teamname,  creatorid);
    }
}