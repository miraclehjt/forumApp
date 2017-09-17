package com.chenzl.app.mappers.backend.fourm;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chenzl.app.bean.app.ForumPostReply;
import com.chenzl.app.bean.app.ForumPostReplyBean;
import com.chenzl.app.entity.SysForumPostReply;
import com.github.pagehelper.Page;

import tk.mybatis.mapper.common.Mapper;

public interface SysForumPostReplyMapper  extends Mapper<SysForumPostReply>{
         
	void modifyPostReplyContent(@Param("id") Integer postReplyId,@Param("content") String postReplyContent);

	//置顶
     void stickyPostReply(Integer postReplyId);
	
     //取消置顶
	 void notStickyPostReplyOther(Map<String, Object> params);
	 
	 //回复
	 SysForumPostReply  getSysForumPostReplyById(final Integer postReplyId);
	 
	 void updatePostReply(SysForumPostReply postReply);
	 
	Page<ForumPostReply> appQueryPostReplyList(final Map<String,Object> params);

	int postReplyReaded(final  Integer postId);

	List<SysForumPostReply> getCctForumPostReplyByMap(Map<String, Object> params);

	int newDebateNumReset(Map<String, Object> params);

	SysForumPostReply getCctForumPostReplyById(final Integer postReplyId);

	int newDebateNumAdd(final Integer postReplyId);

	int debateNumAdd(final Integer postReplyId);
}
