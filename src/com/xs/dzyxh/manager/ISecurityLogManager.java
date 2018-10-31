package com.xs.dzyxh.manager;

import java.util.List;

import com.xs.dzyxh.entity.system.SecurityLog;


public interface ISecurityLogManager {
	
	public List<SecurityLog> getSecurityLog(Integer page, Integer rows, SecurityLog securityLog);
	
	public Integer getSecurityLogCount(Integer page, Integer rows, SecurityLog securityLog);
	
	public void saveSecurityLog(SecurityLog securityLog);

}
