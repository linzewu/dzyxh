package com.xs.dzyxh.manager.window.impl;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.dzyxh.manager.window.IBaseManager;
@Service("driverBaseManager")
public class DriverBaseManagerImpl implements IBaseManager<Object>{

	@Resource(name = "driverHibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	public boolean saveOrUpdate(Object t) throws DataAccessException{
		hibernateTemplate.saveOrUpdate(t);
		return true;
	}

}
