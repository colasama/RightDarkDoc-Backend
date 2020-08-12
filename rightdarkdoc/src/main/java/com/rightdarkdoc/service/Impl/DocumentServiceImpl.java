package com.rightdarkdoc.service.Impl;

import com.rightdarkdoc.dao.DocumentDao;
import com.rightdarkdoc.entity.Document;
import com.rightdarkdoc.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DocumentServiceImpl implements DocumentService {


    @Autowired
    private DocumentDao documentDao;

    /**
     * 创建一个新的文件
     * @param document  创建的文件对象
     */
    @Override
    public void addDocument(Document document) {
        documentDao.addDocument(document);
    }


    /**
     * 更新一个文件的信息
     * @param document 更新文件的信息
     */
    @Override
    public void updateDocument(Document document) {
        documentDao.updateDocument(document);
    }


    /**
     * 对文件重命名
     * @param docid 待更改文件的id
     * @param title 待更改的新文件名
     */
    @Override
    public void updateDocTitle(Integer docid, String title) {
        documentDao.updateDocTitle(docid,title);
    }


    /**
     * 更改文档的权限
     * @param docid  文档的id
     * @param auth   设置的文档权限
     */
    @Override
    public void updateDocAuth(Integer docid, Integer auth) {
        documentDao.updateDocAuth(docid,auth);
    }

    /**
     * 根据文件的id查找doc文件
     * @param docid doc的id
     * @return
     */
    @Override
    public Document selectDocByDocId(Integer docid) {
        return documentDao.selectDocByDocId(docid);
    }


    /**
     * 根据创建者的id查找doc文件
     * @param creatorid 创建者的id
     * @return
     */
    @Override
    public List<Document> selectDocByCreatorId(Integer creatorid){
        return documentDao.selectDocByCreatorId(creatorid);
    }
}
