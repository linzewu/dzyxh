package com.xs.dzyxh.controller;

import java.io.File;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.common.Constant;
import com.xs.common.Sql2WordUtil;
import com.xs.common.Annotation.FunctionAnnotation;
import com.xs.common.Annotation.ModuleAnnotation;
import com.xs.dzyxh.manager.tongan.ITonGanManager;

@Controller
@RequestMapping(value = "/dbc")
@ModuleAnnotation(modeName = Constant.ConstantDZYXH.MODE_NAME_WINDOW, appName = Constant.ConstantDZYXH.APP_NAME_DRIVING,icoUrl="/dzyxh/images/fxp_48.png",href="/dzyxh/page/window/jsz.html",modeIndex=1,appIndex=2)
public class DrivingBaseController {
	
	Logger logger = Logger.getLogger(DrivingBaseController.class);
	
	@Resource(name = "tonGanManager")
	private ITonGanManager tonGanManager;
	
	@FunctionAnnotation(name = "查询基本信息")
	@RequestMapping(value = "getBaseLists")
	public @ResponseBody Map<String, Object> getBaseLists(@RequestParam Map<String,Object> param){
		 return tonGanManager.getBaseList(param);
	}
	
	
	@FunctionAnnotation(name = "查询基本信息")
	@RequestMapping(value = "getDriverImage",method = RequestMethod.GET)
	public String getDriverImage(@RequestParam String sfzmhm,@RequestParam String qh, @RequestParam String jxdm,@RequestParam String zplx){
		
		String key =jxdm+"_"+qh+"_"+sfzmhm+"_"+zplx+".jpg";
		
		String patch =  Sql2WordUtil.getCacheDir()+key;
		File file=new File(patch);
		String filePath=key;
		if(!file.exists()) {
			//String filePath = Sql2WordUtil.sql2WordUtilCase(template, sql, hibernateTemplate, fileName);
			
			//if(filePath==null) {
				return "forward:/images/no-image.jpg";
			//}
			
		}
		
		return "forward:/images/cache/"+filePath;
		
	}
}
