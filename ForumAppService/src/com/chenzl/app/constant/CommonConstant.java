package com.chenzl.app.constant;
/** 
 * @author chenzl
 * @Description:
 */
public class CommonConstant {
	
	//知识库ftp的http访问地址
	public final static String AIKB_FTP_ADDRESS = "http://localhost:8090";
	// FIXME 是否是生产模式 上线时改为 true
	public final static boolean production = false;

	// 外网服务器地址 正式服务器
	//public final static String SERVER_PATH = production ? "http://127.0.0.1:16500/ForumAppService" : "http://127.0.0.1:8090/ForumAppService";
	//知识库ftp的图片访问地址 
	public final static String AIKB_FTP_FILE_ADDRESS = "http://localhost:8090"; 


	// 知识库ftp访问路径 正式环境 / 测试环境 
	public final static String AIKB_FTP_PATH = production ? "/aics/kbstatic" : "/aics/kbstatic";
	
	//图片的存放路径 测试环境地址
	public final static String AIKB_HTTP_FILE_PATH = "/aics/kbstatic/newattach/";
	// 文件服务器地址 测试 内网
	public final static String RS_MANAGER_SERVER = "http://localhost:8090/rsmanager/upload";


	// 根据是否生产模式判断 正式地址 / 测试地址
	public final static String OCS_URL = "http://localhost:8090/ocs_front/ocsfront/html5/webChat.jsp";
}
