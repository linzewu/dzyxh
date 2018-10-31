package com.xs.dzyxh.manager;

import java.util.List;

import com.xs.dzyxh.entity.system.CoreFunction;


public interface ICoreFunctionManager {
	
	public List<CoreFunction> getAllCoreFunction(int status);
	
	public void deleteAllCoreFunction(final int status) ;
	
	public void save(List <CoreFunction> funs);

}
