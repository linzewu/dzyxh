package com.xs.dzyxh.manager;

import java.util.List;

import com.xs.dzyxh.entity.driver.DrivingBase;

public interface IDrivingBaseManager {
	public List<DrivingBase> getDrivingBases(DrivingBase base,Integer page,Integer rows);
	public DrivingBase getDrivingBaseById(DrivingBase base);
	public Integer getUserCount(DrivingBase base);
}
