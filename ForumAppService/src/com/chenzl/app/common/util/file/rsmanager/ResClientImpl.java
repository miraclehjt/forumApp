package com.chenzl.app.common.util.file.rsmanager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import com.chenzl.app.shiro.ResClient;
import com.chenzl.app.shiro.RsQueryParam;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;


public class ResClientImpl implements ResClient {
	
	public  String serverUrl;
	public static Client client = null;
	private static final int fileSize = 1024 * 1024 * 20;

	public String put(String fileName, String localFile) throws Exception {
		if (client == null) {
			client = Client.create();
		}
		String url = this.getServerUrl() + fileName;
		WebResource res = client.resource(url);
		byte[] bytes = FileUtils.readFileToByteArray(new File(localFile));
		MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
		queryParams.add("default", false);
		return res.queryParams(queryParams).post(String.class, bytes);
	}
	public String write(String fileName, String fileContent,String encoding) throws Exception {
		if (client == null) {
			client = Client.create();
		}
		String url = this.getServerUrl() + fileName;
		WebResource res = client.resource(url);
		MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
		if(encoding==null||"".equals(encoding)){
			encoding=defaultEncoding;
		}
		queryParams.add("encoding", encoding);
		return res.queryParams(queryParams).post(String.class,fileContent);
	}
	public String put(String fileName, byte[] bytes) throws Exception {
		if (client == null) {
			client = Client.create();
		}
		String url = this.getServerUrl() + fileName;
		WebResource res = client.resource(url);
		MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
		queryParams.add("default", false);
		String response = res.queryParams(queryParams).put(String.class, bytes);
		return response;
	}

	public String put(String fileName, InputStream inputStream)
			throws Exception {
		if (client == null) {
			client = Client.create();
		}
		String url = this.getServerUrl() + fileName;
		WebResource res = client.resource(url);
		MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
		queryParams.add("default", false);
		return res.queryParams(queryParams).put(String.class, inputStreamToByte(inputStream));
	}

	public String put(String fileName, String localFile, int height, int width)
			throws Exception {
		if (client == null) {
			client = Client.create();
		}
		String url = this.getServerUrl() + fileName;
		MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
		queryParams.add("height", height);
		queryParams.add("width", width);
		WebResource res = client.resource(url);
		byte[] bytes = FileUtils.readFileToByteArray(new File(localFile));
		return res.queryParams(queryParams).post(String.class, bytes);
	}

	public String put(String fileName, byte[] bytes, int height, int width)
			throws Exception {
		if (client == null) {
			client = Client.create();
		}
		String url = this.getServerUrl() + fileName;
		MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
		queryParams.add("height", height);
		queryParams.add("width", width);
		WebResource res = client.resource(url);
		return res.queryParams(queryParams).post(String.class, bytes);
	}

	public String put(String fileName, InputStream inputStream, int height,
			int width) throws Exception {
		if (client == null) {
			client = Client.create();
		}
		String url = this.getServerUrl() + fileName;
		MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
		queryParams.add("height", height);
		queryParams.add("width", width);
		WebResource res = client.resource(url);
		return res.queryParams(queryParams).post(String.class,
				inputStreamToByte(inputStream));
	}

	public String put(String fileName, String localFile, RsQueryParam queryParam)
			throws Exception {
		if (client == null) {
			client = Client.create();
		}
		String url = this.getServerUrl() + fileName;
		MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
		queryParams.add("query", queryParam.toString());
		WebResource res = client.resource(url);
		byte[] bytes = FileUtils.readFileToByteArray(new File(localFile));
		return res.queryParams(queryParams).put(String.class, bytes);
	}

	public String put(String fileName, byte[] bytes, RsQueryParam queryParam)
			throws Exception {
		if (client == null) {
			client = Client.create();
		}
		String url = this.getServerUrl() + fileName;
		MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
		queryParams.add("query", queryParam.toString());
		WebResource res = client.resource(url);
		return res.queryParams(queryParams).put(String.class, bytes);
	}

	public String put(String fileName, InputStream inputStream,
			RsQueryParam queryParam) throws Exception {
		if (client == null) {
			client = Client.create();
		}
		String url = this.getServerUrl() + fileName;
		MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
		queryParams.add("query", queryParam.toString());
		WebResource res = client.resource(url);
		return res.queryParams(queryParams).put(String.class,
				inputStreamToByte(inputStream));
	}

	public String put(String fileName, InputStream inputStream, ResConfigId configId)
			throws Exception {
		if (client == null) {
			client = Client.create();
		}
		String url = this.getServerUrl() + fileName;
		MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
		queryParams.add("configId", configId.getCongigId());
		WebResource res = client.resource(url);
		return res.queryParams(queryParams).put(String.class,
				inputStreamToByte(inputStream));
	}

	public String put(String fileName, String localFile, ResConfigId configId)
			throws Exception {
		if (client == null) {
			client = Client.create();
		}
		String url = this.getServerUrl() + fileName;
		MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
		queryParams.add("configId", configId.getCongigId());
		WebResource res = client.resource(url);
		byte[] bytes = FileUtils.readFileToByteArray(new File(localFile));
		return res.queryParams(queryParams).put(String.class, bytes);
	}

	public String put(String fileName, byte[] bytes, ResConfigId configId)
			throws Exception {
		if (client == null) {
			client = Client.create();
		}
		String url = this.getServerUrl() + fileName;
		MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
		System.out.println(configId.getCongigId());
		queryParams.add("configId", configId.getCongigId());
		WebResource res = client.resource(url);
		return res.queryParams(queryParams).put(String.class, bytes);
	}
	public InputStream get(String filePath) throws Exception {
		if (client == null) {
			client = Client.create();
		}
		return null;
	}

	public void delete(String filePath) throws Exception {
		if (client == null) {
			client = Client.create();
		}
		String url = this.getServerUrl() + filePath;
		WebResource res = client.resource(url);
		res.queryParam("filePath", filePath).delete();
	}

	private byte[] inputStreamToByte(InputStream in) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[fileSize];
		int count = -1;
		while ((count = in.read(data, 0, fileSize)) != -1)
			outStream.write(data, 0, count);
		return outStream.toByteArray();
	}

	public  String getServerUrl() {
		return serverUrl;
	}

	public  void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	public String getFilePath(String srcFilePath, ResResizeSizes resizeSizes)
			throws Exception {
		return srcFilePath.substring(0,srcFilePath.lastIndexOf("."))+"_"+resizeSizes.getResizeSizes() +srcFilePath.substring(srcFilePath.lastIndexOf("."));
	}
	
}
