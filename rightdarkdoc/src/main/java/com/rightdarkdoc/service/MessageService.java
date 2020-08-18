package com.rightdarkdoc.service;

import com.rightdarkdoc.entity.Message;

import java.util.List;

public interface MessageService {
    /**
     * 添加一个某类型的message
     * @param message       消息
     * @param type          消息类型
     * @return
     */
    public Integer addMessage(Message message, Integer type);

    /**
     *根据用户id和type查找message
     * @param userid
     * @param type
     * @return Message列表
     */
    public List<Message> selectMessageByUserIdAndType(Integer userid, Integer type);

    /**
     * 根据用户id查找message
     * @param userid
     * @return
     */
    public List<Message> selectMessageByUserId(Integer userid);

    /**
     * 用户申请加入团队时创建消息
     * @param teamid    申请加入的团队id
     * @param applyuserid    申请者的用户id
     * @param type      APPLY_MESSAGE
     * @return
     */
    public int applyTeamMessage(Integer teamid, Integer applyuserid, Integer type);

    /**
     * 邀请团队新成员时创建消息
     * @param inviteteamid
     * @param inviteuserid
     * @param type
     * @return
     */
    public int inviteTeamMemberMessage(Integer inviteteamid, Integer invitorid, Integer inviteuserid, Integer type);



    /**
     * 修改消息的isread字段
     * @param messageid
     * @param isread
     */
    public void updateIsRead(Integer messageid, Integer isread);

    /**
     * 根据messageid查找message
     * @param messageid
     * @return
     */
    public Message selectMessageByMessageId(Integer messageid);

    /**
     * 更具用户id和内容搜索消息
     * @return
     */
    public List<Message> selectMessageByUseridAndContent(Message message);

}
