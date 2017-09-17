package com.chenzl.app.controllers.app.fourm;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chenzl.app.bean.app.ForumPostBean;
import com.chenzl.app.bean.app.ForumPostReplyBean;
import com.chenzl.app.bean.app.ForumReplyDebateBean;
import com.chenzl.app.entity.SysForumCategory;
import com.chenzl.app.entity.SysForumPost;
import com.chenzl.app.entity.SysForumPostReply;
import com.chenzl.app.entity.SysForumReplyDebate;
import com.chenzl.app.service.backend.common.CommonUtilService;
import com.chenzl.app.service.forum.AppCategoryService;
import com.chenzl.app.service.forum.AppForumPostService;
import com.github.pagehelper.Page;

@Controller
@RequestMapping("app/forumpost")
public class AppForumPostController {

	private static final String TAG = AppForumPostController.class.getSimpleName();
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AppForumPostController.class);
	
	@Autowired
	private AppCategoryService appCategoryService;
	
	@Autowired
	private AppForumPostService appForumPostService;
	
	@Autowired
	private CommonUtilService commonUtilService;
	
	/**
	 * 查询查询互动提问版块
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getCategorys", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getCategorys(HttpServletRequest request) throws Exception {
		LOGGER.debug("AppCategoryController.getCategorys...");
		String userId = request.getParameter("userId");
		String sessionId = request.getParameter("sessionId");
		JSONObject returnObject = new JSONObject();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("state", 1);
			params.put("notCategoryType", 2);
			List<SysForumCategory> list = appCategoryService.queryForumCategoryForApp(params);
			returnObject.put("data", list);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			LOGGER.error(TAG + "getCategorys", e);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	/**
	 * 保存提问回复
	 * @param request
	 * @param forumPostReply
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "saveForumPostReply", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> saveForumPostReply(HttpServletRequest request, ForumPostReplyBean forumPostReply) throws Exception {
		LOGGER.debug("AppForumPostController.saveForumPostReply...");
		JSONObject returnObject = new JSONObject();
		System.out.println(forumPostReply.getPostId());
		Integer postReplyId = forumPostReply.getPostReplyId();
		System.out.println(forumPostReply.getPostReplyContent()+"+++++++++++++++++++++++++postrepltcontent+++++++++++++++++"+postReplyId);
		SysForumPostReply cctForumPostReply = new SysForumPostReply();
		BeanUtils.copyProperties(forumPostReply, cctForumPostReply);
		try {
			if (postReplyId == null) {
				cctForumPostReply.setCreateUser(forumPostReply.getUserId());
				cctForumPostReply.setUpdateUser(forumPostReply.getUserId());
				cctForumPostReply = appForumPostService.createCctForumPostReply(cctForumPostReply);
				System.out.println("cctForumPostReply"+cctForumPostReply);
			} else {
				cctForumPostReply.setUpdateUser(forumPostReply.getUserId());
				cctForumPostReply = appForumPostService.updateCctForumPostReply(cctForumPostReply);
			}
			if (cctForumPostReply == null) {
				returnObject.put("retCode", "1");
				returnObject.put("retMsg", "保存成功");
				returnObject.put("data", cctForumPostReply);
			} else {
				returnObject.put("retCode", "0");
				returnObject.put("retMsg", "保存失败");
				returnObject.put("data", cctForumPostReply);
			}
			returnObject.put("data", cctForumPostReply);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			LOGGER.error(TAG + "saveForumPostReply", e);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	/**
	 * 保存回复评论
	 * @param request
	 * @param forumReplyDebate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "saveForumReplyDebate", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> saveForumReplyDebate(HttpServletRequest request, ForumReplyDebateBean forumReplyDebate) throws Exception {
		LOGGER.debug("AppForumPostController.saveForumReplyDebate...");
		JSONObject returnObject = new JSONObject();
		Integer postReplyDebateId = forumReplyDebate.getPostReplyDebateId();
		SysForumReplyDebate cctForumReplyDebate = new SysForumReplyDebate();
		BeanUtils.copyProperties(forumReplyDebate, cctForumReplyDebate);
		try {
			int i = 0;
			if (postReplyDebateId == null) {
				cctForumReplyDebate.setCreateUser(forumReplyDebate.getUserId());
				cctForumReplyDebate.setUpdateUser(forumReplyDebate.getUserId());
				i = appForumPostService.createCctForumReplyDebate(cctForumReplyDebate);
			} else {
				cctForumReplyDebate.setUpdateUser(forumReplyDebate.getUserId());
				i = appForumPostService.updateCctForumReplyDebate(cctForumReplyDebate);
			}
			if (i == 1) {
				returnObject.put("retCode", "1");
				returnObject.put("retMsg", "保存成功");
			} else {
				returnObject.put("retCode", "0");
				returnObject.put("retMsg", "保存失败");
			}
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			LOGGER.error(TAG + "saveForumReplyDebate", e);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	/**
	 * 提问回复已读标示
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "forumPostReaded", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> forumPostReaded(HttpServletRequest request) throws Exception {
		LOGGER.debug("AppForumPostController.forumPostReaded...");
		String userId = request.getParameter("userId");
		String areaId = request.getParameter("areaId");
		String sessionId = request.getParameter("sessionId");
		String postId = request.getParameter("postId");
		JSONObject returnObject = new JSONObject();
		try {
			appForumPostService.forumPostReaded(Integer.valueOf(postId), userId);
			returnObject.put("retCode", "1");
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			LOGGER.error(TAG + "forumPostReaded", e);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	/**
	 * 获取与我相关帖子的统计
	 * @param request
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value = "getForumPostCount", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getForumPostCount(HttpServletRequest request) throws Exception {
		LOGGER.debug("AppForumPostController.forumPostReaded...");
		String userId = request.getParameter("userId");
		String areaId = request.getParameter("areaId");
		String sessionId = request.getParameter("sessionId");
		JSONObject returnObject = new JSONObject();
		try {
			ForumPostCount forumPostCount = appForumPostService.queryForumPostCount(userId);
			returnObject.put("data", forumPostCount);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			LOGGER.error(TAG + "getForumPostCount", e);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}*/
	
	/**
	 * 保存提问
	 * @param request
	 * @param forumPost
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "saveForumPost", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> saveForumPost(HttpServletRequest request, ForumPostBean forumPost) throws Exception {
		LOGGER.debug("AppForumPostController.saveForumPost...");
		JSONObject returnObject = new JSONObject();
		Integer postId = forumPost.getPostId();
		SysForumPost cctForumPost = new SysForumPost();
		BeanUtils.copyProperties(forumPost, cctForumPost);
		try {
			int i = 0;
			if (postId == null) {
				cctForumPost.setCreateUser(forumPost.getUserId());
				cctForumPost.setUpdateUser(forumPost.getUserId());
				i = appForumPostService.createCctForumPost(cctForumPost);
			} else {
				cctForumPost.setUpdateUser(forumPost.getUserId());
				i = appForumPostService.updateSysForumPost(cctForumPost);
			}
			if (i == 1) {
				returnObject.put("retCode", "1");
				returnObject.put("retMsg", "保存成功");
			} else {
				returnObject.put("retCode", "0");
				returnObject.put("retMsg", "保存失败");
			}
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			LOGGER.error(TAG + "saveForumPost", e);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}

	/**
	 * 查询推荐提问 分页
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getRecommendPosts", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getRecommendPosts(HttpServletRequest request) throws Exception {
		LOGGER.debug("AppForumPostController.getRecommendPosts...");
		String keyword = request.getParameter("keyword");
		String userId = request.getParameter("userId");
		String areaId = request.getParameter("areaId");
		String sessionId = request.getParameter("sessionId");
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String rolesList =request.getParameter("rolesList");
		
		boolean flag=false;
		if(!"".equals(rolesList)&&!"null".equals(rolesList)&&rolesList!=null){
			String[] rolesArr=rolesList.split(",");
			for(int i=0;i<rolesArr.length;i++){
				if(rolesArr[i].equals("appr0001")){
					flag=true;
				}
			}
		}
		JSONObject returnObject = new JSONObject();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("pageNum", Integer.valueOf(pageNum));
			params.put("pageSize", Integer.valueOf(pageSize));
			if(!flag){
				params.put("areaId", areaId);
			}	
			
			params.put("userId", userId);
			if (!StringUtils.isBlank(keyword)) {
				params.put("keyword", keyword);
			}
			
			params.put("state", "1"); // 状态 [1] 正常
				Page<SysForumPost> pageList = appForumPostService.getRecommendPosts(params);
				returnObject.put("data", pageList);
				returnObject.put("page", pageList.getPageNum());
				returnObject.put("pages", pageList.getPages());
			
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	/**
	 * 获取我发起的提问
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getMyInitiatePosts", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getMyInitiatePosts(HttpServletRequest request) throws Exception {
		LOGGER.debug("AppForumPostController.getMyInitiatePosts...");
	
		String userId = request.getParameter("userId");
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");

		JSONObject returnObject = new JSONObject();
		try {
			Map<String, Object> params = new HashMap<String, Object>();

			params.put("userId", userId);
			params.put("pageNum", Integer.valueOf(pageNum));
			params.put("pageSize", Integer.valueOf(pageSize));

			Page<SysForumPost> pageList = appForumPostService.getMyInitiatePosts(params);
			returnObject.put("data", pageList);
			returnObject.put("page", pageList.getPageNum());
			returnObject.put("pages", pageList.getPages());

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	/**
	 * 获取我回覆的提问
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getMyReplyPosts", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getMyReplyPosts(HttpServletRequest request) throws Exception {
		LOGGER.debug("AppForumPostController.getMyReplyPosts...");
	
		String userId = request.getParameter("userId");
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");

		JSONObject returnObject = new JSONObject();
		try {
			Map<String, Object> params = new HashMap<String, Object>();

			params.put("userId", userId);
			params.put("pageNum", Integer.valueOf(pageNum));
			params.put("pageSize", Integer.valueOf(pageSize));

			Page<SysForumPost> pageList = appForumPostService.getMyReplyPosts(params);
			returnObject.put("data", pageList);
			returnObject.put("page", pageList.getPageNum());
			returnObject.put("pages", pageList.getPages());

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	/**
	 * 获取我關注的提问
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getMyLikePosts", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getMyLikePosts(HttpServletRequest request) throws Exception {
		LOGGER.debug("AppForumPostController.getMyLikePosts...");
	
		String userId = request.getParameter("userId");
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");

		JSONObject returnObject = new JSONObject();
		try {
			Map<String, Object> params = new HashMap<String, Object>();

			params.put("userId", userId);
			params.put("pageNum", Integer.valueOf(pageNum));
			params.put("pageSize", Integer.valueOf(pageSize));

			Page<SysForumPost> pageList = appForumPostService.getMyLikePosts(params);
			returnObject.put("data", pageList);
			returnObject.put("page", pageList.getPageNum());
			returnObject.put("pages", pageList.getPages());

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	
	/**
	 * 获取我发起的提问的最新回复数
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getNewReplyNumForMyInitiatePosts", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getNewReplyNumForMyInitiatePosts(HttpServletRequest request) throws Exception {
		LOGGER.debug("AppForumPostController.getNewReplyNumForMyInitiatePosts...");
	
		String userId = request.getParameter("userId");
		String postId = request.getParameter("postId");

		JSONObject returnObject = new JSONObject();
		try {

			Long num = appForumPostService.getNewReplyNumForMyInitiatePosts(userId,postId);
			returnObject.put("num", num);

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	/**
	 * 获取我的评论的最新回复数
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getNewReplyNumForMyReplyPosts", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getNewReplyNumForMyReplyPosts(HttpServletRequest request) throws Exception {
		LOGGER.debug("AppForumPostController.getNewReplyNum...");
	
		String userId = request.getParameter("userId");
		String postReplyId = request.getParameter("postReplyId");

		JSONObject returnObject = new JSONObject();
		try {

			Long num = appForumPostService.getNewReplyNumForMyReplyPosts(userId,postReplyId);
			returnObject.put("num", num);

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	/**
	 * 获取我的新回复数（包括我发起的提问的回复和我的评论的回复）
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getMyNewReplyNum", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getMyNewReplyNum(HttpServletRequest request) throws Exception {
		LOGGER.debug("AppForumPostController.getNewReplyNum...");
	
		String userId = request.getParameter("userId");

		JSONObject returnObject = new JSONObject();
		try {

			Long num = appForumPostService.getNewReplyNumForMyInitiatePosts(userId,null);
			returnObject.put("newReplyNum", num);
			
			Long num1 = appForumPostService.getNewReplyNumForMyReplyPosts(userId,null);
			returnObject.put("newDebateNum", num1);

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	////分页查询热门提问（每页10条）
	@RequestMapping(value="getPostByStarNums", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getPostByStarNums(HttpServletRequest request) throws IOException {
		LOGGER.debug("AppForumPostController.getPostByStarNums...");
		JSONObject returnObject = new JSONObject();
		String areaId = (String) request.getParameter("areaId");
		String sessionId = (String) request.getParameter("sessionId");
		String userId = (String) request.getParameter("userId");
		String rolesList = (String) request.getParameter("rolesList");
		boolean flag=false;

		if(!"".equals(rolesList)&&!"null".equals(rolesList)&&rolesList!=null){
			String[] rolesArr=rolesList.split(",");
			for(int i=0;i<rolesArr.length;i++){
				if(rolesArr[i].equals("appr0001")){
					flag=true;
				}
			}
		}
		
		//分页查询参数
		String pageNum = (String) request.getParameter("pageNum");
		String pageSize = (String) request.getParameter("pageSize");
		
		int iPageNum = Integer.parseInt(pageNum);
		int iPageSize = Integer.parseInt(pageSize);
		
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			
			if(!flag){
				params.put("areaId", areaId);
			}				
			params.put("page", true);
			params.put("pageNum", iPageNum);
			params.put("pageSize", iPageSize);
			params.put("userId", userId);
        	List<SysForumPost> list = appForumPostService.getPostByStarNums(params);
  			Page<SysForumPost> page = (Page<SysForumPost>) list;
  			returnObject.put("pages",page.getPages());
  			returnObject.put("searchList", list);
			return ResponseEntity.status(HttpStatus.OK).body(returnObject);
		} catch (Exception e) {
			LOGGER.error("热门提问查询出错：   "+e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	/**
	 * 获取新回复数（包括我发起的提问的回复和我的评论的回复）
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getNewReplyNum", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getNewReplyNum(HttpServletRequest request) throws Exception {
		LOGGER.debug("AppForumPostController.getNewReplyNum...");
	
		String staffId = request.getParameter("staffId");

		JSONObject returnObject = new JSONObject();
		try {

			Long num = appForumPostService.getNewReplyNumForMyInitiatePosts(staffId,null);
			returnObject.put("newReplyNum", num);
			
			Long num1 = appForumPostService.getNewReplyNumForMyReplyPosts(staffId,null);
			returnObject.put("newDebateNum", num1);

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	/**
	 * 获取我发起问题的新回复总数
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getNewReplyNumSum", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getNewReplyNumSum(HttpServletRequest request) throws Exception {
		LOGGER.debug("AppForumPostController.getNewReplyNumSum...");
		String userId = request.getParameter("userId");
		String postType = request.getParameter("postType");
		JSONObject returnObject = new JSONObject();
		try {
			Long replyNumSum = appForumPostService.queryNewReplyNumSum(userId, Integer.valueOf(postType));
			returnObject.put("data", replyNumSum);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			LOGGER.error(TAG, e);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	/**
	 * 帖子问题点击记录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "forumPostClick", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> forumPostClick(HttpServletRequest request) throws Exception {
		LOGGER.debug("AppForumPostController.forumPostClick...");
		String postId = request.getParameter("postId");
		String userId = request.getParameter("userId");
		JSONObject returnObject = new JSONObject();
		try {
			int i = appForumPostService.createCctForumPostClick(Integer.valueOf(postId), userId);
			if (i == 1) {
				returnObject.put("retCode", "1");
			} else {
				returnObject.put("retCode", "0");
			}
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			LOGGER.error(TAG, e);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	/*@RequestMapping(value="getQuestionNum", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getQuestionNumsOfAreas(HttpServletRequest request) throws IOException {
		LOGGER.debug("AppForumPostController.getQuestionNumsOfAreas...");
		String staffId = request.getParameter("staffId");
		
		Map<String, Object> moduleParams = new HashMap<String, Object>();
		moduleParams.put("staffId", staffId);
		moduleParams.put("moduleId", "app0014");
		
		try {
			List<AreaQuestionNum> list= this.appForumPostService.getQuestionNumsOfAreas();
			
//			long roleModule=commonUtilService.getModuleRole(moduleParams);
//			String role=String.valueOf(roleModule);
			
			JSONObject returnObject = new JSONObject();
			returnObject.put("retCode", 0);
			returnObject.put("retMsg", "请求成功");
			
			returnObject.put("data", list);
		
//			if(role.equals(CommonConstant.PROVINCE_FLAG)){
//		
//			}
//			else {
//		
//			}
			
			
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);

			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}*/
}
