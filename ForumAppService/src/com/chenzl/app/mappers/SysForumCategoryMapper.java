package com.chenzl.app.mappers;

import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;

import com.chenzl.app.entity.SysForumCategory;
import com.github.pagehelper.Page;


public interface SysForumCategoryMapper  extends Mapper<SysForumCategory>{
	
	Page<SysForumCategory> queryForumCategoryForPage(final Map<String, Object> params);

	SysForumCategory  getSysForumCategoryById(final Integer categoryId);
	
	int updateForumCategoryById(final Map<String, Object> params);
	
	//List<SysForumCategory> appQueryCategoryForPage(final Map<String, Object> params);
	
	List<SysForumCategory>  getForumCategoryByCategoryType(final Integer categoryType);
	
	//List<SysForumCategory> getForumCategoryForApp(final Map<String, Object> params);
	
	List<SysForumCategory> getAllCategories();
	
	/*Long getAuditCategoryIdOfStaff(String staffId);*/
	List<SysForumCategory> getForumCategoryByCategoryType(final String params);
	
	List<SysForumCategory> appQueryCategoryForPage( final Map<String,Object> param);
	
	List<SysForumCategory> getForumCategoryForApp(final Map<String,Object> param);
	
}