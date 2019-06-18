package com.xs.dzyxh.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.imageio.stream.FileImageInputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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

@Controller
@RequestMapping(value = "/photoCompose")
public class PhotoComposeController {
	
	@Autowired
	private ServletContext servletContext;
	
//	@Resource(name = "baseParamsManager")
//	private IBaseParamsManager baseParamsManager;
	@Autowired
	private HttpServletRequest request;
	
	@RequestMapping(value = "toHctp", method = RequestMethod.POST)
	public String toHctp(@RequestParam Map param) throws Exception {
		Iterator it = param.keySet().iterator();
		while(it.hasNext()){
		    String paramName = (String) it.next();
		    String paramValue = URLDecoder.decode(param.get(paramName).toString(),"UTF-8");
		    param.put(paramName, paramValue);
		}
		System.out.println(param);
		
		String templateName = param.get("tempName").toString();//"注册申请表";
		String template = templateName+".doc";
		com.aspose.words.Document doc = Sql2WordUtil.map2WordUtil2(template, param);
		String fileName = UUID.randomUUID().toString()+".jpg";// templateName+"_"+param.get("clsbdh")+".jpg";
		Sql2WordUtil.toCase(doc, "", fileName);
		//param.put("bgdImage", Base64.encode(image2byte(Sql2WordUtil.getCacheDir()+fileName)));
		request.setAttribute("bgdImage", Sql2WordUtil.getCacheDir2()+fileName);
		return "qmtphc";
	}
	
	@RequestMapping(value = "composeImage", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> composeImage(@RequestParam Map param) throws Exception{
//		Map<String, List<BaseParams>> bps = (Map<String, List<BaseParams>>) servletContext.getAttribute("bpsMap");
//		if(bps == null) {
//			bps = convertBaseParam2Map();
//		}
		Iterator it = param.keySet().iterator();
		while(it.hasNext()){
		    String paramName = (String) it.next();
		    String paramValue = URLDecoder.decode(param.get(paramName).toString());
		    param.put(paramName, paramValue);
		}
		System.out.println(param);
		
		byte[] zp = Base64.decode(param.get("base64Img").toString());
		param.put("qmzp",new ByteArrayInputStream(zp));
		String templateName = param.get("mbmc").toString();//"注册申请表";
		String template = templateName+".doc";
		com.aspose.words.Document doc = Sql2WordUtil.map2WordUtil2(template, param);
		String fileName = UUID.randomUUID().toString()+".jpg";
		Sql2WordUtil.toCase(doc, "", fileName);
		System.out.println("(((((((((((((((((((((((((((("+Sql2WordUtil.getCacheDir2()+fileName);
		byte[] imgByte = image2byte(Sql2WordUtil.getCacheDir2()+fileName);
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "生成图片成功！", Sql2WordUtil.getCacheDir2()+fileName);	
	}
	
	//图片到byte数组
	  public byte[] image2byte(String path)throws Exception{
	    byte[] data = null;
	    FileImageInputStream input = null;
	    try {
	      input = new FileImageInputStream(new File(path));
	      ByteArrayOutputStream output = new ByteArrayOutputStream();
	      byte[] buf = new byte[1024];
	      int numBytesRead = 0;
	      while ((numBytesRead = input.read(buf)) != -1) {
	      output.write(buf, 0, numBytesRead);
	      }
	      data = output.toByteArray();
	      output.close();
	      input.close();
	    }
	    catch (Exception ex1) {
	      throw ex1;
	    }
	    return data;
	  }
	
//	public Map<String, List<BaseParams>> convertBaseParam2Map() {
//
//		Map<String, List<BaseParams>> mapParam = new HashMap<String, List<BaseParams>>();
//
//		List<BaseParams> bps = (List<BaseParams>) servletContext.getAttribute("bps");
//		if (bps == null) {
//			bps = baseParamsManager.getBaseParams();
//			servletContext.setAttribute("bps",bps );
//		}
//
//		for (BaseParams param : bps) {
//			String key = param.getType();
//			List<BaseParams> typeList = mapParam.get(key);
//			if (typeList == null) {
//				typeList = new ArrayList<BaseParams>();
//				typeList.add(param);
//				mapParam.put(key, typeList);
//			} else {
//				typeList.add(param);
//			}
//		}
//		servletContext.setAttribute("bpsMap", mapParam);
//		return mapParam;
//	}

}
