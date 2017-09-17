package com.chenzl.app.bean.app;

public class QuestionnaireAnswerCount {

	private Long answerCount;
	
	private Long questionnaireDetailId;
	
	private Long questionnaireQuestionId;
	
	private String questionCode;
	
	private String questionContent;
	
	private Long questionScore;

	public Long getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(Long answerCount) {
		this.answerCount = answerCount;
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
}
