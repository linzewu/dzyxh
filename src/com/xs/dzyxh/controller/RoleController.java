package com.xs.dzyxh.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.common.Constant;
import com.xs.common.ResultHandler;
import com.xs.common.Annotation.FunctionAnnotation;
import com.xs.common.Annotation.ModuleAnnotation;
import com.xs.dzyxh.entity.Power;
import com.xs.dzyxh.entity.Role;
import com.xs.dzyxh.manager.IRoleManager;

@Controller
@RequestMapping(value = "/role")
@ModuleAnnotation(modeName = Constant.ConstantDZYXH.MODE_NAME_SYSTEM, appName = Constant.ConstantDZYXH.APP_NAME_ROLE)
public class RoleController {

	@Resource(name = "roleManager")
	private IRoleManager roleManager;

	@FunctionAnnotation(name = "角色查询")
	@RequestMapping(value = "getRoles", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getRoles(Role role, Integer page, Integer rows) {
		List<Role> roles = roleManager.getRoleList(role, page, rows);
		Integer count = roleManager.getRoleCount(role);
		return ResultHandler.toMyJSON(roles, count);
	}

	@FunctionAnnotation(name = "角色查询")
	@RequestMapping(value = "getRole", method = RequestMethod.POST)
	public @ResponseBody Role getRole(String jsmc) {
		Role role = roleManager.getRole(jsmc);
		return role;
	}

	@FunctionAnnotation(name = "权限列表查询")
	@RequestMapping(value = "getPowers", method = RequestMethod.POST)
	public @ResponseBody List<Power> getPowers(HttpServletRequest request) {

		ServletContext sc = request.getSession().getServletContext();
		List<Power> powers = (List<Power>) sc.getAttribute("powers");
		return powers;
	}

	@FunctionAnnotation(name = "角色新增")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> add(Role role, BindingResult result) throws Exception {

		if (!result.hasErrors()) {
			if(role.getJslx()==0){
				throw new Exception("无法新增系统超级管理员");
			}
			role = roleManager.add(role);
			return ResultHandler.resultHandle(result, role, Constant.ConstantMessage.SAVE_SUCCESS);
		} else {
			return ResultHandler.resultHandle(result, null, null);
		}
	}

	@FunctionAnnotation(name = "角色编辑")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> save(Role role, BindingResult result) throws Exception {
		if (!result.hasErrors()) {
			role = roleManager.save(role);
			return ResultHandler.resultHandle(result, role, Constant.ConstantMessage.SAVE_SUCCESS);
		} else {
			return ResultHandler.resultHandle(result, null, null);
		}
	}

	@FunctionAnnotation(name = "角色删除")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> delete(@RequestParam Integer id) throws Exception {
		roleManager.delete(id);
		return ResultHandler.toSuccessJSON("角色删除成功");
	}

}
