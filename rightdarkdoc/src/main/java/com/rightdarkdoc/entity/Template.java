package com.rightdarkdoc.entity;

import java.util.Date;

public class Template {

    private Integer tempid;
    private Integer creatorid;
    private String  title;
    private String content;
    private Date creattime;
    private String creattimeString;
    private Integer istrash;
    private Integer ispublic;
    private String cover="";


    public Integer getTempid() {
        return tempid;
    }

    public void setTempid(Integer tempid) {
        this.tempid = tempid;
    }

    public Integer getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Integer creatorid) {
        this.creatorid = creatorid;
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

    public String getCreattimeString() {
        return creattimeString;
    }

    public void setCreattimeString(String creattimeString) {
        this.creattimeString = creattimeString;
    }

    public Integer getIstrash() {
        return istrash;
    }

    public void setIstrash(Integer istrash) {
        this.istrash = istrash;
    }

    public Integer getIspublic() {
        return ispublic;
    }

    public void setIspublic(Integer ispublic) {
        this.ispublic = ispublic;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "Template{" +
                "tempid=" + tempid +
                ", creatorid=" + creatorid +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", creattime=" + creattime +
                ", creattimeString='" + creattimeString + '\'' +
                ", istrash=" + istrash +
                ", ispublic=" + ispublic +
                ", cover='" + cover + '\'' +
                '}';
    }


}
