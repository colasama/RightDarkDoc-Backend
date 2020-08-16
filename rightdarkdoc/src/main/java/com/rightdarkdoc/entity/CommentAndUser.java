package com.rightdarkdoc.entity;

public class CommentAndUser{
    private Comment comment;
    private String avatar;
    private String username;

    public CommentAndUser(Comment comment, String avatar, String username) {
        this.comment = comment;
        this.avatar = avatar;
        this.username = username;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
