package com.chenzl.app.service.forum;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenzl.app.bean.app.ForumPostReply;
import com.chenzl.app.bean.app.ForumPostReplyBean;
import com.chenzl.app.entity.SysForumCategory;
import com.chenzl.app.entity.SysForumPost;
import com.chenzl.app.entity.SysForumPostReply;
import com.chenzl.app.entity.SysForumReplyDebate;
import com.chenzl.app.mappers.SysForumCategoryMapper;
import com.chenzl.app.mappers.backend.fourm.SysForumPostMapper;
import com.chenzl.app.mappers.backend.fourm.SysForumPostReplyMapper;
import com.chenzl.app.mappers.backend.fourm.SysForumReplyDebateMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class AppCategoryService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AppCategoryService.class);
   
	@Autowired
    private SysForumCategoryMapper appcategorymapper;
	
	@Autowired
	private SysForumPostMapper cctforumpostmapper;
	
	@Autowired
	private SysForumPostReplyMapper cctforumpostreplymapper;
	
	@Autowired
	private SysForumReplyDebateMapper cctforumreplydebatemapper;
	
	/**
	 * 查询版块信息
	 * @param prame
	 * @return
	 */
	public List<SysForumCategory> appQueryCategoryForPage(final Map<String,Object> param) throws Exception{
		List<SysForumCategory> list = appcategorymapper.appQueryCategoryForPage(param);
		return list;
	}
	
	/**
	 * 查询版块信息，排除紧急提问
	 * @return
	 * @throws Exception
	 */
	public List<SysForumCategory> queryForumCategoryForApp(final Map<String,Object> param) throws Exception {
		List<SysForumCategory> list = appcategorymapper.getForumCategoryForApp(param);
		return list;
	}
	
	/**
	 * 查询社区提问信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Page<SysForumPost> appQueryForumPostList(final Map<String,Object> param) throws Exception{
		Integer page = (Integer) param.get("page"); // 页码
		Integer rows = (Integer) param.get("rows"); // 每页显示行
		PageHelper.startPage(page.intValue(), rows.intValue(), "a.create_date desc");
		Page<SysForumPost> pageList = cctforumpostmapper.appQueryForumPostList(param);
		return pageList;
	}
	
	public Page<SysForumPost> appQueryForumPostListAll(final Map<String,Object> param) throws Exception{
		Integer page = (Integer) param.get("page"); // 页码
		Integer rows = (Integer) param.get("rows"); // 每页显示行
		PageHelper.startPage(page.intValue(), rows.intValue(), "a.create_date desc");
		Page<SysForumPost> pageList = cctforumpostmapper.appQueryForumPostListAll(param);
		return pageList;
	}
	/**
	 * 查询回复提问内容
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Page<ForumPostReply> appQueryPostReplyList(final Map<String,Object> params) throws Exception{
		Page<ForumPostReply> pageList = cctforumpostreplymapper.appQueryPostReplyList(params);
		pageList = setCctForumReplyDebate(pageList);
		return pageList;
	}
	
	/**
	 * 查询紧急提问回复
	 * @param params
	 * @return
	 * @throws Exception
	 */
/*	public List<SysForumPostReply> appEmergencyPostReplyList(final Map<String,Object> params) throws Exception{
		Page<SysForumPostReply> pageList = cctforumpostreplymapper.appQueryPostReplyList(params);
		pageList = setCctForumReplyDebate(pageList);
		return pageList;
	}*/
	
	/**
	 * 查询设置回复相关的讨论
	 * @param cctForumPostReplyList
	 * @return
	 * @throws Exception
	 */
	public Page<ForumPostReply> setCctForumReplyDebate(final Page<ForumPostReply> cctForumPostReplyList) throws Exception {
		List<SysForumReplyDebate> cctForumReplyDebateList = null;
		for (ForumPostReply forumPostReply : cctForumPostReplyList) {
			cctForumReplyDebateList = getCctForumReplyDebateByPostReplyId(forumPostReply.getPostReplyId());
			forumPostReply.setCctForumPostReplyDebateList(cctForumReplyDebateList);
		}
		return cctForumPostReplyList;
	}
	
	/**
	 * 通过回复ID查询回复相关的讨论
	 * @param postReplyId
	 * @return
	 * @throws Exception
	 */
	public List<SysForumReplyDebate> getCctForumReplyDebateByPostReplyId(final Integer postReplyId) throws Exception {
		List<SysForumReplyDebate> list=cctforumreplydebatemapper.queryReplyDebateList(postReplyId);
		return list; // 通过回复Id查询相关的讨论
	}
}
