package com.chenzl.app.common;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.chenzl.app.entity.SysUser;
import com.chenzl.app.service.PropertiesService;
import com.asiainfo.sessionManage.common.RedisManager;
/**
 * 后台统一拦截器，用于进行session身份认证，认证成功后将请求转发到对应controller
 * @author  chenzl
 *
 */
@Repository
public class SystemInterceptor implements  HandlerInterceptor {
	 //记录日志LOGGER
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemInterceptor.class);
    
    @Autowired
    private PropertiesService propertiesService;
    
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setCharacterEncoding("UTF-8");  
        
     // 后台session控制  
//        String[] noFilters = new String[] {"loginApp", "sendSMS","searchLog", "notice","knowledgeSearch","favorite"};

        String[] noFilters = new String[] {"sessionOut/isSessionTimeOver","app/news","app/forumpost","forumpost/","manages/slider","manages/page","message/","forum/","manages/searchKeyword","iosConfig","app/login", "page", "manages/forumPost" ,"manages/forum", "update/", "manages/forum", "forum/forumcategory/","forumlearn/","manages/forumcategory","app/kbList","app/forum","app/reg","forumpoststar/","manages/news"};

        String uri = request.getRequestURI();
        return true;
     /*   for(String s : noFilters){
            if(uri.indexOf(s)!=-1){
                return true;
            }
        }
        
      
        String sessionId = request.getParameter("sessionId");
        
        if(StringUtils.isBlank(sessionId)){
            LOGGER.debug("登录时没有合法的session，sessionId=" + sessionId);
            return false;
        }*/
        
       /* RedisManager redisManager = new RedisManager();
        SysUser sysUser = (SysUser) redisManager.getObject(sessionId);*/
        
     /*   if(LOGGER.isDebugEnabled()){
            LOGGER.debug("本次登录员工的sessionId是：" + sessionId);
        }
        
        if(null == sysUser){
            //此处
            LOGGER.debug("从redis中找不到对应员工的登录数据，请重新登录");
            return false;
        }else{
            redisManager.setObject(sessionId, sysUser , 30*60);
            request.setAttribute("currentStaff", sysUser);
            return true;
        }*/
        
    }
    
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception arg3)
            throws Exception {
        
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView arg3)
            throws Exception {
        
    }
}
