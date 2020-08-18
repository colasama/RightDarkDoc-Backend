package com.rightdarkdoc.dao;

import com.rightdarkdoc.entity.DocEdit;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface DocEditDao {

    /**
     * 添加一条编辑记录
     * @param userid
     * @param docid
     * @param date  日期
     */
    public void insertANewEditRecord(DocEdit docEdit);

    /**
     * 查找document的编辑记录
     * @param docid
     * @return 返回编辑用户id
     */
    public List<DocEdit> findDocEditorId(Integer docid);

}
