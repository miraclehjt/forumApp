package com.chenzl.app.bean.app;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.chenzl.app.entity.SysForumReplyDebate;


/** 
 * null
 * @author 
 * @version 1.0  2016-11-08
 */
public class ForumPostReply implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -480732095380918915L;

    private Integer postReplyId;

    private Integer postId;

    private String postReplyContent;

    private Integer replyIsNew;
    
    private Integer debateNum;
    
    private Integer newDebateNum;
    
    private Integer state;

    private String createUser;

    private String updateUser;

    private Date createDate;

    private Date updateDate;
    
    private String createDateStr;
    
    private String userPortrait;
    
    private Short sticky;
    
    private List<SysForumReplyDebate> cctForumPostReplyDebateList;

	public Integer getPostReplyId() {
		return postReplyId;
	}

	public void setPostReplyId(Integer postReplyId) {
		this.postReplyId = postReplyId;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getPostReplyContent() {
		return postReplyContent;
	}

	public void setPostReplyContent(String postReplyContent) {
		this.postReplyContent = postReplyContent;
	}

	public Integer getReplyIsNew() {
		return replyIsNew;
	}

	public void setReplyIsNew(Integer replyIsNew) {
		this.replyIsNew = replyIsNew;
	}

	public Integer getDebateNum() {
		return debateNum;
	}

	public void setDebateNum(Integer debateNum) {
		this.debateNum = debateNum;
	}

	public Integer getNewDebateNum() {
		return newDebateNum;
	}

	public void setNewDebateNum(Integer newDebateNum) {
		this.newDebateNum = newDebateNum;
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
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
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

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
	public String getUserPortrait() {
		return userPortrait;
	}

	public void setUserPortrait(String userPortrait) {
		this.userPortrait = userPortrait;
	}

	public Short getSticky() {
		return sticky;
	}

	public void setSticky(Short sticky) {
		this.sticky = sticky;
	}

	public List<SysForumReplyDebate> getCctForumPostReplyDebateList() {
		return cctForumPostReplyDebateList;
	}

	public void setCctForumPostReplyDebateList(
			List<SysForumReplyDebate> cctForumPostReplyDebateList) {
		this.cctForumPostReplyDebateList = cctForumPostReplyDebateList;
	}
    
}