package com.xs.dzyxh.manager.driverimg.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.dzyxh.manager.driver.IBaseManager;
@Service("driverImgBaseManager")
public class DriverImgBaseManagerImpl implements IBaseManager<Object>{
	@Resource(name = "driimgHibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	public boolean saveOrUpdate(Object t) {
		hibernateTemplate.saveOrUpdate(t);
		return true;
	}
}
