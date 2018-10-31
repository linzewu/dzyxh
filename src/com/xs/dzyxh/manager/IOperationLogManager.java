package com.xs.dzyxh.manager;

import java.util.List;

import com.xs.dzyxh.entity.system.OperationLog;


public interface IOperationLogManager {
	
	public List<OperationLog> getOperationLog(Integer page, Integer rows, OperationLog operationLog);
	
	public Integer getOperationLogCount(Integer page, Integer rows, OperationLog operationLog);
	
	public void saveOperationLog(OperationLog operationLog);
	
	public List<OperationLog> getLoginOperationLog(Integer page, Integer rows, OperationLog operationLog);
	
	public Integer getLoginOperationLogCount(Integer page, Integer rows, OperationLog operationLog) ;

}
