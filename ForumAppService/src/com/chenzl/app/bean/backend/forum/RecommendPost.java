package com.chenzl.app.bean.backend.forum;

import com.chenzl.app.entity.SysForumPost;

public class RecommendPost extends SysForumPost {
	
	private Long recommend;
	
/*   private  String categoryName;
   
	public String getCategoryName() {
	return categoryName;
}

public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
}*/

public Long getRecommend() {
	return recommend;
}

public void setRecommend(Long recommend) {
	this.recommend = recommend;
}

}
