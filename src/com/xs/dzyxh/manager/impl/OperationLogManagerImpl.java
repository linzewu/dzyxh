package com.xs.dzyxh.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.dzyxh.entity.system.OperationLog;
import com.xs.dzyxh.manager.IOperationLogManager;
@Service("operationLogManager")
public class OperationLogManagerImpl implements IOperationLogManager {
	
	@Resource(name = "sysHibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	public List<OperationLog> getOperationLog(Integer page, Integer rows, OperationLog operationLog) {
		DetachedCriteria query = DetachedCriteria.forClass(OperationLog.class);

		Integer firstResult = (page - 1) * rows;
		setCondition(operationLog, query);
		query.addOrder(Order.desc("id"));
		List<OperationLog> vcps = (List<OperationLog>) this.hibernateTemplate.findByCriteria(query, firstResult,
				rows);

		return vcps;
	}

	@Override
	public Integer getOperationLogCount(Integer page, Integer rows, OperationLog operationLog) {
		DetachedCriteria query = DetachedCriteria.forClass(OperationLog.class);

		query.setProjection(Projections.rowCount());
		setCondition(operationLog, query);
		List<Long> count = (List<Long>) hibernateTemplate.findByCriteria(query);

		return count.get(0).intValue();
	}

	private void setCondition(OperationLog operationLog, DetachedCriteria query) {
		if(operationLog.getOperationUser()!=null&&!"".equals(operationLog.getOperationUser().trim())){
			query.add(Restrictions.eq("operationUser", operationLog.getOperationUser()));
		}
		if(operationLog.getModule()!=null&&!"".equals(operationLog.getModule().trim())){
			query.add(Restrictions.like("module", "%"+operationLog.getModule()+"%"));
		}
		if(operationLog.getOperationDate() != null) {
			query.add(Restrictions.ge("operationDate", operationLog.getOperationDate()));
		}
		if(operationLog.getOperationDateEnd() != null) {
			query.add(Restrictions.le("operationDate", operationLog.getOperationDateEnd()));
		}
		if(operationLog.getCoreFunction() != null) {
			if("Y".equals(operationLog.getCoreFunction())) {
				query.add(Restrictions.eq("coreFunction", operationLog.getCoreFunction()));
			}else {
				query.add(Restrictions.isNull("coreFunction"));
			}
		}
	}

	@Override
	public void saveOperationLog(OperationLog operationLog) {
		this.hibernateTemplate.save(operationLog);
	}

	@Override
	public List<OperationLog> getLoginOperationLog(Integer page, Integer rows, OperationLog operationLog) {
		DetachedCriteria query = DetachedCriteria.forClass(OperationLog.class);

		Integer firstResult = (page - 1) * rows;
		setCondition(operationLog, query);
		query.add(Restrictions.eq("operationType", "登录"));
		query.addOrder(Order.desc("id"));
		List<OperationLog> vcps = (List<OperationLog>) this.hibernateTemplate.findByCriteria(query, firstResult,
				rows);

		return vcps;
	}

	@Override
	public Integer getLoginOperationLogCount(Integer page, Integer rows, OperationLog operationLog) {
		DetachedCriteria query = DetachedCriteria.forClass(OperationLog.class);

		query.setProjection(Projections.rowCount());
		setCondition(operationLog, query);
		query.add(Restrictions.eq("operationType", "登录"));
		List<Long> count = (List<Long>) hibernateTemplate.findByCriteria(query);

		return count.get(0).intValue();
	}

}
