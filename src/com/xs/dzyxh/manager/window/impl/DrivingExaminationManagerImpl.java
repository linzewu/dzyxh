package com.xs.dzyxh.manager.window.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.xs.dzyxh.entity.driver.DrivingExamination;
import com.xs.dzyxh.manager.driverimg.IDrivingPhotoManager;
import com.xs.dzyxh.manager.img.IAsposeManager;
import com.xs.dzyxh.manager.img.IBarCodeManager;
import com.xs.dzyxh.manager.tongan.ITonGanManager;
import com.xs.dzyxh.manager.window.IBaseManager;
import com.xs.dzyxh.manager.window.IDrivingExaminationManager;

@Service("drivingExamination")
public class DrivingExaminationManagerImpl implements IDrivingExaminationManager, IBaseManager<DrivingApply> {
	@Resource(name = "driverHibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Resource(name = "driimgManager")
	private IDrivingPhotoManager drivingPhotoManager;

	@Resource(name = "barCodeManager")
	private IBarCodeManager barCodeManager;

	@Resource(name = "asposeManager")
	private IAsposeManager asposeManager;
	
	@Resource(name = "tonGanManager")
	private ITonGanManager tonGanManager;

	public byte[] getExaminationImgToByte( DrivingExamination examinat) throws Exception {
		if (examinat != null) {
			DrivingPhoto driving = new DrivingPhoto();
			driving.setSfzmhm(examinat.getSfzmhm());
			//需要增加条件
			List<DrivingPhoto> photos = drivingPhotoManager.getDrivingPhotos(driving, null, null);

			return getExaminationImgToByte(  examinat, photos);
		}
		return null;
	}
	public byte[] getExaminationImgToByte( DrivingExamination examinat,Collection<DrivingPhoto> photos) throws Exception {

		if (examinat != null) {
			// 得到填充的数据
			Map<String, String> datas = DrivingApplyUitl.convertTjbData( examinat);
			//DrivingPhoto driving = new DrivingPhoto();
			//List<DrivingPhoto> photos = drivingPhotoManager.getDrivingPhotos(driving, null, null);
			List<ImageData> imgs = DrivingApplyUitl.convertTjbImgData(photos);
			if(examinat.getTjyymc()!=null){
				String [] tjyymc= examinat.getTjyymc().split("-");
				if(tjyymc.length>1){
					byte [] dwyztp=tonGanManager.getTjUser(tjyymc[1],tjyymc[0]).getDwyztp();
					if(dwyztp!=null){
						imgs.add(new ImageData("yyyz",new ByteArrayInputStream(dwyztp), 120, 120,1));
					}			
			}
			if (examinat.getLsh() != null) {
				try {
					imgs.add(new ImageData("ewm", barCodeManager.createBarCode(examinat.getLsh()), 135, 40,
							0,HorizontalAlignment.RIGHT));
				} catch (Exception e) {
					// 条形码生成错误，跳过
					// e.printStackTrace();
					System.out.println(e.getMessage());
				}
			}
			ByteArrayOutputStream out = (ByteArrayOutputStream) asposeManager.convertPng(
					DrivingApplyUitl.class.getClassLoader().getResourceAsStream("\\2016最新机动车驾驶人身体条件证明.docx"),
					new ByteArrayOutputStream(), datas, imgs);
			return out.toByteArray();
		}
		}
		return null;
	}
	public DrivingExamination getDrivingExamination(final DrivingExamination dir) {
		List<DrivingExamination> results = getDrivingExaminations(dir, null, null);
		if (results.size() > 0) {
			return results.get(0);
		}
		return null;
	}

	public List<DrivingExamination> getDrivingExaminations(final DrivingExamination dir, final Integer page,
			final Integer rows) {
		return hibernateTemplate.execute(new HibernateCallback<List<DrivingExamination>>() {

			@Override
			public List<DrivingExamination> doInHibernate(Session session) throws HibernateException {
				StringBuffer sql = new StringBuffer(" From DrivingExamination where 1=1");

				List params = new ArrayList();
				if (dir != null) {

					// 查询身份证
					if (Common.isNotEmpty(dir.getSfzmhm())) {
						sql.append(" and  sfzmhm = ?");
						params.add(dir.getSfzmhm());
					}
					// 查询ID
					if (Common.isNotEmpty(dir.getId())) {
						sql.append(" and  id = ?");
						params.add(dir.getId());
					}
					// 查询开始时间
					if (dir.getKssj() != null) {
						sql.append(" and  cjsj >= ?");
						params.add(dir.getKssj());
					}
					// 查询创建结束时间
					if (dir.getJssj() != null) {
						sql.append(" and  cjsj <" + " ?");
						// 结束时间增加一天
						Calendar cal = Calendar.getInstance();
						cal.setTime(dir.getJssj());
						cal.add(Calendar.DATE, 1);
						params.add(cal.getTime());
					}

				}
				sql.append(" order by cjsj desc");
				Query query = session.createQuery(sql.toString());
				if (page != null && rows != null) {
					query.setFirstResult((page - 1) * rows);
					query.setMaxResults(rows);
				}
				int i = 0;
				for (Object param : params) {
					query.setParameter(i, param);
					i++;
				}

				List<DrivingExamination> results = (List<DrivingExamination>) query.list();
				return results;

			}
		});
	}

	@Override
	public Integer getUserCount(final DrivingExamination dir) {
		return hibernateTemplate.execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException {
				StringBuffer sql = new StringBuffer("select count(1) From DrivingExamination where 1=1");

				List params = new ArrayList();
				if (dir != null) {

					// 查询身份证
					if (Common.isNotEmpty(dir.getSfzmhm())) {
						sql.append(" and  sfzmhm = ?");
						params.add(dir.getSfzmhm());
					}
					// 查询期号
					if (Common.isNotEmpty(dir.getId())) {
						sql.append(" and  id = ?");
						params.add(dir.getId());
					}
					// 查询开始时间
					if (dir.getKssj() != null) {
						sql.append(" and  cjsj >= ?");
						params.add(dir.getKssj());
					}
					// 查询创建结束时间
					if (dir.getJssj() != null) {
						sql.append(" and  cjsj <" + " ?");
						// 结束时间增加一天
						Calendar cal = Calendar.getInstance();
						cal.setTime(dir.getJssj());
						cal.add(Calendar.DATE, 1);
						params.add(cal.getTime());
					}

				}
				Query query = session.createQuery(sql.toString());
				int i = 0;
				for (Object param : params) {
					query.setParameter(i, param);
					i++;
				}
				Long count = (Long) query.uniqueResult();
				return count.intValue();
			}
		});
	}

	@Override
	public boolean saveOrUpdate(DrivingApply t) {
		this.hibernateTemplate.saveOrUpdate(t);
		return true;
	}
}
