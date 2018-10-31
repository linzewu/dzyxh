package com.xs.dzyxh.manager;

import java.util.List;

import com.xs.dzyxh.entity.system.SecurityAuditPolicySetting;


public interface ISecurityAuditPolicySettingManager {
	
	public List<SecurityAuditPolicySetting> getList(Integer page, Integer rows, SecurityAuditPolicySetting securityAuditPolicySetting) ;

	public Integer getListCount(Integer page, Integer rows, SecurityAuditPolicySetting securityAuditPolicySetting);
	
	public void updateSecurityAuditPolicySetting(List<SecurityAuditPolicySetting> list);
	
	public SecurityAuditPolicySetting getPolicyByCode(String aqsjclbm);
}
