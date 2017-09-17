package com.chenzl.app.entity;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/** 
 * 帖子点击数量
 * @author chenzl
 */
@Table(name = "sys_forum_post_click")
public class CctForumPostClick {
	
	@Id
    private Integer postClickId;

    private Integer postId;

    private String createUser;

    private Date createDate;

	public Integer getPostClickId() {
		return postClickId;
	}

	public void setPostClickId(Integer postClickId) {
		this.postClickId = postClickId;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
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