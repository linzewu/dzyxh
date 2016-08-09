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
import com.xs.dzyxh.entity.Role;
import com.xs.dzyxh.entity.User;
import com.xs.dzyxh.manager.IUserManager;

@Controller
@RequestMapping(value = "/user")
@ModuleAnnotation(modeName = Constant.ConstantDZYXH.MODE_NAME_SYSTEM, appName = Constant.ConstantDZYXH.APP_NAME_USER)
public class UserController {
	
	@Resource(name="userManager")
	private IUserManager userManager;
	
	@FunctionAnnotation(name = "用户查询")
	@RequestMapping(value = "getUsers", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> getUsers(User user,Integer page,Integer rows) {
		
		List<User> userList  =  userManager.getUsers(user, page, rows);
		
		Integer count = userManager.getUserCount(user);
		
		return ResultHandler.toMyJSON(userList, count);
	}
	
	@FunctionAnnotation(name = "用户查询")
	@RequestMapping(value = "getUser", method = RequestMethod.POST)
	public @ResponseBody User getUser(String yhm) {
		User user  =  userManager.getUser(yhm);
		return user;
	}
	
	
	@FunctionAnnotation(name = "编辑用户")
	@RequestMapping(value = "saveUser", method = RequestMethod.POST)
	public @ResponseBody Map saveUser(User user,BindingResult result) {
		if(!result.hasErrors()){
			
			if(user.getId()==null){
				user.setMm("888888");
			}else{
				User oldUser=userManager.getUser(user.getId());
				user.setMm(oldUser.getMm());
			}
			user = userManager.saveUser(user);
			return ResultHandler.resultHandle(result, user, Constant.ConstantMessage.SAVE_SUCCESS);
		}else{
			return ResultHandler.resultHandle(result, null, null);
		}
	}
	
	@FunctionAnnotation(name = "密码重置")
	@RequestMapping(value = "passwordReset", method = RequestMethod.POST)
	public @ResponseBody Map passwordReset(User user) {
		
		User oldUser=userManager.getUser(user.getId());
		oldUser.setMm("888888");
		userManager.saveUser(oldUser);
		return ResultHandler.toSuccessJSON("密码重置成功");
	}
	
	
	@FunctionAnnotation(name = "删除用户")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> delete(User user) {
		
		userManager.deleteUser(user);
		
		return ResultHandler.toSuccessJSON("用户删除成功");
	}
	
	
	@FunctionAnnotation(name = "编辑用户")
	@RequestMapping(value = "validateUserName")
	public @ResponseBody boolean validateUserName(User user) {
		
		return this.userManager.isExistUserName(user);
	}

}
