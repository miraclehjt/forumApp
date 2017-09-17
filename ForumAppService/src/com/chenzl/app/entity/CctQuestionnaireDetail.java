package com.chenzl.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/** 
 * null
 * @author 
 * @version 1.0  2016-11-08
 */
@Table(name = "cct_questionnaire_detail")
public class CctQuestionnaireDetail implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6008780959619390287L;

	@Id
    private Long questionnaireDetailId;

    private Long questionnaireId;

    private Short questionDetailType;

    private String questionDetailContent;

    private Short state;

    private String createStaff;

    private String updateStaff;

    private Date createDate;

    private Date updateDate;

    public Long getQuestionnaireDetailId() {
        return questionnaireDetailId;
    }

    public void setQuestionnaireDetailId(Long questionnaireDetailId) {
        this.questionnaireDetailId = questionnaireDetailId;
    }

    public Long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public Short getQuestionDetailType() {
        return questionDetailType;
    }

    public void setQuestionDetailType(Short questionDetailType) {
        this.questionDetailType = questionDetailType;
    }

    public String getQuestionDetailContent() {
        return questionDetailContent;
    }

    public void setQuestionDetailContent(String questionDetailContent) {
        this.questionDetailContent = questionDetailContent == null ? null : questionDetailContent.trim();
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public String getCreateStaff() {
        return createStaff;
    }

    public void setCreateStaff(String createStaff) {
        this.createStaff = createStaff == null ? null : createStaff.trim();
    }

    public String getUpdateStaff() {
        return updateStaff;
    }

    public void setUpdateStaff(String updateStaff) {
        this.updateStaff = updateStaff == null ? null : updateStaff.trim();
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
}