package com.xs.common;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xs.common.Annotation.FunctionAnnotation;
import com.xs.common.Annotation.ModuleAnnotation;
import com.xs.dzyxh.entity.Department;
import com.xs.dzyxh.entity.Power;
import com.xs.dzyxh.manager.IDepartmentManager;

@Component("initServerCommonUtil")
public class InitServerCommonUtil {
	
	@Resource(name = "departmentManager")
	private IDepartmentManager departmentManager;
	
	@Value("${dept.root.bmdm}")
	private String bmdm;
	
	@Value("${dept.root.bmmc}")
	private String bmmc;

	public  List<Power> initPower(String[] packs) throws IOException {

		List<Power> powers = new ArrayList<Power>();
		//用来去重复的，临时map
		Map map = new HashMap();
		int index = 0;
		Set<Class<?>> classs =new HashSet<Class<?>>();
		for(String pack:packs){
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

					Method[] methods = c.getMethods();

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
										myPower.setKey(myPower.getKey() + "," + power.getKey());
									}
								}
							}
						}
					}
				}
			}
		}
		return powers;
	}
	
	public void initRootDepartment(){
		
		List<Department> depts = this.departmentManager.getDepts();
		
		if(depts==null||depts.isEmpty()){
			Department rootDept=new Department();
			
			rootDept.setBmjb(0);
			rootDept.setBmdm(bmdm);
			rootDept.setBmmc(bmmc);
			rootDept.setBmqc(bmmc);
			
			departmentManager.saveDept(rootDept);
		}
		
		
	}

}
