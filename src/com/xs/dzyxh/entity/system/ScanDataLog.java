package com.xs.dzyxh.entity.system;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;
@Scope("prototype")
@Entity
@Table(name = "TM_SCAN_DATA_LOG")
public class ScanDataLog implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6540974683770201725L;
	public final static String SJLX_BASE="01";
	public final static String SJLX_APPLY="02";
	public final static String SJLX_EXAMINATION="03";
	public final static String SJLX_DRIVINGPHOTO="04";
	
	@Id
	@Column(length=32)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	private String id;
	
	@Column(length=20)
	private String sfzmhm;
	
	@Column(length=10)
	private String jxdm;
	
	@Column(length=10)
	private String qh;
	
	@Column(length=10)
	private String sjlx;
	
	private Integer clzt;
	@Column(length=4000)
	private String cwxx;
	@Column
	private Date clsj;
	
	@Column
	private Date cjsj;

	public String getId() {
		return id;
	}


	public String getJxdm() {
		return jxdm;
	}

	public String getQh() {
		return qh;
	}

	public String getSjlx() {
		return sjlx;
	}

	public Date getCjsj() {
		return cjsj;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public void setJxdm(String jxdm) {
		this.jxdm = jxdm;
	}

	public void setQh(String qh) {
		this.qh = qh;
	}

	public void setSjlx(String sjlx) {
		this.sjlx = sjlx;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}


	public String getSfzmhm() {
		return sfzmhm;
	}


	public void setSfzmhm(String sfzmhm) {
		this.sfzmhm = sfzmhm;
	}




	public String getCwxx() {
		return cwxx;
	}


	public void setCwxx(String cwxx) {
		this.cwxx = cwxx;
	}


	public Integer getClzt() {
		return clzt;
	}


	public void setClzt(Integer clzt) {
		this.clzt = clzt;
	}


	public Date getClsj() {
		return clsj;
	}


	public void setClsj(Date clsj) {
		this.clsj = clsj;
	}
	
	
	

}
