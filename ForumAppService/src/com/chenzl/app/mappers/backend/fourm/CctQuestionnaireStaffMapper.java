package com.chenzl.app.mappers.backend.fourm;

import java.util.Map;

import tk.mybatis.mapper.common.Mapper;

import com.chenzl.app.entity.CctQuestionnaireStaff;
import com.github.pagehelper.Page;

public interface CctQuestionnaireStaffMapper extends Mapper<CctQuestionnaireStaff> {
	
	CctQuestionnaireStaff getCctQuestionnaireById(final Long questionnaireStaffId);
	
	CctQuestionnaireStaff getCctQuestionnaireStaffByMap(final Map<String, Object> params);

	public Page<CctQuestionnaireStaff> queryCctQuestionnaireStaffForPage(final Map<String, Object> params);
	
	int updateQuestionnaireStaffFlag(final Map<String, Object> params);
}