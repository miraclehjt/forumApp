package com.chenzl.app.mappers.backend.learn;

import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;

import com.chenzl.app.entity.SysKb;
import com.github.pagehelper.Page;

public interface SysKbMapper  extends Mapper<SysKb>{
	
	 public Page<SysKb> getKbListForPage(Map<String, Object> params);
	 
	 public List<SysKb> getKbListForApp(Map<String, Object> params);
     
	 SysKb  getKbListById(final Integer kbId);
	 
	int  updateSysKbStateById(Map<String,Object> params);
}