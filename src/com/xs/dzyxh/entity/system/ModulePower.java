package com.xs.dzyxh.entity.system;

import java.util.ArrayList;
import java.util.List;

public class ModulePower implements Comparable {
	// 模块
	private String module;

	// 目录
	private String catalog;

	// 应用
	private String app;
	// 图标路径
	private String icoUrl;
	// 应用顺序
	private int appIndex;
	// 模块顺序
	private int moduIndex;
	
	private String href;
	private List<Power> powers = new ArrayList<Power>();

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getIcoUrl() {
		return icoUrl;
	}

	public void setIcoUrl(String icoUrl) {
		this.icoUrl = icoUrl;
	}

	public int getAppIndex() {
		return appIndex;
	}

	public void setAppIndex(int appIndex) {
		this.appIndex = appIndex;
	}

	public int getModuIndex() {
		return moduIndex;
	}

	public void setModuIndex(int moduIndex) {
		this.moduIndex = moduIndex;
	}

	public List<Power> getPowers() {
		return powers;
	}

	public void setPowers(List<Power> powers) {
		this.powers = powers;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	@Override
	public int compareTo(Object arg0) {
		ModulePower power = (ModulePower) arg0;
		return String.valueOf(this.appIndex).compareTo(String.valueOf(power.getAppIndex()));
	}
	
	public boolean isExitMenu(String url){
		for(Power p:powers){
			if(p.getKey().equals(url)){
				return true;
			}
		}
		return false;
	}
}
