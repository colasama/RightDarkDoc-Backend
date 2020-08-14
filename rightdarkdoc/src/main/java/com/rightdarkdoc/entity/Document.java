package com.rightdarkdoc.entity;

import java.util.Date;

public class Document {

    private Integer docid;
    private String title;
    private String content;
    private Date creattime;
    private Date lastedittime;
    private Integer editcount;
    private Integer lastedituserid;
    private Integer auth;         //文件的权限
    private Integer teamauth;
    private Integer creatorid;
    private Integer istrash;
    private String creattimeString="";
    private String lastedittimeString="";
    private Integer teamid = 0;


    public Integer getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Integer creatorid) {
        this.creatorid = creatorid;
    }

    public Integer getDocid() {
        return docid;
    }

    public void setDocid(Integer docid) {
        this.docid = docid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }

    public Date getLastedittime() {
        return lastedittime;
    }

    public void setLastedittime(Date lastedittime) {
        this.lastedittime = lastedittime;
    }

    public Integer getEditcount() {
        return editcount;
    }

    public void setEditcount(Integer editcount) {
        this.editcount = editcount;
    }

    public Integer getLastedituserid() {
        return lastedituserid;
    }

    public void setLastedituserid(Integer lastedituserid) {
        this.lastedituserid = lastedituserid;
    }

    public Integer getAuth() {
        return auth;
    }

    public void setAuth(Integer auth) {
        this.auth = auth;
    }

    public Integer getTeamauth() {
        return teamauth;
    }

    public void setTeamauth(Integer teamauth) {
        this.teamauth = teamauth;
    }


    public Integer getIstrash() {
        return istrash;
    }

    public void setIstrash(Integer istrash) {
        this.istrash = istrash;
    }

    public String getCreattimeString() {
        return creattimeString;
    }

    public void setCreattimeString(String creattimeString) {
        this.creattimeString = creattimeString;
    }

    public String getLastedittimeString() {
        return lastedittimeString;
    }

    public void setLastedittimeString(String lastedittimeString) {
        this.lastedittimeString = lastedittimeString;
    }

    public Integer getTeamid() {
        return teamid;
    }

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
    }

    @Override
    public String toString() {
        return "Document{" +
                "docid=" + docid +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", creattime=" + creattime +
                ", lastedittime=" + lastedittime +
                ", editcount=" + editcount +
                ", lastedituserid='" + lastedituserid + '\'' +
                ", auth=" + auth +
                ", teamauth=" + teamauth +
                ", creatorid='" + creatorid + '\'' +
                ", istrash=" + istrash +
                '}';
    }


}
