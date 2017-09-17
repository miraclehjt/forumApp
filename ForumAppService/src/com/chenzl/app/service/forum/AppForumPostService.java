package com.chenzl.app.service.forum;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenzl.app.entity.CctForumPostClick;
import com.chenzl.app.entity.SysForumCategory;
import com.chenzl.app.entity.SysForumPost;
import com.chenzl.app.entity.SysForumPostReply;
import com.chenzl.app.entity.SysForumReplyDebate;
import com.chenzl.app.mappers.SysForumCategoryMapper;
import com.chenzl.app.mappers.SysForumPostClickMapper;
import com.chenzl.app.mappers.backend.fourm.SysForumPostMapper;
import com.chenzl.app.mappers.backend.fourm.SysForumPostReplyMapper;
import com.chenzl.app.mappers.backend.fourm.SysForumReplyDebateMapper;
import com.chenzl.app.mappers.common.CommonMapper;
import com.chenzl.app.service.CommonService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class AppForumPostService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppForumPostService.class);
	
	@Autowired
	private CommonMapper commonMapper;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private SysForumCategoryMapper cctForumCategoryMapper;
	
	@Autowired
	private SysForumPostMapper cctForumPostMapper;
	
	@Autowired
	private SysForumPostReplyMapper cctForumPostReplyMapper;
	
	@Autowired
	private SysForumReplyDebateMapper cctForumReplyDebateMapper;
	
	@Autowired
	private SysForumPostClickMapper cctForumPostClickMapper; 
	
	
	/**
	 * 新增社区提问
	 * @param cctForumPost
	 * @return
	 * @throws Exception
	 */
	public int createCctForumPost(final SysForumPost cctForumPost) throws Exception {
		Timestamp timestamp = commonMapper.selectSysDate();
		cctForumPost.setReplyNum(0); // 总回复数
		cctForumPost.setNewReplyNum(0); // 新回复数
		cctForumPost.setStarNum(0); // 点赞关注数
		cctForumPost.setState(1); // 状态 1 正常
		cctForumPost.setAuditState(0); // 审核状态 0 未审核
		cctForumPost.setCreateDate(timestamp);
		cctForumPost.setUpdateDate(timestamp);
		cctForumPost.setPostType("1");
		// 互动提问
		String postType = cctForumPost.getPostType();
		Integer kbId = cctForumPost.getKbId();
		if (postType != null && postType == "1 "&& kbId != null) {
			List<SysForumCategory> cctForumCategoryList = cctForumCategoryMapper.getForumCategoryByCategoryType(postType); // 查询互动提问版块
			if (!cctForumCategoryList.isEmpty()) { // 设置互动提问版块ID
				cctForumPost.setCategoryId(cctForumCategoryList.get(0).getCategoryId());
			}
		}
		int i = cctForumPostMapper.insertSelective(cctForumPost);
		
		return i;
	}
	
	/**
	 * 更新社区提问
	 * @param cctForumPost
	 * @return
	 * @throws Exception
	 */
	public int updateSysForumPost(final SysForumPost cctForumPost) throws Exception {
		Timestamp timestamp = commonMapper.selectSysDate();
		cctForumPost.setUpdateDate(timestamp);
		int i = cctForumPostMapper.updateByPrimaryKeySelective(cctForumPost);
		return i;
	}
	
	/**
	 * 获取社区提问信息
	 * @param postId
	 * @return
	 * @throws Exception
	 */
	public SysForumPost getForumPost(final Integer postId) throws Exception {
		return cctForumPostMapper.appQueryForumPostById(postId);
	}

	/**
	 * 新增社区提问回复
	 * @param cctForumPostReply
	 * @return
	 * @throws Exception
	 */
	public SysForumPostReply createCctForumPostReply(final SysForumPostReply cctForumPostReply) throws Exception {
		SysForumPost cctForumPost = cctForumPostMapper.getForumPostById(cctForumPostReply.getPostId());
		Timestamp timestamp = commonMapper.selectSysDate();
		cctForumPostReply.setReplyIsNew(1); // 是否新回复 1 是
		cctForumPostReply.setDebateNum(0); // 总评论数
		cctForumPostReply.setNewDebateNum(0); // 新评论数
		cctForumPostReply.setState(1); // 状态 1 正常
		cctForumPostReply.setCreateDate(timestamp);
		cctForumPostReply.setUpdateDate(timestamp);
		int i = cctForumPostReplyMapper.insertSelective(cctForumPostReply);
		// 判断不是自己发的帖子才增加新回复数
		System.out.println("cctForumPostReply.getCreateUser()="+cctForumPostReply.getCreateUser()+"+++++++++++"+cctForumPost.getCreateUser());
		if (cctForumPost != null && !cctForumPostReply.getCreateUser().equals(cctForumPost.getCreateUser())) {
			cctForumPostMapper.newReplyNumAdd(cctForumPostReply.getPostId()); // 新回复数
		}
		cctForumPostMapper.replyNumAdd(cctForumPostReply.getPostId()); // 总回复数
		System.out.println(cctForumPostReply.getPostReplyId()+"++++++++++++++++++++++++++++++++");
		SysForumPostReply newCctForumPostReply = cctForumPostReplyMapper.getCctForumPostReplyById(cctForumPostReply.getPostReplyId());
		return newCctForumPostReply;
	}
	
	/**
	 * 更新社区提问回复
	 * @param cctForumPostReply
	 * @return
	 * @throws Exception
	 */
	public SysForumPostReply updateCctForumPostReply(final SysForumPostReply cctForumPostReply) throws Exception {
		Timestamp timestamp = commonMapper.selectSysDate();
		cctForumPostReply.setUpdateDate(timestamp);
		int i = cctForumPostReplyMapper.updateByPrimaryKeySelective(cctForumPostReply);
		SysForumPostReply newCctForumPostReply = cctForumPostReplyMapper.getCctForumPostReplyById(cctForumPostReply.getPostReplyId());
		return newCctForumPostReply;
	}
	
	/**
	 * 新增社区提问回复讨论
	 * @param cctForumReplyDebate
	 * @return
	 * @throws Exception
	 */
	public int createCctForumReplyDebate(final SysForumReplyDebate cctForumReplyDebate) throws Exception {
		SysForumPostReply cctForumPostReply = cctForumPostReplyMapper.getCctForumPostReplyById(cctForumReplyDebate.getPostReplyId());
		Timestamp timestamp = commonMapper.selectSysDate();
		cctForumReplyDebate.setDebateIsNew(1); // 是否新评论 1 是
		cctForumReplyDebate.setState(1); // 状态 1 正常
		cctForumReplyDebate.setCreateDate(timestamp);
		cctForumReplyDebate.setUpdateDate(timestamp);
		int i = cctForumReplyDebateMapper.insertSelective(cctForumReplyDebate);
		// 判断不是自己发的回复才增加新回复数
		if (cctForumPostReply != null && !cctForumReplyDebate.getCreateUser().equals(cctForumPostReply.getCreateUser())) {
			cctForumPostReplyMapper.newDebateNumAdd(cctForumReplyDebate.getPostReplyId()); // 新评论数
		}
		cctForumPostReplyMapper.debateNumAdd(cctForumReplyDebate.getPostReplyId()); // 总评论数
		return i;
	}
	
	/**
	 * 更新社区提问回复讨论
	 * @param cctForumReplyDebate
	 * @return
	 * @throws Exception
	 */
	public int updateCctForumReplyDebate(final SysForumReplyDebate cctForumReplyDebate) throws Exception {
		Timestamp timestamp = commonMapper.selectSysDate();
		cctForumReplyDebate.setUpdateDate(timestamp);
		int i = cctForumReplyDebateMapper.updateByPrimaryKeySelective(cctForumReplyDebate);
		return i;
	}
	
	/**
	 * 重置自己发起问题的新回复标示
	 * @param postId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public void forumPostReaded(final Integer postId, final String userId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("postId", postId);
		params.put("userId", userId);
		params.put("state", 1);
		params.put("replyIsNew",1);
		// 设置新回复数为0
		cctForumPostMapper.newReplyNumReset(params);
		// 设置新回复为已读
		cctForumPostReplyMapper.postReplyReaded(postId);
		
		List<SysForumPostReply> cctForumPostReplyList = cctForumPostReplyMapper.getCctForumPostReplyByMap(params);
		for (SysForumPostReply cctForumPostReply : cctForumPostReplyList) {
			forumPostReplyReaded(cctForumPostReply.getPostReplyId(), userId);
		}
	}

	/**
	 * 重置自己回复的新评论标示
	 * @param postId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public void forumPostReplyReaded(final Integer postReplyId, final String userId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("postReplyId", postReplyId);
		params.put("userId", userId);
		// 设置新评论数为0
		cctForumPostReplyMapper.newDebateNumReset(params);
		// 设置新评论为已读
		cctForumReplyDebateMapper.replyDebateReaded(postReplyId);
	}

	/**
	 * 获取与我相关帖子的统计
	 * @param staffId
	 * @return
	 * @throws Exception
	 */
	/*public ForumPostCount queryForumPostCount(final String staffId) throws Exception {
		Long forumPostCount = cctForumPostMapper.getForumPostCountByStaffId(staffId);
		Long forumPostReplyCount = cctForumPostMapper.getForumPostReplyCountByStaffId(staffId);
		Long forumReplyDebateCount = cctForumPostMapper.getForumReplyDebateCountByStaffId(staffId);
		Long forumPostStarCount = cctForumPostMapper.getForumPostStarCountByStaffId(staffId);
		return new ForumPostCount(forumPostCount, forumPostReplyCount, forumReplyDebateCount, forumPostStarCount);
	}*/
	
	public Page<SysForumPost> getRecommendPosts(final Map<String, Object> params) throws Exception {
		Integer pageNum = (Integer) params.get("pageNum"); // 页码
		Integer pageSize = (Integer) params.get("pageSize"); // 每页显示行
		PageHelper.startPage(pageNum, pageSize, "a.create_date desc");
		Page<SysForumPost> pageList = cctForumPostMapper.getRecommendPosts(params);
		return pageList;
		}
