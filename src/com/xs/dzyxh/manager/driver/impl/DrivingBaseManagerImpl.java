package com.xs.dzyxh.manager.driver.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.common.Common;
import com.xs.dzyxh.entity.driver.DrivingBase;
import com.xs.dzyxh.manager.driver.IDrivingBaseManager;

@Service("driverManager")
public class DrivingBaseManagerImpl implements IDrivingBaseManager {
	@Resource(name = "driverHibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	public List<DrivingBase> getDrivingBases(final DrivingBase base, final Integer page, final Integer rows) {
		return hibernateTemplate.execute(new HibernateCallback<List<DrivingBase>>() {
			@Override
			public List<DrivingBase> doInHibernate(Session session) throws HibernateException {
				StringBuffer sql = new StringBuffer("From DrivingBase where 1=1");

				List params = new ArrayList();
				if (base != null) {
					// 查询姓名
					if (Common.isNotEmpty(base.getXm())) {
						sql.append(" and  xm like ?");
						params.add("%" + base.getXm() + "%");
					}
					// 查询身份证
					if (Common.isNotEmpty(base.getSfzmhm())) {
						sql.append(" and  sfzmhm like ?");
						params.add("%" + base.getSfzmhm() + "%");
					}
					// 查询性别
					if (Common.isNotEmpty(base.getXb())) {
						sql.append(" and  xb = ?");
						params.add(base.getXb());
					}
					// 查询期号
					if (Common.isNotEmpty(base.getQh())) {
						sql.append(" and  qh like ?");
						params.add("%" + base.getQh() + "%");
					}
					// 查询驾校
					if (Common.isNotEmpty(base.getJxdm())) {
						sql.append(" and  jxdm = ?");
						params.add(base.getJxdm());
					}
					// 查询创建开始时间
					if (base.getCjsjks() != null) {
						sql.append(" and  cjsj >= ?");
						params.add(base.getCjsjks());
					}
					// 查询创建结束时间
					if (base.getCjsjjs() != null) {
						sql.append(" and  cjsj <" + " ?");
						// 结束时间增加一天
						Calendar cal = Calendar.getInstance();
						cal.setTime(base.getCjsjjs());
						cal.add(Calendar.DATE, 1);
						params.add(cal.getTime());
					}
				}
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

				List<DrivingBase> bases = (List<DrivingBase>) query.list();
				return bases;
			}
		});
	}

	@Override
	public Integer getUserCount(final DrivingBase base) {
		return hibernateTemplate.execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException {
				StringBuffer sql = new StringBuffer("select count(*) From DrivingBase where 1=1");

				List params = new ArrayList();
				if (base != null) {
					// 查询姓名
					if (Common.isNotEmpty(base.getXm())) {
						sql.append(" and  xm like ?");
						params.add("%" + base.getXm() + "%");
					}
					// 查询身份证
					if (Common.isNotEmpty(base.getSfzmhm())) {
						sql.append(" and  sfzmhm like ?");
						params.add("%" + base.getSfzmhm() + "%");
					}
					// 查询性别
					if (Common.isNotEmpty(base.getXb())) {
						sql.append(" and  xb = ?");
						params.add(base.getXb());
					}
					// 查询期号
					if (Common.isNotEmpty(base.getQh())) {
						sql.append(" and  qh like ?");
						params.add("%" + base.getQh() + "%");
					}
					// 查询驾校
					if (Common.isNotEmpty(base.getJxdm())) {
						sql.append(" and  jxdm = ?");
						params.add(base.getJxdm());
					}
					// 查询创建开始时间
					if (base.getCjsjks() != null) {
						sql.append(" and  cjsj >= ?");
						params.add(base.getCjsjks());
					}
					// 查询创建结束时间
					if (base.getCjsjjs() != null) {
						sql.append(" and  cjsj <" + " ?");
						// 结束时间增加一天
						Calendar cal = Calendar.getInstance();
						cal.setTime(base.getCjsjjs());
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
	public DrivingBase getDrivingBaseById(final DrivingBase base) {
		return hibernateTemplate.execute(new HibernateCallback<DrivingBase>() {

			@Override
			public DrivingBase doInHibernate(Session session) throws HibernateException {
				StringBuffer sql = new StringBuffer("From DrivingBase where 1=1");

				List params = new ArrayList();
				if (base != null) {
					// 查询身份证
					if (Common.isNotEmpty(base.getSfzmhm())) {
						sql.append(" and  sfzmhm = ?");
						params.add(base.getSfzmhm());
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
				List<DrivingBase> bases = (List<DrivingBase>) query.list();
				if (bases.size() > 0) {
					return bases.get(0);
				}
				return null;
			}
		});
	}

}
