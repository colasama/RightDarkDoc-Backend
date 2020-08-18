package com.rightdarkdoc.dao;


import com.rightdarkdoc.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageDao {


    public int addMessage(Message message);


    public int delMessageByMid(@Param("messageid") Integer messageid);


    public int updateIsRead(@Param("messageid") Integer messageid, @Param("isread") Integer isread);

    /**
     * 根据messageid查看message
     * @param messageid
     * @return
     */
    public Message selectMsgById(@Param("messageid") Integer messageid);


    /**
     * 根据消息类型和用户名查找消息
     * @param userid
     * @param type
     * @return
     */
    public List<Message> selectMsgByUidAndType(Integer userid, Integer type);

    /**
     * 查看用户所有消息
     * @param userid
     * @return
     */
    public List<Message> selectMsgByUid(Integer userid);

    /**
     * 查找是否有相同内容的消息
     * @return
     */
    public List<Message> selectMsgByUseridAndContent (Message message);

}
