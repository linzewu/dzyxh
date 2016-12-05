package com.xs.dzyxh.manager.driver;

import java.util.Collection;
import java.util.List;

import com.xs.dzyxh.entity.driimg.DrivingPhoto;
import com.xs.dzyxh.entity.driver.DrivingBase;
import com.xs.dzyxh.entity.driver.DrivingExamination;

public interface IDrivingExaminationManager {
	public DrivingExamination getDrivingExamination(final DrivingExamination dir);
	public byte[] getExaminationImgToByte(DrivingExamination examinat) throws Exception;
	public List<DrivingExamination> getDrivingExaminations(final DrivingExamination examination, final Integer page, final Integer rows);
	public Integer getUserCount(final DrivingExamination dir);
	public byte[] getExaminationImgToByte( DrivingExamination examinat,Collection<DrivingPhoto> photos) throws Exception ;
}
