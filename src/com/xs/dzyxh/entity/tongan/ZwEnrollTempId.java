package com.xs.dzyxh.entity.tongan;

import javax.persistence.Column;

public class ZwEnrollTempId  implements java.io.Serializable {
	private String userId;
	private String authenInfo;
	@Column(name = "USER_ID", nullable = false, length = 20)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(name = "AUTHEN_INFO", nullable = false, length = 20)
	public String getAuthenInfo() {
		return authenInfo;
	}

	public void setAuthenInfo(String authenInfo) {
		this.authenInfo = authenInfo;
	}
	
}
