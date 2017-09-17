package com.chenzl.app.controllers.backend;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.chenzl.app.entity.SysUser;

@Controller
@RequestMapping("manages")
public class ManagerLoginController {
	
	private static Logger logger = Logger.getLogger(ManagerLoginController.class);

	/**
	 * 返回请求的jsp界面
	 * @param pageName
	 * @param dicName
	 * @return
	 */
	@RequestMapping(value = "page/{pageName}", method = RequestMethod.GET)
	public String toPage(@PathVariable("pageName") String pageName,
			@RequestParam(value = "dicName", defaultValue = "") String dicName,
			HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("login".equals(pageName) && "username".equals(cookie.getName())) {
					request.setAttribute(cookie.getName(), cookie.getValue());
				}
				if ("login".equals(pageName) && "rememberMe".equals(cookie.getName())) {
					request.setAttribute(cookie.getName(), cookie.getValue());
				}
			}
		}
		if (StringUtils.isNotBlank(dicName)) {
			return dicName + "/" + pageName;
		} else {
			return pageName;
		}

	}

	/**
	 * 登录后台系统
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "login/loginManage", method = RequestMethod.POST)
	public String loginManageSystem(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String username = null;
		String password = null;
		String rememberMe = null;
		boolean remember_me = false;
			username = request.getParameter("username");
			password = request.getParameter("password");
			rememberMe = request.getParameter("rememberMe");
System.out.println("username:"+username+"======"+"password:"+password);
		// 用户名不能为空
		if (StringUtils.isBlank(username)) {
			response.sendRedirect(request.getContextPath() + "/manages/page/login.do");
			return null;
			// return "login";
		}

		// 密码不能为空
		if (StringUtils.isBlank(password)) {
			response.sendRedirect(request.getContextPath() + "/manages/page/login.do");
			return null;
			// return "login";
		}
		
		// 是否记住用户名
		if (!StringUtils.isBlank(rememberMe) && "on".equals(rememberMe)) {
			remember_me = true;
			
			Cookie cookie = null;
			
			cookie = new Cookie("username", username);
			cookie.setPath(request.getContextPath() + "/manages/page/login.do");
			cookie.setMaxAge(60 * 60 * 24 * 30);
			response.addCookie(cookie);
			
			cookie = new Cookie("rememberMe", rememberMe);
			cookie.setPath(request.getContextPath() + "/manages/page/login.do");
			cookie.setMaxAge(60 * 60 * 24 * 30);
			response.addCookie(cookie);
		} else {
			remember_me = false;
			
			Cookie cookie = null;
			
			cookie = new Cookie("username", null);
			cookie.setPath(request.getContextPath() + "/manages/page/login.do");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			
			cookie = new Cookie("rememberMe", null);
			cookie.setPath(request.getContextPath() + "/manages/page/login.do");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}

		UsernamePasswordToken token = new UsernamePasswordToken(username, password, remember_me);
		System.out.println(token+"+++++++++++++++++++++++password+username");
		logger.debug("为了验证登录用户而封装的token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
		// 获取当前的Subject
		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.login(token);
		} catch (UnknownAccountException uae) {
			logger.debug("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
		} catch (IncorrectCredentialsException ice) {
			logger.debug("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
		} catch (LockedAccountException lae) {
			logger.debug("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
		} catch (ExcessiveAttemptsException eae) {
			logger.debug("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
		} catch (AuthenticationException ae) {
			// 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
			logger.debug("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
		}

		if (currentUser.isAuthenticated()) { // 登录成功
			SysUser sysUser = (SysUser) request.getSession().getAttribute("sysUser");
			System.out.println("用户信息：++++++++++++"+sysUser);
			logger.debug("用户[" + username + "]登录成功，用户信息为：" + sysUser.getPassword());
			String url_index = request.getContextPath() + "/manages/page/index.do";
			response.sendRedirect(url_index);
			return null;
			// return "index";
		} else { // 登录失败
			token.clear();
			String url_login = request.getContextPath() + "/manages/page/index.do";
			response.sendRedirect(url_login);
			return null;
			// return "login";
		}
	}

	@RequestMapping(value = "login/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		response.sendRedirect(request.getContextPath() + "/manages/page/login.do");
		return null;
		// return "login";
	}
}