//根据areaID 或者StaffID 为“SCKF”做指定的查询
	/*public Page<SysForumPost> getRecommendPostByIds(final Map<String, Object> params) throws Exception {
		Integer pageNum = (Integer) params.get("pageNum"); // 页码
		Integer pageSize = (Integer) params.get("pageSize"); // 每页显示行
		PageHelper.startPage(pageNum, pageSize, "a.create_date desc");
		Page<SysForumPost> pageList = cctForumPostMapper.getRecommendPostByIds(params);
		return pageList;
		}*/
	
	public Page<SysForumPost> getMyInitiatePosts(Map<String, Object> params) throws Exception {
		Integer pageNum = (Integer) params.get("pageNum"); // 页码
		Integer pageSize = (Integer) params.get("pageSize"); // 每页显示行
		PageHelper.startPage(pageNum, pageSize);
		return cctForumPostMapper.getMyInitiatePosts(params);
	}
	
	/**
	 * 帖子点击记录
	 * @param postId
	 * @return
	 */
	public int createCctForumPostClick(final Integer postId, final String createUser) throws Exception {
		Timestamp createDate = commonMapper.selectSysDate();
		CctForumPostClick cctForumPostClick = new CctForumPostClick();
		cctForumPostClick.setPostId(postId);
		cctForumPostClick.setCreateUser(createUser);
		cctForumPostClick.setCreateDate(createDate);
		int i  = cctForumPostClickMapper.insertSelective(cctForumPostClick);
		return i;
	}
	
