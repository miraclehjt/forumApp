package com.chenzl.app.shiro;

import java.io.InputStream;

public interface ResClient {
	static final String defaultEncoding="utf-8"; 
	/**
	 * 文件上传
	 * @param fileName 服务器文件存放路径
	 * @param localFile本地文件绝对路径
	 * @return
	 * @throws Exception
	 */
	public String put(String fileName, String localFile)throws Exception ;
	/**
	 * 
	* @Function: write
	* @Description:传入文件内容，生成文件
	*
	* @param fileName  服务器存放文件相对路径
	* @param fileContent 文件内容
	* @param encoding 编码集
	* @return
	* @throws Exception
	*
	* @version: v1.0.0
	* @author: qiaoww
	* @date: 2013-1-21 上午9:40:41 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2013-1-21     qiaoww           v1.0.0             创建
	 */
	public String write(String fileName, String fileContent,String encoding)throws Exception ;
    /**
     * 文件上传
     * @param fileName 服务端存放路径
     * @param bytes 文件字节流
     * @return
     * @throws Exception
     */
	public String put(String fileName, byte[] bytes)throws Exception ;
    /**
     * 文件上传
     * @param fileName 服务端存放路径
     * @param inputStream 文件流
     * @return
     * @throws Exception
     */
	public String put(String fileName, InputStream inputStream)throws Exception ;
    /**
     * 图片上传并处理
     * @param fileName 服务端存放路径
     * @param localFile本地文件绝对路径
     * @param height 切分高度
     * @param width  切分长度
     * @return
     * @throws Exception
     */
	public String put(String fileName, String localFile, int height, int width)throws Exception ;
    /**
     * 图片上传并处理
     * @param fileName 服务端文件存放路径
     * @param bytes 字节流
     * @param height切分高度
     * @param width切分宽度
     * @return
     * @throws Exception
     */
	public String put(String fileName, byte[] bytes, int height, int width)throws Exception ;
    /**
     * 图片上传并处理
     * @param fileName 服务端文件存放路径
     * @param inputStream 文件流
     * @param height 切分高度
     * @param width 切分宽度
     * @return
     * @throws Exception
     */
	public String put(String fileName, InputStream inputStream, int height,
			int width)throws Exception ;
    /**
     * 图片上传并处理
     * @param fileName 服务端文件存放路径
     * @param localFile本地文件绝对路径
     * @param queryParam切分以及水印参数(如果为空，使用服务端默认配置)
     * @return
     * @throws Exception
     */
	public String put(String fileName, String localFile, RsQueryParam queryParam)throws Exception ;
    /**
     * 图片上传并处理
     * @param fileName 服务端文件存放路径
     * @param bytes字节流
     * @param queryParam切分以及水印参数(如果为空，使用服务端默认配置)
     * @return
     * @throws Exception
     */
	public String put(String fileName, byte[] bytes, RsQueryParam queryParam)throws Exception ;
    /**
     * 图片上传并处理
     * @param fileName 服务端文件存放路径
     * @param inputStream文件流
     * @param queryParam切分以及水印参数(如果为空，使用服务端默认配置)
     * @return
     * @throws Exception
     */
	public String put(String fileName, InputStream inputStream,
			RsQueryParam queryParam)throws Exception ;
	/**
	 * 图片上传并处理
	 * @param fileName 服务端文件存放路径
	 * @param inputStream 文件流
	 * @param configId 配置ID
	 * @return
	 * @throws Exception
	 */
	public String put(String fileName, InputStream inputStream, ResConfigId configId)throws Exception ;
	/**
	 * 图片上传并处理
	 * @param fileName 服务端文件存放路径
	 * @param localFile 本地文件绝对路径
	 * @param configId 配置ID
	 * @return
	 * @throws Exception
	 */
	public String put(String fileName, String localFile, ResConfigId configId)throws Exception ;
	/**
	 * 图片上传并处理
	 * @param fileName 服务端文件存放路径
	 * @param bytes 字节流
	 * @param configId 配置ID
	 * @return
	 * @throws Exception
	 */
	public String put(String fileName, byte[] bytes, ResConfigId configId)throws Exception ;
	/**
	 * 获取图片路径
	* @Function: getFilePath
	* @param srcFilePath 文件原路径
	* @param resizeSizes 裁剪尺寸
	* @return
	* @throws Exception
	*
	* @version: v1.0.0
	* @author: qiaoww
	* @date: 2013-3-12 下午4:28:08 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2013-3-12     qiaoww           v1.0.0             创建
	 */
	public String getFilePath(String srcFilePath,ResResizeSizes resizeSizes)throws Exception;
	/**
	 * 文件获取
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public InputStream get(String fileName)throws Exception ;
    /**
     * 文件删除
     * @param fileName
     * @throws Exception
     */
	public void delete(String fileName)throws Exception ;
	/**
	 * 
	* 
	* 
	* @ClassName: ResConfigId.java
	* @Description: 服务端裁剪配置接口    应用层需要用menu类实现
	*
	* @version: v1.0.0
	* @author: qiaoww
	* @date: 2013-3-7 下午6:02:30 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2013-3-7     qiaoww           v1.0.0              创建
	 */
	public interface ResConfigId{
		public String getCongigId();
		
	}
	public interface ResResizeSizes{
		public String getResizeSizes();
		
	}
}
