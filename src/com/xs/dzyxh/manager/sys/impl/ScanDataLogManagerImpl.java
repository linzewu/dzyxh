package com.xs.dzyxh.manager.sys.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.dzyxh.entity.system.ScanDataLog;
import com.xs.dzyxh.manager.sys.IScanDataLogManager;
@Service("scanDataLogManager")
public class ScanDataLogManagerImpl implements IScanDataLogManager {
	@Resource(name = "sysHibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	public void save(ScanDataLog data){
		hibernateTemplate.save(data);
	}
}	
