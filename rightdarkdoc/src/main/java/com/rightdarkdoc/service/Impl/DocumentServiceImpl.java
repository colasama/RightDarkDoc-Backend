package com.rightdarkdoc.service.Impl;

import com.rightdarkdoc.dao.DocumentDao;
import com.rightdarkdoc.entity.Document;
import com.rightdarkdoc.entity.User;
import com.rightdarkdoc.service.DocumentService;
import com.rightdarkdoc.service.UserFavDocService;
import com.rightdarkdoc.service.UserService;
import com.rightdarkdoc.service.UserViewDocService;
import com.rightdarkdoc.utils.SortUtils;
import com.rightdarkdoc.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class DocumentServiceImpl implements DocumentService {


    @Autowired
    private DocumentDao documentDao;


    @Autowired
    private UserViewDocService userViewDocService;

    @Autowired
    private UserFavDocService userFavDocService;


    /**
     * 创建一个新的文件
     * @param document  创建的文件对象
     */
    @Override
    public Integer addDocument(Document document) {
        return documentDao.addDocument(document);
    }


    /**
     * 根据文件的id删除一个文件
     * @param docid 待删除文件的id
     */
    @Override
    public void deleteDoc(Integer docid) {
        documentDao.deleteDoc(docid);
    }

    /**
     * 删除用户回收站里的所有文档
     * @param creatorid  用户的id
     */
    @Override
    public void deleteAllDocInTrash(Integer creatorid){
        documentDao.deleteAllDocInTrash(creatorid);
    }

    /**
     * 后台更新编辑次数，最后编辑用户，最后编辑时间等信息,写入数据库
     * @param document 更新文件的信息
     */
    @Override
    public void updateDocument(Document document,Integer userid) {
        document.setEditcount(document.getEditcount()+1);
        Date date = new Date();
        document.setLastedittime(date);
        document.setLastedituserid(userid);
        documentDao.updateDocument(document);
    }

    @Override
    public void setDocTeamidToZero(Integer teamid) {
        documentDao.setDocTeamidToZero(teamid);
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
        Document doc = documentDao.selectDocByDocId(docid);
        if(doc!=null) {
            doc.setLastetidtimeString(TimeUtils.formatTime(doc.getLastedittime()));
            doc.setCreatetimeString(TimeUtils.formatTime(doc.getCreattime()));
        }
        return doc;
    }


    /**
     * 根据创建者的id查找doc文件
     * @param creatorid 创建者的id
     * @return
     */
    @Override
    public List<Document> selectDocByCreatorId(Integer creatorid){
        List<Document> docsTemp = documentDao.selectDocByCreatorId(creatorid);
        List<Document> docs = new ArrayList<>();
        for(Document doc : docsTemp){
            if(doc.getIstrash()==0) {
                doc.setLastetidtimeString(TimeUtils.formatTime(doc.getLastedittime()));
                doc.setCreatetimeString(TimeUtils.formatTime(doc.getCreattime()));
                docs.add(doc);
            }
        }
        return docs;
    }


    @Override
    public List<Document> selectDocInTrashByCreatorId(Integer creatorid) {
        List<Document> docsTemp = documentDao.selectDocByCreatorId(creatorid);
        List<Document> docs = new ArrayList<>();
        for(Document doc : docsTemp){
            if(doc.getIstrash()==1) {
                doc.setLastetidtimeString(TimeUtils.formatTime(doc.getLastedittime()));
                doc.setCreatetimeString(TimeUtils.formatTime(doc.getCreattime()));
                docs.add(doc);
            }
        }
        return docs;
    }

    @Override
    public void docMoveToTrash(Document document) {
        document.setIstrash(1);
        updateDocument(document,document.getLastedituserid());
        userViewDocService.delUserViewDocByDocid(document.getDocid());
        userFavDocService.deleteUserFavDocByDocid(document.getDocid());
    }

    /**
     * 根据团队id查找文档
     *
     * @param teamid 团队id
     * @return
     */
    @Override
    public List<Document> selectDocByTeamId(Integer teamid) {
        ArrayList<Document> documents = (ArrayList<Document>) documentDao.selectDocByTeamId(teamid);
        return SortUtils.sortByLastEditTime(documents);
    }

    @Override
    public void setTeamUserDocTeamidToZero(Integer teamid, Integer userid) {
        documentDao.setTeamUserDocTeamidToZero(teamid, userid);
    }
}