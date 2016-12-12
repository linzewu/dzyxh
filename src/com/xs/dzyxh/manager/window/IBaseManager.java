package com.xs.dzyxh.manager.window;

import org.springframework.dao.DataAccessException;

public interface IBaseManager<T> {
	public boolean saveOrUpdate(T t) throws DataAccessException;
	//public boolean save(T t);
}
