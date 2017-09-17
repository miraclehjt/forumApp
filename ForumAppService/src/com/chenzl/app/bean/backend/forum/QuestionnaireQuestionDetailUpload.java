package com.chenzl.app.bean.backend.forum;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class QuestionnaireQuestionDetailUpload {
	
	private Long questionnaireId;
	
	private Short questionnaireType;
	
	private String staffId;
	
	private String areaId;
	
	private String sessionId;
	
	private CommonsMultipartFile[] excelFile;
	
	public Long getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Long questionnaireId) {
		this.questionnaireId = questionnaireId;
	}
	
	public Short getQuestionnaireType() {
		return questionnaireType;
	}

	public void setQuestionnaireType(Short questionnaireType) {
		this.questionnaireType = questionnaireType;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public CommonsMultipartFile[] getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(CommonsMultipartFile[] excelFile) {
		this.excelFile = excelFile;
	}
}
