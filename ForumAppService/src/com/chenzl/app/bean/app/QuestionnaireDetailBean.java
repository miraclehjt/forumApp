package com.chenzl.app.bean.app;

import java.io.Serializable;
import java.util.List;

public class QuestionnaireDetailBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String questionnaireDetailNo;
	
	private Long questionnaireDetailId;

    private Long questionnaireId;

    private Short questionDetailType;

    private String questionDetailContent;
    
    private List<QuestionnaireQuestionBean> questionList;
    
    public QuestionnaireDetailBean() {
    	
    }
    
	public String getQuestionnaireDetailNo() {
		return questionnaireDetailNo;
	}

	public void setQuestionnaireDetailNo(String questionnaireDetailNo) {
		this.questionnaireDetailNo = questionnaireDetailNo;
	}

	public Long getQuestionnaireDetailId() {
		return questionnaireDetailId;
	}

	public void setQuestionnaireDetailId(Long questionnaireDetailId) {
		this.questionnaireDetailId = questionnaireDetailId;
	}

	public Long getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Long questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public Short getQuestionDetailType() {
		return questionDetailType;
	}

	public void setQuestionDetailType(Short questionDetailType) {
		this.questionDetailType = questionDetailType;
	}

	public String getQuestionDetailContent() {
		return questionDetailContent;
	}

	public void setQuestionDetailContent(String questionDetailContent) {
		this.questionDetailContent = questionDetailContent;
	}

	public List<QuestionnaireQuestionBean> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<QuestionnaireQuestionBean> questionList) {
		this.questionList = questionList;
	}
}
