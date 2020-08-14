package com.rightdarkdoc.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamDocumentDao {

    /**
     * 查找团队所有的文档
     * @param teamid    团队id
     * @return
     */
    public List<Integer> findAllTeamDocuments(Integer teamid);

    /**
     * 创建新的团队文档
     * @param teamid    团队id
     * @param docid     文档id
     */
    public void createNewTeamDocument(Integer teamid, Integer docid);

}
