package com.xs.dzyxh.manager.driver.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.dzyxh.manager.base.IBaseManager;
@Service("driverBaseManager")
public class DriverBaseManagerImpl implements IBaseManager<Object>{

	@Resource(name = "driverHibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	public boolean saveOrUpdate(Object t) {
		hibernateTemplate.saveOrUpdate(t);
		return true;
	}

}
