package com.chenzl.app.mappers;

import com.chenzl.app.entity.LoginLog;
import com.chenzl.app.entity.SySUserInfo;
import com.chenzl.app.entity.SysUser;
import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;

public interface SysUserMapper  extends Mapper<SysUser>{

	List<String> getRolesOfUser(String userId) throws Exception;
	
	 List<String> getMenuIdsByUserId(String userId) throws Exception;
	 
	 SysUser queryAuditUserTel(final String params);
	 
	 public SySUserInfo findUserByAccount(final String userId) throws Exception;   
	 
	/* SysUser  queryLoginResultByUserId(Map<String,Object> params);*/
	 
	 /*SySUserInfo findUserByAccount(String UserId);*/
	 
	 void saveLoginLog(LoginLog log);
	 
	/* //插入用户信息
	 public int insertUser(SysUser uservo);*/
	 
	 //查询用户信息
	 public int selectUserById(final String  userId);  
	 
	 SysUser  findAppUserById(final String userId);
}