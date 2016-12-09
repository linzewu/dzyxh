package com.xs.dzyxh.entity.aspose;

import java.io.InputStream;

import com.aspose.words.HorizontalAlignment;
import com.aspose.words.SaveFormat;

public class ImageData extends AsposeBase{

	public ImageData(String name, InputStream value) {
		super(name, value);
	}
	public ImageData(String name, InputStream value,Integer width,Integer height,Integer insertType) {
		super(name, value);
		this.width=width;
		this.height=height;
		this.insertType=insertType;
	}
	public ImageData(String name, InputStream value,Integer width,Integer height,Integer insertType,int align) {
		super(name, value);
		this.width=width;
		this.height=height;
		this.align=align;
		this.insertType=insertType;
	}
	private Integer width;
	private Integer height;
	private int align=HorizontalAlignment.CENTER;
	private  int imageType=SaveFormat.JPEG;
	private Integer insertType=0;

	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getAlign() {
		return align;
	}
	public void setAlign(Integer align) {
		this.align = align;
	}
	public int getImageType() {
		return imageType;
	}
	public void setImageType(int imageType) {
		this.imageType = imageType;
	}
	public void setAlign(int align) {
		this.align = align;
	}
	public Integer getInsertType() {
		return insertType;
	}
	public void setInsertType(Integer insertType) {
		this.insertType = insertType;
	}

}
