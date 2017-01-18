package com.xs.dzyxh.manager.tongan.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.common.Common;
import com.xs.dzyxh.entity.tongan.SqPhotos;
import com.xs.dzyxh.entity.tongan.TjUser;
import com.xs.dzyxh.manager.tongan.ITonGanManager;
@Service("tonGanManager")
public class TonGanManagerImpl implements ITonGanManager {
	@Resource(name = "tonganHibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	public void addData(Object obj){
		hibernateTemplate.merge(obj);
	}
	public SqPhotos getSqPhotos(final String sfzmhm,final String jxdm,final String qh){
		return hibernateTemplate.execute(new HibernateCallback<SqPhotos>() {
			@Override
			public SqPhotos doInHibernate(Session session) throws HibernateException {
				StringBuffer sql = new StringBuffer("From SqPhotos where 1=1");

				List params = new ArrayList();
				if(Common.isNotEmpty(sfzmhm)){
					//查询流水号
					sql.append(" and  id.sfzmhm = ?");	
					params.add(sfzmhm);
					
				}
				if(Common.isNotEmpty(jxdm)){
					//查询流水号
					sql.append(" and  id.jxdm = ?");
					params.add(jxdm);
					
				}
				if(Common.isNotEmpty(qh)){
					//查询流水号
					sql.append(" and  id.qh = ?");
					params.add(qh);
				}
				Query query = session.createQuery(sql.toString());			
				int i = 0;
				for (Object param : params) {
					query.setParameter(i, param);
					i++;
				}
				List<SqPhotos> bases = (List<SqPhotos>) query.list();
				if(bases.size()>0){
					return bases.get(0);
				}
				return null;
			}
		});
	}
	public TjUser getTjUser(final String xm,final String yymc){
		return hibernateTemplate.execute(new HibernateCallback<TjUser>() {
			@Override
			public TjUser doInHibernate(Session session) throws HibernateException {
				StringBuffer sql = new StringBuffer("From TjUser where 1=1");

				List params = new ArrayList();
				if(Common.isNotEmpty(xm)){
					//查询流水号
					sql.append(" and  XM like ?");
					params.add("%" +xm + "%");
					
				}
				if(Common.isNotEmpty(yymc)){
					//查询流水号
					sql.append(" and  YYMC like ?");
					params.add("%" +yymc + "%");
					
				}
				Query query = session.createQuery(sql.toString());			
				int i = 0;
				for (Object param : params) {
					query.setParameter(i, param);
					i++;
				}
				List<TjUser> bases = (List<TjUser>) query.list();
				if(bases.size()>0){
					return bases.get(0);
				}
				return new TjUser();
			}
		});
	}
}
