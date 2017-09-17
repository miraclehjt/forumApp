package com.chenzl.app.entity;

import java.util.Date;

public class SysUserLogin {
    private String userId;

    private Date loginTime;

    private Date loginoutTime;

    private String ip;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }
}