package com.chenzl.app.bean.backend.forum;

import java.util.List;

import com.chenzl.app.entity.CctQuestionnaireDetail;
import com.chenzl.app.entity.CctQuestionnaireQuestion;

public class QuestionnaireQuestionDetail {

	private CctQuestionnaireDetail cctQuestionnaireDetail;
	
	private List<CctQuestionnaireQuestion> cctQuestionnaireQuestionList;

	public QuestionnaireQuestionDetail(CctQuestionnaireDetail cctQuestionnaireDetail, List<CctQuestionnaireQuestion> cctQuestionnaireQuestionList) {
		this.cctQuestionnaireDetail = cctQuestionnaireDetail;
		this.cctQuestionnaireQuestionList = cctQuestionnaireQuestionList;
	}
	
	public CctQuestionnaireDetail getCctQuestionnaireDetail() {
		return cctQuestionnaireDetail;
	}

	public void setCctQuestionnaireDetail(
			CctQuestionnaireDetail cctQuestionnaireDetail) {
		this.cctQuestionnaireDetail = cctQuestionnaireDetail;
	}

	public List<CctQuestionnaireQuestion> getCctQuestionnaireQuestionList() {
		return cctQuestionnaireQuestionList;
	}

	public void setCctQuestionnaireQuestionList(
			List<CctQuestionnaireQuestion> cctQuestionnaireQuestionList) {
		this.cctQuestionnaireQuestionList = cctQuestionnaireQuestionList;
	}
}
