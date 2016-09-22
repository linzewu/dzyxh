package com.xs.dzyxh.manager;

import java.util.List;
import java.util.Map;

import com.xs.dzyxh.entity.BaseParams;
import com.xs.web.util.PageInfo;

public interface IBaseParamsManager {
	
	public List<BaseParams> getBaseParams();
	
	public BaseParams save(BaseParams baseParams);
	
	public void delete(Integer id);
	
	public Map<String,Object> getBaseParams(PageInfo page,BaseParams param);
	

}
