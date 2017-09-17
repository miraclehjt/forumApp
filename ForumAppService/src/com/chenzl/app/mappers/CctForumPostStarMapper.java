package com.chenzl.app.mappers;

import java.util.List;
import java.util.Map;

import com.chenzl.app.entity.CctForumPostStar;

import tk.mybatis.mapper.common.Mapper;


public interface CctForumPostStarMapper extends Mapper<CctForumPostStar> {

	List<CctForumPostStar> getCctForumPostStarByMap(final Map<String, Object> params);
	
	int deleteCctForumPostStarByMap(final Map<String, Object> params);
	
	int updateCctForumPostStaNumAdd(final Integer postId);
	
	int updateCctForumPostStaNumSubtract(final Integer postId);
}