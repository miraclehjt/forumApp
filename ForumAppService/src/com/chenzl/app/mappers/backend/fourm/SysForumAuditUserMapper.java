package com.chenzl.app.mappers.backend.fourm;

import com.chenzl.app.entity.SysForumAuditUser;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

public interface SysForumAuditUserMapper  extends Mapper<SysForumAuditUser>{
	
	//通过用户Id获得审核人员
	SysForumAuditUser getForumAuditUserById(final String userId);
	
	//查询人员列表
		Page<SysForumAuditUser> getAuditUserList(Map<String, Object> param);
		
		//新增人员
		int insertAuditUser(Map<String, Object> param);
		
		//删除已选择的人员
		int deleteAuditUser(Map<String, Object> param);
		
		int updateAuditUser(Map<String, Object> param);

}