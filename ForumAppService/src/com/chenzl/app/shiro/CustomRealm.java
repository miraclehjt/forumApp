package com.chenzl.app.shiro;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenzl.app.entity.SysUser;
import com.chenzl.app.mappers.SysUserMapper;
import com.chenzl.app.service.UserService;
import com.chenzl.app.service.backend.ManageSystemService;

public class CustomRealm extends AuthorizingRealm {

	@Autowired
	private ManageSystemService  manageSystemService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private SysUserMapper sysUserMapper;
	
	/**
	 * 认证方法
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
		String username = usernamePasswordToken.getUsername();

		// 根据用户名从数据库查询用户的信息
		SysUser sysUser = manageSystemService.queryById(username);
		
		// 如果用户不存在
		if (sysUser != null) {	
			// 如果用户存在
			// 取出从数据库取出的用户密码和用户输入的密码进行比对
			String password = sysUser.getPassword();
			String realmName = sysUser.getUserName();
			
			SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, password, realmName);
			
			setSession("sysUser", sysUser);
			
			return simpleAuthenticationInfo;
		}

		return null;
	}

	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		// 从token中取出用户身份信息
		String currentUsername = (String) getAvailablePrincipal(principals);
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		if ((!StringUtils.isBlank(currentUsername) && "AIKF0020".equals(currentUsername))
				|| (!StringUtils.isBlank(currentUsername) && currentUsername.indexOf("SCKF") != -1)) {
			//添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色
			simpleAuthorInfo.addRole("admin");
			//添加权限
			simpleAuthorInfo.addStringPermission("admin:manage");
		}
		
		// 当前用户角色
		List<String> rolesList = null;
		// 当前用户访问权限
		List<String> menuids = null;
		
		try {
		
	rolesList = sysUserMapper.getRolesOfUser(currentUsername);
			if (rolesList != null && !rolesList.isEmpty()) { // 添加当前用户角色
				simpleAuthorInfo.addRoles(rolesList);
			}
			menuids = sysUserMapper.getMenuIdsByUserId(currentUsername);
			if (menuids != null && !menuids.isEmpty()) { // 添加用户访问授权
				for (String menuid : menuids) {
					simpleAuthorInfo.addStringPermission("user:" + menuid);
				}
			}
			
			return simpleAuthorInfo;
		} catch(Exception e) {
			
		}
		
		return null;
	}

	/**
	 * 将一些数据放到ShiroSession中,以便于其它地方使用
	 * 
	 * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
	 */
	private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}
}
