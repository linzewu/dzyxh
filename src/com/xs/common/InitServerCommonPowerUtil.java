package com.xs.common;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.xs.common.Annotation.Modular;
import com.xs.common.Annotation.UserOperation;
import com.xs.dzyxh.entity.system.PowerPoint;
import com.xs.enums.CommonUserOperationEnum;
//import com.xs.veh.manager.UserManager;

@Component("initServerCommonPowerUtil")
public class InitServerCommonPowerUtil {

	protected static Log log = LogFactory.getLog(InitServerCommonPowerUtil.class);

//	@Resource(name = "userManager")
//	private UserManager userManager;

	public List<PowerPoint> initPower(String[] packs) throws IOException {
		List<PowerPoint> powerPonits = new ArrayList<PowerPoint>();
		
		Map<String,PowerPoint> powerPointMap=new HashMap<String,PowerPoint>();

		Set<Class<?>> classs = new HashSet<Class<?>>();
		for (String pack : packs) {
			classs.addAll(Common.getClasses(pack));
		}
		for (Class<?> c : classs) {
			if (c.isAnnotationPresent(Modular.class)) {
				Modular modular = c.getAnnotation(Modular.class);
				boolean empowered=  modular.isEmpowered();
				Method[] methods = c.getMethods();
				for (Method method : methods) {
					if (method.isAnnotationPresent(UserOperation.class)) {
						UserOperation userOperation = method.getAnnotation(UserOperation.class);
						if(userOperation.userOperationEnum()==CommonUserOperationEnum.Default&&userOperation.isMain()) {
							
							String key =modular.modelCode()+"."+userOperation.code();
							PowerPoint powerPonit = new PowerPoint();
							powerPonit.setModel(modular.modelName());
							powerPonit.setModeCode(modular.modelCode());
							powerPonit.setCode(key);
							powerPonit.setName(userOperation.name());
							powerPonit.setEmpowered(empowered);
							powerPointMap.put(key, powerPonit);
						}
						
					}
				}

			}
		}
		
		for(String key:powerPointMap.keySet()) {
			powerPonits.add(powerPointMap.get(key));
		}

		
		return powerPonits;
	}

}
