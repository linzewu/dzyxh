package com.xs.dzyxh.manager;

import java.util.List;

import com.xs.dzyxh.entity.User;

public interface IUserManager {
	
	public List<User> getUsers(User user,Integer page,Integer rows);
	
	public Integer getUserCount(User user);
	
	public User getUser(String userName);
	
	public User getUser(Integer id);
	
	public User saveUser(User user);
	
	public void deleteUser(User user);
	
	public boolean isExistUserName(User user);
	
	public void initAdmin();
	
	public User login(String userName, String password);
	
	

}
