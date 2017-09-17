package com.chenzl.app.mappers.backend.fourm;

import java.util.List;
import java.util.Map;


public interface ForumMapper{

	//查询提问列表
	List getForumPostList(Map<String, Object> param);
	
	//审核提问
	int saveCheck(Map<String, Object> param);
	
}
