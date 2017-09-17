package com.chenzl.app.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 问题回复
 * @author chenzl
 */

@Table(name = "sys_forum_post_reply")
public class SysForumPostReply  implements Serializable{
	
	private static final long serialVersionUID = 4778032421534407539L;

	@Id
	private Integer postReplyId;

    private Integer postId;

    private String postReplyContent;

    private Integer debateNum;

    private Integer newDebateNum;
    
    private Integer replyIsNew;

    private Integer state;

    private String createUser;

    private String updateUser;

    private Date createDate;

    private Date updateDate;
    
  private String createDateStr;
    
    private Integer sticky;
    
    private Integer newReplyNum;
    

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
        this.postReplyContent = postReplyContent == null ? null : postReplyContent.trim();
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

	public Integer getReplyIsNew() {
		return replyIsNew;
	}

	public void setReplyIsNew(Integer replyIsNew) {
		this.replyIsNew = replyIsNew;
	}

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public Integer getSticky() {
		return sticky;
	}

	public void setSticky(Integer sticky) {
		this.sticky = sticky;
	}

	public Integer getNewReplyNum() {
		return newReplyNum;
	}

	public void setNewReplyNum(Integer newReplyNum) {
		this.newReplyNum = newReplyNum;
	}
}