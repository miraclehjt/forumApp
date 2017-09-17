package com.chenzl.app.controllers.app;

import java.text.SimpleDateFormat;
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
import com.chenzl.app.entity.SysKb;
import com.chenzl.app.service.backend.learn.SyskbService;
import com.github.pagehelper.Page;

/**
 * app端查询知识学习Controller
 * @author chenzl
 */
@Controller
@RequestMapping("app/kbList")
public class GetSilderListForApp {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GetSilderListForApp.class);

	@Autowired
	private SyskbService sysKbService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 *app端 查询所有的知识
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getSysKbList", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getSysKbList(HttpServletRequest request) {
		JSONObject returnObject = new JSONObject();
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			List<SysKb> pageList = this.sysKbService.getKbListForApp(params);
			returnObject.put("rows", pageList);
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