/*	public List<SysForumPostReply> getMyReplies(Map<String, Object> params) throws Exception {
		Integer pageNum = (Integer) params.get("pageNum"); // 页码
		Integer pageSize = (Integer) params.get("pageSize"); // 每页显示行
		PageHelper.startPage(pageNum, pageSize);
		return cctForumPostReplyMapper.getMyReplies(params);
	}*/
	
	//获得我关注的问题
	public Page<SysForumPost> getMyLikePosts(Map<String, Object> params) throws Exception {
		Integer pageNum = (Integer) params.get("pageNum"); // 页码
		Integer pageSize = (Integer) params.get("pageSize"); // 每页显示行
		PageHelper.startPage(pageNum, pageSize);
		return cctForumPostMapper.getMyLikePosts(params);
	}
	
	
	public Page<SysForumPost> getMyReplyPosts(Map<String, Object> params) throws Exception {
		
		Integer pageNum = (Integer) params.get("pageNum"); // 页码
		Integer pageSize = (Integer) params.get("pageSize"); // 每页显示行
		PageHelper.startPage(pageNum, pageSize);
		
		return cctForumPostMapper.getMyReplyPosts(params);
		
	}
	
/*
 * 获取我发起的提问的最新回复数
 */
	public Long getNewReplyNumForMyInitiatePosts(String userId,String postId) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("userId", userId);
		
		if (StringUtils.isNotEmpty(postId)) {
			params.put("postId", Integer.valueOf(postId));
			return cctForumPostMapper.getNewReplyNumForMyPost(params);
		}
		
		return cctForumPostMapper.getNewReplyNumForMyInitiatePosts(params);
		
	}
	
	//获取我的评论的最新回复数
