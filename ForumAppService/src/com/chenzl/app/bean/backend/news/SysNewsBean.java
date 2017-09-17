package com.chenzl.app.bean.backend.news;

import java.io.Serializable;
import java.util.Date;

/**
 * 新闻bean
 * @author chenzl
 */
public class SysNewsBean  implements Serializable{

	private static final long serialVersionUID = 3861194776470170746L;
	
	  private Integer newsId;

	    private String newsTitle;

	    private String newsLink;

	    private Integer newsType;

	    private String description;

	    private Integer state;

	    private String createDate;

	    private String createUser;

	    private String endDate;

	    private String updateUser;
	    
	    private String userId;

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public Integer getNewsId() {
			return newsId;
		}

		public void setNewsId(Integer newsId) {
			this.newsId = newsId;
		}

	
		public String getNewsTitle() {
			return newsTitle;
		}

		public void setNewsTitle(String newsTitle) {
			this.newsTitle = newsTitle;
		}

		public String getNewsLink() {
			return newsLink;
		}

		public void setNewsLink(String newsLink) {
			this.newsLink = newsLink;
		}

		public Integer getNewsType() {
			return newsType;
		}

		public void setNewsType(Integer newsType) {
			this.newsType = newsType;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Integer getState() {
			return state;
		}

		public void setState(Integer state) {
			this.state = state;
		}

		public String getCreateDate() {
			return createDate;
		}

		public void setCreateDate(String createDate) {
			this.createDate = createDate;
		}

		public String getCreateUser() {
			return createUser;
		}

		public void setCreateUser(String createUser) {
			this.createUser = createUser;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
		public String getUpdateUser() {
			return updateUser;
		}
		public void setUpdateUser(String updateUser) {
			this.updateUser = updateUser;
		}
	    
}
