package com.chenzl.app.service.backend.forum;

import org.springframework.stereotype.Service;

import com.chenzl.app.bean.backend.forum.ForumPostBackend;
import com.chenzl.app.bean.backend.forum.RecommendPost;
import com.chenzl.app.bean.backend.forum.SysForumPostSelect;
import com.chenzl.app.common.DataSource;
import com.chenzl.app.entity.SySUserInfo;
import com.chenzl.app.entity.SysForumAuditUser;
import com.chenzl.app.entity.SysForumPost;
import com.chenzl.app.entity.SysForumPostReply;
import com.chenzl.app.entity.SysForumRecommendPost;
import com.chenzl.app.entity.SysForumReplyDebate;
import com.chenzl.app.entity.SysUser;
import com.chenzl.app.mappers.SysUserMapper;
import com.chenzl.app.mappers.backend.fourm.ForumMapper;
import com.chenzl.app.mappers.backend.fourm.SysForumAuditUserMapper;
import com.chenzl.app.mappers.backend.fourm.SysForumPostMapper;
import com.chenzl.app.mappers.backend.fourm.SysForumPostReplyMapper;
import com.chenzl.app.mappers.backend.fourm.SysForumRecommendPostMapper;
import com.chenzl.app.mappers.backend.fourm.SysForumReplyDebateMapper;
import com.chenzl.app.mappers.common.CommonMapper;
import com.chenzl.app.service.CommonService;
import com.chenzl.app.service.backend.common.CommonUtilService;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 提问管理
 * @author chenzl
 *
 */
@Service
public class ForumPostService {
    
	@Autowired
	private CommonMapper commonMapper;

	@Autowired
	private CommonService commonService;
	
	
	@Autowired
	private SysForumPostMapper forumPostMapper;
	
	@Autowired
	private SysUserMapper  userMapper;
	
	/*@Autowired
	private SysBbsMapper cctSysBbsMapper;*/
	
	@Autowired
	private SysForumPostReplyMapper replyMapper;
	
	@Autowired
	private SysForumRecommendPostMapper recommendPostMapper;
	
	@Autowired
	private CommonUtilService commonUtilService;

	/*@Autowired
	private SysForumPostReplyMapper replyMapper;
	*/
	/*@Autowired
	private SysForumReplyDebateMapper debateMapper;
	*/
	@Autowired
	private SysForumPostMapper sysForumPostMapper;

	@Autowired
	private SysForumPostReplyMapper sysForumPostReplyMapper;
	
	@Autowired
	private SysForumReplyDebateMapper debateMapper;
	
	public Page<RecommendPost> queryForumPostForPage(final Map<String, Object> params) throws Exception {
		Integer page = (Integer) params.get("page"); // 页码
		Integer rows = (Integer) params.get("rows"); // 每页显示行
		PageHelper.startPage(page.intValue(), rows.intValue());
		Page<RecommendPost> pageList = forumPostMapper.queryForumPostForPage(params);

		return pageList;
	}
	
	//删除帖子
	public void deletePostById(Integer postId,String userId) throws Exception {
		SysForumPost post = new SysForumPost();
		post.setPostId(postId);
		post.setState(0);
		post.setUpdateDate(new Date());
		post.setUpdateUser(userId);
		this.forumPostMapper.updateByPrimaryKeySelective(post);
	}
	
	//推荐
	public void recommendPost(Integer postId,String areaIds,String userId) throws Exception {
	
//			String pk = this.commonService.generatePrimaryKeyBySequence("cct_forum_recommend_post$seq");
			
		String[] areaArray = areaIds.split("_");
		
		for (String area : areaArray) {
			
			SysForumRecommendPost post = new SysForumRecommendPost();
			post.setAreaId(areaIds);
			post.setCreateDate(new Date());
			post.setCreateUser(userId);
			post.setUpdateDate(new Date());
			post.setUpdateUser(userId);
	//	post.setRecommendPostId();
			post.setPostId(postId);
			post.setState(1);
			
			this.recommendPostMapper.insertRecommendPost(post);
		}
			
	//		this.recommendPostMapper.insertSelective(post);

	}
	
