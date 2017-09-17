package com.chenzl.app.service.backend.forum;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenzl.app.entity.SysForumCategory;
import com.chenzl.app.entity.SysForumPost;
import com.chenzl.app.entity.SysForumPostReply;
import com.chenzl.app.entity.SysForumReplyDebate;
import com.chenzl.app.mappers.SysForumCategoryMapper;
import com.chenzl.app.mappers.common.CommonMapper;
import com.chenzl.app.service.BaseService;
import com.chenzl.app.service.CommonService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class ForumCategoryService  extends BaseService<SysForumCategory>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ForumCategoryService.class);

	@Autowired
	private CommonMapper commonMapper;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private SysForumCategoryMapper  sysforumcategorymapper;
	
	/**
	 * 查询知识板块列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Page<SysForumCategory> queryForumCategoryForPage(final Map<String, Object> params) throws Exception {
		Integer page = (Integer) params.get("page"); // 页码
		Integer rows = (Integer) params.get("rows"); // 每页显示行
		PageHelper.startPage(page.intValue(), rows.intValue());
		Page<SysForumCategory> pageList = sysforumcategorymapper.queryForumCategoryForPage(params);
		return pageList;
	}
	
	/**
	 * 根据Id查询版块信息
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	public SysForumCategory queryForumCategoryById(final Integer categoryId) throws Exception {
		SysForumCategory  forumCategory = sysforumcategorymapper.getSysForumCategoryById(categoryId);
		return forumCategory;
	}
	
	/**
	 * 添加板块
	 * @param cctforumcategory
	 * @return
	 * @throws Exception
	 */
	public int addForumCategory(final SysForumCategory sysforumcategory) throws Exception {
		Timestamp timestamp = commonMapper.selectSysDate();
	//	sysforumcategory.setCategoryId(categoryId);
		sysforumcategory.setCreateDate(timestamp);
		sysforumcategory.setUpdateDate(timestamp);
		return sysforumcategorymapper.insertSelective(sysforumcategory);
	}
	/**
	 * 修改板块内容信息
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public int updateForumCategory(SysForumCategory sysforumcategory) throws Exception {
		Timestamp timestamp = commonMapper.selectSysDate();
		sysforumcategory.setUpdateDate(timestamp);
		return sysforumcategorymapper.updateByPrimaryKeySelective(sysforumcategory);
	}
	/**
	 * 删除版块信息
	 * @param categoryId
	 * @param staffId
	 * @return
	 * @throws Exception
	 */
	public int deleteForumCategoryById(final Integer categoryId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("categoryId", categoryId);
		/*List<CctForumPost> cctForumPostList = cctForumPostMapper.appQueryForumPostList(params);
		for (CctForumPost cctForumPost : cctForumPostList) {
			deleteForumPost(cctForumPost.getPostId());
		}*/
		int i = sysforumcategorymapper.deleteByPrimaryKey(categoryId);
		return i;
	}

	/**
	 * 删除问题
	 * @param postId
	 * @return
	 * @throws Exception
	 */
	/*public int deleteForumPost(final Long postId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("postId", postId);
		List<CctForumPostReply> cctForumPostReplyList = cctForumPostReplyMapper.getCctForumPostReplyByMap(params);
		for (CctForumPostReply cctForumPostReply : cctForumPostReplyList) {
			deleteForumPostReply(cctForumPostReply.getPostReplyId());
		}
		int i = cctForumPostMapper.deleteByPrimaryKey(postId);
		return i;
	}*/
	
	/**
	 * 删除问题回复
	 * @param postReplyId
	 * @return
	 * @throws Exception
	 */
/*	public int deleteForumPostReply(final Long postReplyId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("postReplyId", postReplyId);
		List<CctForumReplyDebate> cctForumReplyDebateList = cctForumReplyDebateMapper.queryReplyDebateList(postReplyId);
		for (CctForumReplyDebate cctForumReplyDebate : cctForumReplyDebateList) {
			deleteForumReplyDebate(cctForumReplyDebate.getPostReplyDebateId());
		}
		int i = cctForumPostReplyMapper.deleteByPrimaryKey(postReplyId);
		return i;
	}
	*/
	/**
	 * 删除回复评论
	 * @param postReplyDebateId
	 * @return
	 * @throws Exception
	 */
/*	public int deleteForumReplyDebate(final Long postReplyDebateId) throws Exception {
		int i = cctForumReplyDebateMapper.deleteByPrimaryKey(postReplyDebateId);
		return i;
	}
	*/
	/**
	 * 根据版块类型获取版块信息
	 * @param categoryType
	 * @return
	 */
	public List<SysForumCategory> getForumCategoryByCategoryType(final Integer categoryType) {
		return sysforumcategorymapper.getForumCategoryByCategoryType(categoryType);
	}
	
	public List<SysForumCategory> getAllCategories() {
		return sysforumcategorymapper.getAllCategories();
	}
	

}
