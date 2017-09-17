package com.chenzl.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="sys_kb")
public class SysKb  implements Serializable{
	
	private static final long serialVersionUID = 6112631389911941520L;

	@Id
    private Integer kbId;

    private String kbTitle;

    private Integer kbType;

    private Integer state;

    private Date createDate;

    private Date updateDate;

    private String content;

    private String kbLink;

    private String createUser;

    private String updateUser;

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
        this.kbTitle = kbTitle == null ? null : kbTitle.trim();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getKbLink() {
        return kbLink;
    }

    public void setKbLink(String kbLink) {
        this.kbLink = kbLink == null ? null : kbLink.trim();
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
}