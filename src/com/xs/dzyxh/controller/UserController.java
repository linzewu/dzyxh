package com.xs.dzyxh.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;


import com.xs.common.Common;
import com.xs.common.Constant;
import com.xs.common.ResultHandler;
import com.xs.common.Annotation.FunctionAnnotation;
import com.xs.common.Annotation.ModuleAnnotation;
import com.xs.common.Annotation.UserOperation;
import com.xs.dzyxh.entity.system.BlackList;
import com.xs.dzyxh.entity.system.CoreFunction;
import com.xs.dzyxh.entity.system.Role;
import com.xs.dzyxh.entity.system.SecurityAuditPolicySetting;
import com.xs.dzyxh.entity.system.SecurityLog;
import com.xs.dzyxh.entity.system.User;
import com.xs.dzyxh.manager.IBlackListManager;
import com.xs.dzyxh.manager.ICoreFunctionManager;
import com.xs.dzyxh.manager.IRoleManager;
import com.xs.dzyxh.manager.ISecurityAuditPolicySettingManager;
import com.xs.dzyxh.manager.ISecurityLogManager;
import com.xs.dzyxh.manager.IUserManager;
import com.xs.enums.CommonUserOperationEnum;

@Controller
@RequestMapping(value = "/user")
@ModuleAnnotation(modeName = Constant.ConstantDZYXH.MODE_NAME_SYSTEM, appName = Constant.ConstantDZYXH.APP_NAME_USER,href="/dzyxh/page/system/UserManager.html",icoUrl="/dzyxh/images/user.png",modeIndex=4,appIndex=1)
public class UserController {

	@Resource(name = "userManager")
	private IUserManager userManager;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private IBlackListManager blackListManager;
	
	@Autowired
	private ISecurityLogManager securityLogManager;
	
	@Resource(name = "coreFunctionManager")
	private ICoreFunctionManager coreFunctionManager;
	
	@Resource(name = "securityAuditPolicySettingManager")
	private ISecurityAuditPolicySettingManager securityAuditPolicySettingManager;
	
	@Resource(name = "roleManager")
	private IRoleManager roleManager;

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
			if(!"Y".equals(String.valueOf(user.getIsPolice())) && checkIsPolice(user)) {
				return ResultHandler.toMyJSON(Constant.ConstantState.STATE_VALIDATE_ERROR, "该角色包含警员功能，不允许授予非警员账号！");
			}

