package com.chenzl.app.bean.backend.forum;

import java.io.Serializable;

public class Questionnaire implements Serializable {

	private static final long serialVersionUID = 1L;

	private String questionnaireId;

	private String questionnaireName;

	private String questionnaireType;
	
	private String questionnaireState;

	private String endDate;
	
	private String staffId;

	private String areaId;

	private String sessionId;

	public String getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(String questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String getQuestionnaireName() {
		return questionnaireName;
	}

	public void setQuestionnaireName(String questionnaireName) {
		this.questionnaireName = questionnaireName;
	}
	
	public String getQuestionnaireType() {
		return questionnaireType;
	}

	public void setQuestionnaireType(String questionnaireType) {
		this.questionnaireType = questionnaireType;
	}

	public String getQuestionnaireState() {
		return questionnaireState;
	}

	public void setQuestionnaireState(String questionnaireState) {
		this.questionnaireState = questionnaireState;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
