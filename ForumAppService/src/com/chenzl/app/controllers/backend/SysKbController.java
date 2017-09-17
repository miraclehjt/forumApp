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
import com.chenzl.app.bean.backend.learn.SysKbBean;
import com.chenzl.app.entity.SysKb;
import com.chenzl.app.service.backend.learn.SyskbService;
import com.github.pagehelper.Page;

/**
 * 知识学习Controller
 * 
 * @author chenzl
 */
@Controller
@RequestMapping("manages/learn")
public class SysKbController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SliderController.class);

	@Autowired
	private SyskbService sysKbService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 查询所有的知识
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getSysKbList", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getSysKbList(HttpServletRequest request) {
		
		JSONObject returnObject = new JSONObject();
		
		String kbTitle = request.getParameter("kbTitle");
		String kbId = request.getParameter("kbId");
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
			if (!StringUtils.isBlank(kbTitle)) {
				params.put("kbTitle",kbTitle);
			}
			Page<SysKb> pageList = this.sysKbService.getKbList(params);

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
	 * 保存知识
	 * @param request
	 * @param response
	 * @param savekb
	 * @throws Exception
	 */
	@RequestMapping(value = "saveKb", method = RequestMethod.POST)
	public void saveKb(HttpServletRequest request,HttpServletResponse response, SysKbBean sysKbBean)
			throws Exception {
		LOGGER.debug("SysKb.savekb...");
		
		SysKb sysKb = new SysKb();
		if (sysKbBean != null && sysKbBean.getKbId()!=null) {
			sysKb.setKbId(sysKbBean.getKbId());
		}
		
		if (sysKbBean != null) {
			sysKb.setKbTitle(sysKbBean.getKbTitle()); // 知识标题
			//sysKb.setKbType(sysKbBean.getKbType()); //类型
	        sysKb.setContent(sysKbBean.getContent());//知识描述
	        sysKb.setKbLink(sysKbBean.getKbLink());//知识链接
	        sysKb.setCreateDate(sdf.parse(sysKbBean.getCreateDate()));//知识发布时间
	        sysKb.setUpdateDate(sdf.parse(sysKbBean.getUpdateDate()));//知识截至时间
	        System.out.println("sysKbBean.getCreateDate()++++++++++++++"+sysKbBean.getCreateDate()+"++++++++++"+"sysKbBean.getUpdateDate()++++++++++"+sysKbBean.getUpdateDate());
			if (sysKbBean.getKbId()==null) {
				sysKb.setState(1); // 状态 [1] 正常
				sysKb.setKbType(1);
				sysKb.setCreateUser(String.valueOf(sysKbBean.getUserId()));
				sysKb.setUpdateUser(String.valueOf(sysKbBean.getUserId()));
				sysKbService.addKb(sysKb);
			} else {
				sysKb.setUpdateUser(String.valueOf(sysKbBean.getUserId()));
				sysKbService.updateKb(sysKb);
			}
		}

		response.sendRedirect(request.getContextPath()
				+ "/manages/page/learn.do?dicName=learn");
	}

	/**
	 * 根据ID查询知识列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getKbById", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getKbById(
			HttpServletRequest request) throws Exception {
		LOGGER.debug("SysKbController.getKbById...");
		String kbId = request.getParameter("kbId");
		JSONObject returnObject = new JSONObject();
		try {
			SysKb sysKb = sysKbService.queryKbById(Integer.valueOf(kbId));
			returnObject.put("data", sysKb);
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
	
	@RequestMapping(value = "deleteKb", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> deleteKb(HttpServletRequest request) throws Exception {
		LOGGER.debug("SysKbController.deleteKb...");
		String kbId = request.getParameter("kbId");
		String userId = request.getParameter("userId");
		JSONObject returnObject = new JSONObject();
		try {
			int i = sysKbService.deleteKb(Integer.valueOf(kbId), userId);
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