public Long getNewReplyNumForMyReplyPosts(String userId,String postReplyId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("userId", userId);
		if (StringUtils.isNotEmpty(postReplyId)) {
			params.put("replyId", Integer.valueOf(postReplyId));
			return cctForumPostMapper.getNewReplyNumForMyPostReply(params);
		}
		return cctForumPostMapper.getNewReplyNumForMyReplyPosts(params);
	}
	
/*	public List<SysForumPostReply> getNewRepliesForPost(String staffId,String postId) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("staffId", staffId);
		params.put("postId", postId);
		
		return cctForumPostReplyMapper.getNewRepliesForPost(params);
	}
		*/
		//首页查询最热5条提问（分地市）
	/*	public List<SysForumPost> getPostByStarNum(Map<String,Object> prame) throws Exception{
			List<SysForumPost> list = cctForumPostMapper.getPostByStarNum(prame);
			return list;
		}*/
		
		//分页查询热门提问（每页10条）
		public List<SysForumPost> getPostByStarNums(Map<String, Object> params) {
			boolean page = params.get("page") != null ? (Boolean) params.get("page") : false;
			if (page) {
				int pageNum = (Integer) params.get("pageNum");
				int pageSize = (Integer) params.get("pageSize");
				PageHelper.startPage(pageNum, pageSize, "a.star_num desc, a.create_date desc");
			}
			List<SysForumPost> list = this.cctForumPostMapper.getPostByStarNums(params);
			return list;
		}

		//分页查询热门提问（每页10条）
	/*	public List<SysForumPost> getPostByStarNumsAll(Map<String, Object> params) {
					boolean page = params.get("page") != null ? (Boolean) params.get("page") : false;
					if (page) {
						int pageNum = (Integer) params.get("pageNum");
						int pageSize = (Integer) params.get("pageSize");
						PageHelper.startPage(pageNum, pageSize, "a.star_num desc, a.create_date desc");
					}
					List<SysForumPost> list = this.cctForumPostMapper.getPostByStarNumsAll(params);
					return list;
				}
	*/
	/**
	 * 获取我发起问题的新回复总数
	 * @param staffId
	 * @return
	 */
	public Long queryNewReplyNumSum(final String userId, final Integer postType){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("postType", postType);
		params.put("userId", userId);
		Long replyNumSum = cctForumPostMapper.getNewReplyNumSum(params);
		return replyNumSum;
	}
	
	/**
	 * 根据知识ID获取相关问题
	 * @param kbId
	 * @return
	 * @throws Exception
	 */
	/*public Page<SysForumPost> getForumPostByKbId(Map<String, Object> params) throws Exception {
		Integer pageNum = (Integer) params.get("pageNum"); // 页码
		Integer pageSize = (Integer) params.get("pageSize"); // 每页显示行
		PageHelper.startPage(pageNum, pageSize);
		Page<SysForumPost> cctForumPostList = cctForumPostMapper.appQueryForumPostList(params);
		return cctForumPostList;
	}*/
	
	// 首页查询最热5条提问（分地市）
	/*public List<SysForumPost> getNewPosts(Map<String, Object> params) throws Exception {

		List<SysForumPost> list = cctForumPostMapper.getNewPosts(params);

		return list;

	}*/
	
	// 根据"999"地市和SCKF员工首页查询最热5条提问（分地市）
	/*	public List<SysForumPost> getNewPostByIds(Map<String, Object> params) throws Exception {
			List<SysForumPost> list = cctForumPostMapper.getNewPostByIds(params);
			return list;
		}*/
	
	/**
	 * 帖子点击记录
	 * @param postId
	 * @return
	 */
	/*public int createSysForumPostClick(final Long postId, final String createStaff) throws Exception {
		String postClickId = commonService.generatePrimaryKeyBySequence("CCT_FORUM_POST_CLICK$SEQ");
		Timestamp createDate = commonMapper.selectSysDate();
		SysForumPostClick cctForumPostClick = new SysForumPostClick();
		cctForumPostClick.setPostClickId(Long.valueOf(postClickId));
		cctForumPostClick.setPostId(postId);
		cctForumPostClick.setCreateStaff(createStaff);
		cctForumPostClick.setCreateDate(createDate);
		int i  = cctForumPostClickMapper.insertSelective(cctForumPostClick);
		return i;
	}*/
	
	//查询地市提问量
/*	public List<AreaQuestionNum> getQuestionNumsOfAreas() throws Exception{
		
		return this.cctForumPostMapper.getQuestionNumsOfAreas();
		
	}*/
}
