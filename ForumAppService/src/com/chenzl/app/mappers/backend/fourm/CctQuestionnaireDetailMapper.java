package com.chenzl.app.mappers.backend.fourm;

import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;

import com.chenzl.app.bean.app.QuestionnaireDetailBean;
import com.chenzl.app.entity.CctQuestionnaireDetail;
import com.github.pagehelper.Page;

public interface CctQuestionnaireDetailMapper extends Mapper<CctQuestionnaireDetail> {

	Page<CctQuestionnaireDetail> getCctQuestionnaireDetailForPage(final Map<String, Object> params);
	
	List<CctQuestionnaireDetail> getCctQuestionnaireDetailList(final Map<String, Object> params);
	
	CctQuestionnaireDetail getCctQuestionnaireDetailById(final Long questionnaireDetailId);
	
	int updateQuestionnaireDetailStateById(final Map<String, Object> params);
	
	List<QuestionnaireDetailBean> getAppCctQuestionnaireDetail(final Map<String, Object> params);
	
	List<QuestionnaireDetailBean> getCctQuestionnaireDetailListForExport(final Map<String, Object> params);
}