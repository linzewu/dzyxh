package com.xs.dzyxh.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.dzyxh.entity.BaseEntity;

@Scope("prototype")
@Component("role")
@Entity
@Table(name = "TB_Role")
public class Role extends BaseEntity {
	
	//角色名称
	@Column(length=120)
	private String jsmc;
	
	//角色级别
	@Column
	private Integer jsjb;
	
	//角色类型
	@Column
	private Integer jslx;
	
	//角色权限
	@Column(length=4000)
	private String jsqx;
	
	//备注
	@Column(length=400)
	private String bz;
	

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getJsqx() {
		return jsqx;
	}

	public void setJsqx(String jsqx) {
		this.jsqx = jsqx;
	}

	public String getJsmc() {
		return jsmc;
	}

	public Integer getJsjb() {
		return jsjb;
	}

	public Integer getJslx() {
		return jslx;
	}

	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}

	public void setJsjb(Integer jsjb) {
		this.jsjb = jsjb;
	}

	public void setJslx(Integer jslx) {
		this.jslx = jslx;
	} 

}
