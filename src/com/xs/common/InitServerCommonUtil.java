package com.xs.common;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xs.common.Annotation.FunctionAnnotation;
import com.xs.common.Annotation.ModuleAnnotation;
import com.xs.dzyxh.entity.system.Department;
import com.xs.dzyxh.entity.system.ModulePower;
import com.xs.dzyxh.entity.system.Power;
import com.xs.dzyxh.entity.system.Role;
import com.xs.dzyxh.entity.system.User;
import com.xs.dzyxh.manager.IDepartmentManager;
import com.xs.dzyxh.manager.IRoleManager;
import com.xs.dzyxh.manager.IUserManager;

@Component("initServerCommonUtil")
public class InitServerCommonUtil {
	
	protected static Log log = LogFactory.getLog(InitServerCommonUtil.class);

	@Resource(name = "departmentManager")
	private IDepartmentManager departmentManager;

	@Value("${dept.root.bmdm}")
	private String bmdm;

	@Value("${dept.root.bmmc}")
	private String bmmc;

	@Resource(name = "roleManager")
	private IRoleManager roleManager;

	@Resource(name = "userManager")
	private IUserManager userManager;

	public List<Power> getAllPowers(List<ModulePower> modules){
		List<Power> powers=new ArrayList<Power>();
		for(ModulePower m:modules){
			powers.addAll(m.getPowers());
		}
		return powers;
	}
	public List<ModulePower> initPower(String[] packs) throws IOException {
		List<ModulePower> modules=new ArrayList<ModulePower>();
		

		Set<Class<?>> classs = new HashSet<Class<?>>();
		for (String pack : packs) {
			classs.addAll(Common.getClasses(pack));
		}

		for (Class<?> c : classs) {
			Annotation[] as = c.getAnnotations();
			for (Annotation a : as) {
				if (a.annotationType() == ModuleAnnotation.class) {

					RequestMapping rm = (RequestMapping) c.getAnnotation(RequestMapping.class);

					ModuleAnnotation ma = (ModuleAnnotation) a;

					String path = rm.value()[0];

					String appName = ma.appName();

					String moduleName = ma.modeName();

					String icoUrl=ma.icoUrl();
					
					int appIndex=ma.appIndex();
					
					int moduIndex=ma.modeIndex();
					ModulePower module=new ModulePower();
				
					module.setApp(appName);
					module.setAppIndex(appIndex);
					module.setModule(moduleName);
					module.setModuIndex(moduIndex);
					module.setIcoUrl(icoUrl);
					module.setHref(ma.href());
					Method[] methods = c.getMethods();
					List<Power> powers = module.getPowers();
					// 用来去重复的，临时map
					Map map = new HashMap();
					modules.add(module);
					int index = 0;
					for (Method method : methods) {

						Annotation[] methodAnnotations = method.getAnnotations();
						
						for (Annotation methodAnnotation : methodAnnotations) {
							if (methodAnnotation.annotationType() == FunctionAnnotation.class) {

								FunctionAnnotation fa = (FunctionAnnotation) methodAnnotation;

								RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
								String functionKey = requestMapping.value()[0];
								String[] functionName = fa.name();
								String[] buttonName = fa.buttonName();
								
								for (String fn : functionName) {
									Power power = new Power();
									power.setApp(appName);
									power.setModule(moduleName);
									power.setFunction(fn);
									power.setButtonName(buttonName);
									power.setKey(path + "/" + functionKey);

									if (!powers.contains(power)) {
										powers.add(power);
										map.put(power.getModule() + power.getApp() + power.getFunction(), index);
										index++;
									} else {
										Integer myIndex = (Integer) map
												.get(power.getModule() + power.getApp() + power.getFunction());
										Power myPower = powers.get(myIndex);
										myPower.setKey(myPower.getKey() + ";" + power.getKey());
									}
								}
							}
						}
					}
				}
			}
		}
		return modules;
	}

	public void initRootDepartment() {
		List<Department> depts = this.departmentManager.getDepts();
		if (depts == null || depts.isEmpty()) {
			Department rootDept = new Department();
			rootDept.setBmjb(0);
			rootDept.setBmdm(bmdm);
			rootDept.setBmmc(bmmc);
			rootDept.setBmqc(bmmc);
			departmentManager.saveDept(rootDept);
		}
	}

	public void initAdminRole() {

		int count = roleManager.getRoleCount(null);
		if (count == 0) {
			Role role = new Role();
			role.setJsjb(0);
			role.setJslx(0);
			role.setJsmc("系统超级管理员");
			this.roleManager.add(role);
		}
	}

	public void initAdmin() {
		
		log.info("初始化用户");
		int count = userManager.getUserCount(null);
		log.info("用户count："+count);
		if (count == 0) {
			Role role = roleManager.getSystemRole();
			User user = new User();
			user.setBmdm(bmdm);
			user.setJs(role.getId().toString());			
			
			user.setYhm("admin");
			user.setSeq(1);
			user.setSfzh("000000000000000000");
			user.setZt(User.ZT_CZMM);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, 3);
			user.setZhyxq(calendar.getTime());
			user.setMmyxq(user.getZhyxq());
			user.setYhxm("系统超级管理员");
			user = this.userManager.saveUser(user);
			user.setMm(user.encodePwd("888888"));
			this.userManager.saveUser(user);
		}
	}

}
