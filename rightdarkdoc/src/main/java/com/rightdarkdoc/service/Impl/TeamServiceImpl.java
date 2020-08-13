package com.rightdarkdoc.service.Impl;

import com.rightdarkdoc.dao.TeamDao;
import com.rightdarkdoc.entity.Team;
import com.rightdarkdoc.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 根据团队id删除记录
     *
     * @param teamid
     */
    @Override
    public void deleteTeamByTeamid(Integer teamid) {
        teamDao.deleteTeamByTeamid(teamid);
    }

    /**
     * 通过团队id查找团队
     *
     * @param teamid
     * @return
     */
    @Override
    public Team findTeamByTeamid(Integer teamid) {
        return teamDao.findTeamByTeamid(teamid);
    }

    /**
     * 修改team信息
     *
     * @param team
     */
    @Override
    public void updateTeam(Team team) {
        teamDao.updateTeamByTeamId(team);
    }

    /**
     * 根据搜索内容查找团队（模糊匹配teamname）
     *
     * @param searchContent
     * @return
     */
    @Override
    public List<Team> findTeamsBySearchContent(String searchContent) {
        String searchContent1 = "%" + searchContent + "%";                      //加上模糊匹配
        return teamDao.findTeamsBySearchContent(searchContent1);
    }

    @Override
    public List<Team> findMyCreateTeamsByCreatorId(Integer creatorid) {
        return teamDao.findMyCreateTeams(creatorid);
    }

}