package com.chenzl.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/** 
 * null
 * @author 
 * @version 1.0  2016-11-09
 */
@Table(name = "cct_questionnaire_staff")
public class CctQuestionnaireStaff implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3824055470334737771L;

	@Id
    private Long questionnaireStaffId;

    private Long questionnaireId;

    private String staffId;

    private Short flag;

    private String createStaff;

    private Date createDate;
    
    private String staffName;

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

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId == null ? null : staffId.trim();
    }

    public Short getFlag() {
        return flag;
    }

    public void setFlag(Short flag) {
        this.flag = flag;
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

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
}