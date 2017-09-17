package com.chenzl.app.bean.app;

import java.io.Serializable;

public class QuestionnaireDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String questionnaireDetailId;

    private String questionnaireId;

    private String questionDetailType;

    private String questionDetailContent;
    
	private String staffId;

	private String areaId;

	private String sessionId;

	public String getQuestionnaireDetailId() {
		return questionnaireDetailId;
	}

	public void setQuestionnaireDetailId(String questionnaireDetailId) {
		this.questionnaireDetailId = questionnaireDetailId;
	}

	public String getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(String questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String getQuestionDetailType() {
		return questionDetailType;
	}

	public void setQuestionDetailType(String questionDetailType) {
		this.questionDetailType = questionDetailType;
	}

	public String getQuestionDetailContent() {
		return questionDetailContent;
	}

	public void setQuestionDetailContent(String questionDetailContent) {
		this.questionDetailContent = questionDetailContent;
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
}
