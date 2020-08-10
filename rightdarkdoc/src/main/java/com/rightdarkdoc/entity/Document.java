package com.rightdarkdoc.entity;

import java.util.Date;

public class Document {

    private int docId;
    private String title;
    private String content;
    private Date createTime;
    private Date lastEditTime;
    private int editCount;
    private String lastEditUserId;
    private int auth;         //文件的权限
    private int teamAuth;
    private String creatorId;
    private int isTrash;




    public String getLastEditUserId() {
        return lastEditUserId;
    }

    public void setLastEditUserId(String lastEditUserId) {
        this.lastEditUserId = lastEditUserId;
    }

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

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
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
                ", lastEditUser='" + lastEditUserId + '\'' +
                ", auth=" + auth +
                ", teamAuth=" + teamAuth +
                ", creator='" + creatorId + '\'' +
                ", isTrash=" + isTrash +
                '}';
    }

}
