package com.xs.dzyxh.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.dzyxh.entity.system.CoreFunction;
import com.xs.dzyxh.manager.ICoreFunctionManager;
@Service("coreFunctionManager")
public class CoreFunctionManagerImpl implements ICoreFunctionManager {

	@Resource(name = "sysHibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	@Override
	public List<CoreFunction> getAllCoreFunction(int status) {
		return (List<CoreFunction>)this.hibernateTemplate.find(" from CoreFunction where status = ?", status);
	}

	@Override
	public void deleteAllCoreFunction(final int status) {
		this.hibernateTemplate.execute(new HibernateCallback<CoreFunction>() {
			@Override
			public CoreFunction doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery("delete from CoreFunction where status = "+status);
                query.executeUpdate();
                return null;
			}
			
		});

	}

	@Override
	public void save(List<CoreFunction> funs) {
		for(CoreFunction fun:funs) {
			this.hibernateTemplate.save(fun);
		}

	}

}
