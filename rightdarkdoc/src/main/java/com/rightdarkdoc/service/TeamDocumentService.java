package com.rightdarkdoc.service;

import java.util.List;

public interface TeamDocumentService {

    /**
     * 查找团队所有文档的功能
     * @param teamid
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
