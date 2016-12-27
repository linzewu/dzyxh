package com.xs.dzyxh.entity.tongan;

import javax.persistence.Column;

public class DrvTempMidId  implements java.io.Serializable{
	private String sfzmhm;
	private String qh;
	private String jxdm;

	@Column(name = "SFZMHM", length = 18)
	public String getSfzmhm() {
		return sfzmhm;
	}

	public void setSfzmhm(String sfzmhm) {
		this.sfzmhm = sfzmhm;
	}

	@Column(name = "QH", length = 10)
	public String getQh() {
		return qh;
	}

	public void setQh(String qh) {
		this.qh = qh;
	}
	

	@Column(name = "JXDM", length = 20)
	public String getJxdm() {
		return jxdm;
	}

	public void setJxdm(String jxdm) {
		this.jxdm = jxdm;
	}
}
