package com.chenzl.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/** 
 * null
 * @author 
 * @version 1.0  2016-11-07
 */
@Table(name = "cct_questionnaire_question")
public class CctQuestionnaireQuestion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 858883413059821016L;

	@Id
    private Long questionnaireQuestionId;

    private Long questionnaireDetailId;

    private String questionCode;

    private String questionContent;
    
    private Long questionScore;

    private Short state;

    private String createStaff;

    private String updateStaff;

    private Date createDate;

    private Date updateDate;

    public Long getQuestionnaireQuestionId() {
        return questionnaireQuestionId;
    }

    public void setQuestionnaireQuestionId(Long questionnaireQuestionId) {
        this.questionnaireQuestionId = questionnaireQuestionId;
    }

    public Long getQuestionnaireDetailId() {
        return questionnaireDetailId;
    }

    public void setQuestionnaireDetailId(Long questionnaireDetailId) {
        this.questionnaireDetailId = questionnaireDetailId;
    }

    public String getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode == null ? null : questionCode.trim();
	}

	public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent == null ? null : questionContent.trim();
    }

	public Long getQuestionScore() {
		return questionScore;
	}

	public void setQuestionScore(Long questionScore) {
		this.questionScore = questionScore;
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