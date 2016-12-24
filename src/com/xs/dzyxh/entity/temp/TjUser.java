package com.xs.dzyxh.entity.temp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
@Entity
@Table(name = "TJ_USER", schema = "TONGAN")
public class TjUser {
	private String xm;
	private String zw;
	private String kl;
	private String zwmb;
	private String yymc;
	private byte[] dwyztp;
	
	@Column(name = "XM", length = 20)
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	
	@Column(name = "ZW", length = 20)
	public String getZw() {
		return zw;
	}
	public void setZw(String zw) {
		this.zw = zw;
	}
	
	@Column(name = "KL", length = 20)
	public String getKl() {
		return kl;
	}
	public void setKl(String kl) {
		this.kl = kl;
	}
	
	@Column(name = "ZWMB", length = 300)
	public String getZwmb() {
		return zwmb;
	}
	public void setZwmb(String zwmb) {
		this.zwmb = zwmb;
	}
	
	@Column(name = "YYMC", length = 200)
	public String getYymc() {
		return yymc;
	}
	public void setYymc(String yymc) {
		this.yymc = yymc;
	}
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "DWYZTP", columnDefinition = "BLOB")
	public byte[] getDwyztp() {
		return dwyztp;
	}
	public void setDwyztp(byte[] dwyztp) {
		this.dwyztp = dwyztp;
	}
	
	
}
