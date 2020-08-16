package com.rightdarkdoc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class Message {

    private Integer messageid;
    private Integer userid;
    private String content;
    private Integer isread;

    /**
     * 0： 普通消息
     * 1： 申请消息
     * 2： 邀请消息
     */
    private Integer type;

    private Date messagetime;
    private String messagetimestring;

    /**
     * 不同类型的消息，可能要额外保存的东西
     * @return
     */
    private Integer applyuserid = 0;        //默认普通消息时值为0
    private Integer inviteteamid = 0;       //默认普通消息时值为0
    private Integer applyteamid = 0;
    private Integer inviteuserid = 0;

    public Integer getApplyteamid() {
        return applyteamid;
    }

    public void setApplyteamid(Integer applyteamid) {
        this.applyteamid = applyteamid;
    }

    public Integer getInviteuserid() {
        return inviteuserid;
    }

    public void setInviteuserid(Integer inviteuserid) {
        this.inviteuserid = inviteuserid;
    }

    public Integer getApplyuserid() {
        return applyuserid;
    }

    public void setApplyuserid(Integer applyuserid) {
        this.applyuserid = applyuserid;
    }

    public Integer getInviteteamid() {
        return inviteteamid;
    }

    public void setInviteteamid(Integer inviteteamid) {
        this.inviteteamid = inviteteamid;
    }

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

    public Integer getIsread() {
        return isread;
    }

    public void setIsread(Integer isread) {
        this.isread = isread;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getMessagetime() {
        return messagetime;
    }

    public void setMessagetime(Date messagetime) {
        this.messagetime = messagetime;
    }

    public String getMessagetimestring() {
        return messagetimestring;
    }

    public void setMessagetimestring(String messagetimestring) {
        this.messagetimestring = messagetimestring;
    }
}
