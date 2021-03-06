package com.xs.dzyxh.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.common.ResultHandler;
import com.xs.common.Annotation.Modular;
import com.xs.common.Annotation.UserOperation;
import com.xs.dzyxh.entity.system.CoreFunction;
import com.xs.dzyxh.manager.ICoreFunctionManager;

@Controller
@RequestMapping(value = "/coreFunction",produces="application/json")
@Modular(modelCode="coreFunction",modelName="核心功能管理",isEmpowered=false)
public class CoreFunctionController {
	
	@Resource(name = "coreFunctionManager")
	private ICoreFunctionManager coreFunctionManager;
	
	@Autowired
	private ServletContext servletContext;
	
	@UserOperation(code="save",name="保存核心功能")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Map saveCoreFunction(@RequestParam("functionPoint") String functionPoint) {
			this.coreFunctionManager.deleteAllCoreFunction(0);
			String[] functionPo = functionPoint.split(",");
			List<CoreFunction> funs =new ArrayList<CoreFunction>();
			for(String str:functionPo) {
				CoreFunction core = new CoreFunction();
				core.setFunctionPoint(str);
				//0:核心功能  1：非常规功能  2：警员功能
				core.setStatus(0);
				funs.add(core);
			}
			this.coreFunctionManager.save(funs);
			List<CoreFunction> coreList = this.coreFunctionManager.getAllCoreFunction(0);
			servletContext.setAttribute("coreFunctionList", coreList);
			return  ResultHandler.toSuccessJSON("保存核心功能成功！");		
	}
	
	@UserOperation(code="getAllCoreFunction",name="获取核心功能")
	@RequestMapping(value = "getAllCoreFunction", method = RequestMethod.POST)
	public @ResponseBody List<CoreFunction> getAllCoreFunction() {
		List<CoreFunction> list = (List<CoreFunction>)servletContext.getAttribute("coreFunctionList");
		return list;
	}
	
	@UserOperation(code="getAllSpecialCoreFunction",name="获取非常规功能")
	@RequestMapping(value = "getAllSpecialCoreFunction", method = RequestMethod.POST)
	public @ResponseBody List<CoreFunction> getAllSpecialCoreFunction() {
		List<CoreFunction> coreList = this.coreFunctionManager.getAllCoreFunction(1);
		return coreList;
	}
	
	@UserOperation(code="getAllPoliceCoreFunction",name="获取警员功能")
	@RequestMapping(value = "getAllPoliceCoreFunction", method = RequestMethod.POST)
	public @ResponseBody List<CoreFunction> getAllPoliceCoreFunction() {
		List<CoreFunction> coreList = this.coreFunctionManager.getAllCoreFunction(2);
		return coreList;
	}
	
	@UserOperation(code="saveSpecialCoreFunction",name="保存非常规功能")
	@RequestMapping(value = "saveSpecialCoreFunction", method = RequestMethod.POST)
	public @ResponseBody Map saveSpecialCoreFunction(@RequestParam("functionPoint") String functionPoint) {
			this.coreFunctionManager.deleteAllCoreFunction(1);
			String[] functionPo = functionPoint.split(",");
			List<CoreFunction> funs =new ArrayList<CoreFunction>();
			for(String str:functionPo) {
				CoreFunction core = new CoreFunction();
				core.setFunctionPoint(str);
				//0:核心功能  1：非常规功能  2：警员功能
				core.setStatus(1);
				funs.add(core);
			}
			this.coreFunctionManager.save(funs);
			return  ResultHandler.toSuccessJSON("保存非常规功能成功！");		
	}
	
	@UserOperation(code="savePoliceCoreFunction",name="保存警员功能")
	@RequestMapping(value = "savePoliceCoreFunction", method = RequestMethod.POST)
	public @ResponseBody Map savePoliceCoreFunction(@RequestParam("functionPoint") String functionPoint) {
			this.coreFunctionManager.deleteAllCoreFunction(2);
			String[] functionPo = functionPoint.split(",");
			List<CoreFunction> funs =new ArrayList<CoreFunction>();
			for(String str:functionPo) {
				CoreFunction core = new CoreFunction();
				core.setFunctionPoint(str);
				//0:核心功能  1：非常规功能  2：警员功能
				core.setStatus(2);
				funs.add(core);
			}
			this.coreFunctionManager.save(funs);
			return  ResultHandler.toSuccessJSON("保存警员功能成功！");		
	}

}
