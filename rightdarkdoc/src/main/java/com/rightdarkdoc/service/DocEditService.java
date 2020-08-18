package com.rightdarkdoc.service;

import com.rightdarkdoc.entity.DocEdit;

import java.util.List;

public interface DocEditService {
    /**
     * 插入一条编辑记录
     * @param userid
     * @param docid
     */
    public void addANewEditRecord(Integer userid, Integer docid);

    /**
     * 查找文档的编辑记录
     * @param docid
     * @return
     */
    public List<DocEdit> findDocEditorRecord(Integer docid);
}
