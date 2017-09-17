package com.chenzl.app.mappers.backend.fourm;

import java.util.List;
import java.util.Map;

import com.chenzl.app.bean.app.QuestionnaireAnswerCount;
import com.chenzl.app.bean.app.QuestionnaireAnswerQuestion;
import com.chenzl.app.entity.CctQuestionnaireAnswer;

import tk.mybatis.mapper.common.Mapper;

public interface CctQuestionnaireAnswerMapper extends Mapper<CctQuestionnaireAnswer> {
	
	CctQuestionnaireAnswer getCctQuestionnaireAnswerByMap(final Map<String, Object> params);
	
	Long getQuestionnaireAnswerCountByQuestionnaireDetailId(final Long questionnaireDetailId);
	
	Long queryQuestionnaireAnswerNoVote(final Long questionnaireDetailId);
	
	List<QuestionnaireAnswerCount> getQuestionnaireAnswerCountListByQuestionnaireDetailId(final Long questionnaireDetailId);
	
	List<QuestionnaireAnswerQuestion> getQuestionnaireAnswerQuestionListByQuestionnaireDetailId(final Long questionnaireDetailId);
}