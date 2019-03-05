package com.xs.dzyxh.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

import org.apache.axis2.AxisFault;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.common.ApplicationException;
import com.xs.common.ResultHandler;
import com.xs.dzyxh.out.service.client.TmriJaxRpcOutNewAccessServiceStub;
import com.xs.dzyxh.out.service.client.TmriJaxRpcOutService;

import net.sf.json.JSONObject;
@Controller
@RequestMapping(value = "/imageScan")
public class ImageScanController {
	
	@Autowired
	private TmriJaxRpcOutService tmriJaxRpcOutService;
	
	@RequestMapping(value = "uploadScanImage", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> uploadScanImage(String clsbdh,String picData) throws AxisFault{
		TmriJaxRpcOutNewAccessServiceStub trias = tmriJaxRpcOutService.createTmriJaxRpcOutNewAccessServiceStub();
		TmriJaxRpcOutNewAccessServiceStub.WriteObjectOut wo = tmriJaxRpcOutService.createWriteObjectOut();
		System.out.println(clsbdh);
		System.out.println(picData);
		JSONObject jo = new JSONObject();
		jo.put("xtlb", "01");
		jo.put("ywzj", clsbdh);
		jo.put("ywzldm", "10228");
		
		jo.put("yxhzl", picData);
		

		Document document = DocumentHelper.createDocument(); // 创建文档
		document.setXMLEncoding("UTF-8");
		Element veh = document.addElement("root").addElement("veh");
		veh = JSONConvertXML(veh, jo);
		wo.setUTF8XmlDoc(document.asXML());
		wo.setJkid("25C02");
		try {
			urlDecode(trias.writeObjectOut(wo).getWriteObjectOutReturn());
		} catch (Exception e) {
			throw new ApplicationException("上传扫描文件到综合平台异常", e);
		}
		return ResultHandler.toSuccessJSON("上传成功");
	}
	
	public static Element JSONConvertXML(Element e, JSONObject jo) {

		Set<String> keySet = jo.keySet();
		for (String key : keySet) {
			if (!"".equals(jo.getString(key))) {
				Element node = e.addElement(key.toLowerCase());
				node.setText(urlEncode(jo.getString(key)));
			}
		}
		return e;

	}

	public static String urlDecode(String str) {
		String xml = "";

		try {
			xml = URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			throw new ApplicationException(e);
		}

		return xml;
	}
	
	public static String urlEncode(String str) {
		String newStr = "";

		try {
			newStr = URLEncoder.encode(str, "utf-8");
		} catch (Exception e) {
			throw new ApplicationException(e);
		}

		return newStr;
	}

}
