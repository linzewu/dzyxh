package com.xs.dzyxh.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.dzyxh.entity.system.SecurityLog;
import com.xs.dzyxh.manager.ISecurityLogManager;
@Service("securityLogManager")
public class SecurityLogManagerImpl implements ISecurityLogManager {
	
	@Resource(name = "sysHibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	public List<SecurityLog> getSecurityLog(Integer page, Integer rows, SecurityLog securityLog) {
		DetachedCriteria query = DetachedCriteria.forClass(SecurityLog.class);

		Integer firstResult = (page - 1) * rows;
		
		List<SecurityLog> vcps = (List<SecurityLog>) this.hibernateTemplate.findByCriteria(query, firstResult,
				rows);

		return vcps;
	}

	@Override
	public Integer getSecurityLogCount(Integer page, Integer rows, SecurityLog securityLog) {
		DetachedCriteria query = DetachedCriteria.forClass(SecurityLog.class);

		query.setProjection(Projections.rowCount());
		
		List<Long> count = (List<Long>) hibernateTemplate.findByCriteria(query);

		return count.get(0).intValue();
	}

	@Override
	public void saveSecurityLog(SecurityLog securityLog) {
		this.hibernateTemplate.save(securityLog);
	}

}
