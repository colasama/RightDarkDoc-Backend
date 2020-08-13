package com.rightdarkdoc.dao;

import com.rightdarkdoc.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao {

    /**
     * 创建新的评论
     * @param comment
     */
    public void createNewComment(Comment comment);

    /**
     * 根据评论id找到评论
     * @param comid
     */
    public Comment findCommentByCommentId(Integer comid);

    /**
     * 删除评论
     * @param comid
     */
    public void deleteComment(Integer comid);

    /**
     * 查看文档的所有评论
     * @param docid 文档id
     */
    public List<Comment> findAllCommentsByDocumentId(Integer docid);
}
