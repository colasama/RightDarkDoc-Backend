package com.rightdarkdoc.entity;

import java.util.Date;

public class Document {

    private int docId;
    private String title;
    private String content;
    private Date createTime;
    private Date lastEditTime;
    private int editCount;
    private String lastEditUser;
    private int auth;         //文件的权限
    private int teamAuth;
    private String creator;
    private int isTrash;


    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public int getEditCount() {
        return editCount;
    }

    public void setEditCount(int editCount) {
        this.editCount = editCount;
    }

    public String getLastEditUser() {
        return lastEditUser;
    }

    public void setLastEditUser(String lastEditUser) {
        this.lastEditUser = lastEditUser;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }

    public int getTeamAuth() {
        return teamAuth;
    }

    public void setTeamAuth(int teamAuth) {
        this.teamAuth = teamAuth;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getIsTrash() {
        return isTrash;
    }

    public void setIsTrash(int isTrash) {
        this.isTrash = isTrash;
    }

    @Override
    public String toString() {
        return "Document{" +
                "docId=" + docId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                ", editCount=" + editCount +
                ", lastEditUser='" + lastEditUser + '\'' +
                ", auth=" + auth +
                ", teamAuth=" + teamAuth +
                ", creator='" + creator + '\'' +
                ", isTrash=" + isTrash +
                '}';
    }

}
