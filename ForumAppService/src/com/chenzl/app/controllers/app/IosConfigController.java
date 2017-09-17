package com.chenzl.app.controllers.app;

import java.util.List;

import net.sf.json.JSONObject;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/*import com.chenzl.app.entity.CctIosConfig;
import com.chenzl.app.service.common.IosConfigService;*/

@Controller
@RequestMapping("iosConfig")
public class IosConfigController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IosConfigController.class);
	
	/*@Autowired
	private IosConfigService iosConfigService;
	
	
	
	@RequestMapping(value = "getIosConfig", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getIosConfig() {
		LOGGER.debug("iosConfig.getIosConfig...");
		
		try {
			
			JSONObject returnJsonObject = new JSONObject();
			
			List<CctIosConfig> configList = this.iosConfigService.getIosConfig();
			
			if (configList.size() > 0) {
				returnJsonObject.put("errorCode", 0);
				returnJsonObject.put("config", configList.get(0));
			}
			else {
				returnJsonObject.put("errorCode", -1);
			}
			
			
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnJsonObject,HttpStatus.OK);
			
			
			return responseEntity;
			
		} catch (Exception e) {
			// TODO: handle exception
			
			
			ResponseEntity<JSONObject> responseEntity = 
					new ResponseEntity<JSONObject>(new JSONObject(), HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}*/

}
