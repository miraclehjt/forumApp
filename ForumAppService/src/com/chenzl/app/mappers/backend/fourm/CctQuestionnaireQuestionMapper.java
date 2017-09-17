package com.chenzl.app.mappers.backend.fourm;

import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;

import com.chenzl.app.bean.app.QuestionnaireQuestionBean;
import com.chenzl.app.entity.CctQuestionnaireQuestion;
import com.github.pagehelper.Page;

public interface CctQuestionnaireQuestionMapper extends Mapper<CctQuestionnaireQuestion> {
	/*
    int deleteByPrimaryKey(Long questionnaireQuestionId);

    int insert(CctQuestionnaireQuestion record);

    int insertSelective(CctQuestionnaireQuestion record);

    CctQuestionnaireQuestion selectByPrimaryKey(Long questionnaireQuestionId);

    int updateByPrimaryKeySelective(CctQuestionnaireQuestion record);

    int updateByPrimaryKey(CctQuestionnaireQuestion record);
    */
	
	Page<CctQuestionnaireQuestion> getQuestionnaireQuestionForPage(final Map<String, Object> params);
	
	CctQuestionnaireQuestion getQuestionnaireQuestionById(final Long questionnaireQuestionId);
	
	int updateQuestionnaireQuestionStateById(final Map<String, Object> params);
	
	List<QuestionnaireQuestionBean> getAppCctQuestionnaireQuestion(final Map<String, Object> params);
}