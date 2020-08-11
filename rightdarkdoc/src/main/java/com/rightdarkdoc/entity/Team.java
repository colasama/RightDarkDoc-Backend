package com.rightdarkdoc.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class Team {

    private Integer teamid;
    private String teamname;
    private Integer creatorid;
    private String teaminfo;              //团队的信息

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public void setCreatorid(Integer creatorid) {
        this.creatorid = creatorid;
    }

    public void setTeaminfo(String teaminfo) {
        this.teaminfo = teaminfo;
    }

    public Integer getTeamid() {
        return teamid;
    }

    public String getTeamname() {
        return teamname;
    }

    public Integer getCreatorid() {
        return creatorid;
    }

    public String getTeaminfo() {
        return teaminfo;
    }
}
