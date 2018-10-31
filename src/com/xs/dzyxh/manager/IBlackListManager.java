package com.xs.dzyxh.manager;

import java.util.List;

import com.xs.dzyxh.entity.system.BlackList;

public interface IBlackListManager {
	
	public void saveBlackList(BlackList blackList);
	
	public void deleteBlackList(BlackList blackList);
	
	public void deleteSystemBlackList();
	
	public BlackList getBlackListByIp(String ip);
	
	public boolean checkIpIsBan(String ip);
	
	public List<BlackList> getList(Integer page, Integer rows, BlackList blackList);
	
	public Integer getListCount(Integer page, Integer rows, BlackList blackList);
	
	public List<BlackList> getEnableList();

}
