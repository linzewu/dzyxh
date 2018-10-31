package com.xs.dzyxh.manager.impl;

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
import com.xs.dzyxh.entity.system.WinEquipment;
import com.xs.dzyxh.manager.IWinEquipmentManager;

@Service("winEquipmentManager")
public class WinEquipmentManagerImpl implements IWinEquipmentManager {
	@Resource(name = "sysHibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	public List<WinEquipment> getWinEquipments(final WinEquipment win, final Integer page, final Integer rows) {

		return hibernateTemplate.execute(new HibernateCallback<List<WinEquipment>>() {

			@Override
			public List<WinEquipment> doInHibernate(Session session) throws HibernateException {
				StringBuffer sql = new StringBuffer("From  WinEquipment where 1=1");

				List params = new ArrayList();

				List<String> dms = null;

				if (win != null) {

					if (Common.isNotEmpty(win.getName())) {
						sql.append(" and  name like ?");
						params.add("%" + win.getName() + "%");
					}

					if (Common.isNotEmpty(win.getIp())) {
						sql.append(" and  ip = ?");
						params.add(win.getIp());
					}

					if (win.getStatus() != null) {
						sql.append(" and  status = ?");
						params.add(win.getStatus());
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
				List<WinEquipment> wins = (List<WinEquipment>) query.list();
				return wins;
			}
		});
	}

	@Override
	public WinEquipment getWinEquipment(String ip) {
		List<WinEquipment> wins = (List<WinEquipment>) this.hibernateTemplate.find("From  WinEquipment where ip=?",
				ip);
		if (wins == null || wins.isEmpty()) {
			return null;
		}
		return wins.get(0);
	}
	@Override
	public WinEquipment saveWinEquipment(WinEquipment win) {
		this.hibernateTemplate.merge(win);
		return win;
	}

	@Override
	public void deleteWinEquipment(WinEquipment win) {
		this.hibernateTemplate.delete(win);
	}

	@Override
	public Integer getWinEquipmentCount(final WinEquipment win) {
		return hibernateTemplate.execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException {
				StringBuffer sql = new StringBuffer("select count(*) From WinEquipment where 1=1");

				List<String> dms = null;

				List params = new ArrayList();

				if (win != null) {

					if (Common.isNotEmpty(win.getName())) {
						sql.append(" and  name like ?");
						params.add("%" + win.getName() + "%");
					}

					if (Common.isNotEmpty(win.getIp())) {
						sql.append(" and  ip = ?");
						params.add(win.getIp());
					}

					if (win.getStatus() != null) {
						sql.append(" and  status = ?");
						params.add(win.getStatus());
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

}
