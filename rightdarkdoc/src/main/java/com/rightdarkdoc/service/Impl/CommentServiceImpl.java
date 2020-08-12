package com.rightdarkdoc.service.Impl;

import com.rightdarkdoc.dao.CommentDao;
import com.rightdarkdoc.dao.DocumentDao;
import com.rightdarkdoc.entity.Comment;
import com.rightdarkdoc.entity.Document;
import com.rightdarkdoc.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    /**
     * 创建新的团队文档评论
     *
     * @param comment
     */
    @Override
    public void createNewComment(Comment comment) {
        commentDao.createNewComment(comment);
    }

    /**
     * 根据评论id找到评论
     *
     * @param integer   评论id
     * @return
     */
    @Override
    public Comment findCommentByCommentId(Integer comid) {
        return commentDao.findCommentByCommentId(comid);
    }

    /**
     * 根据评论id删除评论
     *
     * @param comid
     * @return
     */
    @Override
    public void deleteComment(Integer comid) {
        commentDao.deleteComment(comid);
    }

    /**
     * 根据文档id查找评论
     *
     * @param docid
     * @return
     */
    @Override
    public List<Comment> findAllCommentsByDocumentId(Integer docid) {
        return commentDao.findAllCommentsByDocumentId(docid);
    }

}
