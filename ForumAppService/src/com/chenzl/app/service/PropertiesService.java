package com.chenzl.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
/**
 * 使用此类获取env.properties中配置的变量
 * @author chenzl
 *
 */
@Service
public class PropertiesService {
	 @Value("${SESSION_USER}")//SESSION_STAFF对应的键
	    public String session_user;
}
