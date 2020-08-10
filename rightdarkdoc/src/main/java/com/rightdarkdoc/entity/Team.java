package com.rightdarkdoc.entity;

public class Team {



    private int teamId;
    private String teamName;
    private String creatorId;
    private String teamInfo;              //团队的信息


    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCreator() {
        return creatorId;
    }

    public void setCreator(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getInfo() {
        return teamInfo;
    }

    public void setInfo(String teamInfo) {
        this.teamInfo = teamInfo;
    }


    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", creator='" + creatorId + '\'' +
                ", info='" + teamInfo + '\'' +
                '}';
    }



}
