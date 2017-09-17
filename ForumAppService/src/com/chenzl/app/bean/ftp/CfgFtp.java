package com.chenzl.app.bean.ftp;

import java.io.Serializable;

public class CfgFtp implements Serializable{
	private static final long serialVersionUID = -1536645221709813521L;

	private String hostIp;
	private String port;
	private String userName;
	private String password;
	private String remotePath;
	
	public String getHostIp() {
		return hostIp;
	}
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRemotePath() {
		return remotePath;
	}
	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}
}
