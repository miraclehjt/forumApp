package com.chenzl.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/** 
 * null
 * @author 
 * @version 1.0  2016-11-16
 */
@Table(name = "sys_forum_post_star")
public class CctForumPostStar implements Serializable {
	
	private static final long serialVersionUID = 5950324971120268155L;

	@Id
    private Integer postStarId;

    private Integer postId;

    private String userId;

    private String createUser;

    private Date createDate;

	public Integer getPostStarId() {
		return postStarId;
	}

	public void setPostStarId(Integer postStarId) {
		this.postStarId = postStarId;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}