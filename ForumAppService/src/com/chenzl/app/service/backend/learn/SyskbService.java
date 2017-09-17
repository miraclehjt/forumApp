package com.chenzl.app.service.backend.learn;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chenzl.app.entity.SysKb;
import com.chenzl.app.mappers.backend.learn.SysKbMapper;
import com.chenzl.app.mappers.common.CommonMapper;
import com.chenzl.app.service.BaseService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class SyskbService  extends BaseService<SysKb>{
       
	 @Autowired
    private  SysKbMapper  sysKbMapper; 
	 
	 @Autowired
		private CommonMapper commonMapper;
	 
	 //查询全部的知识列表
	 public Page<SysKb> getKbList(final Map<String,Object> params){
		 Integer page = (Integer) params.get("page"); // 页码
			Integer rows = (Integer) params.get("rows"); // 每页显示行
			PageHelper.startPage(page.intValue(), rows.intValue());
			Page<SysKb> pagelist = this.sysKbMapper.getKbListForPage(params);
			return pagelist;
	 }
	 
	 //app查询最新的前五条数据
	 public List<SysKb> getKbListForApp(final Map<String,Object> params){
			List<SysKb> pagelist = this.sysKbMapper.getKbListForApp(params);
			return pagelist;
	 }
	 //添加版块
	 public int addKb(final SysKb sysKb) throws Exception {
			Timestamp timestamp = commonMapper.selectSysDate();
			sysKb.setCreateDate(timestamp);
			sysKb.setUpdateDate(timestamp);
			return sysKbMapper.insertSelective(sysKb);
		}
	 
		/**
		 * 修改知识
		 * @param 
		 * @return
		 * @throws Exception
		 */
		public int updateKb(SysKb sysKb) throws Exception {
			Timestamp timestamp = commonMapper.selectSysDate();
			//sysKb.setCreateDate(timestamp);
			sysKb.setUpdateDate(timestamp);
			return sysKbMapper.updateByPrimaryKeySelective(sysKb);
		}
		
		/**
		 * 根据Id查询
		 * @param kbId
		 * @return
		 * @throws Exception
		 */
		public SysKb queryKbById(Integer kbId)  throws Exception{
			  SysKb   sysKb=sysKbMapper.getKbListById(kbId);
			  return sysKb;
		}
		
		/**
		 * 删除知识
		 * @param wctId
		 * @param staffId
		 * @return
		 * @throws Exception
		 */
		public int deleteKb(Integer kbId, String userId) throws Exception {
			Map<String, Object> params = new HashMap<String, Object>();
			Timestamp timestamp = commonMapper.selectSysDate();
			params.put("kbId", kbId);
			params.put("state", Short.valueOf("0"));
			params.put("updateUser", userId);
			params.put("updateDate", timestamp);
			int i = sysKbMapper.updateSysKbStateById(params);
			return i;
		}
		
}
