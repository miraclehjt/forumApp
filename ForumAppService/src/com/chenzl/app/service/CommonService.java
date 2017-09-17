package com.chenzl.app.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.chenzl.app.common.DataSource;
import com.chenzl.app.bean.common.BSDcmntInfo;
import com.chenzl.app.bean.ftp.CfgFtp;
import com.chenzl.app.exception.BusinessException;
import com.chenzl.app.mappers.common.CommonMapper;
/**
 * 包含一些其它service需要调用的公用方法
 * @author chenzl
 *
 */
@Service
public class CommonService {
	 private static final Logger LOGGER = LoggerFactory.getLogger(CommonService.class);
	    
	    @Autowired
	    private CommonMapper commonMapper;
	    
	    /**
	     * 调用usp_getno存储过程生成主键
	     * @param tablename 表名
	     * @param columnname 字段名
	     * @param valuelength 生成主主键长度
	     * @param retVal 输出的主键值
	     * @return
	     * @throws Exception
	     */
	    public String queryPrimaryKeyByProcedure(String tablename, String columnname, 
	            int valuelength,String retVal) throws Exception{
	        Map<String,Object> queryMap = new HashMap<String, Object>();
	        queryMap.put("tableName", tablename);
	        queryMap.put("columnname", columnname);
	        queryMap.put("valuelength", valuelength);
	        queryMap.put("numValue", retVal);
	        try {
	            if(LOGGER.isInfoEnabled()){
	                LOGGER.info("调用queryPrimaryKeyByProcedure方法生成：" + tablename + "的主键开始！");
	            }
	            
	            this.commonMapper.queryPrimaryKeyByProcedure(queryMap);
	            
	            if(LOGGER.isInfoEnabled()){
	                LOGGER.info("表" + tablename + "中" + columnname + "字段生成主键为：" + queryMap.get("numValue"));
	            }
	        } catch (Exception e) {
	            throw new BusinessException("调用存储过程usp_getno时出错",e);
	        }
	        return (String) queryMap.get("numValue");
	        
	    }
	    
	    /**
	     * 调用sequence生成主键
	     * @param sequenceName
	     * @return
	     * @throws Exception
	     */
	    public String generatePrimaryKeyBySequence(String sequenceName) throws Exception{
	        Map<String,String> queryMap = new HashMap<String, String>();
	        queryMap.put("sequenceName", sequenceName);
	        return this.commonMapper.generatePrimaryKeyBySequence(queryMap);
	    }

	    /**
	     * 查询app使用量
	     * @Description
	     * @return
	     */
		public Long getAppUserCount() {
			Long userCount = this.commonMapper.getAppUserCount();
			return userCount;
		}
		
		


		@DataSource(name=DataSource.ftd)
		public BSDcmntInfo getBSDcmntInfoByDocumengId(String documentId) {
			
			Map<String,String> param = new HashMap<String,String>();
			param.put("documentId", documentId);
			return this.commonMapper.getBSDcmntInfoByDocumengId(param);
		}

		public CfgFtp getCfgFtpByPathCode(String cfgFtpPathCode) {
			Map<String,String> param = new HashMap<String,String>();
			param.put("pathCode", cfgFtpPathCode);
			return this.commonMapper.getCfgFtpByPathCode(param);
		}
		
		public Map<String, Object> queryAppNewVersion(String appid) throws Exception {
			return commonMapper.queryAppNewVersion(appid);
		}
		
		public int insertNewAppInfo(final String version, final String appid, final String apkpath, final String version2, final String ipapath) throws Exception {
			Map<String, Object> appinfo = new HashMap<String, Object>();
			appinfo.put("version", version);
			appinfo.put("appid", appid);
			appinfo.put("apkpath", apkpath);
			appinfo.put("version2", version2);
			appinfo.put("ipapath", ipapath);
			return commonMapper.insertNewAppInfo(appinfo);
		}
}
