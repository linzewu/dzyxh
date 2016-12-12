package com.xs.dzyxh.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xs.common.ComputerInfoUtil;
import com.xs.common.Constant;
import com.xs.common.ResultHandler;
import com.xs.common.Annotation.FunctionAnnotation;
import com.xs.common.Annotation.ModuleAnnotation;
import com.xs.dzyxh.entity.system.BaseParams;
import com.xs.dzyxh.manager.sys.IBaseParamsManager;
import com.xs.web.util.PageInfo;

@Controller
@RequestMapping(value = "/bps")
@ModuleAnnotation(modeName = Constant.ConstantDZYXH.MODE_NAME_SYSTEM, appName = Constant.ConstantDZYXH.APP_NAME_SYS,icoUrl="/dzyxh/images/system.png",href="/dzyxh/page/system/systemInfo.html",modeIndex=4,appIndex=2)
public class BaseParamsController {

	@Resource(name = "baseParamsManager")
	private IBaseParamsManager baseParamsManager;

	@RequestMapping(value = "all.js", produces = "application/javascript; charset=utf-8")
	public @ResponseBody String getBaseParamsOfJS(HttpServletRequest request) throws JsonProcessingException {
		ServletContext servletContext = request.getSession().getServletContext();
		List<BaseParams> bps = (List<BaseParams>) servletContext.getAttribute("bps");

		ObjectMapper objectMapper = new ObjectMapper();
		String js = " var bps=" + objectMapper.writeValueAsString(bps);
		return js;
	}

	@RequestMapping(value = "all.json")
	public @ResponseBody Map getBaseParams(HttpServletRequest request) {

		RequestContext requestContext = new RequestContext(request);

		ServletContext servletContext = request.getSession().getServletContext();

		List<BaseParams> bps = (List<BaseParams>) servletContext.getAttribute("bps");

		return ResultHandler.toMyJSON(1, requestContext.getMessage(Constant.ConstantKey.SUCCESS), bps);
	}
	@FunctionAnnotation(name = "系统参数查询查询")
	@RequestMapping(value = "getComputerInfo")
	public @ResponseBody Map getComputer(HttpServletRequest request) {
		Map map = ComputerInfoUtil.getComputerInfo();
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, Constant.ConstantMessage.SUCCESS, map);
	}

	@RequestMapping(value = "getLicenseInfo")
	public @ResponseBody Map getLicenseInfo(HttpServletRequest request) {
		Map map = ComputerInfoUtil.getComputerInfo();
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, Constant.ConstantMessage.SUCCESS, map);
	}
	@FunctionAnnotation(name = "保存系统参数")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Map save(BaseParams baseParams) {
		baseParams = this.baseParamsManager.save(baseParams);
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "保存成功",baseParams);
	}
	@FunctionAnnotation(name = "删除系统参数")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody Map delete(@RequestParam Integer id) {
		this.baseParamsManager.delete(id);
		return ResultHandler.toSuccessJSON("删除成功！");
	}

	@RequestMapping(value = "getBaseParamsOfPage")
	public @ResponseBody Map getBaseParamsOfPage(PageInfo pageInfo,BaseParams param) {
		Map data = this.baseParamsManager.getBaseParams(pageInfo,param);
		return data;
	}
	@FunctionAnnotation(name = "刷新系统参数")
	@RequestMapping(value = "refresh")
	public @ResponseBody Map refresh(HttpServletRequest request) {
		ServletContext servletContext = request.getSession().getServletContext();
		servletContext.setAttribute("bps", baseParamsManager.getBaseParams());
		return ResultHandler.toSuccessJSON("刷新成功");
	}

}