	//取消推荐 
	public void notRecommendPost(Integer postId,String areaIds,String  userId) throws Exception {
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("postId", postId);
		params.put("areaId", areaIds);
			this.recommendPostMapper.deleteRecommendPostInArea(params);
}
	//更新推荐
	public void updateRecommendPost(Integer recommendId,Integer postId,String areaIds,String userId) throws Exception {
		SysForumRecommendPost post = new SysForumRecommendPost();
		post.setAreaId(areaIds);
		post.setUpdateDate(new Date());
		post.setUpdateUser(userId);
		post.setRecommendPostId(recommendId);
		post.setPostId(postId);
		post.setState(1);
		this.recommendPostMapper.updateByPrimaryKeySelective(post);

}
	
public List<String> getPostRecommendAreas(Integer postId) {
		
		return this.recommendPostMapper.getPostRecommendAreas(postId);
		
	}

/**
 *获得问题详情
 * @param postId
 * @return
 * @throws Exception
 */
	public Map<String, Object> getForumPostDetail(Integer postId) throws Exception {
		
		HashMap<String, Object> jsonObject = new HashMap<String, Object>();
		
		//查询问题详情
		SysForumPost sysForumPost = this.forumPostMapper.getForumPostById(postId);
		
		jsonObject.put("postId", postId);
		jsonObject.put("title", sysForumPost.getPostTitle());
		jsonObject.put("content", sysForumPost.getPostContent());
		jsonObject.put("createDate", sysForumPost.getCreateDate());
		jsonObject.put("createUser", sysForumPost.getCreateUser());

		//发帖人信息
		SySUserInfo  userInfo = this.userMapper.findUserByAccount(sysForumPost.getCreateUser());//根据员工的Id查询发帖人信息sysForumPost.getCreateUser()
		String portrait = userInfo.getPortrait()!=null ? userInfo.getPortrait() : "";
		jsonObject.put("userPortrait", portrait);
		jsonObject.put("userName", userInfo.getUserName());
		
		//查询评论列表
		List<ForumPostBackend> replyList = this.forumPostMapper.getPostRepliesByPostId(postId);
	
//		sortPostReply(replyList);
	
		for(int i=0 ; i<replyList.size(); i++) {
			ForumPostBackend reply = replyList.get(i);
				
			List<ForumPostBackend> replyDebateList = this.forumPostMapper.getReplyDebatesByReplyId(reply.getFpId());
	
			
			//评论的回复
			reply.setDebateList(replyDebateList);
		}
		
		jsonObject.put("replies", replyList);
		
		
		return jsonObject;
		
	}
	
	

//	/**
//	 * 回复排序
//	 * @param list
//	 */
//	private void sortPostReply(List<ForumPostBackend>replies) {
//		
//		
//		Comparator<ForumPostBackend> comparator = new Comparator<ForumPostBackend>() {
//
//			
//			public int compare(ForumPostBackend o1, ForumPostBackend o2) {
//				// TODO Auto-generated method stub
//				
//				if(o1.getSticky().intValue() != o2.getSticky().intValue()) {
//					int i = o1.getSticky().intValue() - o2.getSticky().intValue();
//					return i;
//
//				}
//				else {
//					return o1.getCreateDate().compareTo(o2.getCreateDate());
//				}
//
//			}
//		};
//		
//		Collections.sort(replies, comparator);
//		int i = 0;
//	}

//删除评论回复
public void deleteReplyDebate(String replyDebateId) throws Exception {
		
//		this.forumPostMapper.deleteReplyDebateById(Long.valueOf(replyDebateId));
		
		SysForumReplyDebate debate = this.debateMapper.getDebateById(Integer.valueOf(replyDebateId));
		debate.setState(0);
		this.debateMapper.updateByPrimaryKeySelective(debate);
		
		SysForumPostReply reply = this.replyMapper.getSysForumPostReplyById(debate.getPostReplyId());
	
		//更新reply的debateNum和newDebateNum
		if (debate.getDebateIsNew().shortValue() != 0) {
			Integer newDebateNum = reply.getNewDebateNum();
			reply.setNewDebateNum(newDebateNum - 1);
		}
		Integer debateNum = reply.getDebateNum();
		reply.setDebateNum(debateNum -1);
//		this.replyMapper.updateByPrimaryKeySelective(reply);
		this.replyMapper.updatePostReply(reply);
	}
	
