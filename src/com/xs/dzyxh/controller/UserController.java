package com.xs.dzyxh.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.xs.common.Constant;
import com.xs.common.ResultHandler;
import com.xs.common.Annotation.FunctionAnnotation;
import com.xs.common.Annotation.ModuleAnnotation;
import com.xs.dzyxh.entity.system.Role;
import com.xs.dzyxh.entity.system.User;
import com.xs.dzyxh.manager.sys.IUserManager;

@Controller
@RequestMapping(value = "/user")
@ModuleAnnotation(modeName = Constant.ConstantDZYXH.MODE_NAME_SYSTEM, appName = Constant.ConstantDZYXH.APP_NAME_USER)
public class UserController {

	@Resource(name = "userManager")
	private IUserManager userManager;

	@FunctionAnnotation(name = "用户查询")
	@RequestMapping(value = "getUsers", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getUsers(User user, Integer page, Integer rows) {
		List<User> userList = userManager.getUsers(user, page, rows);
		Integer count = userManager.getUserCount(user);
		return ResultHandler.toMyJSON(userList, count);
	}

	@FunctionAnnotation(name = "用户查询")
	@RequestMapping(value = "getUser", method = RequestMethod.POST)
	public @ResponseBody User getUser(String yhm) {
		User user = userManager.getUser(yhm);
		return user;
	}

	@FunctionAnnotation(name = "编辑用户")
	@RequestMapping(value = "saveUser", method = RequestMethod.POST)
	public @ResponseBody Map saveUser(User user, BindingResult result) {
		if (!result.hasErrors()) {

			if (user.getId() == null) {
				user.setMm("888888");
				user.setZt(User.ZT_CZMM);
			} else {
				User oldUser = userManager.getUser(user.getId());
				user.setMm(oldUser.getMm());
			}
			user = userManager.saveUser(user);
			return ResultHandler.resultHandle(result, user, Constant.ConstantMessage.SAVE_SUCCESS);
		} else {
			return ResultHandler.resultHandle(result, null, null);
		}
	}

	@FunctionAnnotation(name = "密码重置")
	@RequestMapping(value = "passwordReset", method = RequestMethod.POST)
	public @ResponseBody Map passwordReset(User user) {

		User oldUser = userManager.getUser(user.getId());
		oldUser.setMm("888888");
		user.setZt(User.ZT_CZMM);
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

	@RequestMapping(value = "getCurrentUser", method = RequestMethod.POST)
	public @ResponseBody User getCurrentUser(HttpSession session) {
		User user = (User) session.getAttribute("user");
		return user;
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public @ResponseBody Map login(HttpServletRequest request, String userName, String password) {

		User user = userManager.login(userName, password);
		HttpSession session = request.getSession();
		RequestContext requestContext = new RequestContext(request);
		if (user != null) {

			if (user.getZt() == User.ZT_TY) {
				Map data = ResultHandler.toMyJSON(Constant.ConstantState.STATE_ERROR, "该用户已停用", null);
				return data;
			}
			
			session.setAttribute(Constant.ConstantKey.USER_SESSIO_NKEY, user);
			Map data = ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS,
					requestContext.getMessage(Constant.ConstantMessage.LOGIN_SUCCESS), user);
			data.put("session", session.getId());
			return data;
		} else {
			Map data = ResultHandler.toMyJSON(0, requestContext.getMessage(Constant.ConstantMessage.LOGIN_FAILED));
			data.put("session", session.getId());
			session.removeAttribute(Constant.ConstantKey.USER_SESSIO_NKEY);
			return data;
		}
	}

	@RequestMapping(value = "logout", method = RequestMethod.POST)
	public @ResponseBody Map logout(HttpSession session) {
		session.removeAttribute(Constant.ConstantKey.USER_SESSIO_NKEY);
		session.invalidate();
		return ResultHandler.toSuccessJSON("注销成功");
	}
	
	@RequestMapping(value = "isLogin", method = RequestMethod.POST)
	public @ResponseBody boolean isLogin(HttpSession session) {
		User user = (User)session.getAttribute(Constant.ConstantKey.USER_SESSIO_NKEY);
		if(user!=null){
			return true;
		}else{
			return false;
		}
	}

	
}
