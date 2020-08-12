package com.rightdarkdoc.dao;


import com.rightdarkdoc.entity.Document;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DocumentDao {


    /**
     * 创建一个document
     * @param document document对象
     * @return
     */
    public void addDocument(Document document);


    /**
     * 更新doc的信息
     * @param document  保存信息的document对象
     */
    public void updateDocument(Document document);


    /**
     * 更新doc的名字
     * @param docid 待更新doc的id
     * @param title 新的名字
     */
    public void updateDocTitle(@Param("docid") Integer docid,@Param("title") String title);


    /**
     * 根据doc的id查找doc
     * @param docid doc的id
     * @return
     */
    public Document selectDocByDocId(@Param("docid") Integer docid);

    /**
     * 根据创建者id查找doc
     * @param creatorid 创建者的id
     * @return 包含doc的list
     */
    public List<Document> selectDocByCreatorId(@Param("creatorid") Integer creatorid);

}

