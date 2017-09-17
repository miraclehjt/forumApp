package com.chenzl.app.bean.app;

import java.io.Serializable;

public class QuestionnaireQuestionBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long questionnaireQuestionId;

	private Long questionnaireDetailId;

	private String questionCode;
	
	private String questionContent;

	public QuestionnaireQuestionBean() {
		
	}
	
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
		this.questionCode = questionCode;
	}

	public String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}
}
