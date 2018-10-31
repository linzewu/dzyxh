package com.xs.dzyxh.manager;

import java.util.List;
import java.util.Map;

import com.xs.dzyxh.entity.tongan.SqPhotos;
import com.xs.dzyxh.entity.tongan.TjUser;

public interface ITonGanManager {
	public void addData(Object obj);
	public TjUser getTjUser(final String xm,final String yymc);
	public SqPhotos getSqPhotos(final String sfzmhm,final String jxdm,final String qh);
	
	public Map<String,Object> getBaseList(Map<String,Object> param);
}
