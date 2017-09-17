package com.chenzl.app.entity;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/*
 * 关键之搜索
 * chenzl
 */
@Table(name = "sys_cscapp_keyword")
public class CctCscappKeyword {

		@Id
	    private Integer keywordId;

	    private String keyword;

	    private Date creatTime;

	    private String createUser;

	    private Date updateTime;

	    private String updateUser;

	    private Integer keywordLevel;

	
	    public String getKeyword() {
	        return keyword;
	    }

	    public void setKeyword(String keyword) {
	        this.keyword = keyword == null ? null : keyword.trim();
	    }

	    public Date getCreatTime() {
	        return creatTime;
	    }

	    public void setCreatTime(Date creatTime) {
	        this.creatTime = creatTime;
	    }

	
	    public Date getUpdateTime() {
	        return updateTime;
	    }

	    public void setUpdateTime(Date updateTime) {
	        this.updateTime = updateTime;
	    }

		public Integer getKeywordId() {
			return keywordId;
		}

		public void setKeywordId(Integer keywordId) {
			this.keywordId = keywordId;
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

		public Integer getKeywordLevel() {
			return keywordLevel;
		}

		public void setKeywordLevel(Integer keywordLevel) {
			this.keywordLevel = keywordLevel;
		}

	}
