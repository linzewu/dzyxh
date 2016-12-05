package com.xs.dzyxh.manager.job.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.dzyxh.entity.driimg.DrivingPhoto;
import com.xs.dzyxh.entity.driver.DrivingApply;
import com.xs.dzyxh.entity.driver.DrivingBase;
import com.xs.dzyxh.entity.driver.DrivingExamination;
import com.xs.dzyxh.manager.driver.IDrivingApplyManager;
import com.xs.dzyxh.manager.driver.IDrivingExaminationManager;
import com.xs.dzyxh.manager.job.IDataScanJobManager;
@Service("dataScanJobManagerImpl")
public class DataScanJobManagerImpl implements IDataScanJobManager {
	@Resource(name = "driverApplyManager")
	private IDrivingApplyManager drivingApplyManager;
	@Resource(name = "drivingExamination")
	private IDrivingExaminationManager drivingExaminationManager;
	
	@Resource(name = "driverHibernateTemplate")
	private HibernateTemplate driverHibernateTemplate;
	
	@Resource(name = "driimgHibernateTemplate")
	private HibernateTemplate driimgHibernateTemplate;
	
	@Override
	public void saveAll(DrivingBase base, DrivingApply apply, DrivingExamination examination,
			Map<String, DrivingPhoto> imgs) throws Exception {
		driverHibernateTemplate.saveOrUpdate(base);
		driverHibernateTemplate.saveOrUpdate(apply);
		driverHibernateTemplate.saveOrUpdate(examination);
		for(DrivingPhoto photo:imgs.values()){
			driimgHibernateTemplate.saveOrUpdate(photo);
		}
		apply.setSqrqzzp(getPhotoId(imgs,"05"));//申请人签字,照片表外键
		apply.setDlrqzzp(getPhotoId(imgs,"06"));// 代理人签字,照片表外键
		apply.setSfzzpId(getPhotoId(imgs,"03"));//身份证照片ID
		apply.setZw1zpId(getPhotoId(imgs,"07"));//指纹1照片ID
		apply.setZw2zpId(getPhotoId(imgs,"08"));//指纹2照片ID
		apply.setJbrqzzpId(getPhotoId(imgs,"09"));//经办人照片ID
		DrivingPhoto photo = new DrivingPhoto(null, base.getSfzmhm(), null,
				drivingApplyManager.getApplyImgToByte(base, apply, imgs.values()), "01", new Date(System.currentTimeMillis()),null, null, null, null, null, base.getQh(),
				base.getJxdm());
		driimgHibernateTemplate.saveOrUpdate(photo);
		apply.setJdcjszsqbzp(photo.getId());// 机动车驾驶证申请表,照片表外键
		examination.setSqrqmId(getPhotoId(imgs,"10"));//体检表申请人签名
		examination.setYsqmId(getPhotoId(imgs,"11"));//体检表医生签名
		examination.setDlrqmId(getPhotoId(imgs,"12")); //体检表代理人签名
		examination.setYyyzId(getPhotoId(imgs,"13"));//医院印章
		examination.setXyId(getPhotoId(imgs,"14"));//学员照片
		DrivingPhoto photo2 = new DrivingPhoto(null, base.getSfzmhm(), null,
				drivingExaminationManager.getExaminationImgToByte(examination, imgs.values()), "01", new Date(System.currentTimeMillis()),null, null, null, null, null, base.getQh(),
				base.getJxdm());
		driimgHibernateTemplate.saveOrUpdate(photo2);
		examination.setSqbtpId(photo2.getId());//体检表合成照片
		driverHibernateTemplate.saveOrUpdate(apply);
		driverHibernateTemplate.saveOrUpdate(examination);

	}
	private String getPhotoId(Map<String, DrivingPhoto> imgs,String key){
		if(imgs.containsKey(key)){
			return imgs.get(key).getId();
		}
		return null;
	}
}
