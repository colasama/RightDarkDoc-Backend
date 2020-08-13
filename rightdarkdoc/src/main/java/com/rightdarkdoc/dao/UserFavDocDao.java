package com.rightdarkdoc.dao;


import com.rightdarkdoc.entity.Document;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserFavDocDao {


    /**
     * 增加用户收藏的文档
     * @param userid
     * @param docid
     */
    public void addUserFavDoc(@Param("userid") Integer userid,@Param("docid") Integer docid);


    /**
     * 删除用户收藏的文档
     * @param userid 用户的id
     * @param docid  文档的id
     */
    public void deleteUserFavDoc(@Param("userid") Integer userid,@Param("docid") Integer docid);


    /**
     * 根据用户id选择其收藏的docid
     */
    public List<Integer> selectDocByUserid(@Param("userid") Integer userid);


    /**
     * 根据用户id和文档id选择目录项，主要是用来判重
     * @param userid
     * @param docid
     * @return
     */
    public Integer selectDocByUidAndDid(@Param("userid") Integer userid,@Param("docid") Integer docid);


}
