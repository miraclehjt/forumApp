package com.chenzl.app.controllers.app.fourm;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chenzl.app.service.forum.ForumPostStarService;


@Controller
@RequestMapping("forumpoststar")
public class AppForumPostStarController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AppForumPostStarController.class);
	
	@Autowired
	private ForumPostStarService forumPostStarService;
	
	/**
	 * 判断是否已顶贴点赞
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "isForumPostStared", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> isForumPostStared(HttpServletRequest request) throws Exception {
		LOGGER.debug("QuestionnaireController.isForumPostStared...");
		String postId = request.getParameter("postId");
		String userId = request.getParameter("userId");
		String areaId = request.getParameter("areaId");
		String sessionId = request.getParameter("sessionId");
		JSONObject returnObject = new JSONObject();
		try {
			Boolean b = forumPostStarService.isForumPostStared(Integer.valueOf(postId), userId);
			returnObject.put("data", b);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	/**
	 * 顶贴点赞或取消
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "forumPostStar", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> forumPostStar(HttpServletRequest request) throws Exception {
		LOGGER.debug("QuestionnaireController.forumPostStar...");
		String postId = request.getParameter("postId");
		String userId = request.getParameter("userId");
		JSONObject returnObject = new JSONObject();
		try {
			forumPostStarService.operateForumPostStar(Integer.valueOf(postId), userId);
			returnObject.put("retCode", "1");
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
}
