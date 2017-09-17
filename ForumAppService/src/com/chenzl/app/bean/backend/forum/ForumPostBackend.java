package com.chenzl.app.bean.backend.forum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.chenzl.app.entity.SysForumPost;

public class ForumPostBackend{
	
	//id
	private Integer fpId;
	
	//员工id
		private Integer user_id;
	//员工名
	private String userName;
	
	//员工头像
	private String userPortrait;
	
	//贴子内容
	private String postContent;
	
	//发帖日期
	private Date createDate;
	
	//置顶
	private Short sticky;
	
	//评论列表
	private List<ForumPostBackend> debateList;

	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPortrait() {
		return userPortrait;
	}

	public void setUserPortrait(String userPortrait) {
		this.userPortrait = userPortrait;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public List<ForumPostBackend> getDebateList() {
		return debateList;
	}

	public void setDebateList(List<ForumPostBackend> debateList) {
		this.debateList = debateList;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	
	
	
	public Integer getFpId() {
		return fpId;
	}

	public void setFpId(Integer fpId) {
		this.fpId = fpId;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Short getSticky() {
		return sticky;
	}

	public void setSticky(Short sticky) {
		this.sticky = sticky;
	}
}
