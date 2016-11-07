package com.xs.dzyxh.manager.sys.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.dzyxh.entity.system.Role;
import com.xs.dzyxh.manager.sys.IRoleManager;

@Service("roleManager")
public class RoleManagerImpl implements IRoleManager {
	
	@Resource(name = "sysHibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	public List<Role> getRoleList(final Role role,final Integer page,final Integer rows) {
		
		return  hibernateTemplate.execute(new HibernateCallback<List<Role>>() {

			@Override
			public List<Role> doInHibernate(Session session) throws HibernateException {
				StringBuffer sql=new StringBuffer("From Role where 1=1");
				
				List params=new ArrayList();
				
				if(role!=null){
					if(role.getJsmc()!=null&&!"".equals(role.getJsmc())){
						sql.append(" and  jsmc like ?");
						params.add("%"+role.getJsmc()+"%");
					}
					
					if(role.getJslx()!=null){
						sql.append(" and  jslx = ?");
						params.add(role.getJslx());
					}
					
					if(role.getJsjb()!=null){
						sql.append(" and jsjb = ?");
						params.add(role.getJsjb());
					}
				}
				Query query = session.createQuery(sql.toString());
				
				if(page!=null&&rows!=null){
					query.setFirstResult((page-1)*rows);
					query.setMaxResults(rows);
				}
				
				int i=0;
				for(Object param: params){
					query.setParameter(i, param);
					i++;
				}
				
				List<Role> roles  =(List<Role>) query.list();
				return roles;
			}
		});
	}
	
	@Override
	public Integer getRoleCount(final Role role) {
		
	
		
		Integer count = hibernateTemplate.execute(new HibernateCallback<Integer>() {

			@Override
			public Integer doInHibernate(Session session) throws HibernateException {
				
				StringBuffer sql=new StringBuffer("Select count(id) From Role  where 1=1");
				
				List params=new ArrayList();
				
				if(role!=null){
					if(role.getJsmc()!=null&&!"".equals(role.getJsmc())){
						sql.append(" and  jsmc like ?");
						params.add("%"+role.getJsmc()+"%");
					}
					
					if(role.getJslx()!=null){
						sql.append(" and  jslx = ?");
						params.add(role.getJslx());
					}
					
					if(role.getJsjb()!=null){
						sql.append(" and jsjb = ?");
						params.add(role.getJsjb());
					}
				}
				
				int i=0;
				Query query = session.createQuery(sql.toString());
				for(Object param: params){
					query.setParameter(i, param);
					i++;
				}
				Long count =(Long) query.uniqueResult();
				return count.intValue();
			}
			
		});
		
		return count;
	}

	@Override
	public Role getRole(String roleName) {
		
		StringBuffer sql=new StringBuffer("From role where jsmc=?");
		
		List<Role> roles = (List<Role>) this.hibernateTemplate.find(sql.toString(), roleName);
		
		if(roles!=null&&!roles.isEmpty()){
			return roles.get(0);
		}else{
			return null;
		}
	}

	@Override
	public Role add(Role role){
		Serializable id = hibernateTemplate.save(role);
		role.setId(Integer.parseInt(id.toString()));
		return role;
	}

	@Override
	public Role save(Role role) throws Exception {
		if(role.getJslx()==0){
			throw new Exception("系统超级管理员无法修改！");
		}
		return hibernateTemplate.merge(role);
	}

	@Override
	public void delete(Integer id) throws Exception {
		Role role= this.hibernateTemplate.load(Role.class, id);
		
		if(role.getJslx()==0){
			throw new Exception("系统超级管理员无法删除");
		}else{
			hibernateTemplate.delete(role);
		}
		
	}

	@Override
	public Role getSystemRole() {
		
		DetachedCriteria dc = DetachedCriteria.forClass(Role.class);
		
		dc.add(Restrictions.eq("jsjb",0));
		dc.add(Restrictions.eq("jslx",0));
		
		List<Role> roles = (List<Role>) this.hibernateTemplate.findByCriteria(dc);
		
		return roles.get(0);
	}

}
