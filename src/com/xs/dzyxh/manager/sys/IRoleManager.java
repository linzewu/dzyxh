package com.xs.dzyxh.manager.sys;

import java.util.List;

import com.xs.dzyxh.entity.system.Role;

public interface IRoleManager {
	
	public List<Role> getRoleList(Role role,Integer page,Integer rows);
	
	public Integer getRoleCount(Role role);
	
	public Role getRole(String roleName);
	
	public Role add(Role role);
	
	public Role save(Role role) throws Exception;
	
	public void delete(Integer id) throws Exception;
	
	public Role getSystemRole();

}
