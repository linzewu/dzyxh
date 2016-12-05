package com.xs.dzyxh.manager.driver;

import java.util.Collection;
import java.util.List;

import com.xs.dzyxh.entity.driimg.DrivingPhoto;
import com.xs.dzyxh.entity.driver.DrivingApply;
import com.xs.dzyxh.entity.driver.DrivingBase;


public interface IDrivingApplyManager {
	public List<DrivingApply> getDrivingApplys(DrivingApply base,Integer page,Integer rows);
	public DrivingApply getDrivingApplyById(DrivingApply base);
	public byte[] getApplyImgToByte(DrivingBase base,DrivingApply apply) throws Exception;
	public byte[] getApplyImgToByte(DrivingBase base,DrivingApply apply,Collection<DrivingPhoto> photos) throws Exception;
}
