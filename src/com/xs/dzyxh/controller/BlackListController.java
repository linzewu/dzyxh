package com.xs.dzyxh.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.common.Common;
import com.xs.common.Constant;
import com.xs.common.ResultHandler;
import com.xs.common.Annotation.Modular;
import com.xs.common.Annotation.UserOperation;
import com.xs.dzyxh.entity.system.BlackList;
import com.xs.dzyxh.entity.system.SecurityAuditPolicySetting;
import com.xs.dzyxh.entity.system.SecurityLog;
import com.xs.dzyxh.entity.system.User;
import com.xs.dzyxh.manager.IBlackListManager;
import com.xs.dzyxh.manager.ISecurityLogManager;

@Controller
@RequestMapping(value = "/blackList",produces="application/json")
@Modular(modelCode="blackList",modelName="黑名单管理",isEmpowered=false)
public class BlackListController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Resource(name = "blackListManager")
	private IBlackListManager blackListManager;
	
	@Autowired
	private ISecurityLogManager securityLogManager;
	
	@UserOperation(code="getBlackList",name="查询黑名单")
	@RequestMapping(value = "getBlackList", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> getBlackList(Integer page, Integer rows, BlackList black) {		
		List<BlackList> vcps = blackListManager.getList(page, rows, black);
		
		Integer total = blackListManager.getListCount(page, rows, black);
		
		Map<String,Object> data =new HashMap<String,Object>();
		
		data.put("rows", vcps);
		data.put("total", total);		
		
		return data;
	}
	
	@UserOperation(code="save",name="保存黑名单")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Map saveBlackList(HttpSession session,@Valid BlackList blackList, BindingResult result) {
		if (!result.hasErrors()) {
			User user = (User)session.getAttribute("user");
			blackList.setCreateBy(user.getYhm());
			blackList.setLastUpdateTime(new Date());
			blackList.setFailCount(0);
			blackList.setEnableFlag("Y");
			this.blackListManager.saveBlackList(blackList);
			return  ResultHandler.resultHandle(result,null ,Constant.ConstantMessage.SAVE_SUCCESS);
		}else{
			return ResultHandler.resultHandle(result,null ,null);
		}
	}
	
	@UserOperation(code="delete",name="删除黑名单")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody void delete(BlackList blackList,HttpSession session){
		User user = (User)session.getAttribute("user");
		this.blackListManager.deleteBlackList(blackList);
		//写入安全日志
		SecurityLog securityLog = new SecurityLog();
		securityLog.setCreateUser(User.SYSTEM_USER);
		securityLog.setUpdateUser(User.SYSTEM_USER);
		securityLog.setClbm(SecurityAuditPolicySetting.IP_LOCK);
		securityLog.setIpAddr(Common.getIpAdrress(request));
		securityLog.setContent("用户："+user.getYhm()+" 删除黑名单管理IP："+blackList.getIp());
		securityLogManager.saveSecurityLog(securityLog);
	}

}
