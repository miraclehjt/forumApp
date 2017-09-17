package com.chenzl.app.mappers.backend.fourm;

import java.util.Map;

import tk.mybatis.mapper.common.Mapper;

import com.chenzl.app.entity.CctQuestionnaire;
import com.github.pagehelper.Page;

public interface CctQuestionnaireMapper extends Mapper<CctQuestionnaire> {
	Page<CctQuestionnaire> getCctQuestionnaireForPage(final Map<String, Object> params);
	
	Page<CctQuestionnaire> getAppCctQuestionnaireForPage(final Map<String, Object> params);
	
	Page<CctQuestionnaire> getMyQuestionnaireForPage(final Map<String, Object> params);
	
	CctQuestionnaire getCctQuestionnaireById(final Long questionnaireId);
	
	int updateQuestionnaireStateById(final Map<String, Object> params);
}