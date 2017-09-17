package com.chenzl.app.controllers.backend;

import java.util.Date;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.chenzl.app.entity.CctCscappKeyword;
import com.chenzl.app.service.backend.knowledge.SearchKeywordService;
import com.github.pagehelper.Page;

@Controller
@RequestMapping("manages/searchKeyword")
public class SearchKeywordController {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SearchKeywordController.class);
	
	@Autowired
	SearchKeywordService searchKeywordService;
	
	@RequestMapping(value = "getAllKeywords", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getAllKeywords(HttpServletRequest request) {
		LOGGER.debug("SearchKeywordController.getAllKeywords...");

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
		JSONObject returnObject = new JSONObject();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("page", Integer.valueOf(iPage));
			params.put("rows", Integer.valueOf(iRows));

			Page<CctCscappKeyword> pageList = this.searchKeywordService.getAllKeywords(params);

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
	 * 根据id获取keyword
	 */
	@RequestMapping(value = "getKeywordById", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getKeywordById(
			@RequestParam("keywordId") String keywordId) {
	
		LOGGER.debug("getKeywordById:"+keywordId);

		JSONObject returnObject = new JSONObject();
		try {

			CctCscappKeyword keyword = this.searchKeywordService.getKeywordById(keywordId);
			returnObject.put("data", keyword);

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
	 * 增加/更新
	 */
	@RequestMapping(value = "saveKeyword", method = RequestMethod.POST)
	public String saveKeyword(HttpServletRequest request,CctCscappKeyword keyword,
			HttpServletResponse response) {
		LOGGER.debug("SearchKeywordController.saveKeyword...");

		try {
//			ShiroHttpServletRequest
			if (keyword.getKeywordId()==null) {//增加
				if (keyword.getKeywordLevel() == 0) {
					List<CctCscappKeyword> recommends = this.searchKeywordService.getRecommendKeywords();
					if (recommends.size() == 5) {
						request.setAttribute("msg", "搜索推荐关键字最多只能添加5个！");
						return "search_keyword";
					}
					else {
						this.searchKeywordService.createKeyword(keyword.getKeyword(), keyword.getKeywordLevel(), keyword.getCreateUser(), new Date());
					}
				}
				else {
					this.searchKeywordService.createKeyword(keyword.getKeyword(), keyword.getKeywordLevel(), keyword.getCreateUser(), new Date());
				}
				
			}
			else {//修改

				if (keyword.getKeywordLevel() == 0) {
					List<CctCscappKeyword> recommends = this.searchKeywordService.getRecommendKeywords();
					boolean bInRecommends = false;
					for (int i=0; i<recommends.size(); i++)
					{
						CctCscappKeyword tempKeyword = recommends.get(i);
						if (tempKeyword.getKeywordId().equals(keyword.getKeyword()) ) {
							bInRecommends = true;
							break;
						}
					}
					
					if (recommends.size() == 5 && !bInRecommends) {
						request.setAttribute("msg", "搜索推荐关键字最多只能添加5个！");
						return "search_keyword";
					}
					else {
						this.searchKeywordService.updateKeyword(keyword.getKeywordId(), keyword.getKeyword(), keyword.getKeywordLevel(), keyword.getUpdateUser(), new Date());
					}
				}
				else {
					this.searchKeywordService.updateKeyword(keyword.getKeywordId(), keyword.getKeyword(), keyword.getKeywordLevel(), keyword.getUpdateUser(), new Date());
				}
				
			}

			
			return "search_keyword";

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "保存失败！");
			return "/search_keyword";

		}


	}

	/*
	 * 删除
	 */
	@RequestMapping(value = "deleteKeyword", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> deleteKeyword(
			@RequestParam("keywordId") String keywordId) {
		if (null == keywordId) {
		
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		try {
			this.searchKeywordService.deleteKeyword(keywordId);

			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("errorCode", "200");
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnJsonObject, HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("删除搜索关键字失败");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}

	}
}
