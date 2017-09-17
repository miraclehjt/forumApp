package com.chenzl.app.mappers.backend.fourm;

import java.util.List;

import tk.mybatis.mapper.common.Mapper;

import com.chenzl.app.entity.SysForumReplyDebate;

public interface SysForumReplyDebateMapper  extends Mapper<SysForumReplyDebate>{
   
	SysForumReplyDebate getDebateById(final Integer debateId);
	
	List<SysForumReplyDebate>  queryReplyDebateList(final Integer postReplyId);

	// 设置新评论为已读
     int replyDebateReaded(final Integer postReplyId);
}