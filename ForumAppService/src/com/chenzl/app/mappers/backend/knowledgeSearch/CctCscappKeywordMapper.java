package com.chenzl.app.mappers.backend.knowledgeSearch;

import java.util.List;
import java.util.Map;
import tk.mybatis.mapper.common.Mapper;
import com.chenzl.app.entity.CctCscappKeyword;
import com.github.pagehelper.Page;

public interface CctCscappKeywordMapper extends Mapper<CctCscappKeyword> {
    
	public Page<CctCscappKeyword> getKeywordsForPage(Map<String, Object> params);
	
	public List<CctCscappKeyword> getRecommendKeywords();
	
	public int updateKeywordLevelNotId(final Map<String, Object> params);
	
	public List<String> queryDefaultKeyword();
	
	public List<String> queryRecommendKeyword();
}