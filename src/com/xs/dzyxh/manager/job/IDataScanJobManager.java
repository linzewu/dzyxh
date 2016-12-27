package com.xs.dzyxh.manager.job;

import java.util.Map;

import com.xs.dzyxh.entity.driimg.DrivingPhoto;
import com.xs.dzyxh.entity.driver.DrivingApply;
import com.xs.dzyxh.entity.driver.DrivingBase;
import com.xs.dzyxh.entity.driver.DrivingExamination;
import com.xs.dzyxh.entity.tongan.SqPhotos;

public interface IDataScanJobManager {
	public void saveAll(DrivingBase base,DrivingApply apply,DrivingExamination examination,Map<String, DrivingPhoto> imgs)throws Exception ;
	public void saveImg(SqPhotos sqPhotos, Map<String, DrivingPhoto> photos)throws Exception ;
}
