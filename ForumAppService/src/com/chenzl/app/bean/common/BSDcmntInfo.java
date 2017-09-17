package com.chenzl.app.bean.common;
import java.io.Serializable;
/**
 * @author chenzl
 */
public class BSDcmntInfo implements Serializable {
private static final long serialVersionUID = -7272805579360721607L;
	
	private String transDocName;
	private String cfgFtpPathCode;
	private String documentName;
	public String getTransDocName() {
		return transDocName;
	}
	public void setTransDocName(String transDocName) {
		this.transDocName = transDocName;
	}
	public String getCfgFtpPathCode() {
		return cfgFtpPathCode;
	}
	public void setCfgFtpPathCode(String cfgFtpPathCode) {
		this.cfgFtpPathCode = cfgFtpPathCode;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
}
