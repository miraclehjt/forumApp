package com.chenzl.app.bean.app;

import java.io.Serializable;
import java.util.Date;

import com.chenzl.app.bean.common.BaseBean;

/**
 * 回复评论Bean
 * @author Administrator
 *
 */
public class ForumReplyDebateBean extends BaseBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer postReplyDebateId;

    private Integer postReplyId;

    private String postReplyDebateContent;

    private Short debateIsNew;
    
    private Integer state;

    private String createUser;

    private String updateUser;

    private Date createDate;

    private Date updateDate;

	public Integer getPostReplyDebateId() {
		return postReplyDebateId;
	}

	public void setPostReplyDebateId(Integer postReplyDebateId) {
		this.postReplyDebateId = postReplyDebateId;
	}

	public Integer getPostReplyId() {
		return postReplyId;
	}

	public void setPostReplyId(Integer postReplyId) {
		this.postReplyId = postReplyId;
	}

	public String getPostReplyDebateContent() {
		return postReplyDebateContent;
	}

	public void setPostReplyDebateContent(String postReplyDebateContent) {
		this.postReplyDebateContent = postReplyDebateContent;
	}

	public Short getDebateIsNew() {
		return debateIsNew;
	}

	public void setDebateIsNew(Short debateIsNew) {
		this.debateIsNew = debateIsNew;
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

	public String toString() {
		return "ForumReplyDebateBean [postReplyDebateId=" + postReplyDebateId
				+ ", postReplyId=" + postReplyId + ", postReplyDebateContent="
				+ postReplyDebateContent + ", debateIsNew=" + debateIsNew
				+ ", state=" + state + ", createUser=" + createUser
				+ ", updateUser=" + updateUser + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + ", userId=" + userId
				+ ", sessionId=" + sessionId + "]";
	}
}
