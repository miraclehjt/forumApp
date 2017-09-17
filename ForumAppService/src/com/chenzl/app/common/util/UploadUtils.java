package com.chenzl.app.common.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.util.StringUtils;

import com.chenzl.app.constant.CommonConstant;
/**
 * 文件上传工具
 * @author chenzl
 *
 */
public class UploadUtils {
	
public final static String WEB_SITE = "cscapp";
	
	public final static String FILE_TYPE_APK = "apk";
	
	public final static String FILE_TYPE_PNG = "png";
	
	public final static String FILE_TYPE_JPG = "jpg";
	
	private final static String FILE = "/file";
	
	private final static String IMAGE = "/image";
	
//	private final static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
//	private final static SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public static String getFileNameWithTimestamp(String fileName) {
		if (StringUtils.isEmpty(fileName))
			return "";
		StringBuffer file = new StringBuffer(String.valueOf((new Date()).getTime()));
		file.append(fileName);
		return file.toString();
	}
	
	public static String getFileDate(Timestamp timestamp) {
		if (timestamp == null)
			return "";
		return new SimpleDateFormat("yyyyMMdd").format(timestamp);
	}
	
	public static String getFileDateTime(Timestamp timestamp) {
		if (timestamp == null)
			return "";
		return new SimpleDateFormat("yyyyMMddHHmmss").format(timestamp);
	}
	
	public static String createFilePath(String website, String pictype, 
			String picDate, String fileName) {
		StringBuffer path = new StringBuffer(FILE);
		path.append("/").append(website)
			.append("/").append(pictype)
			.append("/").append(picDate)
			.append("/").append(fileName);
		return path.toString();
	}
	
	public static String createImagePath(String website, String pictype, 
			String picDate, String fileName) {
		StringBuffer path = new StringBuffer(IMAGE);
		path.append("/").append(website)
			.append("/").append(pictype)
			.append("/").append(picDate)
			.append("/").append(fileName);
		return path.toString();
	}
	
	public static String getFileUrlPath(String filePath) {
		if (StringUtils.isEmpty(filePath))
			return "";
		String path = filePath.replaceAll("\\\\","/");
		return path;
	}
	
	public static String getFileServerPath(String filePath) {
		if (!StringUtils.isEmpty(filePath) && 
				(filePath.indexOf("http") != -1 || filePath.indexOf("https") != -1))
			return filePath;
		String serverPath = CommonConstant.RS_MANAGER_SERVER;
		StringBuffer fileServerPath = new StringBuffer();
		fileServerPath.append(serverPath).append(filePath);
		return fileServerPath.toString();
	}
	
	public static String getImageServerPath(String filePath) {
		return getFileServerPath(filePath);
	}
}
