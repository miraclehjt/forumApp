package com.chenzl.app.entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

@Table( name = "sys_user_tel")
public class SysUserTel  implements Serializable{
	
	private static final long serialVersionUID = -6128516443318473598L;

	@Id
    private String userId;

    private String phone;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
}