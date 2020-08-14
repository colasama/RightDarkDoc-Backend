package com.rightdarkdoc.service;

import com.rightdarkdoc.entity.Document;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFavDocService {


    /**
     * 增加用户的收藏
     * @param userid 用户id
     * @param docid doc文档id
     */
    public void addUserFavDoc(Integer userid,Integer docid);


    /**
     * 删除用户的收藏
     * @param userid 用户id
     * @param docid doc文档id
     */
    public void deleteUserFavDoc(Integer userid,Integer docid);


    /**
     * 删除对于某个用户的收藏
     * @param docid
     */
    public void deleteUserFavDocByDocid(Integer docid);


    /**
     * 根据用户id查询文档
     * @param userid 用户的id
     * @return 包含Doc
     */
    public List<Integer> selectDocByUserid(Integer userid);


    /**
     * 根据用户的id和文档id查找相应表项，主要用于查重
     * @param userid 用户id
     * @param docid doc文档id
     * @return  返回查找到的docid
     */
    public Integer selectDocByUidAndDid(Integer userid,Integer docid);


}
