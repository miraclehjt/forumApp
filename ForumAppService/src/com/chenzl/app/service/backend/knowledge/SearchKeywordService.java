package com.chenzl.app.service.backend.knowledge;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chenzl.app.entity.CctCscappKeyword;
import com.chenzl.app.mappers.backend.knowledgeSearch.CctCscappKeywordMapper;
import com.chenzl.app.service.CommonService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class SearchKeywordService {
	
	@Autowired
	CctCscappKeywordMapper keywordMapper;
	
	@Autowired
	CommonService commonService;
	
	public Page<CctCscappKeyword> getAllKeywords(final Map<String, Object> params){
		Integer page = (Integer) params.get("page"); // 页码
		Integer rows = (Integer) params.get("rows"); // 每页显示行
		PageHelper.startPage(page.intValue(), rows.intValue());
		
		Page<CctCscappKeyword> pagelist = this.keywordMapper.getKeywordsForPage(params);
		
		return pagelist;
	}
	

	public CctCscappKeyword getKeywordById(String keywordId) {
		return this.keywordMapper.selectByPrimaryKey(Integer.valueOf(keywordId));
	}
	

	
	public int updateKeywordLevelNotId(final Integer keywordId, final Integer level) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keywordId", keywordId);
		params.put("level", level);
		return this.keywordMapper.updateKeywordLevelNotId(params);
	}
	
	public void createKeyword(String name,Integer level,String user,Date date) throws Exception {
		CctCscappKeyword keyword = new CctCscappKeyword();
		keyword.setCreateUser(user);
		keyword.setCreatTime(date);
		keyword.setKeyword(name);
		keyword.setUpdateUser(user);
		keyword.setUpdateTime(date);
		keyword.setKeywordLevel(level);
		
		this.keywordMapper.insertSelective(keyword);
		/*
		if (level.intValue() == 1) {
			updateKeywordLevelNotId(1, 0);
		}*/
	}
	
	public void updateKeyword(Integer keywordId,String name,Integer level,String user,Date date) {
		CctCscappKeyword keyword = new CctCscappKeyword();
		keyword.setKeyword(name);
		keyword.setUpdateUser(user);
		keyword.setUpdateTime(date);
		keyword.setKeywordLevel(level);
		keyword.setKeywordId(Integer.valueOf(keywordId));
		
		this.keywordMapper.updateByPrimaryKeySelective(keyword);
		
		if (level.intValue() == 1) {
			updateKeywordLevelNotId(Integer.valueOf(keywordId), 0);
		}
	}
	
	public void deleteKeyword(String keywordId) {
		this.keywordMapper.deleteByPrimaryKey(Integer.valueOf(keywordId));
	}
	
	public List<CctCscappKeyword> getRecommendKeywords() {
		return this.keywordMapper.getRecommendKeywords();
	}

}
