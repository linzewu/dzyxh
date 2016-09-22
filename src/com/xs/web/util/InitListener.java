package com.xs.web.util;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xs.common.DSACoder;
import com.xs.common.InitServerCommonUtil;
import com.xs.dzyxh.entity.BaseParams;
import com.xs.dzyxh.entity.Power;
import com.xs.dzyxh.manager.IBaseParamsManager;
import com.xs.dzyxh.manager.IDepartmentManager;

/**
 * Application Lifecycle Listener implementation class InitListener
 * 
 */
public class InitListener implements ServletContextListener {

	protected static Log log = LogFactory.getLog(InitListener.class);

	private WebApplicationContext wac;

	private ServletContext servletContext;
	
	private InitServerCommonUtil initServerCommonUtil;
	
	/**
	 * Default constructor.
	 */
	public InitListener() {
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent contextEvent) {

		try {
			servletContext = contextEvent.getServletContext();

			Properties p = new Properties();

			p.load(InitListener.class.getClassLoader().getResourceAsStream("license.properties"));

			String sign = p.getProperty("sign");

			String publicKey = p.getProperty("publicKey");

			String data = p.getProperty("License");

			if (DSACoder.verify(data.getBytes(), publicKey, sign)) {

			} else {
				throw new Exception("license 文件非法");
			}

			wac = WebApplicationContextUtils.getWebApplicationContext(contextEvent.getServletContext());

			// 加载参数表
			IBaseParamsManager baseParamsManager = (IBaseParamsManager) wac.getBean("baseParamsManager");
			List<BaseParams> bps = baseParamsManager.getBaseParams();
			servletContext.setAttribute("bps", bps);
			
			InitServerCommonUtil initServerCommonUtil= (InitServerCommonUtil) wac.getBean("initServerCommonUtil");

			// 加功能列表
			List<Power> powers = initServerCommonUtil.initPower(new String[] { "com.xs.dzyxh.controller" });
			servletContext.setAttribute("powers", powers);
			
			//初始化部门
			initServerCommonUtil.initRootDepartment();
			
			initServerCommonUtil.initAdminRole();
			
			initServerCommonUtil.initAdmin();
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent contextEvent) {
		
	}

}
