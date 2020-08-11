package com.rightdarkdoc.entity;

import java.util.Date;

public class Document {

    private Integer docid;
    private String title;
    private String content;
    private Date creattime;
    private Date lastedittime;
    private Integer editcount;
    private String lastedituserid;
    private Integer auth;         //文件的权限
    private Integer teamauth;
    private String creatorid;
    private Integer istrash;

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

    public String getLastedituserid() {
        return lastedituserid;
    }

    public void setLastedituserid(String lastedituserid) {
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

    public String getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(String creatorid) {
        this.creatorid = creatorid;
    }

    public Integer getIstrash() {
        return istrash;
    }

    public void setIstrash(Integer istrash) {
        this.istrash = istrash;
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
