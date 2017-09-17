package com.chenzl.app.entity;

import java.util.Date;

public class SysForumPostStar {
    private String postStarId;

    private String postId;

    private String userId;

    private String createUser;

    private Date createDate;

    public String getPostStarId() {
        return postStarId;
    }

    public void setPostStarId(String postStarId) {
        this.postStarId = postStarId == null ? null : postStarId.trim();
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId == null ? null : postId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}