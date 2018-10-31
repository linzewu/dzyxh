package com.xs.dzyxh.manager.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.aspose.words.HorizontalAlignment;
import com.xs.common.Common;
import com.xs.dzyxh.entity.aspose.ImageData;
import com.xs.dzyxh.entity.driimg.DrivingPhoto;
import com.xs.dzyxh.entity.driver.DrivingApply;
import com.xs.dzyxh.entity.driver.DrivingBase;
import com.xs.dzyxh.manager.IAsposeManager;
import com.xs.dzyxh.manager.IBarCodeManager;
import com.xs.dzyxh.manager.IBaseManager;
import com.xs.dzyxh.manager.IDrivingApplyManager;
import com.xs.dzyxh.manager.IDrivingPhotoManager;
@Service("driverApplyManager")
public class DrivingApplyManagerImpl implements IDrivingApplyManager,IBaseManager<DrivingApply> {
	@Resource(name = "driverHibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	@Resource(name = "driimgManager")
	private IDrivingPhotoManager drivingPhotoManager;
	
	@Resource(name = "barCodeManager")
	private IBarCodeManager barCodeManager;

	@Resource(name = "asposeManager")
	private IAsposeManager asposeManager;
	
	public byte[] getApplyImgToByte(DrivingBase base,DrivingApply apply) throws Exception{
	
		if(base!=null){
			//得到填充的数据
			//Map<String,String> datas=DrivingApplyUitl.convertData(base, apply);			
			DrivingPhoto driving=new DrivingPhoto();
			//有流水号则使用流水号查询
			if(apply.getLsh()!=null){
				driving.setLsh(apply.getLsh());
			}else if(apply.getId()!=null){
				//使用身份证、期号、驾校代码查询图片
				driving.setSfzmhm(apply.getId().getSfzmhm());
				driving.setQh(apply.getId().getQh());
				driving.setJxdm(apply.getId().getJxdm());
			}
			List<DrivingPhoto> photos=drivingPhotoManager.getDrivingPhotos(driving, null,null);		
			return  getApplyImgToByte( base, apply,photos);
		}
		return null;
	}
	public byte[] getApplyImgToByte(DrivingBase base,DrivingApply apply,Collection<DrivingPhoto> photos) throws Exception{

		if(base!=null){
			//得到填充的数据
			Map<String,String> datas=DrivingApplyUitl.convertData(base, apply,photos);			
			List<ImageData> imgs=DrivingApplyUitl.convertSqbImgData(photos);
			if(apply.getLsh()!=null){
				try {
					imgs.add(new ImageData("ewm", barCodeManager.createBarCode(apply.getLsh()), 135, 40,0,HorizontalAlignment.RIGHT));
				} catch (Exception e) {
					// 条形码生成错误，跳过
					//e.printStackTrace();
					System.out.println(e.getMessage());
				}
			}
			ByteArrayOutputStream out=(ByteArrayOutputStream)asposeManager.convertPng(DrivingApplyUitl.class.getClassLoader().getResourceAsStream("\\机动车驾驶证申请表2016.doc"), new ByteArrayOutputStream() , datas, imgs);
			return out.toByteArray();
		}
		return null;
	}
	@Override
	public List<DrivingApply> getDrivingApplys(final DrivingApply base,final Integer page, final Integer rows) {
		return hibernateTemplate.execute(new HibernateCallback<List<DrivingApply>>() {
			@Override
			public List<DrivingApply> doInHibernate(Session session) throws HibernateException {
				StringBuffer sql = new StringBuffer("From DrivingApply where 1=1");

				List params = new ArrayList();
				if (base != null&&base.getId()!=null) {
					//查询身份证
					if (Common.isNotEmpty(base.getId().getSfzmhm())) {
						sql.append(" and  id.sfzmhm = ?");
						params.add(base.getId().getSfzmhm());
					}
					//查询期号
					if (Common.isNotEmpty(base.getId().getQh())) {
						sql.append(" and  id.qh = ?");
						params.add(base.getId().getQh());
					}
					//查询驾校
					if (Common.isNotEmpty(base.getId().getJxdm())) {
						sql.append(" and  id.jxdm = ?");
						params.add(base.getId().getJxdm());
					}
				}else{
					return null;
				}
				Query query = session.createQuery(sql.toString());
				int i = 0;
				for (Object param : params) {
					query.setParameter(i, param);
					i++;
				}
				List<DrivingApply> bases = (List<DrivingApply>) query.list();
				return bases;
			}
		});
	}

	@Override
	public DrivingApply getDrivingApplyById(final DrivingApply base) {
		return hibernateTemplate.execute(new HibernateCallback<DrivingApply>() {
			@Override
			public DrivingApply doInHibernate(Session session) throws HibernateException {
				StringBuffer sql = new StringBuffer("From DrivingApply where 1=1");

				List params = new ArrayList();
				if(base != null&&(Common.isNotEmpty(base.getLsh()))){
					//查询流水号
					sql.append(" and  lsh = ?");
					params.add(base.getLsh());
					
				}else if (base != null&&base.getId()!=null) {
					//查询身份证
					if (Common.isNotEmpty(base.getId().getSfzmhm())) {
						sql.append(" and  id.sfzmhm = ?");
						params.add(base.getId().getSfzmhm());
					}
					//查询期号
					if (Common.isNotEmpty(base.getId().getQh())) {
						sql.append(" and  id.qh = ?");
						params.add(base.getId().getQh());
					}
					//查询驾校
					if (Common.isNotEmpty(base.getId().getJxdm())) {
						sql.append(" and  id.jxdm = ?");
						params.add(base.getId().getJxdm());
					}
				}else{
					return null;
				}
				sql.append(" order by cjsj desc ");
				Query query = session.createQuery(sql.toString());
				
				int i = 0;
				for (Object param : params) {
					query.setParameter(i, param);
					i++;
				}
				List<DrivingApply> bases = (List<DrivingApply>) query.list();
				if(bases.size()>0){
					return bases.get(0);
				}
				return null;
			}
		});
	}

	@Override
	public boolean saveOrUpdate(DrivingApply t) {
		hibernateTemplate.saveOrUpdate(t);
		return true;
	}
	
}
