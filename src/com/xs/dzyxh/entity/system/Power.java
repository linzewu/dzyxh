package com.xs.dzyxh.entity.system;

public class Power {

	// 模块
	private String module;

	// 目录
	private String catalog;

	// 应用
	private String app;

	// 功能
	private String function;

	// 请求路径
	private String key;

	@Override
	public int hashCode() {
		return module.hashCode() + app.hashCode() + function.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Power other = (Power) obj;

		if (other.getApp().equals(this.app) && other.getModule().equals(this.getModule())
				&& other.getFunction().equals(this.function)) {
			return true;
		} else {
			return false;
		}
	}

	public String getModule() {
		return module;
	}

	public String getCatalog() {
		return catalog;
	}

	public String getApp() {
		return app;
	}

	public String getFunction() {
		return function;
	}

	public String[] buttonName;

	public void setModule(String module) {
		this.module = module;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String[] getButtonName() {
		return buttonName;
	}

	public void setButtonName(String[] buttonName) {
		this.buttonName = buttonName;
	}

	@Override
	public String toString() {

		return "模块名称：" + this.module + "\t 应用名称：" + this.app + "\t 功能名称：" + this.function + "\t key:" + this.key;

	}

}
