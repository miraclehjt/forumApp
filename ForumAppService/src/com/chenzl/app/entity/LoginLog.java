package com.chenzl.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="sys_user_login")
public class LoginLog  implements Serializable{
	
	private static final long serialVersionUID = -1165135219218183167L;

	@Id
    private String userId;

    private Date loginTime;

    private Date loginoutTime;

    private String loginIp;
    
    private String sessionId;
    
    private String Login_Faild_Result;
    
    private String Login_Flag;//0成功1失败

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLoginoutTime() {
        return loginoutTime;
    }

    public void setLoginoutTime(Date loginoutTime) {
        this.loginoutTime = loginoutTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String ip) {
        this.loginIp = loginIp == null ? null : ip.trim();
    }

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getLogin_Faild_Result() {
		return Login_Faild_Result;
	}
	public void setLogin_Faild_Result(String login_Faild_Result) {
		Login_Faild_Result = login_Faild_Result;
	}

	public String getLogin_Flag() {
		return Login_Flag;
	}

	public void setLogin_Flag(String login_Flag) {
		Login_Flag = login_Flag;
	}
    
}