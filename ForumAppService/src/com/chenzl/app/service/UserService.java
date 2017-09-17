package com.chenzl.app.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.chenzl.app.entity.LoginLog;
import com.chenzl.app.entity.SySUserInfo;
import com.chenzl.app.entity.SysUser;
import com.chenzl.app.entity.SysUserTel;
import com.chenzl.app.exception.BusinessException;
import com.chenzl.app.mappers.SysUserMapper;
import com.chenzl.app.mappers.common.SysUserTelMapper;
import com.chenzl.app.shiro.RedisManager;

/**
 * 与用户信息相关方法
 * 
 * @author chenzl
 */
@Service
public class UserService  extends BaseService<SysUser>{
   
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class); 

	@Autowired
	private SysUserMapper  sysUserMapper;

  /*@Autowired
  private SysUserTelMapper sysUserTelMapper;
*/
	/**
	 * 登录操作
	 * @param account
	 *            登录用户名
	 * @param passWord
	 *            密码
	 * @return
	 * @throws
	 */
	public JSONObject loginApp(String userName, String password	,
		HttpServletRequest request) throws Exception {
	Example example = new Example(SysUser.class);
	example.createCriteria().andEqualTo("userId", userName);
	example.createCriteria().andEqualTo("password", password);
	List<SysUser> list = this.sysUserMapper.selectByExample(example);
	JSONObject resultJSON = new JSONObject();
	if (list.size() < 1) {
		resultJSON.put("errorCode", -100);
		resultJSON.put("retMsg", "无App帐号！");
		return resultJSON;
/*		throw new BusinessException("当前登录的账号" + account
					+ "在cct_user_tel表中没以对应记录，无app权限！");*/
		}
	SysUser sysUser = list.get(0);
	Calendar now = Calendar.getInstance();
	now.add(Calendar.MINUTE, -30);
	DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = now.getTime();
		System.out.println(df.format(date));
	Example validateExample = new Example(SysUser.class);
	validateExample.createCriteria().andEqualTo("userId", sysUser.getUserId()).andEqualTo("password",  sysUser.getPassword())
   .andLessThanOrEqualTo("createDate", date);
		String userId = sysUser.getUserId();
		SysUser userInfo = this.sysUserMapper.findAppUserById(userId);
		if (userInfo != null && userInfo.getPassword().equals(password)) {
		resultJSON.put("retCode", 0);
			resultJSON.put("retMsg", "通过密码登录成功！");
			} else {
			resultJSON.put("retCode", -101);
			resultJSON.put("retMsg", "密码错误，请重新输入密码！");
			return resultJSON;
		}
	HttpSession session = request.getSession(true);
	String sessionId = session.getId();

		if (LOGGER.isInfoEnabled()) {
		LOGGER.info("本次登录的账号是：" + userId + ",sessionId = " + sessionId);
	}
		resultJSON.put("sessionId", sessionId);
		resultJSON.put("sessionUser", userId);
	// 登录成功后，先redis中存入用户信息
		// 此处的异常无法捕获，因此redis源码中将异常进行了捕获，所以务必保证redis配置的正确，或者重写jredis中此方法，并重新编译jar包
	/*	RedisManager redisManager = new RedisManager();
		redisManager.setObject(sessionId, uInfo, 30* 60);*/
	    return resultJSON;
	}

	/*
	 * 判断session是否过期
	 */
//   public Object sessionOverTime(final String sessionId){
//	   RedisManager redisManager = new RedisManager();
//	  // redisManager.getObject(staffId);
//	   System.out.println("sessionO"+redisManager.getObject(sessionId)+"++++++++++++++++++++++++++++++");
//	  return  redisManager.getObject(sessionId);
//   }
	
	/**
	 * 根据sessionId获取登陆日志
	 * 
	 * @Description
	 * @param sessionId
	 * @return
	 */
//	public String getLoginLogBySessionId(String sessionId) {
//
//		String result = this.cctWeixinStaffTelMapper
//				.getLoginLogBySessionId(sessionId);
//		return result;
//	}

	/**
	 * 保存登录日志
	 * @Description
	 * @param log
	 */
	public void saveLoginLog(LoginLog log) {
	this.sysUserMapper.saveLoginLog(log);

}

//	public void savePortrait(String staffId, String portraitUrl)
//			throws Exception {
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("staffId", staffId);
//		params.put("portrait", portraitUrl);
//		this.cctSysStaffMapper.savePortrait(params);
//	}

//	public List<CctSysModule> getModulesByStaffId(String staffId)
//			throws Exception {
//		return this.cctSysStaffMapper.getModulesByStaffId(staffId);
//	}
//
//	public int resetPswd(String account, String pswd, String oldPswd)
//			throws Exception {
//
//		CctStaffInfo staffInfo = this.cctSysStaffMapper
//				.findStaffByAccount(account);
//
//		if (staffInfo != null && oldPswd.equals(staffInfo.getStaffPwd())) {
//			HashMap<String, Object> params = new HashMap<String, Object>();
//			params.put("staffId", account);
//			params.put("password", pswd);
//
//			this.cctSysStaffMapper.resetPassword(params);
//
//			return 0;
//		}
//
//		return -100;
//
//	}

	// ------------------------------以下为本类私有工具方法------------------------------
	/**
	 * 创建指定数量的随机字符串
	 * 
	 * @param numberFlag
	 *            是否是数字
	 * @param length
	 * @return
	 */
	private String createRandom(boolean numberFlag, int length) {
		String retStr = "";
		String strTable = numberFlag ? "1234567890"
				: "1234567890abcdefghijkmnpqrstuvwxyz";
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = "";
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr += strTable.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);
		LOGGER.debug("动态密码为：  " + retStr);
		System.out.println("动态密码为：  " + retStr);
		return retStr;
	}

//	@DataSource(name = DataSource.aikb)
//	public List<StudyTypeBean> getStudyTypes() {
//
//		return this.cctWeixinStaffTelMapper.getStudyTypes();
//	}
	
//增加用户
public int savaUserInfo(SysUser uservo){
	
	 return this.sysUserMapper.insertSelective(uservo);
	
}

//根据userId查询用户
public int selectUserById(String  userId) {  
    // TODO Auto-generated method stub  
    return this.sysUserMapper.selectUserById(userId);  
}  
}
