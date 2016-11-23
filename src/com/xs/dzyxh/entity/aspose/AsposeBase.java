package com.xs.dzyxh.entity.aspose;

public abstract class AsposeBase {
	private String name;
	private Object value;
	private String type;

	public AsposeBase(String name,Object value){
		this.name=name;
		this.value=value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}	
