package com.xs.dzyxh.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.dzyxh.entity.system.BlackList;
import com.xs.dzyxh.entity.system.User;
import com.xs.dzyxh.manager.IBlackListManager;
@Service("blackListManager")
public class BlackListManagerImpl implements IBlackListManager {

	@Resource(name = "sysHibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	@Override
	public void saveBlackList(BlackList blackList) {
		this.hibernateTemplate.saveOrUpdate(blackList);
	}

	@Override
	public void deleteBlackList(BlackList blackList) {
		this.hibernateTemplate.delete(blackList);

	}

	@Override
	public void deleteSystemBlackList() {
		List<BlackList> blackList = (List<BlackList>) hibernateTemplate.find("from BlackList where createBy=?",User.SYSTEM_USER);
		hibernateTemplate.deleteAll(blackList);
	}

	@Override
	public BlackList getBlackListByIp(String ip) {
		BlackList blackList = this.hibernateTemplate.get(BlackList.class, ip);
		return blackList;
	}

	@Override
	public boolean checkIpIsBan(String ip) {
		System.out.println(ip);
		BlackList blackList = this.hibernateTemplate.get(BlackList.class, ip);
		if(blackList!=null) {
			//阈值取阈值表
			if("Y".equals(blackList.getEnableFlag())||!User.SYSTEM_USER.equals(blackList.getCreateBy())) {
				System.out.println("true");
				return true;
			}
		}
		return false;
	}

	@Override
	public List<BlackList> getList(Integer page, Integer rows, BlackList blackList) {
		DetachedCriteria query = DetachedCriteria.forClass(BlackList.class);

		Integer firstResult = (page - 1) * rows;
		query.add(Restrictions.eq("enableFlag", "Y"));
		
		List<BlackList> vcps = (List<BlackList>) this.hibernateTemplate.findByCriteria(query, firstResult,
				rows);

		return vcps;
	}

	@Override
	public Integer getListCount(Integer page, Integer rows, BlackList blackList) {
		DetachedCriteria query = DetachedCriteria.forClass(BlackList.class);

		query.setProjection(Projections.rowCount());
		
		List<Long> count = (List<Long>) hibernateTemplate.findByCriteria(query);

		return count.get(0).intValue();
	}

	@Override
	public List<BlackList> getEnableList() {
		return (List<BlackList>)this.hibernateTemplate.find(" from BlackList where enableFlag = 'Y'", null);
	}

}