			if (user.getId() == null) {
				user = userManager.saveUser(user);
				user.setMm(user.encodePwd(Constant.initPassword));
				//user.setMm("888888");
				//user.setZt(User.ZT_CZMM);
			} else {
				User oldUser = userManager.getUser(user.getId());
				user.setMm(oldUser.getMm());
				user.setYhm(oldUser.getYhm());
				user.setSfzh(oldUser.getSfzh());
				user.setZjdlsj(oldUser.getZjdlsj());
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
		oldUser.setMm(oldUser.encodePwd("888888"));
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
		HttpSession session = request.getSession();
		RequestContext requestContext = new RequestContext(request);
		if(blackListManager.checkIpIsBan(Common.getIpAdrress(request))) {
			Map data=ResultHandler.toMyJSON(0, "当前IP已被列入黑名单，请联系管理员！");
			return data;
		}
		User user = userManager.login(userName);
		
		if (user != null) {

			if (user.getZt() == User.ZT_TY) {
				Map data = ResultHandler.toMyJSON(Constant.ConstantState.STATE_ERROR, "该用户已停用", null);
				return data;
			}
			//校验用户是否被锁定
			if(user.getZt() == 1) {
				SecurityAuditPolicySetting set = securityAuditPolicySettingManager.getPolicyByCode(SecurityAuditPolicySetting.ACCOUNT_LOCK);
				String msg = "登录失败，当前用户登录失败次数超过"+set.getClz()+"次已被锁定，请联系管理员解锁！";
				Map data = ResultHandler.toMyJSON(Constant.ConstantState.STATE_ERROR, msg, null);
				return data;
			}
			
			if(!checkIp(user,request)) {
				Map data = ResultHandler.toMyJSON(Constant.ConstantState.STATE_ERROR, "登录IP地址不合法！", null);
				return data;
			}
			Date nowDate = new Date();
			
			if(nowDate.after(user.getZhyxq())) {
				Map data = ResultHandler.toMyJSON(Constant.ConstantState.STATE_ERROR, "用户账号已过期！", null);		
				user.setMmyxq(user.getZhyxq());
				this.userManager.saveUser(user);
				return data;
			}
			//校验时间是否在允许的时间段内
			if(!checkLognTime(user)) {
				Map data = ResultHandler.toMyJSON(Constant.ConstantState.STATE_ERROR, "不在允许的时间段内登录！", null);
				return data;
			}
			String encodePwd =user.encodePwd(password);
			if(!user.getMm().equals(encodePwd)) {				
				failLoginCount(user,request);
				//失败加入黑名单
				int sycou= addBlackList(request);
				String msg = "用户名密码错误！当前Ip还有"+sycou+"次机会就会被锁定.";
				Map data = ResultHandler.toMyJSON(Constant.ConstantState.STATE_ERROR, msg, null);
				
				return data;
			}
			
			
				//判断密码是否过期
			if (user.getMmyxq().before(nowDate)) {
				user.setPwOverdue("Y");
			}
			//上次登录的时间跟ip记录
			user.setLastTimeLoginDate(user.getZjdlsj());
			user.setLastTimeIP(user.getIp());
			//修改最后登录时间
			user.setZjdlsj(new Date());
			user.setIp(Common.getIpAdrress(request));
			user.setLoginFailCou(0);
			this.userManager.saveUser(user);
			System.out.println(user.getZjdlsj());
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
	
	@UserOperation(code="updatePassword",name="校验密码",userOperationEnum=CommonUserOperationEnum.AllLoginUser,isMain=false)
	@RequestMapping(value = "validatePassworrd")
	public @ResponseBody boolean validatePassworrd(HttpSession session,String oldPassword) {
		User user = (User)session.getAttribute("user");
		User querUser = userManager.getUser(user.getId());
		if(querUser.getMm().equals(user.encodePwd(oldPassword))){
			return true;
		}else{
			return false;
		}
	}
	
	@UserOperation(code="updatePassword",name="修改用户密码",userOperationEnum=CommonUserOperationEnum.AllLoginUser)
	@RequestMapping(value = "updatePassword", method = RequestMethod.POST)
	public @ResponseBody Map updatePassword(HttpSession session,String newPassword) {
		User sessionUser = (User)session.getAttribute("user");
		User user = this.userManager.getUser(sessionUser.getId());
		
		user.setMm(user.encodePwd(newPassword));
		user.setZt(0);
		//修改密码，密码有效期延长3个月
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(user.getMmyxq());
		calendar.add(Calendar.MONTH, 3);
		user.setMmyxq(calendar.getTime());
		this.userManager.saveUser(user);
		session.invalidate();
		return ResultHandler.toSuccessJSON("密碼修改成功！");
	}
	
	private User failLoginCount(User u,HttpServletRequest request) {
		int cou = u.getLoginFailCou() == null? 0:u.getLoginFailCou();
		cou++;		
		u.setLoginFailCou(cou);
		SecurityAuditPolicySetting set = securityAuditPolicySettingManager.getPolicyByCode(SecurityAuditPolicySetting.ACCOUNT_LOCK);
		int clz = set.getClz() == null?0:Integer.parseInt(set.getClz());
		if(cou == clz) {
			//锁定
			u.setZt(1);
			u.setLoginFailCou(0);
		}
		//登录失败时间/ip
		u.setLastTimeLoginFailDate(new Date());
		String ip = Common.getIpAdrress(request);
		u.setLastTimeLoginFailIP(ip);
		
		this.userManager.saveUser(u);
		if (u.getZt() == 1) {
			//写入安全日志
			SecurityLog securityLog = new SecurityLog();
			securityLog.setCreateUser(User.SYSTEM_USER);
			securityLog.setUpdateUser(User.SYSTEM_USER);
			securityLog.setUserName(u.getYhm());
			securityLog.setClbm(SecurityAuditPolicySetting.ACCOUNT_LOCK);
			securityLog.setIpAddr(ip);
			securityLog.setContent("用户:"+u.getYhm()+"违反账户锁定安全审计策略设置，用户锁定");
			securityLogManager.saveSecurityLog(securityLog);
		}
		return u;
	}
	
	private int addBlackList(HttpServletRequest request) {
		int sycou = 0;
		String ip = Common.getIpAdrress(request);
		SecurityAuditPolicySetting set = securityAuditPolicySettingManager.getPolicyByCode(SecurityAuditPolicySetting.IP_LOCK);
		int clz = set.getClz() == null?0:Integer.parseInt(set.getClz());
		BlackList black = blackListManager.getBlackListByIp(ip);
		if (black == null) {
			BlackList newBlack = new BlackList();
			newBlack.setCreateBy(User.SYSTEM_USER);
			newBlack.setEnableFlag("N");
			newBlack.setFailCount(1);
			newBlack.setIp(ip);
			newBlack.setLastUpdateTime(new Date());
			blackListManager.saveBlackList(newBlack);
			sycou = clz-newBlack.getFailCount();
		}else {
			black.setFailCount(black.getFailCount()+1);
			black.setLastUpdateTime(new Date());
			if(black.getFailCount()>=clz) {
				black.setEnableFlag("Y");
			}
			blackListManager.saveBlackList(black);
			sycou = clz-black.getFailCount();
			if("Y".equals(black.getEnableFlag())) {
				//写入安全日志
				SecurityLog securityLog = new SecurityLog();
				securityLog.setCreateUser(User.SYSTEM_USER);
				securityLog.setUpdateUser(User.SYSTEM_USER);
				securityLog.setClbm(SecurityAuditPolicySetting.IP_LOCK);
				securityLog.setIpAddr(ip);
				securityLog.setContent("IP终端:"+ip+"违反IP终端锁定(黑名单)安全审计策略设置，加入黑名单");
				securityLogManager.saveSecurityLog(securityLog);
			}
		}
		return sycou;
	}
	
	/**
	 * 验证IP是否属于某个IP段
	 * @param ip 所验证的IP号码
	 * @param beginIP IP段 开始
	 * @param endIP IP段 结束
	 * @return
	 */
	public boolean ipExistsInRange(String ip, String beginIP,String endIP) {

		ip = ip.trim();

		return getIp2long(beginIP) <= getIp2long(ip) && getIp2long(ip) <= getIp2long(endIP);

	}

	public long getIp2long(String ip) {

		ip = ip.trim();

		String[] ips = ip.split("\\.");

		long ip2long = 0L;

		for (int i = 0; i < 4; ++i) {

			ip2long = ip2long << 8 | Integer.parseInt(ips[i]);

		}

		return ip2long;

	}

	public long getIp2long2(String ip) {

		ip = ip.trim();

		String[] ips = ip.split("\\.");

		long ip1 = Integer.parseInt(ips[0]);

		long ip2 = Integer.parseInt(ips[1]);

		long ip3 = Integer.parseInt(ips[2]);

		long ip4 = Integer.parseInt(ips[3]);

		long ip2long = 1L * ip1 * 256 * 256 * 256 + ip2 * 256 * 256 + ip3 * 256 + ip4;

		return ip2long;

	}

	/**
	 * 校验ip是否是用户设置的允许登录ip
	 * @param user
	 * @return
	 */
	private boolean checkIp(User user,HttpServletRequest request) {
		//固定ip
		String ips =user.getGdip();
		//IP起始地址
		String beginIp = user.getIpqsdz();
		//IP结束地址
		String endIp = user.getIpjsdz();
		//当前ip
		String removeIp=Common.getIpAdrress(request);
		if(StringUtils.isNotEmpty(beginIp) && StringUtils.isNotEmpty(endIp)) {
			return this.ipExistsInRange(removeIp, beginIp, endIp);
		}else {
			if(!StringUtils.isEmpty(ips)) {
				
				for(String ip :ips.split(",")) {
					if(ip.equals(removeIp)) {
						return true;
					}
				}
				
			}else {
				return true;
			}
		}		
		
		return false;
	}
	
	/**
	 * 校验时间是否在允许的时间段内
	 * @param user
	 * @return
	 */
	private boolean checkLognTime(User user) {
		String beginTimeStr = user.getPermitBeginTime();
		String endTimeStr = user.getPermitEndTime();
		
		beginTimeStr=beginTimeStr==null?"00:00":beginTimeStr;
		endTimeStr=endTimeStr==null?"24:00":endTimeStr;
		
		String[] arrBegin=beginTimeStr.split(":");
		String[] arrEnd=endTimeStr.split(":");
		
		int  beginTime= Integer.parseInt(arrBegin[0])*60+Integer.parseInt(arrBegin[1]);
		int  endTime= Integer.parseInt(arrEnd[0])*60+Integer.parseInt(arrEnd[1]);
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int countMinute=hour*60+minute;
		if(countMinute<beginTime||countMinute>endTime) {
			return false;
		}else {
			return true;
		}
		
	}
	
	private boolean checkIsPolice(User user) {
		boolean flag = false;
		List<CoreFunction> coreList = this.coreFunctionManager.getAllCoreFunction(2);
		Role role = roleManager.getRoleById(Integer.parseInt(user.getJs())); 
		for(CoreFunction fun:coreList) {
			if(role.getJsqx().indexOf(fun.getFunctionPoint()) != -1) {
				flag = true;
				return flag;
			}
		}
		
		return flag;
	}
	
	@UserOperation(code="saveUser",name="校验身份证",isMain=false)
	@RequestMapping(value = "validateIdCard")
	public @ResponseBody boolean validateIdCard(User user) {
		User querUser = this.userManager.queryUser(user);
		if(querUser==null){
			return true;
		}else{
			return false;
		}

	}
	
}
