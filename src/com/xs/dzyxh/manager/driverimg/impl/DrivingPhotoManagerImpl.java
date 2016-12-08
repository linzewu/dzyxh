package com.xs.dzyxh.manager.driverimg.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.common.Common;
import com.xs.dzyxh.entity.driimg.DrivingPhoto;
import com.xs.dzyxh.manager.driver.IBaseManager;
import com.xs.dzyxh.manager.driverimg.IDrivingPhotoManager;

@Service("driimgManager")
public class DrivingPhotoManagerImpl implements IDrivingPhotoManager {
	@Resource(name = "driimgHibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	public DrivingPhoto getDrivingPhotoById(final DrivingPhoto dri) {
		return hibernateTemplate.execute(new HibernateCallback<DrivingPhoto>() {
			
			@Override
			public DrivingPhoto doInHibernate(Session session) throws HibernateException {
				StringBuffer sql = new StringBuffer("From DrivingPhoto where 1=1");

				List params = new ArrayList();
				if (dri != null) {
					// 查询姓名
					if (Common.isNotEmpty(dri.getId())) {
						sql.append(" and  id = ?");
						params.add(dri.getId());
					} else {
						return null;
					}
				} else {
					return null;
				}
				Query query = session.createQuery(sql.toString());
				int i = 0;
				for (Object param : params) {
					query.setParameter(i, param);
					i++;
				}
				List<DrivingPhoto> dirs = (List<DrivingPhoto>) query.list();
				if (dirs.size() > 0) {
					return dirs.get(0);
				}
				return null;
			}
		});
	}
	@Override
	public List<DrivingPhoto> getDrivingPhotos(final DrivingPhoto dir,final Integer page,final Integer rows) {
		return hibernateTemplate.execute(new HibernateCallback<List<DrivingPhoto>>() {

			@Override
			public List<DrivingPhoto> doInHibernate(Session session) throws HibernateException {
				StringBuffer sql = new StringBuffer(" From DrivingPhoto where 1=1");

				List params = new ArrayList();
				if (dir != null) {
					// 查询身份证
					if (Common.isNotEmpty(dir.getSfzmhm())) {
						sql.append(" and  sfzmhm = ?");
						params.add(dir.getSfzmhm());
					}
					// 查询期号
					if (Common.isNotEmpty(dir.getQh())) {
						sql.append(" and  qh = ?");
						params.add(dir.getQh());
					}
					// 查询驾校
					if (Common.isNotEmpty(dir.getJxdm())) {
						sql.append(" and  jxdm = ?");
						params.add(dir.getJxdm());
					}
					// 查询流水号
					if (Common.isNotEmpty(dir.getLsh())) {
						sql.append(" and  lsh = ?");
						params.add(dir.getLsh());
					}
				}
				sql.append(" order by cjsj asc"); 
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

				List<DrivingPhoto> dirimgs = (List<DrivingPhoto>) query.list();
				return dirimgs;
			}
		});
	}

	@Override
	public List<String> getDrivingPhotoIds(final DrivingPhoto dir,final Integer page,final Integer rows) {
		return hibernateTemplate.execute(new HibernateCallback<List<String>>() {

			@Override
			public List<String> doInHibernate(Session session) throws HibernateException {
				StringBuffer sql = new StringBuffer("select id From DrivingPhoto where 1=1");

				List params = new ArrayList();
				if (dir != null) {
					// 查询身份证
					if (Common.isNotEmpty(dir.getSfzmhm())) {
						sql.append(" and  sfzmhm = ?");
						params.add(dir.getSfzmhm());
					}
					// 查询期号
					if (Common.isNotEmpty(dir.getQh())) {
						sql.append(" and  qh = ?");
						params.add(dir.getQh());
					}
					// 查询驾校
					if (Common.isNotEmpty(dir.getJxdm())) {
						sql.append(" and  jxdm = ?");
						params.add(dir.getJxdm());
					}
					// 查询流水号
					if (Common.isNotEmpty(dir.getLsh())) {
						sql.append(" and  lsh = ?");
						params.add(dir.getLsh());
					}
				}
				sql.append(" order by cjsj asc"); 
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

				List<String> dirimgs = (List<String>) query.list();
				return dirimgs;
			}
		});
	}
	@Override
	public boolean saveOrUpdate(DrivingPhoto dir) {
		hibernateTemplate.saveOrUpdate(dir);
		return false;
	}

}
