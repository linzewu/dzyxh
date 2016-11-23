package com.xs.dzyxh.manager.driver.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
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
import com.xs.dzyxh.entity.driver.DrivingBase;
import com.xs.dzyxh.entity.driver.DrivingExamination;
import com.xs.dzyxh.manager.driimg.IDrivingPhotoManager;
import com.xs.dzyxh.manager.driver.IDrivingExaminationManager;
import com.xs.dzyxh.manager.img.IAsposeManager;
import com.xs.dzyxh.manager.img.IBarCodeManager;

@Service("drivingExamination")
public class DrivingExaminationManagerImpl implements IDrivingExaminationManager {
	@Resource(name = "driverHibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Resource(name = "driimgManager")
	private IDrivingPhotoManager drivingPhotoManager;
	
	@Resource(name = "barCodeManager")
	private IBarCodeManager barCodeManager;

	@Resource(name = "asposeManager")
	private IAsposeManager asposeManager;
	
	public byte[] getExaminationImgToByte(DrivingBase base,DrivingExamination examinat) throws Exception{
		
		if(base!=null){
			//得到填充的数据
			Map<String,String> datas=DrivingApplyUitl.convertTjbData(base, examinat);			
			DrivingPhoto driving=new DrivingPhoto();
			//有流水号则使用流水号查询
			if(examinat.getLsh()!=null){
				driving.setLsh(examinat.getLsh());
			}else if(examinat.getId()!=null){
				//使用身份证、期号、驾校代码查询图片
				driving.setSfzmhm(examinat.getId().getSfzmhm());
				driving.setQh(examinat.getId().getQh());
				driving.setJxdm(examinat.getId().getJxdm());
			}
			List<DrivingPhoto> photos=drivingPhotoManager.getDrivingPhotos(driving, null,null);
			List<ImageData> imgs=DrivingApplyUitl.convertTjbImgData(photos);
			if(examinat.getLsh()!=null){
				try {
					imgs.add(new ImageData("ewm", barCodeManager.createBarCode(examinat.getLsh()), 135, 40,HorizontalAlignment.RIGHT));
				} catch (Exception e) {
					// 条形码生成错误，跳过
					//e.printStackTrace();
					System.out.println(e.getMessage());
				}
			}
			ByteArrayOutputStream out=(ByteArrayOutputStream)asposeManager.convertPng(DrivingApplyUitl.class.getClassLoader().getResourceAsStream("\\2016最新机动车驾驶人身体条件证明.docx"), new ByteArrayOutputStream() , datas, imgs);
			return out.toByteArray();
		}
		return null;
	}
	
	public DrivingExamination getDrivingExamination(final DrivingExamination dir) {
		return hibernateTemplate.execute(new HibernateCallback<DrivingExamination>() {

			@Override
			public DrivingExamination doInHibernate(Session session) throws HibernateException {
				StringBuffer sql = new StringBuffer(" From DrivingExamination where 1=1");

				List params = new ArrayList();
				if (dir != null) {
					if (dir.getId() != null) {
						// 查询身份证
						if (Common.isNotEmpty(dir.getId().getSfzmhm())) {
							sql.append(" and  sfzmhm = ?");
							params.add(dir.getId().getSfzmhm());
						}
						// 查询期号
						if (Common.isNotEmpty(dir.getId().getQh())) {
							sql.append(" and  qh = ?");
							params.add(dir.getId().getQh());
						}
						// 查询驾校
						if (Common.isNotEmpty(dir.getId().getJxdm())) {
							sql.append(" and  jxdm = ?");
							params.add(dir.getId().getJxdm());
						}
					}
					// 查询流水号
					if (Common.isNotEmpty(dir.getLsh())) {
						sql.append(" and  lsh = ?");
						params.add(dir.getLsh());
					}
				}
			//	sql.append(" order by cjsj asc");
				Query query = session.createQuery(sql.toString());		
				int i = 0;
				for (Object param : params) {
					query.setParameter(i, param);
					i++;
				}

				List<DrivingExamination> results = (List<DrivingExamination>) query.list();
				if(results.size()>0){
					return results.get(0);
				}
				return null;
			}
		});
	}
}
