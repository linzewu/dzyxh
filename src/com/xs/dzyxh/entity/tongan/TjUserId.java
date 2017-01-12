package com.xs.dzyxh.entity.tongan;

import javax.persistence.Column;

public class TjUserId implements java.io.Serializable {
	private String xm;
	private String yymc;
	@Column(name = "XM", nullable = false, length = 20)
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	@Column(name = "YYMC", nullable = false, length = 200)
	public String getYymc() {
		return yymc;
	}
	public void setYymc(String yymc) {
		this.yymc = yymc;
	}
	
	
}
