package com.xs.dzyxh.manager;

import java.util.List;

import com.xs.dzyxh.entity.system.BaseParams;
import com.xs.dzyxh.entity.vehimg.VehImg;

public interface IVehImgManager {
	
	public void saveImg(VehImg vehImg);
	
	public void updateVehImgZpztOfzplx(String lsh,String zplx);
	
	public List<VehImg> getVehImgByLshOfzc(String lsh);
	
	public List<BaseParams> getZpzl(String ywlx);
	
	public VehImg getImgById(Integer id);
	
	public List getYwListByClsbdh(String clsbdh);
	
	

}
