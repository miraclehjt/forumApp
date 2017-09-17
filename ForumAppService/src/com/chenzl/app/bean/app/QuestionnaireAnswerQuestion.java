package com.chenzl.app.bean.app;

public class QuestionnaireAnswerQuestion {

	private Long questionnaireDetailId;
	
	private Long questionnaireQuestionId;
	
	private String questionDetailContent;
	
	private String questionnaireAnswer;
	
	private String staffId;
	
	private String staffName;
	
	private String staffTel;

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

	public String getQuestionDetailContent() {
		return questionDetailContent;
	}

	public void setQuestionDetailContent(String questionDetailContent) {
		this.questionDetailContent = questionDetailContent;
	}

	public String getQuestionnaireAnswer() {
		return questionnaireAnswer;
	}

	public void setQuestionnaireAnswer(String questionnaireAnswer) {
		this.questionnaireAnswer = questionnaireAnswer;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffTel() {
		return staffTel;
	}

	public void setStaffTel(String staffTel) {
		this.staffTel = staffTel;
	}
}
