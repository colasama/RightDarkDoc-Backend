package com.rightdarkdoc.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserViewDocDao {


    public void addUserViewDoc(@Param("userid") Integer userid,@Param("docid") Integer docid);

    public void delUserViewDoc(@Param("userid") Integer userid,@Param("docid") Integer docid);

    public List<Integer> selectUserViewDoc(@Param("userid") Integer userid);

    public Integer selectUserViewDocByUidAndDid(@Param("userid") Integer userid,@Param("docid") Integer docid);

}
