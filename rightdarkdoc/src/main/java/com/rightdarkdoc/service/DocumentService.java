package com.rightdarkdoc.service;

import com.rightdarkdoc.entity.Document;

import java.util.List;

public interface DocumentService {


    /**
     * 创建一个新文件
     * @param document  创建的文件对象
     */
    public void addDocument(Document document);


    /**
     * 删除一个文件
     * @param docid 待删除文件的id
     */
    public void deleteDoc(Integer docid);


    /**
     * 删除用户回收站里的所有文档
     * @param creatorid  用户的id
     */
    public void deleteAllDocInTrash(Integer creatorid);


    /**
     * 更新一个文件的内容
     * @param document
     */
    public void updateDocument(Document document);

    /**
     * 更新一个文件的名字
     * @param docid 待更改文件的id
     * @param title 待更改的新文件名
     */
    public void updateDocTitle(Integer docid,String title);


    /**
     * 修改文档的权限
     * @param docid  文档的id
     * @param auth   设置的文档权限
     */
    public void updateDocAuth(Integer docid,Integer auth);


    /**
     * 根据doc的id查找doc
     * @param docid doc的id
     * @return   对应的doc文件
     */
    public Document selectDocByDocId(Integer docid);

    /**
     * 根据创建者查找doc文档
     * @param creatorid 创建者的id
     * @return 包含doc文档的列表
     */
    public List<Document> selectDocByCreatorId(Integer creatorid);



}
