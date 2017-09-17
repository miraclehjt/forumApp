package com.chenzl.app.mappers.backend.fourm;

import java.util.HashMap;
import java.util.List;

import com.chenzl.app.entity.SysForumRecommendPost;

import tk.mybatis.mapper.common.Mapper;

public interface SysForumRecommendPostMapper  extends Mapper<SysForumRecommendPost>{
	
	//插入地市推荐
	void insertRecommendPost(SysForumRecommendPost post);
	
	//删除地市推荐
	void deleteRecommendPostInArea(HashMap<String, Object> params);
	
	//查询要推荐的地市
	List<String> getPostRecommendAreas(Integer postId);
	
}
