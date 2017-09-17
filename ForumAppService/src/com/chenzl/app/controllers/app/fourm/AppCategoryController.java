package com.chenzl.app.controllers.app.fourm;

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

import com.chenzl.app.bean.app.ForumPostReply;
import com.chenzl.app.bean.app.ForumPostReplyBean;
import com.chenzl.app.entity.SysForumCategory;
import com.chenzl.app.entity.SysForumPost;
import com.chenzl.app.service.forum.AppCategoryService;
import com.chenzl.app.service.forum.AppForumPostService;
import com.github.pagehelper.Page;


@Controller
@RequestMapping("app/forum")
public class AppCategoryController {
   
   private static final Logger LOGGER = LoggerFactory.getLogger(AppCategoryController.class);
   
   @Autowired
   private AppCategoryService appcategoryservice;
   
   @Autowired
   private AppForumPostService appForumPostService;
   
   /**
    * 查询版块的信息
    * @param request
    * @return
    * @throws Exception
    */
   @RequestMapping(value = "queryCategoryForPage", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> queryCategoryForPage(
			HttpServletRequest request) throws Exception {
		LOGGER.debug("AppCategoryController.queryCategoryForPage...");
		String categoryId = request.getParameter("categoryId");
		String categoryType=request.getParameter("categoryType");
		String categoryName=request.getParameter("categoryName");
		String userId = request.getParameter("userId");
		String sessionId = request.getParameter("sessionId");
		JSONObject returnObject = new JSONObject();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			if (!StringUtils.isBlank(categoryName)) {
				params.put("categoryName", categoryName);
			}
			params.put("categoryId", categoryId);
			params.put("userId", userId);
			params.put("categoryType", categoryType);
			params.put("state", 1);
			List<SysForumCategory> list = appcategoryservice.appQueryCategoryForPage(params);
			returnObject.put("data", list);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
   
   /**
    * 查询版块下的社区提问
    * @param request
    * @return
    */
   @RequestMapping(value="queryForumPostList", method = RequestMethod.GET)
  	public ResponseEntity<JSONObject> queryForumPostList(HttpServletRequest request)throws Exception {
  		LOGGER.debug("AppCategoryController.queryForumPostList...");
  		String userId = request.getParameter("userId");
  		String areaId = request.getParameter("areaId");
  		String sessionId = request.getParameter("sessionId");
  		String categoryId = request.getParameter("categoryId");
  		String postTitle = request.getParameter("postTitle");
  		String postType = request.getParameter("postType");
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
  			if (!StringUtils.isBlank(categoryId) && !"null".equals(categoryId)) {
  				params.put("categoryId", Integer.valueOf(categoryId));
  			}
  			if (!StringUtils.isBlank(postType) && !"null".equals(postType)) {
  				params.put("postType", postType);
  			}
			params.put("areaId", areaId);
			params.put("auditState", 1); // 审核状态 [1] 审核通过
  			params.put("state", 1); // 状态 [1] 正常
			params.put("kbIdIsNull", "true");
  			if (!StringUtils.isBlank(postTitle)) {
  				params.put("postTitle", postTitle);
  			}
  			else{
  				Page<SysForumPost> pageList =appcategoryservice.appQueryForumPostList(params);
  	  			returnObject.put("rows", pageList);
  	  			returnObject.put("pages", pageList.getPages());
  			}
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
    * app端查询社区提问对应的回复
    * @param request
    * @return
    * @throws Exception
    */
   @RequestMapping(value = "appQueryPostReplyList", method = RequestMethod.GET)
   public ResponseEntity<JSONObject> appQueryPostReplyList(HttpServletRequest request) throws Exception{
	   LOGGER.debug("AppCategoryController.appQueryPostReplyList...");
	    String postId=request.getParameter("postId");
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
  			params.put("postId", postId);
			params.put("state", 1); // 状态 [1] 正常
 			Page<ForumPostReply> pageList =appcategoryservice.appQueryPostReplyList(params);
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
    * app端查询社区提问对应的回复
    * @param request
    * @return
    * @throws Exception
    */
  /* @RequestMapping(value = "appEmergencyQueryPostReplyList", method = RequestMethod.GET)
   public ResponseEntity<JSONObject> appEmergencyQueryPostReplyList(HttpServletRequest request) throws Exception{
	   LOGGER.debug("AppCategoryController.appEmergencyQueryPostReplyList...");
	    String postId=request.getParameter("postId");
 		String userId = request.getParameter("userId");
 		String areaId = request.getParameter("areaId");
 		String sessionId = request.getParameter("sessionId");
 		JSONObject returnObject = new JSONObject();
 		try {
 			Map<String, Object> params = new HashMap<String, Object>();
  			params.put("postId", postId);
			params.put("state", Short.valueOf("1")); // 状态 [1] 正常
 			List<SysForumPostReply> pageList =appcategoryservice.appEmergencyPostReplyList(params);
  			returnObject.put("rows", pageList);
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
   */
   /**
    * 查询回复评论列表
    * @param request
    * @return
    * @throws Exception
    */
	/*@RequestMapping(value = "appQueryReplyDebateList", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> appQueryReplyDebateList(HttpServletRequest request) throws Exception{
		String postReplyId = request.getParameter("postReplyId");
		String userId = request.getParameter("userId");
 		String areaId = request.getParameter("areaId");
 		String sessionId = request.getParameter("sessionId");
 		JSONObject returnObject = new JSONObject();
 		try {
 			List<SysForumReplyDebate> cctForumReplyDebateList = appcategoryservice.getSysForumReplyDebateByPostReplyId(Integer.valueOf(postReplyId));
 			returnObject.put("data", cctForumReplyDebateList);
 			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
 			return responseEntity;
 		} catch (Exception e) {
 			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(),org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
 			return responseEntity;
 		}
	}
	*/
	/**
	 * 根据提问ID获取提问信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
@RequestMapping(value = "getForumPost", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getForumPost(HttpServletRequest request) throws Exception{
		String postId = request.getParameter("postId");
		String userId = request.getParameter("userId");
 		String areaId = request.getParameter("areaId");
 		String sessionId = request.getParameter("sessionId");
 		JSONObject returnObject = new JSONObject();
 		try {
 			SysForumPost cctForumPost = appForumPostService.getForumPost(Integer.valueOf(postId));
 			returnObject.put("data", cctForumPost);
 			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
 			return responseEntity;
 		} catch (Exception e) {
 			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(),org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
 			return responseEntity;
 		}
	}
	/**
	 * 条件查询版块社区问题
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="querySearchPostList", method = RequestMethod.POST)
  	public ResponseEntity<JSONObject> querySearchPostList(HttpServletRequest request)throws Exception {
  		LOGGER.debug("AppCategoryController.queryForumPostList...");
  		String userId = request.getParameter("userId");
  		String areaId = request.getParameter("areaId");
  		String sessionId = request.getParameter("sessionId");
  		String categoryId = request.getParameter("categoryId");
  		String postTitle = request.getParameter("postTitle");
  		String postType = request.getParameter("postType");
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
  			if (!StringUtils.isBlank(categoryId) && !"null".equals(categoryId)) {
  				params.put("categoryId", Integer.valueOf(categoryId));
  			}
  			if (!StringUtils.isBlank(postType) && !"null".equals(postType)) {
  				params.put("postType", Integer.valueOf(postType));
  			}
			params.put("areaId", areaId);
			params.put("auditState",1); // 审核状态 [1] 审核通过
  			params.put("state", 1); // 状态 [1] 正常
  			if (!StringUtils.isBlank(postTitle)) {
  				params.put("postTitle", postTitle);
  			}
  			Page<SysForumPost> pageList =appcategoryservice.appQueryForumPostList(params);
  			returnObject.put("rows", pageList);
  			returnObject.put("pages", pageList.getPages());
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
	 * 条件查询版块社区问题
	 * @param request
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value="queryPostListByKbId", method = RequestMethod.POST)
  	public ResponseEntity<JSONObject> queryPostListByKbId(HttpServletRequest request)throws Exception {
  		LOGGER.debug("AppCategoryController.queryPostListByKbId...");
  		String userId = request.getParameter("userId");
  		String areaId = request.getParameter("areaId");
  		String sessionId = request.getParameter("sessionId");
  		String kbId = request.getParameter("kbId");
  		String pageNum = request.getParameter("pageNum");
  		String pageSize = request.getParameter("pageSize");
  		JSONObject returnObject = new JSONObject();
  		int iPageNum = 0;
  		int iPageSize = 0;
  		if (!StringUtils.isBlank(pageNum)) {
  			iPageNum = Integer.parseInt(pageNum);
  		} else {
  			iPageNum = 1;
  		}
  		if (!StringUtils.isBlank(pageSize)) {
  			iPageSize = Integer.parseInt(pageSize);
  		} else {
  			iPageSize = 10;
  		}
  		try {
  			Map<String, Object> params = new HashMap<String, Object>();
  			params.put("state", Short.valueOf("1"));
			params.put("auditState", Short.valueOf("1"));
			params.put("kbId", kbId);
			params.put("pageNum", Integer.valueOf(iPageNum));
			params.put("pageSize", Integer.valueOf(iPageSize));
  			Page<SysForumPost> pageList = appForumPostService.getForumPostByKbId(params);
  			returnObject.put("rows", pageList);
  			returnObject.put("pageNum", pageList.getPageNum());
  			returnObject.put("pages", pageList.getPages());
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
     }*/
}
