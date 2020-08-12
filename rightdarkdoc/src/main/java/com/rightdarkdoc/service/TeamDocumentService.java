package com.rightdarkdoc.service;

import java.util.List;

public interface TeamDocumentService {

    /**
     * 查找团队所有文档的功能
     * @param teamid
     * @return
     */
    public List<Integer> findAllTeamDocuments(Integer teamid);
}
