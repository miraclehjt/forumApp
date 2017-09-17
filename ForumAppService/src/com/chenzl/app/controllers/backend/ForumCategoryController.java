package com.chenzl.app.controllers.backend;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.chenzl.app.bean.backend.forum.ForumCategory;
import com.chenzl.app.entity.SysForumCategory;
import com.chenzl.app.service.backend.forum.ForumCategoryService;
import com.github.pagehelper.Page;

@Controller
@RequestMapping("manages/forumcategory")
public class ForumCategoryController {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ForumCategoryController.class);

	@Autowired
	private ForumCategoryService  forumgoryservice;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 *获得版块信息 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryForumCategoryForPage", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> queryForumCategoryForPage(
			HttpServletRequest request) throws Exception {
		LOGGER.debug("ForumCategoryController.queryForumCategoryForPage...");
		String categoryName = request.getParameter("categoryName");
		String userId = request.getParameter("userId");
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
			if (!StringUtils.isBlank(categoryName)) {
				params.put("categoryName", categoryName);
			}
			params.put("state", 1);
			Page<SysForumCategory> pageList = forumgoryservice.
					queryForumCategoryForPage(params);
			returnObject.put("rows", pageList);
			returnObject.put("total", pageList.getTotal());
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
	 * 根据Id查询版块
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getCategoryById", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getQuestionnaireById(
			HttpServletRequest request) throws Exception {
		LOGGER.debug("CctForumCategory.getCategoryById...");
		String categoryId = request.getParameter("categoryId");
		JSONObject returnObject = new JSONObject();
		try {
			SysForumCategory sysForumCategory = forumgoryservice.queryForumCategoryById(Integer.valueOf(categoryId));
			returnObject.put("data", sysForumCategory);
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
	 * 根据版块类型获取版块
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getForumCategoryByType", method = RequestMethod.POST)
	public  ResponseEntity<JSONObject> getForumCategoryByType(HttpServletRequest request) throws Exception {
		LOGGER.debug("ForumCategoryController.getForumCategoryByType...");
		String categoryType = request.getParameter("categoryType");
		JSONObject returnObject = new JSONObject();
		try {
			List<SysForumCategory> forumCategoryList = forumgoryservice.getForumCategoryByCategoryType(Integer.valueOf(categoryType));
			returnObject.put("data", forumCategoryList);
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
	 * 添加修改模块信息
	 * 
	 * @param request
	 * @param response
	 * @param forumcategory
	 * @throws Exception
	 */
	@RequestMapping(value = "saveForumCategory", method = RequestMethod.POST)
	public void saveForumCategory(HttpServletRequest request,
			HttpServletResponse response, ForumCategory forumcategory)
			throws Exception {
		LOGGER.debug("CctForumCategory.CctForumCategory...");
		
		SysForumCategory sysforumcategory = new SysForumCategory();
		if (forumcategory != null && forumcategory.getCategoryId()!=null) {
			sysforumcategory.setCategoryId(forumcategory.getCategoryId());
		}
		
		if (forumcategory != null) {
			sysforumcategory.setCategoryName(forumcategory.getCategoryName()); // 版块名称
			sysforumcategory.setCategoryType(forumcategory.getCategoryType()); // 版块类型
	
			if (forumcategory.getCategoryId()==null) {
				sysforumcategory.setState(1); // 状态 [1] 正常
				sysforumcategory.setCreateUser(forumcategory.getUserId());
				sysforumcategory.setUpdateUser(forumcategory.getUserId());
				forumgoryservice.addForumCategory(sysforumcategory);
			} else {
				sysforumcategory.setUpdateUser(forumcategory.getUserId());
				forumgoryservice.updateForumCategory(sysforumcategory);
			}
		}

		response.sendRedirect(request.getContextPath()
				+ "/manages/page/forumcategory.do?dicName=forum");
	}

	/**
	 * 删除板块信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteForumCategory", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> deleteForumCategory(
			HttpServletRequest request) throws Exception {
		LOGGER.debug("ForumCategoryController.deleteForumCategory...");
		String categoryId = request.getParameter("categoryId");
		String userId = request.getParameter("userId");
		JSONObject returnObject = new JSONObject();
		try {
			int i = forumgoryservice.deleteForumCategoryById(Integer.valueOf(categoryId));
			if (i == 1) {
				returnObject.put("retCode", "1");
				returnObject.put("retMsg", "删除成功");
			} else {
				returnObject.put("retCode", "0");
				returnObject.put("retMsg", "删除失败");
			}
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
	 * 获取所有板块
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getAllCategories", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getAllCategories(
			HttpServletRequest request) throws Exception {
		LOGGER.debug("CctForumCategoryController.getAllCategories...");
	
		String userId = request.getParameter("userId");
		String sessionId = request.getParameter("sessionId");

		JSONObject returnObject = new JSONObject();

		try {
			List<SysForumCategory> list = forumgoryservice.getAllCategories();
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

}
