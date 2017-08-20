package com.xs.dzyxh.manager.job;

import java.util.List;
import java.util.Map;

import com.xs.dzyxh.entity.driimg.DrivingPhoto;
import com.xs.dzyxh.entity.driver.DrivingApply;
import com.xs.dzyxh.entity.driver.DrivingBase;
import com.xs.dzyxh.entity.driver.DrivingExamination;
import com.xs.dzyxh.entity.tongan.SqPhotos;

public interface IDataScanJobManager {
	public void saveAll(DrivingBase base,DrivingApply apply,DrivingExamination examination,Map<String, DrivingPhoto> imgs,List<Object> datas)throws Exception ;
	
	public void saveTongan(List<Object> datas);
	
	public void saveImg(SqPhotos sqPhotos, Map<String, DrivingPhoto> photos)throws Exception ;
}
