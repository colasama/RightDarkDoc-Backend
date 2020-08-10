package com.rightdarkdoc.entity;

public class Comment {

    private int commentId;
    private int documentId;
    private String content;
    private int userId;


    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", documentId=" + documentId +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                '}';
    }

}
