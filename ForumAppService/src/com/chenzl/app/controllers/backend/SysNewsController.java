package com.chenzl.app.controllers.backend;

import java.text.SimpleDateFormat;
import java.util.HashMap;
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
import com.chenzl.app.bean.backend.news.SysNewsBean;
import com.chenzl.app.entity.SysNews;
import com.chenzl.app.service.backend.news.SysNewsService;
import com.github.pagehelper.Page;

/**
 * 查询所有的新闻列表
 * @author chenzl
 *
 */
@Controller
@RequestMapping("manages/news")
public class SysNewsController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SysNewsController.class);
	
	@Autowired
	private SysNewsService sysNewsService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 查询所有的
	 * @param request
	 * @return
	 */
	@RequestMapping(value="getNewsList",method=RequestMethod.POST)
	public ResponseEntity<JSONObject>  getNewsList(HttpServletRequest request){
		
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
			returnObject.put("rows", pageList);
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
	
	/**
	 * 保存新闻
	 * @param request
	 * @param response
	 * @param sysNewsBean
	 * @throws Exception
	 */
	@RequestMapping(value = "saveNews", method = RequestMethod.POST)
      public void saveNews(HttpServletRequest request,HttpServletResponse response,SysNewsBean sysNewsBean) throws Exception{
    	  LOGGER.debug("SysNews.saveNews...");
  		
  		SysNews sysNews = new SysNews();
  		if (sysNewsBean != null && sysNewsBean.getNewsId()!=null) {
  			sysNews.setNewsId(sysNewsBean.getNewsId());
  		}
  		
  		if (sysNewsBean != null) {
  			sysNews.setNewsTitle(sysNewsBean.getNewsTitle()); // 新闻标题
  			//sysKb.setKbType(sysKbBean.getKbType()); //类型
  			sysNews.setDescription(sysNewsBean.getDescription());//新闻描述
  			sysNews.setNewsLink(sysNewsBean.getNewsLink());//新闻链接
  			sysNews.setCreateDate(sdf.parse(sysNewsBean.getCreateDate()));//新闻发布时间
  			sysNews.setEndDate(sdf.parse(sysNewsBean.getEndDate()));//新闻截至时间
  	        System.out.println("sysNewsBean.getCreateDate()++++++++++++++"+sysNewsBean.getCreateDate());
  			if (sysNewsBean.getNewsId()==null) {
  				sysNews.setState(1); // 状态 [1] 正常
  				sysNews.setNewsType(1);
  				sysNews.setCreateUser(sysNewsBean.getUserId());
  				sysNews.setUpdateUser(sysNewsBean.getUserId());
  				sysNewsService.addNews(sysNews);
  			} else {
  				sysNews.setUpdateUser(sysNewsBean.getUserId());
  				sysNewsService.updateNews(sysNews);
  			}
  		}
  		response.sendRedirect(request.getContextPath()
  				+ "/manages/page/newsTop.do?dicName=news");
      }
      
      /**
       * 根据条件查询新闻
       * @param request
       * @return
       * @throws Exception
       */
  	@RequestMapping(value = "getNewsById", method = RequestMethod.POST)
  	public ResponseEntity<JSONObject> getNewsById(
  			HttpServletRequest request) throws Exception {
  		LOGGER.debug("SysNewsController.getNewsById...");
  		String newsId = request.getParameter("newsId");
  		JSONObject returnObject = new JSONObject();
  		try {
  			SysNews sysNews = sysNewsService.queryNewsById(Integer.valueOf(newsId));
  			returnObject.put("data", sysNews);
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
  	 * 删除新闻
  	 * @param request
  	 * @return
  	 * @throws Exception
  	 */
  	@RequestMapping(value = "deleteNews", method = RequestMethod.POST)
  	public ResponseEntity<JSONObject> deleteNews(HttpServletRequest request) throws Exception {
		LOGGER.debug("SysNewsController.deleteNews...");
		String newsId = request.getParameter("newsId");
		String userId = request.getParameter("userId");
		JSONObject returnObject = new JSONObject();
		try {
			int i = sysNewsService.deleteNews(Integer.valueOf(newsId), userId);
			if (i == 1) {
				returnObject.put("retCode", "1");
				returnObject.put("retMsg", "删除成功");
			} else {
				returnObject.put("retCode", "0");
				returnObject.put("retMsg", "删除失败");
			}
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = 
					new ResponseEntity<JSONObject>(new JSONObject(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
  	
}
