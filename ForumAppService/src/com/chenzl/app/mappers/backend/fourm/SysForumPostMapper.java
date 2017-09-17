package com.chenzl.app.mappers.backend.fourm;

import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;

import com.chenzl.app.bean.backend.forum.ForumPostBackend;
import com.chenzl.app.bean.backend.forum.RecommendPost;
import com.chenzl.app.entity.SysForumPost;
import com.github.pagehelper.Page;

public interface SysForumPostMapper  extends Mapper<SysForumPost>{
	
 //查询提问列表
 	List getForumPostList(Map<String, Object> param);
 	
 	//审核提问
 	int saveCheck(Map<String, Object> param);
 	
 	Page<RecommendPost> queryForumPostForPage(Map<String, Object> params);
 	
 	List<Map<String, Object>> getAreas();

	List<Map<String, Object>> getAreaById(final String areaId);
	
	int newReplyNumAdd(final Integer postId);
	
	int replyNumAdd(final Integer postId);
 	
	//查询问题详情
	SysForumPost getForumPostById(final Integer postId);
	
	//问题评论
	List<ForumPostBackend> getPostRepliesByPostId(final Integer postId);
	
	//评论回复
	List<ForumPostBackend> getReplyDebatesByReplyId(final Integer replyId);
	
	void updateForumPost(SysForumPost post);
	
	
	void modifyPost(Map<String,Object> params);
	
	Page<SysForumPost> appQueryForumPostList(final Map<String,Object> param);
	
	Page<SysForumPost> appQueryForumPostListAll(final Map<String,Object> param);
	
	SysForumPost appQueryForumPostById(final Integer postId);

	//新回复重置
	int newReplyNumReset(Map<String, Object> params);
	
	// 推荐提问
		Page<SysForumPost> getRecommendPosts(Map<String, Object> params);

		//获取我发起的提问
		Page<SysForumPost> getMyInitiatePosts(final Map<String, Object> params);
		
//获取我回覆的提问
		Page<SysForumPost> getMyReplyPosts(final Map<String, Object> params);
//获得我关注的问题
		Page<SysForumPost> getMyLikePosts(final Map<String, Object> params);

		//获取我的问题的最新的回复数
		Long getNewReplyNumForMyPost(final Map<String, Object> params);

		Long getNewReplyNumForMyInitiatePosts(final Map<String, Object> params);

		//获取我的评论的最新回复数
		Long getNewReplyNumForMyPostReply( final Map<String, Object> params);

		Long getNewReplyNumForMyReplyPosts(final Map<String, Object> params);

		List<SysForumPost> getPostByStarNums(final Map<String, Object> params);
 
		//获取我发起问题的新回复总数
		Long getNewReplyNumSum(final Map<String, Object> params);

}