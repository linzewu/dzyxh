package com.xs.dzyxh.entity.system;

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
@Table(name = "TM_SCAN_JOB_LOG")
public class ScanJobLog {
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private Long id;
	
	@Column
	private Date kssj;
	
	@Column
	private Date jssj;
	
	@Column
	private Integer sjl;
	
	@Column(length=10)
	private String rwlx;
	
	@Column(length=2000)
	private String ycxx;
	
	
	

	public String getYcxx() {
		return ycxx;
	}

	public void setYcxx(String ycxx) {
		this.ycxx = ycxx;
	}

	public String getRwlx() {
		return rwlx;
	}

	public void setRwlx(String rwlx) {
		this.rwlx = rwlx;
	}


	public Date getKssj() {
		return kssj;
	}

	public Date getJssj() {
		return jssj;
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setKssj(Date kssj) {
		this.kssj = kssj;
	}

	public void setJssj(Date jssj) {
		this.jssj = jssj;
	}

	public Integer getSjl() {
		return sjl;
	}

	public void setSjl(Integer sjl) {
		this.sjl = sjl;
	}

	
	

}
