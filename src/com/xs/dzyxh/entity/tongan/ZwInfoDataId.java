package com.xs.dzyxh.entity.tongan;

import javax.persistence.Column;

public class ZwInfoDataId  implements java.io.Serializable {
	private String id;
	private String subKey;
	
	@Column(name = "USER_ID", nullable = false, length = 20)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "SUB_KEY", nullable = false, length = 20)
	public String getSubKey() {
		return subKey;
	}
	public void setSubKey(String subKey) {
		this.subKey = subKey;
	}
}
