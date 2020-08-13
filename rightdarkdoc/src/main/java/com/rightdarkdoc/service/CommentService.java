package com.rightdarkdoc.service;

import com.rightdarkdoc.entity.Comment;

import java.util.List;

public interface CommentService {

    /**
     * 创建新的团队文档评论
     * @param comment
     */
    public void createNewComment(Comment comment);

    /**
     * 根据评论id找到评论
     * @param comid
     * @return
     */
    public Comment findCommentByCommentId(Integer comid);

    /**
     * 根据评论id删除评论
     * @param comid
     * @return
     */
    public void deleteComment(Integer comid);

    /**
     * 根据文档id查找评论
     * @param docid
     * @return
     */
    public List<Comment> findAllCommentsByDocumentId(Integer docid);
}
