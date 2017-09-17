package com.chenzl.app.bean.common;

import java.io.Serializable;

/** 
 * @author chenzl
 * @Description:
 */
public class StudyTypeBean implements Serializable{

	
	private static final long serialVersionUID = -5791313041704131361L;
	
	private String typeId;
	private String typeName;
	private String typeImage;
	private String remark;
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeImage() {
		return typeImage;
	}
	public void setTypeImage(String typeImage) {
		this.typeImage = typeImage;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
