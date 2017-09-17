package com.chenzl.app.mappers.common;

import java.util.Map;
import java.sql.Timestamp;
import com.chenzl.app.bean.common.BSDcmntInfo;
import com.chenzl.app.bean.ftp.CfgFtp;

/**
 * 封闭一些公用的mapper方法，供commonService调用
 * @author chenzl
 *
 */
public interface CommonMapper {
	 /**
     * 调用usp_getno存储过程生成主键
     * @param queryMap
     */
    void queryPrimaryKeyByProcedure(Map<String,Object> queryMap);
    
    /**
     * 生成需要的主键
     * @param sequenceName
     * @return 
     */
    String generatePrimaryKeyBySequence(Map<String,String> queryMap);

	Long getAppUserCount();

	BSDcmntInfo getBSDcmntInfoByDocumengId(Map<String, String> param);

	CfgFtp getCfgFtpByPathCode(Map<String, String> param);
	
	Map<String, Object> queryAppNewVersion(String appid);
	
	int insertNewAppInfo(Map<String, Object> param);
	
	Timestamp selectSysDate();
}
