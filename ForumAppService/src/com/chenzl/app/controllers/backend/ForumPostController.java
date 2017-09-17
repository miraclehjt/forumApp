package com.chenzl.app.controllers.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chenzl.app.bean.backend.forum.RecommendPost;
import com.chenzl.app.service.backend.common.CommonUtilService;
import com.chenzl.app.service.backend.forum.ForumPostService;
import com.github.pagehelper.Page;

/**
 * 社区提问管理
 * @author chenzl
 *
 */

@Controller
@RequestMapping("manages/forumPost")
public class ForumPostController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ForumPostController.class);

	@Autowired
	private ForumPostService forumPostService;

	
	/**
	 * 分页查询贴子
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getForumPostList", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getForumPostList(
			HttpServletRequest request) throws Exception {
		LOGGER.debug("ForumPostControlller.getForumPostList...");
		String keyword = request.getParameter("keyword");
		String userId = request.getParameter("userId");
		String areaId = request.getParameter("areaId");
		String sessionId = request.getParameter("sessionId");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		JSONObject returnObject = new JSONObject();
		int iPage = 0;
		int iRows = 0;
		if (!StringUtils.isBlank(page)) {
			iPage = Integer.parseInt(page);
		} else {
			iPage = 1;
		}
		if (!StringUtils.isBlank(rows)) {
			iRows = Integer.parseInt(rows);
		} else {
			iRows = 10;
		}
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("page", Integer.valueOf(iPage));
			params.put("rows", Integer.valueOf(iRows));
			if (!StringUtils.isBlank(keyword)) {
				params.put("searchKeyword", keyword);
			}
		/*if (!StringUtils.isBlank(userId) && userId.indexOf("SCKF") == -1) {
				params.put("areaId", areaId);
			}*/
			params.put("state", "1"); // 状态 [1] 正常
			Page<RecommendPost> pageList = this.forumPostService
					.queryForumPostForPage(params);
			returnObject.put("rows", pageList);
			returnObject.put("total", pageList.getTotal());
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}

	/**
	 * 删除帖子
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteForumPost", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> deleteForumPost(HttpServletRequest request)
			throws Exception {

		String postId = request.getParameter("postId");
		String userId = request.getParameter("userId");
	

		try {

			JSONObject returnObject = new JSONObject();

			returnObject.put("retCode", 0);
			returnObject.put("retMsg", "删除成功");

			this.forumPostService.deletePostById(Integer.valueOf(postId),userId);

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);

			return responseEntity;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}

	}

	/**
	 * 推荐
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
@RequestMapping(value = "recommendPost", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> recommendPost(HttpServletRequest request)
			throws Exception {
		String postId = request.getParameter("postId");
		String userId = request.getParameter("userId");
		String areaIds = request.getParameter("areaIds");
		String status = request.getParameter("status");
	//	String recommendId = request.getParameter("recommendId");

		try {
			JSONObject returnObject = new JSONObject();
	
			if (status.equals("1")) {
				this.forumPostService.recommendPost(Integer.valueOf(postId),
						areaIds, userId);
			} else {
				this.forumPostService.notRecommendPost(Integer.valueOf(postId), areaIds, userId);
			}

			returnObject.put("retCode", 0);
			returnObject.put("retMsg", "设置成功");

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);

			return responseEntity;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}

	}
	
	
	/*
	 * 获取推荐区域
	 */
@RequestMapping(value = "getPostRecommendAreas", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getPostRecommendAreas(
			HttpServletRequest request) throws Exception {
	
		String postId = request.getParameter("postId");
		
		try {

			JSONObject returnObject = new JSONObject();

			List<String> areas = this.forumPostService
					.getPostRecommendAreas(Integer.valueOf(postId));

			returnObject.put("data", areas);
			returnObject.put("retCode", 0);
			returnObject.put("retMsg", "success");

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);

			return responseEntity;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}

	}
	/**
	 * 获取提问详情
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getFormPostDetail", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getFormPostDetail(
			HttpServletRequest request) throws Exception {

		String postId = request.getParameter("postId");

		try {

			JSONObject returnObject = new JSONObject();

			Map<String, Object> dataObject = this.forumPostService
					.getForumPostDetail(Integer.valueOf(postId));
			System.out.println("+++++++++++++查询评论列别哦+++++++++"+dataObject);
			returnObject.put("data", dataObject);
			returnObject.put("retCode", 0);
			returnObject.put("retMsg", "success");
System.out.println("+++++++++++++评论结果+++++++++"+returnObject);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);

			return responseEntity;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}

	}



	/**
	 * 删除评论回复
	 * @param request
	 * @return
	 * @throws Exception
	 */
