package com.xs.dzyxh.controller;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.axiom.om.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.common.Constant;
import com.xs.common.ResultHandler;
import com.xs.common.Sql2WordUtil;
import com.xs.dzyxh.entity.system.BaseParams;
import com.xs.dzyxh.manager.IBaseParamsManager;

@Controller
@RequestMapping(value = "/photoCompose")
public class PhotoComposeController {
	
	@Autowired
	private ServletContext servletContext;
	
	@Resource(name = "baseParamsManager")
	private IBaseParamsManager baseParamsManager;
	
	@RequestMapping(value = "toHctp", method = RequestMethod.POST)
	public String toHctp(@RequestParam Map param) {
		System.out.println(param);
		return "qmtphc";
	}
	
	@RequestMapping(value = "composeImage", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> composeImage(@RequestParam Map param) throws Exception{
		Map<String, List<BaseParams>> bps = (Map<String, List<BaseParams>>) servletContext.getAttribute("bpsMap");
		if(bps == null) {
			bps = convertBaseParam2Map();
		}
		byte[] zp = Base64.decode(param.get("base64Img").toString());
		param.put("qmzp",new ByteArrayInputStream(zp));
		String templateName = "注册申请表";
		String template = templateName+".doc";
		com.aspose.words.Document doc = Sql2WordUtil.map2WordUtil(template, param,bps);
		String fileName = templateName+"_"+param.get("clsbdh")+".jpg";
		Sql2WordUtil.toCase(doc, "", fileName);
		
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "生成图片成功！", fileName);	
	}
	
	public Map<String, List<BaseParams>> convertBaseParam2Map() {

		Map<String, List<BaseParams>> mapParam = new HashMap<String, List<BaseParams>>();

		List<BaseParams> bps = (List<BaseParams>) servletContext.getAttribute("bps");
		if (bps == null) {
			bps = baseParamsManager.getBaseParams();
			servletContext.setAttribute("bps",bps );
		}

		for (BaseParams param : bps) {
			String key = param.getType();
			List<BaseParams> typeList = mapParam.get(key);
			if (typeList == null) {
				typeList = new ArrayList<BaseParams>();
				typeList.add(param);
				mapParam.put(key, typeList);
			} else {
				typeList.add(param);
			}
		}
		servletContext.setAttribute("bpsMap", mapParam);
		return mapParam;
	}

}
