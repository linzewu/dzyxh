package com.xs.dzyxh.manager.sys.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.dzyxh.manager.window.IBaseManager;

@Service("sysBaseManager")
public class SysBaseManagerImpl implements IBaseManager<Object> {
	@Resource(name = "sysHibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	public boolean saveOrUpdate(Object t) {
		hibernateTemplate.saveOrUpdate(t);
		return true;
	}

}
