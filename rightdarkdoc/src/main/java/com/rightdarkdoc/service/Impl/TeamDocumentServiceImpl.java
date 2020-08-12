package com.rightdarkdoc.service.Impl;

import com.rightdarkdoc.dao.TeamDocumentDao;
import com.rightdarkdoc.service.TeamDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamDocumentServiceImpl implements TeamDocumentService {

    @Autowired
    private TeamDocumentDao teamDocumentDao;
    /**
     * 查找团队所有文档的功能
     *
     * @param teamid
     * @return
     */
    @Override
    public List<Integer> findAllTeamDocuments(Integer teamid) {
        return teamDocumentDao.findAllTeamDocuments(teamid);
    }
}
