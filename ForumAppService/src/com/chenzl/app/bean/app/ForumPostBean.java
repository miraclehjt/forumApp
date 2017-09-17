package com.chenzl.app.bean.app;

import java.io.Serializable;
import java.util.Date;

import com.chenzl.app.bean.common.BaseBean;

public class ForumPostBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

    private Integer postId;

    private Integer categoryId;

    private String postTitle;

    private String postContent;

    private String postType;

    private Integer kbId;
    
    private String kbTitle;

    private String areaId;

    private Integer state;

    private Integer replyNum;
    
    private Integer newReplyNum;
    
    private Integer starNum;

    private String createUser;

    private String updateUser;

    private Date createDate;

    private Date updateDate;

    private Short auditState;

    private String auditUser;

    private String auditComment;

    private Date auditDate;
    
 

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}



	public Integer getCategoryId() {
		return categoryId;
	}



	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}



	public String getPostTitle() {
		return postTitle;
	}



	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}



	public String getPostContent() {
		return postContent;
	}



	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}



	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public Integer getKbId() {
		return kbId;
	}



	public void setKbId(Integer kbId) {
		this.kbId = kbId;
	}



	public String getKbTitle() {
		return kbTitle;
	}



	public void setKbTitle(String kbTitle) {
		this.kbTitle = kbTitle;
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



	public Integer getReplyNum() {
		return replyNum;
	}



	public void setReplyNum(Integer replyNum) {
		this.replyNum = replyNum;
	}



	public Integer getNewReplyNum() {
		return newReplyNum;
	}



	public void setNewReplyNum(Integer newReplyNum) {
		this.newReplyNum = newReplyNum;
	}



	public Integer getStarNum() {
		return starNum;
	}



	public void setStarNum(Integer starNum) {
		this.starNum = starNum;
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



	public Short getAuditState() {
		return auditState;
	}



	public void setAuditState(Short auditState) {
		this.auditState = auditState;
	}



	public String getAuditUser() {
		return auditUser;
	}



	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}



	public String getAuditComment() {
		return auditComment;
	}



	public void setAuditComment(String auditComment) {
		this.auditComment = auditComment;
	}



	public Date getAuditDate() {
		return auditDate;
	}



	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}



	public String toString() {
		return "ForumPostBean [postId=" + postId + ", categoryId=" + categoryId
				+ ", postTitle=" + postTitle + ", postContent=" + postContent
				+ ", postType=" + postType
				+  ", kbId=" + kbId
				+ ", areaId=" + areaId + ", state=" + state + ", replyNum="
				+ replyNum + ", newReplyNum=" + newReplyNum + ", starNum="
				+ starNum + ", createUser=" + createUser + ", updateUser="
				+ updateUser + ", createDate=" + createDate + ", updateDate="
				+ updateDate + ", auditState=" + auditState + ", auditUser="
				+ auditUser + ", auditComment=" + auditComment
				+ ", auditDate=" + auditDate + "]";
	}
}
