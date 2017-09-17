package com.chenzl.app.controllers.backend;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chenzl.app.entity.SysForumAuditUser;
import com.chenzl.app.bean.backend.forum.SysForumPostSelect;
import com.chenzl.app.constant.CommonConstant;
import com.chenzl.app.entity.SySUserInfo;
import com.chenzl.app.service.backend.common.CommonUtilService;
import com.chenzl.app.service.backend.forum.ForumPostService;
import com.chenzl.app.service.backend.forum.ForumService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("manages/forum")
public class ForumController {

private static final Logger LOGGER = LoggerFactory.getLogger(ForumController.class);
	
	@Autowired
	private ForumService forumService;

	// 查询提问列表
	@RequestMapping(value = "getForumPostList", method = RequestMethod.POST)
	public ResponseEntity<PageInfo<SysForumPostSelect>> getForumPostList(HttpServletRequest request)
			throws UnsupportedEncodingException {
		LOGGER.debug("ForumController.getForumPostList...");
		String userId = request.getParameter("userId");
		String areaId = request.getParameter("areaId");
		String sessionId = request.getParameter("sessionId");
		String postNameSearch = request.getParameter("postNameSearch");
		String auditStateSearch = request.getParameter("auditStateSearch");
		String forumcategoryId = request.getParameter("forumcategoryId");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		int iPage = 0;
		int iRows = 0;
		String postTitle = "";
		if (!StringUtils.isBlank(postNameSearch)) {
			postTitle = postNameSearch;
		}
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
			params.put("areaId", areaId);
			params.put("page", Integer.valueOf(iPage));
			params.put("rows", Integer.valueOf(iRows));
			params.put("userId", userId);
			if (!StringUtils.isBlank(postTitle)) {
				params.put("postTitle", postTitle);
			}
			if (!StringUtils.isBlank(auditStateSearch)) {
				params.put("auditState", auditStateSearch);
			}
			
		SysForumAuditUser  auditUser = this.forumService.getAuditUserById(userId);
			Integer auditCategoryId = auditUser.getCategoryId();
			Integer state = auditUser.getState();
			System.out.println(state+"+++++++++++auditUser state+++++++++++++++++++++"+auditCategoryId+"+++++auditCategoryId ++++++++++++++++++++++++++++++");
			if (state.equals(0)) {
				PageInfo<SysForumPostSelect> list = new PageInfo<SysForumPostSelect>();
				return ResponseEntity.ok(list);
			}
			
			if (!StringUtils.isBlank(forumcategoryId)) {
				params.put("categoryId", forumcategoryId);
			}
			else {
				if(auditCategoryId==null) {//配置了审核权限但没添加进审核人员列表
					PageInfo<SysForumPostSelect> list = new PageInfo<SysForumPostSelect>();
					return ResponseEntity.ok(list);
				}
				else if (auditCategoryId != 0) {
					params.put("categoryId", auditCategoryId);
				}
			}
			PageInfo<SysForumPostSelect> list = forumService.getForumPostList(params);
			return ResponseEntity.ok(list);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	// 审核提问
	@RequestMapping(value = "saveCheck", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> saveCheck(HttpServletRequest request) throws Exception {
		LOGGER.debug("ForumController.saveCheck...");
		String postId = request.getParameter("postId");
		String auditState = request.getParameter("auditState");
		String auditComment = request.getParameter("auditComment");
		String userId = request.getParameter("userId");
		String areaId = request.getParameter("areaId");
		String sessionId = request.getParameter("sessionId");
		String noticeFlag=request.getParameter("noticeFlag");
		String createUser=request.getParameter("createUser");
		JSONObject returnObject = new JSONObject();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("postId", Integer.valueOf(postId));
		params.put("auditState", Integer.valueOf(auditState));
		params.put("auditUser", userId);
		params.put("noticeFlag", noticeFlag);
		params.put("createUser", createUser);

		if (!StringUtils.isBlank(auditComment)) {
			params.put("auditComment", auditComment);
		}
		try {
			int i = forumService.saveCheck(params);
             
			if (i == 0) {
				returnObject.put("retCode", "0");
				returnObject.put("retMsg", "提问审核失败！");
			} else {
				returnObject.put("retCode", "1");
				returnObject.put("retMsg", "提问审核成功！");
			}

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject,
					org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	// 查询审核人员列表
	@RequestMapping(value = "getAuditUser", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getAuditUser(HttpServletRequest request) throws UnsupportedEncodingException {
		LOGGER.debug("ForumController.getAuditUser...");
		JSONObject returnObject = new JSONObject();
		String auditUser = request.getParameter("auditUser");
		String stateSearch = request.getParameter("stateSearch");
		String areaSearch = request.getParameter("areaSearch");
		String userId = request.getParameter("userId");
		String areaId = request.getParameter("areaId");
		String sessionId = request.getParameter("sessionId");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
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
			if (!StringUtils.isBlank(auditUser)) {
				params.put("auditUser", auditUser);
			}
			if (!StringUtils.isBlank(stateSearch)) {
				params.put("stateSearch", stateSearch);
			}
			Page<SysForumAuditUser> pageList = forumService.getAuditUserList(params);

			returnObject.put("rows", pageList);
			returnObject.put("total", pageList.getTotal());

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject,
					org.springframework.http.HttpStatus.OK);
			return responseEntity;

		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}

	// 增加审核人员
	@RequestMapping(value = "insertAuditStaff", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> insertAuditStaff(HttpServletRequest request) throws Exception {
		LOGGER.debug("ForumController.insertAuditStaff...");
		String auditStaff = request.getParameter("auditStaff");
		String areaIdSelect = request.getParameter("areaIdSelect");
		// String staffId = request.getParameter("staffId");
		// String areaId = request.getParameter("areaId");
		// String sessionId = request.getParameter("sessionId");
		JSONObject returnObject = new JSONObject();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("staffId", auditStaff);
		params.put("areaId", areaIdSelect);
		params.put("categoryId", 0);

		try {

			int i = forumService.insertAuditUser(params);
			if (i == 0) {
				returnObject.put("retCode", "0");
				returnObject.put("retMsg", "新增审核人员失败！");
			} else if (i == 1) {
				returnObject.put("retCode", "1");
				returnObject.put("retMsg", "新增审核人员成功！");
			} else {
				returnObject.put("retCode", "2");
				returnObject.put("retMsg", "该人员已添加为该审核人员！");
			}

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject,
					org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}

	// 删除审核人员
	@RequestMapping(value = "deleteAuditUser", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> deleteAuditUser(HttpServletRequest request) {
		LOGGER.debug("ForumController.deleteAuditUser...");

		String auditUser = request.getParameter("auditUser");
		JSONObject returnObject = new JSONObject();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", auditUser);
		try {
			int result = forumService.deleteAuditUser(params);
			if (result == 0) {
				returnObject.put("retCode", "0");
				returnObject.put("retMsg", "删除审核人员失败！");
			} else {
				returnObject.put("retCode", "1");
				returnObject.put("retMsg", "删除审核人员成功！");
			}

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject,
					org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}

	// 对审核人员进行生效失效处理
	@RequestMapping(value = "setAuditUserEnable", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> setAuditUserEnable(HttpServletRequest request) {
		LOGGER.debug("ForumController.setAuditUserEnable...");

		String auditUser = request.getParameter("auditUser");
		String state = request.getParameter("state");
		JSONObject returnObject = new JSONObject();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", auditUser);
		params.put("state", Integer.valueOf(state));

		try {
			int result = forumService.setAuditUserEnable(params);
			if (result == 0) {
				returnObject.put("retCode", "0");
				returnObject.put("retMsg", "操作失败！");
			} else {
				returnObject.put("retCode", "1");
				returnObject.put("retMsg", "操作成功！");
			}

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject,
					org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}

	// 设置版块
	@RequestMapping(value = "setAuditCategory", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> setAuditCategory(HttpServletRequest request) {
		LOGGER.debug("ForumController.setAuditCategory...");
		String auditStaff = request.getParameter("auditStaff");
		String categoryId = request.getParameter("categoryId");
		JSONObject returnObject = new JSONObject();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("staffId", auditStaff);
		params.put("categoryId", categoryId);

		try {
			int result = forumService.setAuditCategory(params);
			if (result == 0) {
				returnObject.put("retCode", "0");
				returnObject.put("retMsg", "操作失败！");
			} else {
				returnObject.put("retCode", "1");
				returnObject.put("retMsg", "操作成功！");
			}

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject,
					org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
}

