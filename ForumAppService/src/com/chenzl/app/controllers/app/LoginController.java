package com.chenzl.app.controllers.app;

import java.util.Date;
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

import com.asiainfo.sessionManage.common.RedisManager;
import com.chenzl.app.entity.LoginLog;
import com.chenzl.app.entity.SysUser;
import com.chenzl.app.exception.BusinessException;
import com.chenzl.app.service.CommonService;
import com.chenzl.app.service.UserService;
import com.sun.jersey.api.core.HttpRequestContext;

@Controller
@RequestMapping("app/login")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    
    private static final String APPID = "com.chenzl.forumAppService";

    @Autowired
    private UserService userService;
    
    @Autowired
    private CommonService commonService;

    /**
     * 登录app时调用方法
     * 
     * @param account
     * @param password
     * @return
     */
    @RequestMapping(value = "loginApp", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> loginApp(@RequestParam(value = "userName") String userName,
            @RequestParam(value = "passWord") String password,
            HttpServletRequest request,HttpServletResponse response) {
    		String loginFlag = "0";//登录状态
    		String failMessage = "";//失败信息
    		JSONObject resultJSON = new JSONObject();
        try {
            // 如果账号或密码为空，则返回400状态
            if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("登录app，帐号是：" + userName + ",密码是：" + password);
            }
            resultJSON = this.userService.loginApp(userName, password,request);
          
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("帐号" + userName + "登录成功！");
            }
            try {
				saveLoginLog(request, loginFlag, failMessage, resultJSON);
			} catch (Exception e) {
				LOGGER.error("*******保存登录日志失败，失败原因： "+e.getLocalizedMessage());
			}
            return ResponseEntity.ok(resultJSON);

        } catch (Exception e) {
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("登录app出现错误：" + e.getMessage());
            }
            loginFlag = "1";
            failMessage = e.getMessage();
            e.printStackTrace();
        }
        try {
			saveLoginLog(request, loginFlag, failMessage, resultJSON);
		} catch (BusinessException e) {
			LOGGER.error("*******保存登录日志失败，失败原因： "+e.getLocalizedMessage());
		}

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 保存登录日志
     * @Description
     * @param request
     * @param loginFlag
     * @param failMessage
     * @param resultJSON
     * @throws BusinessException 
     */
	private void saveLoginLog(HttpServletRequest request, String loginFlag, String failMessage,
			JSONObject resultJSON) throws BusinessException {
		String sessionId = request.getSession().getId();
//    	String result = this.staffService.getLoginLogBySessionId(sessionId);
//    	throw new BusinessException("");
    	LoginLog log = new LoginLog();
    	log.setLoginIp(request.getRemoteAddr());
    	log.setLoginTime(new Date());
    	log.setLogin_Flag(loginFlag);
    	if(sessionId==null && "".equals(sessionId)){
    		log.setSessionId("");
    	}else{
    		log.setSessionId(sessionId);
    	}
    	//log.setSessionId(sessionId.isEmpty() ? "" : sessionId);
    	log.setLogin_Faild_Result(failMessage);
    	JSONObject o = (JSONObject) resultJSON.get("sessionUser");
    	System.out.println(o);
    	String userId = o.getString("userId");
    	log.setUserId(userId);
    	this.userService.saveLoginLog(log);
//    	if(result == null){
//    	}
	}
    
    @RequestMapping(value = "getHomePageData",method = RequestMethod.GET)
    public ResponseEntity<JSONObject> getHomeData(@RequestParam(value = "sessionId")String sessionId){
        JSONObject resultJSON = new JSONObject();
        System.out.println(sessionId);
        
        RedisManager redisManager = new RedisManager();
        SysUser user = (SysUser) redisManager.getObject(sessionId);
        System.out.println("userName = " + user.getUserName());
        resultJSON.put("name", "success");
        
        return ResponseEntity.ok(resultJSON);
        
    }
   /* 
    *//**
     * 获取首页我学业务8种类型
     * @Description
     * @param request
     * @return
     *//*
    @RequestMapping(value = "getEightTypeData", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> getEightTypeData(HttpServletRequest request){
    	JSONObject resultJSON = new JSONObject();
    	try {
			List<StudyTypeBean> list = this.staffService.getStudyTypes();
			resultJSON.put("data", list);
			return ResponseEntity.ok(resultJSON);
		} catch (Exception e) {
			LOGGER.error("******获取首页我学业务8种类型失败：  "+e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    	
    }*/
    
    /**
     * 查询首页4个统计数据：app使用量、营销量、考试量、提问量
     * @Description
     * @param request
     * @return
     */
   /* @RequestMapping(value = "getStatisticData", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> getStatisticData(HttpServletRequest request){
    	JSONObject resultJSON = new JSONObject();
    	try {
			Long userCount = this.commonService.getAppUserCount();
			Long examCount = this.commonService.getExamCount();
			Long businessCount = this.commonService.getBusinessSuccessCount();
			Long postCountLong = this.commonService.getPostCount();
			resultJSON.put("userCount", userCount);
			resultJSON.put("sellCount", businessCount);
			resultJSON.put("examCount", examCount);
			resultJSON.put("questionCount", postCountLong);
			return ResponseEntity.ok(resultJSON);
		} catch (Exception e) {
			LOGGER.error("******获取首页统计数据失败：  "+e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    }*/
    
    /**
     * 下载apk文件
     * @param request
     * @param response
     */
    /*
    @RequestMapping("/downloadApk")
    public void downloadApk(HttpServletRequest request, HttpServletResponse response){
            String realPath = request.getRealPath("/");
            String path = realPath+"download/SecretaryApp.apk";
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            response.setContentType("application/octet-stream");
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            OutputStream outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int i = -1;
            while ((i = fis.read(buffer)) != -1) {
             outputStream.write(buffer, 0, i);
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    */
    /**
     * 修改下载地址为文件服务器下载地址
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
   /* @RequestMapping("/downloadApk")
    public String downloadApk(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Map<String, Object> newAppInfo = commonService.queryAppNewVersion(APPID);
    	String apkpath = (String) newAppInfo.get("APK_PATH");
    	String apkPathServer = UploadUtils.getFileServerPath(apkpath);
    	response.sendRedirect(apkPathServer);
    	return null;
    }*/
    
  /*  @RequestMapping(value = "getModules", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> getModules(@RequestParam(value = "staffId") String staffId) throws Exception {
    	JSONObject resultJSON = new JSONObject();
    	try {
			List<CctSysModule> list = this.staffService.getModulesByStaffId(staffId);
			resultJSON.put("modules", list);
			resultJSON.put("errorCode", 0);
			return ResponseEntity.ok(resultJSON);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
    	
    }*/
}
