package com.chenzl.app.mappers.common;

import java.util.HashMap;

import tk.mybatis.mapper.common.Mapper;

import com.chenzl.app.entity.SysUserTel;

public interface SysUserTelMapper extends Mapper<SysUserTel>{
	
	HashMap<String, Object> getUserTelByUserId(final String userId);
}