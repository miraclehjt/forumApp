package com.chenzl.app.entity;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 问题推荐
 * @author chenzl
 *
 */

@Table(name = "sys_forum_recommend_post")
public class SysForumRecommendPost {
	
	@Id
    private Integer recommendPostId;

    private Integer postId;

    private String areaId;

    private Integer state;

    private String createUser;

    private String updateUser;

    private Date createDate;

    private Date updateDate;

    public Integer getRecommendPostId() {
        return recommendPostId;
    }

    public void setRecommendPostId(Integer recommendPostId) {
        this.recommendPostId = recommendPostId ;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }



    public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}