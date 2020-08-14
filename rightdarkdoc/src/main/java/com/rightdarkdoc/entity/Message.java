package com.rightdarkdoc.entity;

public class Message {

    private Integer messageid;
    private Integer userid;
    private String content;
    private String isread;
    private String type;

    public Integer getMessageid() {
        return messageid;
    }

    public void setMessageid(Integer messageid) {
        this.messageid = messageid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsread() {
        return isread;
    }

    public void setIsread(String isread) {
        this.isread = isread;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageid=" + messageid +
                ", userid=" + userid +
                ", content='" + content + '\'' +
                ", isread='" + isread + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
