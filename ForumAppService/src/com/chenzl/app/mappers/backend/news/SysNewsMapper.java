package com.chenzl.app.mappers.backend.news;

import java.util.Map;
import tk.mybatis.mapper.common.Mapper;
import com.chenzl.app.entity.SysNews;
import com.github.pagehelper.Page;

public interface SysNewsMapper  extends Mapper<SysNews>{
	
	//查询所有的新闻列表
	public Page<SysNews> queryNewsListForPage(Map<String,Object> params);

	//根据Id查询新闻
	SysNews getNewsListById(final Integer newsId);
	
	//更新新闻状态
	int updateNewsStateById(Map<String,Object> params);
}