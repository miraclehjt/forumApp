package com.chenzl.app.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/** 
 * null
 * @author 
 * @version 1.0  2016-11-07
 */
@Table(name = "cct_questionnaire")
public class CctQuestionnaire implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2362858618314350297L;

	@Id
    private Long questionnaireId;

    private String questionnaireName;

    private Short questionnaireType;
    
    private Short questionnaireState;
    
    private Timestamp endDate;

    private Short state;

    private String createStaff;

    private String updateStaff;

    private Date createDate;

    private Date updateDate;
    
    private String endDateStr;

    public Long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getQuestionnaireName() {
        return questionnaireName;
    }

    public void setQuestionnaireName(String questionnaireName) {
        this.questionnaireName = questionnaireName == null ? null : questionnaireName.trim();
    }
    
    public Short getQuestionnaireType() {
		return questionnaireType;
	}

	public void setQuestionnaireType(Short questionnaireType) {
		this.questionnaireType = questionnaireType;
	}

	public Short getQuestionnaireState() {
        return questionnaireState;
    }

    public void setQuestionnaireState(Short questionnaireState) {
        this.questionnaireState = questionnaireState;
    }
    
    public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
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

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
}