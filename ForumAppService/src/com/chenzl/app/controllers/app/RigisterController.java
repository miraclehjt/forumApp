package com.chenzl.app.controllers.app;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.chenzl.app.entity.SysUser;
import com.chenzl.app.service.UserService;

/**
 * 用户注册Controller
 * 
 * @author chenzl
 */

@Controller
@RequestMapping("app/reg")
public class RigisterController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RigisterController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "appSaveNewUser")
	public ResponseEntity<JSONObject>  appSaveNewUser(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = request.getParameter("userId");
		String sex = request.getParameter("sex");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		SysUser uservo = new SysUser();
		JSONObject obj = new JSONObject();
		try {
			int  m = userService.selectUserById(userId);
			if (m == 1) {
				obj.put("reCode", "1");
				obj.put("reMg", "用户已存在,重新输入");
			} else {
				uservo.setAccess("2");
				uservo.setCreateDate(new Date());
				uservo.setAreaId("11111");
				uservo.setPassword(password);
				uservo.setPhone(phone);
				uservo.setStatus("1");
				uservo.setUserEmail(email);
				uservo.setUserId(userId);
				uservo.setUserName(userName);
				uservo.setUserSex(sex);
				uservo.setUserType("1");
				// uservo.setUserPortrait(userPortrait);
				int i = userService.savaUserInfo(uservo);
				if (i == 1) {
					obj.put("reCode", "2");
					obj.put("reMg", "注册成功");
				} else {
					obj.put("reMg", "异常错误，请重新注册");
				}
			}
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					obj, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
}
