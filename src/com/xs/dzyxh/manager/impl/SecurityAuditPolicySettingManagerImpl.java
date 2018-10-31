package com.xs.dzyxh.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.dzyxh.entity.system.SecurityAuditPolicySetting;
import com.xs.dzyxh.manager.ISecurityAuditPolicySettingManager;

@Service("securityAuditPolicySettingManager")
public class SecurityAuditPolicySettingManagerImpl implements ISecurityAuditPolicySettingManager {

	@Resource(name = "sysHibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	@Override
	public List<SecurityAuditPolicySetting> getList(Integer page, Integer rows,
			SecurityAuditPolicySetting securityAuditPolicySetting) {
		DetachedCriteria query = DetachedCriteria.forClass(SecurityAuditPolicySetting.class);

		Integer firstResult = (page - 1) * rows;
		if(securityAuditPolicySetting.getAqsjcllxmc()!=null&&!"".equals(securityAuditPolicySetting.getAqsjcllxmc().trim())){
			query.add(Restrictions.eq("aqsjcllxmc", securityAuditPolicySetting.getAqsjcllxmc()));
		}
		
		List<SecurityAuditPolicySetting> vcps = (List<SecurityAuditPolicySetting>) this.hibernateTemplate.findByCriteria(query, firstResult,
				rows);

		return vcps;
	}

	@Override
	public Integer getListCount(Integer page, Integer rows, SecurityAuditPolicySetting securityAuditPolicySetting) {
		DetachedCriteria query = DetachedCriteria.forClass(SecurityAuditPolicySetting.class);

		query.setProjection(Projections.rowCount());
		if(securityAuditPolicySetting.getAqsjcllxmc()!=null&&!"".equals(securityAuditPolicySetting.getAqsjcllxmc().trim())){
			query.add(Restrictions.eq("aqsjcllxmc", securityAuditPolicySetting.getAqsjcllxmc()));
		}
		List<Long> count = (List<Long>) hibernateTemplate.findByCriteria(query);

		return count.get(0).intValue();
	}

	@Override
	public void updateSecurityAuditPolicySetting(List<SecurityAuditPolicySetting> list) {
		for(SecurityAuditPolicySetting vo:list) {
			this.hibernateTemplate.update(vo);
		}
	}

	@Override
	public SecurityAuditPolicySetting getPolicyByCode(String aqsjclbm) {
		StringBuffer sb=new StringBuffer("from SecurityAuditPolicySetting where aqsjclbm=? AND sfkq = '0'");
		
		List<SecurityAuditPolicySetting> sets=(List<SecurityAuditPolicySetting> )this.hibernateTemplate.find(sb.toString(), aqsjclbm);
		
		return sets==null||sets.size()==0?null:sets.get(0);
	}

}
