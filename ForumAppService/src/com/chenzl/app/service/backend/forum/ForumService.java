package com.chenzl.app.service.backend.forum;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenzl.app.bean.backend.forum.SysForumPostSelect;
import com.chenzl.app.common.DataSource;
import com.chenzl.app.entity.SysForumAuditUser;
import com.chenzl.app.mappers.SysUserMapper;
import com.chenzl.app.mappers.backend.fourm.ForumMapper;
import com.chenzl.app.mappers.backend.fourm.SysForumAuditUserMapper;
import com.chenzl.app.mappers.common.CommonMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ForumService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ForumService.class);
	 
	@Autowired
	private ForumMapper forumMapper;
	
	@Autowired
	private SysForumAuditUserMapper auditUserMapper;
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private CommonMapper commonMapper;
	 
	//查询提问列表
	@DataSource(name = DataSource.ftd)
	public PageInfo<SysForumPostSelect> getForumPostList(Map<String, Object> params) {
		
		Integer page = (Integer) params.get("page"); // 页码
		Integer rows = (Integer) params.get("rows"); // 每页显示行
		PageHelper.startPage(page.intValue(), rows.intValue());	
		List<SysForumPostSelect> list =forumMapper.getForumPostList(params);
		
		return new PageInfo<SysForumPostSelect>(list);
	}
	
	public SysForumAuditUser getAuditUserById(String userId) {
		return auditUserMapper.getForumAuditUserById(userId);
	}
	
	//审核提问
		@DataSource(name = DataSource.ftd)
		public int saveCheck(Map<String, Object> params) {
	    	Timestamp timestamp = commonMapper.selectSysDate();
			params.put("auditDate", timestamp);
			int result=forumMapper.saveCheck(params);
			return result;
		}
		
		public Page<SysForumAuditUser> getAuditUserList(
				Map<String, Object> params) {
			Integer page = (Integer) params.get("page"); // 页码
			Integer rows = (Integer) params.get("rows"); // 每页显示行
			PageHelper.startPage(page.intValue(), rows.intValue());	
			Page<SysForumAuditUser> list =auditUserMapper.getAuditUserList(params);
			
			return list;
		}
		
		public int insertAuditUser(Map<String, Object> params){
				int result=auditUserMapper.insertAuditUser(params);
				return result;
		}
		
		
		public int deleteAuditUser(Map<String, Object> params){
			
			int result=auditUserMapper.deleteAuditUser(params);
			return result;
		}
		
		public int setAuditUserEnable(Map<String, Object> params){
			int result=auditUserMapper.updateAuditUser(params);
			return result;
		}
		
		public int setAuditCategory(Map<String, Object> params){
			int result=auditUserMapper.updateAuditUser(params);
			return result;
		}
}
