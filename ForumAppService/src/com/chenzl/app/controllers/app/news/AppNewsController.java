package com.chenzl.app.controllers.app.news;

import java.text.SimpleDateFormat;
import java.util.HashMap;
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

import com.chenzl.app.controllers.backend.SysNewsController;
import com.chenzl.app.entity.SysNews;
import com.chenzl.app.service.backend.news.SysNewsService;
import com.github.pagehelper.Page;

@Controller
@RequestMapping("app/news")
public class AppNewsController {
	
   private static final Logger LOGGER = LoggerFactory.getLogger(AppNewsController.class);
	
	@Autowired
	private SysNewsService sysNewsService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
	/**
	 * 查询所有的新闻
	 * @param request
	 * @return
	 */
	@RequestMapping(value="getTopTenNews",method=RequestMethod.POST)
	public ResponseEntity<JSONObject>  getTopTenNews(HttpServletRequest request){
        JSONObject returnObject = new JSONObject();
		String newsTitle = request.getParameter("newsTitle");
		String newsId = request.getParameter("newsId");
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
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("page", Integer.valueOf(iPage));
			params.put("rows", Integer.valueOf(iRows));
			if (!StringUtils.isBlank(newsTitle)) {
				params.put("newsTitle",newsTitle);
			}
			Page<SysNews> pageList = this.sysNewsService.queryNewsListAll(params);
			returnObject.put("data", pageList);
			returnObject.put("total", pageList.getTotal());
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		}catch(Exception e){
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
}
