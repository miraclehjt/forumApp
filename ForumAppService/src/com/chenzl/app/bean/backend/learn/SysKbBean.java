package com.chenzl.app.bean.backend.learn;

import java.io.Serializable;
import java.util.Date;

/**
 * 知识列表Bean
 * @author chenzl
 */
public class SysKbBean implements Serializable{
	
	private static final long serialVersionUID = 1894674868717793461L;

	private Integer kbId;

	    private String kbTitle;

	    private Integer kbType;

	    private Integer state;

	    private String createDate;

	    private String updateDate;

	    private String content;

	    private String kbLink;

	    private String createUser;

	    private String updateUser;
	    
	    private String userId;
	    
		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
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

		public Integer getKbType() {
			return kbType;
		}

		public void setKbType(Integer kbType) {
			this.kbType = kbType;
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

		public String getUpdateDate() {
			return updateDate;
		}

		public void setUpdateDate(String updateDate) {
			this.updateDate = updateDate;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getKbLink() {
			return kbLink;
		}

		public void setKbLink(String kbLink) {
			this.kbLink = kbLink;
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
	    
}
