package com.rightdarkdoc.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserViewDocDao {


    public void addUserViewDoc(@Param("userid") Integer userid,@Param("docid") Integer docid,@Param("viewtime") Date date);

    public void delUserViewDoc(@Param("userid") Integer userid,@Param("docid") Integer docid);

    public void updateViewtime(@Param("userid") Integer userid,@Param("docid") Integer docid,@Param("viewtime") Date viewtime);

    public List<Integer> selectUserViewDoc(@Param("userid") Integer userid);

    public Integer selectUserViewDocByUidAndDid(@Param("userid") Integer userid,@Param("docid") Integer docid);

}
