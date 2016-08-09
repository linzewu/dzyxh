package com.xs.dzyxh.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.common.Constant;
import com.xs.common.ResultHandler;
import com.xs.common.Annotation.FunctionAnnotation;
import com.xs.common.Annotation.ModuleAnnotation;
import com.xs.dzyxh.entity.Department;
import com.xs.dzyxh.manager.impl.DepartmentManagerImpl;

@Controller
@RequestMapping(value = "/dept")
@ModuleAnnotation(modeName = Constant.ConstantDZYXH.MODE_NAME_SYSTEM, appName = Constant.ConstantDZYXH.APP_NAME_DEPT)
public class DepartmentContorller {

	@Resource(name = "departmentManager")
	private DepartmentManagerImpl deptManager;

	@FunctionAnnotation(name = "部门查询")
	@RequestMapping(value = "getDepts", method = RequestMethod.POST)
	public @ResponseBody List<Department> getDepts() {
		List<Department> depts = deptManager.getDepts();
		return depts;
	}

	@FunctionAnnotation(name = "部门信息修改", buttonName = "deptAdd,deptSave")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Map saveDept(Department dept, BindingResult result) {
		
		if(!result.hasErrors()){
			Department d = this.deptManager.saveDept(dept);
			return ResultHandler.resultHandle(result, d, Constant.ConstantMessage.SAVE_SUCCESS);
		}else{
			return ResultHandler.resultHandle(result, null, null);
		}

		

	}

	@FunctionAnnotation(name = "部门信息删除")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody Map deleteDept(Department dept, BindingResult result) {
		this.deptManager.deleteDept(dept);
		return ResultHandler.toSuccessJSON("删除成功！");
	}

}
