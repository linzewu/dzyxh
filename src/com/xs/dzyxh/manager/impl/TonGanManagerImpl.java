package com.xs.dzyxh.manager.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.common.Common;
import com.xs.dzyxh.entity.tongan.SqPhotos;
import com.xs.dzyxh.entity.tongan.TjUser;
import com.xs.dzyxh.manager.ITonGanManager;

@Service("tonGanManager")
public class TonGanManagerImpl implements ITonGanManager {
	@Resource(name = "tonganHibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	public void addData(Object obj) {
		hibernateTemplate.merge(obj);
	}

	public SqPhotos getSqPhotos(final String sfzmhm, final String jxdm, final String qh) {
		return hibernateTemplate.execute(new HibernateCallback<SqPhotos>() {
			@Override
			public SqPhotos doInHibernate(Session session) throws HibernateException {
				StringBuffer sql = new StringBuffer("From SqPhotos where 1=1");

				List params = new ArrayList();
				if (Common.isNotEmpty(sfzmhm)) {
					// 查询流水号
					sql.append(" and  id.sfzmhm = ?");
					params.add(sfzmhm);

				}
				if (Common.isNotEmpty(jxdm)) {
					// 查询流水号
					sql.append(" and  id.jxdm = ?");
					params.add(jxdm);

				}
				if (Common.isNotEmpty(qh)) {
					// 查询流水号
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
				if (bases.size() > 0) {
					return bases.get(0);
				}
				return null;
			}
		});
	}

	public TjUser getTjUser(final String xm, final String yymc) {
		return hibernateTemplate.execute(new HibernateCallback<TjUser>() {
			@Override
			public TjUser doInHibernate(Session session) throws HibernateException {
				StringBuffer sql = new StringBuffer("From TjUser where 1=1");

				List params = new ArrayList();
				if (Common.isNotEmpty(xm)) {
					// 查询流水号
					sql.append(" and  XM like ?");
					params.add("%" + xm + "%");

				}
				if (Common.isNotEmpty(yymc)) {
					// 查询流水号
					sql.append(" and  YYMC like ?");
					params.add("%" + yymc + "%");

				}
				Query query = session.createQuery(sql.toString());
				int i = 0;
				for (Object param : params) {
					query.setParameter(i, param);
					i++;
				}
				List<TjUser> bases = (List<TjUser>) query.list();
				if (bases.size() > 0) {
					return bases.get(0);
				}
				return new TjUser();
			}
		});
	}

	@Override
	public Map<String,Object> getBaseList(final Map<String, Object> param) {

		return hibernateTemplate.execute(new HibernateCallback<Map<String,Object>>() {

			@Override
			public Map<String,Object> doInHibernate(Session session) throws HibernateException {
				// TODO Auto-generated method stub
				StringBuffer sql = new StringBuffer(
						"select jxdm,jxmc,qh,sfzmhm,xm,xb,lxdh from DRV_TEMP_MID where 1=1 ");
				
				StringBuffer countSql = new StringBuffer(
						"select count(*) from DRV_TEMP_MID where 1=1 ");
				
				String sfzmhm = (String)param.get("sfzmhm");
				String xm = (String)param.get("xm");
				String qh = (String)param.get("qh");
				int curPage = Integer.parseInt(param.get("page").toString());
				int pageSize = Integer.parseInt(param.get("rows").toString());
				List<Object> values = new ArrayList<Object>();
				if (StringUtils.isNotEmpty(sfzmhm)) {
					sql.append(" and sfzmhm like ?");
					countSql.append(" and sfzmhm like ?");
					values.add("%" + sfzmhm + "%");
				}
				if (StringUtils.isNotEmpty(xm)) {
					sql.append(" and xm like ?");
					countSql.append(" and xm like ?");
					values.add("%" + xm + "%");

				}
				if (StringUtils.isNotEmpty(qh)) {
					sql.append(" and qh = ?");
					countSql.append(" and qh = ?");
					values.add(qh);
				}

				Query query = session.createSQLQuery(sql.toString()).setFirstResult((curPage - 1) * pageSize)
						.setMaxResults(pageSize).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				Query countQuery = session.createSQLQuery(countSql.toString());
				
				for (int i = 0; i < values.size(); i++) {
					query.setParameter(i, values.get(i));
					countQuery.setParameter(i, values.get(i));
				}
				
				BigDecimal count = (BigDecimal) countQuery.uniqueResult();
				
				Map<String,Object> data =new HashMap<String,Object>();
				data.put("total", count.intValue());
				data.put("rows", query.list());
				
				return data;
			}
		});

	}
}
