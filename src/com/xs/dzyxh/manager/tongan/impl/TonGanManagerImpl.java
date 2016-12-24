package com.xs.dzyxh.manager.tongan.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.dzyxh.manager.tongan.ITonGanManager;
@Service("tonGanManager")
public class TonGanManagerImpl implements ITonGanManager {
	@Resource(name = "tonganHibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	public void addData(Object obj){
		hibernateTemplate.merge(obj);
	}
}
