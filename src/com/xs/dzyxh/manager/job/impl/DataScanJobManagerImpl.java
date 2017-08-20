package com.xs.dzyxh.manager.job.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.dzyxh.entity.driimg.DrivingPhoto;
import com.xs.dzyxh.entity.driver.DrivingApply;
import com.xs.dzyxh.entity.driver.DrivingBase;
import com.xs.dzyxh.entity.driver.DrivingExamination;
import com.xs.dzyxh.entity.tongan.SqPhotos;
import com.xs.dzyxh.manager.job.IDataScanJobManager;
import com.xs.dzyxh.manager.tongan.ITonGanManager;
import com.xs.dzyxh.manager.window.IDrivingApplyManager;
import com.xs.dzyxh.manager.window.IDrivingExaminationManager;
@Service("dataScanJobManagerImpl")
public class DataScanJobManagerImpl implements IDataScanJobManager {
	@Resource(name = "driverApplyManager")
	private IDrivingApplyManager drivingApplyManager;
	@Resource(name = "drivingExamination")
	private IDrivingExaminationManager drivingExaminationManager;
	@Resource(name = "tonGanManager")
	private ITonGanManager tonGanManager;
	
	@Resource(name = "driverHibernateTemplate")
	private HibernateTemplate driverHibernateTemplate;
	
	@Resource(name = "driimgHibernateTemplate")
	private HibernateTemplate driimgHibernateTemplate;
	
	@Resource(name = "tonganHibernateTemplate")
	private HibernateTemplate tonganHibernateTemplate;
	
	public void saveImg(SqPhotos sqPhotos, Map<String, DrivingPhoto> photos)throws Exception {
		if(sqPhotos!=null){
			DrivingBase base=driverHibernateTemplate.get(DrivingBase.class, sqPhotos.getId().getSfzmhm());
			if(base==null){
				base =new DrivingBase();
				base.setSfzmhm(sqPhotos.getId().getSfzmhm());
				base.setQh(sqPhotos.getId().getQh());
				base.setJxdm(sqPhotos.getId().getJxdm());
				driverHibernateTemplate.save(base);
			}
			tonganHibernateTemplate.saveOrUpdate(sqPhotos);
		}
		for(DrivingPhoto photo:photos.values()){
			driimgHibernateTemplate.saveOrUpdate(photo);
		}
	}
	
	@Override
	public void saveAll(DrivingBase base, DrivingApply apply, DrivingExamination examination,
			Map<String, DrivingPhoto> imgs,List<Object>  datas) throws Exception {
		for(Object obj:datas){
			tonganHibernateTemplate.saveOrUpdate(obj);
		}	
		driverHibernateTemplate.saveOrUpdate(base);
		driverHibernateTemplate.saveOrUpdate(apply);
		driverHibernateTemplate.saveOrUpdate(examination);
		for(DrivingPhoto photo:imgs.values()){
			driimgHibernateTemplate.saveOrUpdate(photo);
		}
		apply.setSqrqzzp(getPhotoId(imgs,"05"));//申请人签字,照片表外键
		apply.setDlrqzzp(getPhotoId(imgs,"06"));// 代理人签字,照片表外键
		apply.setSfzzpId(getPhotoId(imgs,"04"));//身份证照片ID
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
				drivingExaminationManager.getExaminationImgToByte(examination, imgs.values()), "02", new Date(System.currentTimeMillis()),null, null, null, null, null, base.getQh(),
				base.getJxdm());
		driimgHibernateTemplate.saveOrUpdate(photo2);
		examination.setSqbtpId(photo2.getId());//体检表合成照片
		SqPhotos sqPhoto=tonGanManager.getSqPhotos(photo2.getSfzmhm(), photo2.getJxdm(), photo2.getQh());
		if(sqPhoto!=null){
			sqPhoto.setPhotoTjb(photo2.getZp());
			sqPhoto.setPhotoSqb(photo.getZp());
			tonganHibernateTemplate.update(sqPhoto);
		}
		driverHibernateTemplate.saveOrUpdate(apply);
		driverHibernateTemplate.saveOrUpdate(examination);

	}
	private String getPhotoId(Map<String, DrivingPhoto> imgs,String key){
		if(imgs.containsKey(key)){
			return imgs.get(key).getId();
		}
		return null;
	}

	@Override
	public void saveTongan(List<Object> datas) {
		for(Object obj:datas){
			tonganHibernateTemplate.saveOrUpdate(obj);
		}	
	}
}
