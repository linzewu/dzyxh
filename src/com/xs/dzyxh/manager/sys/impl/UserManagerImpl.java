package com.xs.dzyxh.manager.sys.impl;

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
import com.xs.dzyxh.entity.system.User;
import com.xs.dzyxh.manager.sys.IDepartmentManager;
import com.xs.dzyxh.manager.sys.IUserManager;

@Service("userManager")
public class UserManagerImpl implements IUserManager {

	@Resource(name = "sysHibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Resource(name = "departmentManager")
	private IDepartmentManager deptManager;

	@Override
	public List<User> getUsers(final User user, final Integer page, final Integer rows) {

		return hibernateTemplate.execute(new HibernateCallback<List<User>>() {

			@Override
			public List<User> doInHibernate(Session session) throws HibernateException {
				StringBuffer sql = new StringBuffer("From User where 1=1");

				List params = new ArrayList();

				List<String> dms = null;

				if (user != null) {

					if (Common.isNotEmpty(user.getYhm())) {
						sql.append(" and  yhm like ?");
						params.add("%" + user.getYhm() + "%");
					}

					if (Common.isNotEmpty(user.getYhxm())) {
						sql.append(" and  yhxm like ?");
						params.add("%" + user.getYhxm() + "%");
					}

					if (Common.isNotEmpty(user.getSfzh())) {
						sql.append(" and  sfzh like ?");
						params.add(user.getSfzh() + "%");
					}

					if (Common.isNotEmpty(user.getBmdm())) {

						// 使用seq代替是否查询子级部门 seq =1 查询子级部门
						if (user.getSeq() != null && user.getSeq() == 1) {

							dms = deptManager.getSubCodes(user.getBmdm());
							sql.append(" and  bmdm in (:dms)");

						} else {
							sql.append(" and  bmdm =?");
							params.add(user.getBmdm());
						}

					}

				}
				Query query = session.createQuery(sql.toString());

				query.setFirstResult((page - 1) * rows);
				query.setMaxResults(rows);

				int i = 0;
				for (Object param : params) {
					query.setParameter(i, param);
					i++;
				}

				if (dms != null) {
					query.setParameterList("dms", dms);
				}
				List<User> users = (List<User>) query.list();
				return users;
			}
		});
	}

	@Override
	public User getUser(String userName) {
		List<User> users = (List<User>) this.hibernateTemplate.find("From User where yhm=?", userName);
		if (users == null || users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}

	@Override
	public User getUser(Integer id) {

		return this.hibernateTemplate.get(User.class, id);
	}

	@Override
	public User saveUser(User user) {
		this.hibernateTemplate.merge(user);
		return user;
	}

	@Override
	public void deleteUser(User user) {
		this.hibernateTemplate.delete(user);
	}

	@Override
	public Integer getUserCount(final User user) {

		return hibernateTemplate.execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException {
				StringBuffer sql = new StringBuffer("select count(*) From User where 1=1");

				List<String> dms = null;
				List params = new ArrayList();

				if (user != null) {

					if (Common.isNotEmpty(user.getYhm())) {
						sql.append(" and  yhm like ?");
						params.add("%" + user.getYhm() + "%");
					}

					if (Common.isNotEmpty(user.getYhxm())) {
						sql.append(" and  yhxm like ?");
						params.add("%" + user.getYhxm() + "%");
					}

					if (Common.isNotEmpty(user.getSfzh())) {
						sql.append(" and  sfzh like ?");
						params.add(user.getSfzh() + "%");
					}

					if (Common.isNotEmpty(user.getBmdm())) {

						// 使用seq代替是否查询子级部门 seq =1 查询子级部门
						if (user.getSeq() != null && user.getSeq() == 1) {

							dms = deptManager.getSubCodes(user.getBmdm());
							sql.append(" and  bmdm in (:dms)");

						} else {
							sql.append(" and  bmdm =?");
							params.add(user.getBmdm());
						}

					}

				}
				Query query = session.createQuery(sql.toString());

				int i = 0;
				for (Object param : params) {
					query.setParameter(i, param);
					i++;
				}

				if (dms != null) {
					query.setParameterList("dms", dms);
				}

				Long count = (Long) query.uniqueResult();
				return count.intValue();
			}
		});
	}

	public boolean isExistUserName(User user) {

		StringBuffer sb = new StringBuffer("from User where yhm=?");

		List<User> users;

		if (user.getId() != null) {
			sb.append(" and id!=?");
			users = (List<User>) this.hibernateTemplate.find(sb.toString(), user.getYhm(), user.getId());
		} else {
			users = (List<User>) this.hibernateTemplate.find(sb.toString(), user.getYhm());
		}

		return users == null || users.size() == 0 ? true : false;
	}

	public void initAdmin() {

		List<User> users = (List<User>) this.hibernateTemplate.find("from User where yhm=?", "admin");

		if (users == null || users.isEmpty()) {
			User user = new User();
		}

	}

	public User login(String userName, String password) {
		List<User> list =  (List<User>) hibernateTemplate.find("from User where yhm=? and mm=?", userName,password);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
