package com.xs.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.xs.dzyxh.entity.system.BaseParams;

public class BaseParamsUtil {

	public static List<BaseParams> getBaseParamsByType(String type) {

		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();

		List<BaseParams> bps = (List<BaseParams>) servletContext.getAttribute("bps");

		List<BaseParams> types = new ArrayList<BaseParams>();

		for (BaseParams bp : bps) {

			if (bp.getType().equals(type)) {
				types.add(bp);
			}

		}
		return types;

	}

	public static String getBaseParamNameByType(String type, String val) {

		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();

		List<BaseParams> bps = (List<BaseParams>) servletContext.getAttribute("bps");

		for (BaseParams bp : bps) {
			if (bp.getType().equals(type) && bp.getParamValue().equals(val)) {
				return bp.getParamName();
			}

		}
		return "";

	}

	public static String getBaseParamNameByCode(List<BaseParams> bps, String val) {
		if (bps != null) {
			for (BaseParams bp : bps) {
				if (bp.getParamValue().equals(val)) {
					return bp.getParamName();
				}
			}
		}
		return "";
	}

}