@RequestMapping(value = "deleteReplyDebate", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> deleteReplyDebate(
			HttpServletRequest request) throws Exception {

		String replyDebateId = request.getParameter("replyDebateId");

		try {
			JSONObject returnObject = new JSONObject();

			this.forumPostService.deleteReplyDebate(replyDebateId);

			returnObject.put("retCode", 0);
			returnObject.put("retMsg", "操作成功");

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);

			return responseEntity;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}

	}
	
	/**
	 * 删除回复
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "deletePostReply", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> deletePostReply(
			HttpServletRequest request) throws Exception {

		String postReplyId = request.getParameter("postReplyId");

		try {
			JSONObject returnObject = new JSONObject();

			this.forumPostService.deletePostReply(postReplyId);

			returnObject.put("retCode", 0);
			returnObject.put("retMsg", "操作成功");

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);

			return responseEntity;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}

	}

	/*
	 * 管理员发起话题
	 */
	@RequestMapping(value = "saveForumPost", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> saveForumPost(HttpServletRequest request) throws Exception {

		// String staffId = postFormWrapper.getCreateStaff();
		String userId = request.getParameter("userId");
		String postTitle = request.getParameter("postTitle");
		String postContent = request.getParameter("postContent");
		String areaId = request.getParameter("postArea");
		String categoryId=request.getParameter("categoryId");
		String postType=request.getParameter("postType");

		try {
			JSONObject returnObject = new JSONObject();

			this.forumPostService.saveForumPost(Integer.valueOf(categoryId),postType,
					postTitle, postContent, userId, areaId);

			returnObject.put("retCode", 0);
			returnObject.put("retMsg", "操作成功");

			 ResponseEntity<JSONObject> responseEntity = new
			 ResponseEntity<JSONObject>(
			 returnObject, org.springframework.http.HttpStatus.OK);
			
			 return responseEntity;

//			return "forum/post_list";

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;

//			request.setAttribute("msg", "操作失败");

//			return "forum/post_list";
		}

	}
	

	/**
	 * 获取员工是否有省级权限
	 * @param request
	 * @return
	 * @throws Exception
	 */
/*	@RequestMapping(value = "getStaffPower", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getStaffPower(HttpServletRequest request) throws Exception {
		String staffId = request.getParameter("staffId");
		
		Map<String, Object> moduleParams = new HashMap<String, Object>();
		moduleParams.put("staffId", staffId);
		moduleParams.put("moduleId", "app0014");
		
		try {
			long roleModule=commonUtilService.getModuleRole(moduleParams);
			
			String role=String.valueOf(roleModule);
			
			JSONObject returnObject = new JSONObject();
			returnObject.put("retCode", 0);
			returnObject.put("retMsg", "操作成功");
		
			if(role.equals(CommonConstant.PROVINCE_FLAG)){
				returnObject.put("provincePower", 1);
			}
			else {
				returnObject.put("provincePower", 0);
			}
			
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);

			return responseEntity;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
		
		
	}*/
	
	/*@RequestMapping(value = "getAllAreas", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getAllAreas(HttpServletRequest request) throws Exception {
		LOGGER.debug("ForumPostController.getAllAreas...");

		JSONObject returnObject = new JSONObject();
		try {
			List<Map<String,Object>> areaList = mkActivityService.queryAreas();;
	
			returnObject.put("data", areaList);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = 
					new ResponseEntity<JSONObject>(new JSONObject(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}*/
	

@RequestMapping(value = "getDisplayAreas", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getDisplayAreas(HttpServletRequest request) throws Exception {
		LOGGER.debug("ForumPostController.getDisplayAreas...");
		String userId = request.getParameter("userId");
		String areaId = request.getParameter("areaId");
		String sessionId = request.getParameter("sessionId");
		JSONObject returnObject = new JSONObject();
		try {
			List<Map<String,Object>> areaList = null;

				areaList = forumPostService.queryAreaById(areaId);
			
			returnObject.put("data", areaList);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = 
					new ResponseEntity<JSONObject>(new JSONObject(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	

@RequestMapping(value = "getAreas", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getAreas(HttpServletRequest request) throws Exception {
		LOGGER.debug("ForumPostController.getAreas...");
		String userId = request.getParameter("userId");
		String areaId = request.getParameter("areaId");
		String sessionId = request.getParameter("sessionId");
		JSONObject returnObject = new JSONObject();

		try {
			
			List<Map<String,Object>> areaList = null;
				areaList = forumPostService.queryAreaById(areaId);
			returnObject.put("data", areaList);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = 
					new ResponseEntity<JSONObject>(new JSONObject(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	/**
	 * 置顶回复
	 */
@RequestMapping(value = "stickyPostReply", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> stickyPostReply(
			HttpServletRequest request) throws Exception {

		String postReplyId = request.getParameter("postReplyId");
		String postId = request.getParameter("postId");

		try {
			JSONObject returnObject = new JSONObject();

			this.forumPostService.stickyPostReply(postReplyId,postId);

			returnObject.put("retCode", 0);
			returnObject.put("retMsg", "操作成功");

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);

			return responseEntity;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}

	}
	
	/**
	 * 修改问题标题
	 */
@RequestMapping(value = "modifyPostTitle", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> modifyPostTitle(
			HttpServletRequest request) throws Exception {

		String postTitle = request.getParameter("postTitle");
		String postId = request.getParameter("postId");

		try {
			JSONObject returnObject = new JSONObject();

			this.forumPostService.modifyPostTitle(postId,postTitle);

			returnObject.put("retCode", 0);
			returnObject.put("retMsg", "操作成功");

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);

			return responseEntity;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}

	}

	
	/**
	 * 修改问题内容
	 */
	@RequestMapping(value = "modifyPostContent", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> modifyPostContent(
			HttpServletRequest request) throws Exception {

		String postContent = request.getParameter("postContent");
		String postId = request.getParameter("postId");

		try {
			JSONObject returnObject = new JSONObject();

			this.forumPostService.modifyPostContent(postId,postContent);

			returnObject.put("retCode", 0);
			returnObject.put("retMsg", "操作成功");

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);

			return responseEntity;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}

	}
	/**增加
	 * 管理员直接回复问题
	 * @param request
	 * @return
	 * @throws Exception
	 */
@RequestMapping(value = "addPostReplyContent", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addPostReplyContent(
			HttpServletRequest request) throws Exception {
		String postReplyContent = request.getParameter("postReplyContent");
		String postId = request.getParameter("postId");
		String userId = request.getParameter("userId");
		String areaId = request.getParameter("areaId");
		String sessionId = request.getParameter("sessionId");
		JSONObject returnObject = new JSONObject();
		try {
			StringBuffer content = new StringBuffer();
			content.append(postReplyContent);
			int postReplyId = forumPostService.createForumPostReply(Integer.valueOf(postId), content.toString(), userId);
			returnObject.put("postReplyId", postReplyId);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(	returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}

	}
	/**
	 * 修改回复内容
	 */
@RequestMapping(value = "modifyPostReplyContent", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> modifyPostReplyContent(
			HttpServletRequest request) throws Exception {

		String postReplyContent = request.getParameter("postReplyContent");
		String postReplyId = request.getParameter("postReplyId");

		try {
			JSONObject returnObject = new JSONObject();

			this.forumPostService.modifyPostReplyContent(Integer.valueOf(postReplyId),postReplyContent);

			returnObject.put("retCode", 0);
			returnObject.put("retMsg", "操作成功");

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);

			return responseEntity;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}

	}
}
