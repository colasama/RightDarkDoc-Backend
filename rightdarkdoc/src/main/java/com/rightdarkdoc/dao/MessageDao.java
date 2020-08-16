package com.rightdarkdoc.dao;


import com.rightdarkdoc.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageDao {


    public int addMessage(Message message);


    public int delMessageByMid(@Param("messageid") Integer messageid);


    public int updateIsRead(@Param("messageid") Integer messageid,@Param("isread") Integer isread);


    public Message selectMsgById(@Param("messageid") Integer messageid);


    public List<Message> selectMsgByUid(@Param("userid") Integer userid);

}
