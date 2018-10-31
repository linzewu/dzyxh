package com.xs.common;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xs.dzyxh.entity.BaseEntity;
import com.xs.dzyxh.entity.system.User;


public class MyHibernateTemplate extends HibernateTemplate {


	@Override
	public Serializable save(Object entity) throws DataAccessException {
		
		HttpSession session=getSession();
		if (entity instanceof BaseEntity) {
			BaseEntity baseEntity = (BaseEntity) entity;
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			Date nowDate = new Date();
			baseEntity.setCreateTime(sdf.format(nowDate));
			baseEntity.setUpdateTime(sdf.format(nowDate));
			if (session != null) {
				User user = (User) session.getAttribute("user");
				if (user != null) {
					String userName = user.getYhm();
					baseEntity.setCreateUser(userName);
					baseEntity.setUpdateUser(userName);
				}
			}else{
				baseEntity.setCreateUser("system");
				baseEntity.setUpdateUser("system");
			}
		}
		return super.save(entity);
	}

	@Override
	public void update(Object entity) throws DataAccessException {
		HttpSession session=getSession();
		if (entity instanceof BaseEntity) {
			BaseEntity baseEntity = (BaseEntity) entity;
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			Date nowDate = new Date();
			baseEntity.setUpdateTime(sdf.format(nowDate));
			if (session != null) {
				User user = (User) session.getAttribute("user");
				if (user != null) {
					String userName = user.getYhm();
					baseEntity.setUpdateUser(userName);
				}
			}else{
				baseEntity.setUpdateUser("system");
			}
		}
		super.update(entity);
	}
	
	
	@Override
	public <T> T merge(T entity) throws DataAccessException {
		HttpSession session=getSession();
		
		if (entity instanceof BaseEntity) {
			BaseEntity baseEntity = (BaseEntity) entity;
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			Date nowDate = new Date();
			baseEntity.setUpdateTime(sdf.format(nowDate));
			if(StringUtils.isEmpty(baseEntity.getCreateTime())) {
				baseEntity.setCreateTime(sdf.format(nowDate));
			}
			if (session != null) {
				User user = (User) session.getAttribute("user");
				if (user != null) {
					String userName = user.getYhm();
					baseEntity.setUpdateUser(userName);
					if(StringUtils.isEmpty(baseEntity.getCreateUser())) {
						baseEntity.setCreateUser(userName);
					}
				}
			}else{
				baseEntity.setUpdateUser("system");
				if(StringUtils.isEmpty(baseEntity.getCreateUser())) {
					baseEntity.setCreateUser("system");
				}
			}
		}
		return super.merge(entity);
	}

	public HttpSession getSession(){
		
		RequestAttributes rab = RequestContextHolder.getRequestAttributes();
		
		if(rab!=null){
			HttpServletRequest request = ((ServletRequestAttributes)rab).getRequest();
			
			if(request!=null){
				return request.getSession();
			}else{
				return null;
			}
		}else{
			return null;
		}		
	}

	@Override
	public void saveOrUpdate(Object entity) throws DataAccessException {
		HttpSession session=getSession();
		
		if (entity instanceof BaseEntity) {
			BaseEntity baseEntity = (BaseEntity) entity;
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			Date nowDate = new Date();
			baseEntity.setUpdateTime(sdf.format(nowDate));
			if(StringUtils.isEmpty(baseEntity.getCreateTime())) {
				baseEntity.setCreateTime(sdf.format(nowDate));
			}
			if (session != null) {
				User user = (User) session.getAttribute("user");
				if (user != null) {
					String userName = user.getYhm();
					baseEntity.setUpdateUser(userName);
					if(StringUtils.isEmpty(baseEntity.getCreateUser())) {
						baseEntity.setCreateUser(userName);
					}
				}
			}else{
				baseEntity.setUpdateUser("system");
				if(StringUtils.isEmpty(baseEntity.getCreateUser())) {
					baseEntity.setCreateUser("system");
				}
			}
		}
		super.saveOrUpdate(entity);
	}

}
