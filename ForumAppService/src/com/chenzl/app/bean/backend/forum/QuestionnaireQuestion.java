package com.chenzl.app.bean.backend.forum;

import java.io.Serializable;

public class QuestionnaireQuestion implements Serializable {

	private static final long serialVersionUID = 1L;

	private String questionnaireQuestionId;

	private String questionnaireDetailId;
	
	private String questionDetailType;

	private String questionCode;
	
	private String questionContent;
	
	private Long questionScore;

	private String staffId;

	private String areaId;

	private String sessionId;
	
	public QuestionnaireQuestion(String questionnaireQuestionId,
			String questionnaireDetailId, String questionDetailType,
			String questionContent) {
		super();
		this.questionnaireQuestionId = questionnaireQuestionId;
		this.questionnaireDetailId = questionnaireDetailId;
		this.questionDetailType = questionDetailType;
		this.questionContent = questionContent;
	}

	public String getQuestionnaireQuestionId() {
		return questionnaireQuestionId;
	}

	public void setQuestionnaireQuestionId(String questionnaireQuestionId) {
		this.questionnaireQuestionId = questionnaireQuestionId;
	}

	public String getQuestionnaireDetailId() {
		return questionnaireDetailId;
	}

	public void setQuestionnaireDetailId(String questionnaireDetailId) {
		this.questionnaireDetailId = questionnaireDetailId;
	}
	
	public String getQuestionDetailType() {
		return questionDetailType;
	}

	public void setQuestionDetailType(String questionDetailType) {
		this.questionDetailType = questionDetailType;
	}

	public String getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}

	public String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}
	
	public Long getQuestionScore() {
		return questionScore;
	}

	public void setQuestionScore(Long questionScore) {
		this.questionScore = questionScore;
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
