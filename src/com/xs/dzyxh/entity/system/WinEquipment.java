package com.xs.dzyxh.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xs.dzyxh.entity.BaseEntity;

@Scope("prototype")
@Component("winequipment")
@Entity
@Table(name = "TB_Win_Equipment")
public class WinEquipment extends BaseEntity{
	
	private String ip;
	private Integer status;
	private String name;
	private String ywms;
	@Column(name = "YWMS")
	public String getYwms() {
		return ywms;
	}
	public void setYwms(String ywms) {
		this.ywms = ywms;
	}
	@Column(length=20,name = "IP")
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@Column(name = "STATUS")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(length=200,name = "NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
