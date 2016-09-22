package com.xs.dzyxh.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

@Scope("prototype")
@Component("user")
@Entity
@Table(name = "TB_User")
public class User extends BaseEntity {
	
	
	public final static Integer ZT_TY=2;
	
	public final static Integer ZT_CZMM=0;
	
	public final static Integer ZT_ZC=1;
	
	@Column(length=32)
	private String bmdm;
	
	@Column(length=32)
	private String yhxm;
	
	@Column(length=32,unique=true)
	private String yhm;
	
	@Column(length=32)
	private String mm;
	
	@Column(length=32)
	private String sfzh;
	
	@Column(length=32)
	private String gh;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat( pattern = "yyyy-MM-dd" )
	@Column
	private Date mmyxq;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat( pattern = "yyyy-MM-dd" )
	@Column
	private Date zhyxq;
	
	@Column(length=32)
	private String ipqsdz;
	
	@Column(length=32)
	private String ipjsdz;
	
	@Column(length=1000)
	private String gdip;
	
	@Column
	private Integer zt;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	@Column
	private Date zjdlsj;
	
	@Column
	private Integer qxms;
	
	@Column(length=2000)
	private String js;
	
	@Column(length=8000)
	private String qx;
	
	

	public String getMm() {
		return mm;
	}

	public void setMm(String mm) {
		this.mm = mm;
	}

	public String getJs() {
		return js;
	}

	public String getQx() {
		return qx;
	}


	public void setJs(String js) {
		this.js = js;
	}

	public void setQx(String qx) {
		this.qx = qx;
	}

	public String getBmdm() {
		return bmdm;
	}

	public String getYhxm() {
		return yhxm;
	}

	public String getYhm() {
		return yhm;
	}

	public String getSfzh() {
		return sfzh;
	}

	public String getGh() {
		return gh;
	}

	public Date getMmyxq() {
		return mmyxq;
	}

	public Date getZhyxq() {
		return zhyxq;
	}

	public String getIpqsdz() {
		return ipqsdz;
	}

	public String getIpjsdz() {
		return ipjsdz;
	}

	public String getGdip() {
		return gdip;
	}


	public void setBmdm(String bmdm) {
		this.bmdm = bmdm;
	}

	public void setYhxm(String yhxm) {
		this.yhxm = yhxm;
	}

	public void setYhm(String yhm) {
		this.yhm = yhm;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	public void setGh(String gh) {
		this.gh = gh;
	}

	public void setMmyxq(Date mmyxq) {
		this.mmyxq = mmyxq;
	}

	public void setZhyxq(Date zhyxq) {
		this.zhyxq = zhyxq;
	}

	public void setIpqsdz(String ipqsdz) {
		this.ipqsdz = ipqsdz;
	}

	public void setIpjsdz(String ipjsdz) {
		this.ipjsdz = ipjsdz;
	}

	public void setGdip(String gdip) {
		this.gdip = gdip;
	}

	public Integer getZt() {
		return zt;
	}

	public Date getZjdlsj() {
		return zjdlsj;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public void setZjdlsj(Date zjdlsj) {
		this.zjdlsj = zjdlsj;
	}

	public Integer getQxms() {
		return qxms;
	}

	public void setQxms(Integer qxms) {
		this.qxms = qxms;
	}


	
}
	
	
