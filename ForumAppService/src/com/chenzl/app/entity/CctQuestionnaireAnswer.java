package com.chenzl.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/** 
 * null
 * @author 
 * @version 1.0  2016-11-14
 */
@Table(name = "cct_questionnaire_answer")
public class CctQuestionnaireAnswer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5224066580709882314L;

	@Id
    private Long questionnaireAnswerId;

    private Long questionnaireStaffId;

    private Long questionnaireId;

    private Long questionnaireDetailId;

    private Long questionnaireQuestionId;

    private String questionnaireCode;
    
    private String questionnaireAnswer;
    
    private Long questionnaireScore;

    private String createStaff;

    private Date createDate;

    public Long getQuestionnaireAnswerId() {
        return questionnaireAnswerId;
    }

    public void setQuestionnaireAnswerId(Long questionnaireAnswerId) {
        this.questionnaireAnswerId = questionnaireAnswerId;
    }

    public Long getQuestionnaireStaffId() {
        return questionnaireStaffId;
    }

    public void setQuestionnaireStaffId(Long questionnaireStaffId) {
        this.questionnaireStaffId = questionnaireStaffId;
    }

    public Long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public Long getQuestionnaireDetailId() {
        return questionnaireDetailId;
    }

    public void setQuestionnaireDetailId(Long questionnaireDetailId) {
        this.questionnaireDetailId = questionnaireDetailId;
    }

    public Long getQuestionnaireQuestionId() {
        return questionnaireQuestionId;
    }

    public void setQuestionnaireQuestionId(Long questionnaireQuestionId) {
        this.questionnaireQuestionId = questionnaireQuestionId;
    }
    
    public String getQuestionnaireCode() {
		return questionnaireCode;
	}

	public void setQuestionnaireCode(String questionnaireCode) {
		this.questionnaireCode = questionnaireCode == null ? null : questionnaireCode.trim();
	}

	public String getQuestionnaireAnswer() {
        return questionnaireAnswer;
    }

    public void setQuestionnaireAnswer(String questionnaireAnswer) {
        this.questionnaireAnswer = questionnaireAnswer == null ? null : questionnaireAnswer.trim();
    }
    
    public Long getQuestionnaireScore() {
		return questionnaireScore;
	}

	public void setQuestionnaireScore(Long questionnaireScore) {
		this.questionnaireScore = questionnaireScore;
	}

	public String getCreateStaff() {
        return createStaff;
    }

    public void setCreateStaff(String createStaff) {
        this.createStaff = createStaff == null ? null : createStaff.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}