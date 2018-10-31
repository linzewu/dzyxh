package com.xs.dzyxh.manager.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.dzyxh.entity.system.BaseParams;
import com.xs.dzyxh.manager.IBaseParamsManager;
import com.xs.web.util.PageInfo;

@Service("baseParamsManager")
public class BaseParamsManagerImpl implements IBaseParamsManager {

	@Resource(name = "sysHibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	public List<BaseParams> getBaseParams() {
		DetachedCriteria dc = DetachedCriteria.forClass(BaseParams.class);
		dc.addOrder(Order.asc("type"));
		dc.addOrder(Order.asc("seq"));
		List<BaseParams> bps = (List<BaseParams>) this.hibernateTemplate
				.findByCriteria(dc);
		return bps;
	}

	@Override
	public BaseParams save(BaseParams baseParams) {
		baseParams.setCreateTime(new Date());
		baseParams = this.hibernateTemplate.merge(baseParams);
		return baseParams;
	}

	@Override
	public void delete(Integer id) {
		BaseParams baseParams = this.hibernateTemplate.get(BaseParams.class, id);
		if(baseParams!=null){
			this.hibernateTemplate.delete(baseParams);
		}
	}


	@Override
	public Map<String,Object> getBaseParams(PageInfo page,BaseParams param) {
		
		DetachedCriteria dc = DetachedCriteria.forClass(BaseParams.class);
		dc.addOrder(Order.asc("type"));
		dc.addOrder(Order.asc("seq"));
		
		if(param.getParamName()!=null&&!"".equals(param.getParamName().trim())){
			dc.add(Restrictions.eq("paramName", param.getParamName()));
		}
		
		if(param.getType()!=null&&!"".equals(param.getType())){
			dc.add(Restrictions.eq("type", param.getType()));
		}
		
		List<BaseParams> rows = (List<BaseParams>) this.hibernateTemplate.findByCriteria(dc, page.getPage(), page.getRows());
		
		
		
		dc.setProjection(Projections.rowCount());
		Long count =  (Long) this.hibernateTemplate.findByCriteria(dc,0,1).get(0);
		
		Map<String,Object> data=new HashMap<String,Object>();
		data.put("rows", rows);
		data.put("total", count);
		
		return data;
	}

}
