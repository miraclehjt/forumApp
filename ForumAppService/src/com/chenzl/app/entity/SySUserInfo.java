package com.chenzl.app.entity;

public class SySUserInfo extends SysUser {

	private static final long serialVersionUID = 1L;
	
	private String portrait;
	
	private String areaName;

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}