	//删除回复
public void deletePostReply(String postReplyId) throws Exception {
//		this.forumPostMapper.deletePostReplyById(Long.valueOf(postReplyId));
		
		//更新reply state为0
		SysForumPostReply reply = this.replyMapper.getSysForumPostReplyById(Integer.valueOf(postReplyId));
		reply.setState(0);
//		this.replyMapper.updateByPrimaryKeySelective(reply);
		this.replyMapper.updatePostReply(reply);
		
		//更新post的replyNum和newReplyNum
		SysForumPost post = this.forumPostMapper.getForumPostById(reply.getPostId());
		Integer repyNumLong = post.getReplyNum();
		post.setReplyNum(repyNumLong-1);
		
		if (reply.getReplyIsNew() != 0 ) {
			Integer newReplyNum = post.getNewReplyNum();
			post.setNewReplyNum(newReplyNum-1);
		}
		
//		this.forumPostMapper.updateByPrimaryKeySelective(post);
		this.forumPostMapper.updateForumPost(post);
		
	}
	

	public void stickyPostReply(String postReplyId,String postId) throws Exception {
		this.replyMapper.stickyPostReply(Integer.valueOf(postReplyId));
		
		HashMap<String, Object> paramsHashMap = new HashMap<String, Object>();
		paramsHashMap.put("postReplyId", Long.valueOf(postReplyId));
		paramsHashMap.put("postId", Long.valueOf(postId));
		this.replyMapper.notStickyPostReplyOther(paramsHashMap);
		
	}
	
	public void saveForumPost(Integer categoryId,String postType, String title,String content,String userId, String areaId) throws Exception {
		
		SysForumPost forumPost = new SysForumPost();
		
		
	//	String pk = this.commonService.generatePrimaryKeyBySequence("cct_forum_post$seq");
		forumPost.setPostId(1);
		forumPost.setCategoryId(categoryId);
		forumPost.setPostType(postType);
		forumPost.setPostTitle(title);
		forumPost.setPostContent(content);
		forumPost.setCreateUser(userId);
		forumPost.setCreateDate(new Date());
		forumPost.setUpdateDate(new Date());
		forumPost.setUpdateUser(userId);
		forumPost.setStarNum(0);
		forumPost.setState(1);
		forumPost.setAuditState(0);
		
		forumPost.setAreaId(areaId);
		
		this.forumPostMapper.insertSelective(forumPost);
	}
	
/*	public List<Area> getAreas() {
		return cctSysBbsMapper.getAreas();
	}
	*/
	public void modifyPostTitle(String postId,String postTitle) throws Exception {
		HashMap<String, Object> paramsHashMap = new HashMap<String, Object>();
		paramsHashMap.put("postTitle", postTitle);
		paramsHashMap.put("postId",Integer.valueOf(postId));
		
		this.forumPostMapper.modifyPost(paramsHashMap);
	}
	
	//修改问题内容
	public void modifyPostContent(String postId,String postContent) throws Exception {
		HashMap<String, Object> paramsHashMap = new HashMap<String, Object>();
		paramsHashMap.put("postContent", postContent);
		paramsHashMap.put("postId", Integer.valueOf(postId));
		
		this.forumPostMapper.modifyPost(paramsHashMap);
	}
	
	
	public void modifyPostReplyContent(Integer postReplyId,String postReplyContent) throws Exception {
		this.sysForumPostReplyMapper.modifyPostReplyContent(postReplyId, postReplyContent);
	}

    /**
     * 增加问题回复
     */
	public int createForumPostReply(final Integer postId, final String postReplyContent, final String userId) throws Exception {
		SysForumPostReply forumPostReply = new SysForumPostReply();
    	int postReplyId =forumPostReply.getPostReplyId();
		Timestamp timestamp = commonMapper.selectSysDate();
	//	cctForumPostReply.setPostReplyId(postReplyId);
		forumPostReply.setPostId(postId);
		forumPostReply.setPostReplyContent(postReplyContent);
		forumPostReply.setState(1); // 状态 1 正常
		forumPostReply.setCreateUser(userId);
		forumPostReply.setUpdateUser(userId);
		forumPostReply.setCreateDate(timestamp);
		forumPostReply.setUpdateDate(timestamp);
		sysForumPostReplyMapper.insertSelective(forumPostReply);
		forumPostMapper.replyNumAdd(forumPostReply.getPostId()); // 总回复数
		forumPostMapper.newReplyNumAdd(forumPostReply.getPostId()); // 新回复数
	
		return postReplyId;
	}
	
	/**
	 * 查询地区
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryAreas() throws Exception {
		return forumPostMapper.getAreas();
	}
	
	/**
	 * 查询地区
	 * @param areaId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryAreaById(final String areaId) throws Exception {
		return  forumPostMapper.getAreaById(areaId);
	}

}
