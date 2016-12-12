package com.xs.dzyxh.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.common.Constant;
import com.xs.common.ResultHandler;
import com.xs.common.Annotation.FunctionAnnotation;
import com.xs.common.Annotation.ModuleAnnotation;
import com.xs.dzyxh.entity.driver.DrivingBase;
import com.xs.dzyxh.manager.window.IDrivingBaseManager;

@Controller
@RequestMapping(value = "/dbc")
@ModuleAnnotation(modeName = Constant.ConstantDZYXH.MODE_NAME_WINDOW, appName = Constant.ConstantDZYXH.APP_NAME_DRIVING,icoUrl="/dzyxh/images/fxp_48.png",href="/dzyxh/page/window/jsz.html",modeIndex=1,appIndex=2)
public class DrivingBaseController {
	@Resource(name = "driverManager")
	private IDrivingBaseManager drivingBaseManager;
	
	@FunctionAnnotation(name = "基本信息查询")
	@RequestMapping(value = "getDriBases")
	public @ResponseBody Map<String, Object> getDriBases(DrivingBase base, Integer page, Integer rows) {
		List<DrivingBase> baseList = drivingBaseManager.getDrivingBases(base, page, rows);
		Integer count = drivingBaseManager.getUserCount(base);
		return ResultHandler.toMyJSON(baseList, count);
	}
	@FunctionAnnotation(name = "根据ID查询基本信息")
	@RequestMapping(value = "getDriBaseById")
	public @ResponseBody DrivingBase getDrivingBaseById(DrivingBase base){
		return drivingBaseManager.getDrivingBaseById(base);
	}
	
	
}
