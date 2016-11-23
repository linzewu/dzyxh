package com.xs.dzyxh.manager.driver;

import com.xs.dzyxh.entity.driver.DrivingBase;
import com.xs.dzyxh.entity.driver.DrivingExamination;

public interface IDrivingExaminationManager {
	public DrivingExamination getDrivingExamination(final DrivingExamination dir);
	public byte[] getExaminationImgToByte(DrivingBase base,DrivingExamination examinat) throws Exception;
}
