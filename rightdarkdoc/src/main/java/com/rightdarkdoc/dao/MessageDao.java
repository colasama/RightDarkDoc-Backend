package com.rightdarkdoc.dao;


import com.rightdarkdoc.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MessageDao {




    public int addMessage(Message message);


}
