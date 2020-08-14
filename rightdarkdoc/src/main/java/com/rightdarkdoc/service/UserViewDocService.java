package com.rightdarkdoc.service;


import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserViewDocService {


    /**
     * 增加用户最近浏览
     * @param userid
     * @param docid
     */
    public void addUserViewDoc(Integer userid, Integer docid, Date viewtime);


    /**
     * 删除用户最近浏览
     * @param userid
     * @param docid
     */
    public void delUserViewDoc(Integer userid,Integer docid);


    /**
     * 刷新最近浏览的浏览时间
     * @param userid 浏览的用户id
     * @param docid 浏览的文档id
     * @param viewtime 更新的时间
     */
    public void updateViewtime(Integer userid,Integer docid,Date viewtime);


    /**
     * 查找用户最近浏览的文档
     * @param userid
     * @return
     */
    public List<Integer> selectUserViewDoc(Integer userid);

    /**
     * 查找目录项，目的是查重
     * @param userid
     * @param docid
     * @return
     */
    public Integer selectUserViewDocByUidAndDid(Integer userid,Integer docid);




}
