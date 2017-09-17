package com.chenzl.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 评论回复
 * @author chenzl
 *
 */

@Table(name = "sys_forum_reply_debate")
public class SysForumReplyDebate  implements Serializable{
	
	private static final long serialVersionUID = 1350002004260806325L;

	@Id
	private Integer  postReplyDebateId;

    private Integer postReplyId;

    private String postReplyDebateContent;

    private Integer debateIsNew;

    private Integer state;

    private String createUser;

    private String updateUser;

    private Date createDate;

    private Date updateDate;
    
    private String portrait;
    
    private String createDateStr;
    
    public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

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
        this.postReplyDebateContent = postReplyDebateContent == null ? null : postReplyDebateContent.trim();
    }

    public Integer getDebateIsNew() {
        return debateIsNew;
    }

    public void setDebateIsNew(Integer debateIsNew) {
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

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
}