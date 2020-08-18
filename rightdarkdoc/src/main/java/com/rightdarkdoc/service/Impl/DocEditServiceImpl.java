package com.rightdarkdoc.service.Impl;

import com.rightdarkdoc.dao.DocEditDao;
import com.rightdarkdoc.entity.DocEdit;
import com.rightdarkdoc.service.DocEditService;
import com.rightdarkdoc.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DocEditServiceImpl implements DocEditService {

    @Autowired
    private DocEditDao docEditDao;
    /**
     * 插入一条编辑记录
     *
     * @param userid
     * @param docid
     */
    @Override
    public void addANewEditRecord(Integer userid, Integer docid) {
        Date date = new Date();
        DocEdit docEdit = new DocEdit();

        docEdit.setDocid(docid);
        docEdit.setUserid(userid);
        docEdit.setEdittime(date);
        docEdit.setEdittimestring(TimeUtils.formatTime(date));

        docEditDao.insertANewEditRecord(docEdit);
    }

    /**
     * 查找文档的编辑记录
     *
     * @param docid
     * @return
     */
    @Override
    public List<DocEdit> findDocEditorRecord(Integer docid) {
        return docEditDao.findDocEditorId(docid);
    }
}
