package com.xs.dzyxh.manager.img;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.xs.dzyxh.entity.aspose.ImageData;

public interface IAsposeManager {

	public OutputStream convertPng(InputStream word,OutputStream out, Map<String, String> stringDatas,List<ImageData> imageDatas) throws Exception;
	public OutputStream convertJpeg(InputStream word,OutputStream out, Map<String, String> stringDatas,List<ImageData> imageDatas) throws Exception;
	public void convertPng(InputStream word,String saveFilePath, Map<String, String> stringDatas,List<ImageData> imageDatas) throws Exception;
	public void convertJpeg(InputStream word,String saveFilePath, Map<String, String> stringDatas,List<ImageData> imageDatas) throws Exception;
}
