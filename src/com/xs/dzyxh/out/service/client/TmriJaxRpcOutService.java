package com.xs.dzyxh.out.service.client;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.axis2.AxisFault;
import org.apache.http.HttpRequest;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.xs.common.ApplicationException;
import com.xs.dzyxh.entity.system.BaseParams;
import com.xs.dzyxh.entity.system.User;
import com.xs.dzyxh.manager.sys.IBaseParamsManager;

import net.sf.json.JSONObject;

@Service
public class TmriJaxRpcOutService {

	@Resource(name = "baseParamsManager")
	private IBaseParamsManager baseParamsManager;

	@Autowired
	private HttpSession session;
	@Autowired
	private HttpServletRequest request;

	@Value("${stu.properties.jkxlh}")
	private String jkxlh;

	@Value("${stu.properties.xtlb}")
	private String xtlb;

	@Value("${stu.properties.dwmc}")
	private String dwmc;

	@Value("${stu.properties.dwjgdm}")
	private String dwjgdm;

	@Value("${stu.properties.zdbs}")
	private String zdbs;

	public TmriJaxRpcOutNewAccessServiceStub createTmriJaxRpcOutNewAccessServiceStub() throws AxisFault {
		ServletContext servletContext = request.getSession().getServletContext();

		List<BaseParams> bps = (List<BaseParams>) servletContext.getAttribute("bps");
		BaseParams baseParam = null;
		for (BaseParams bp:bps) {
			if ("ptip".equals(bp.getType())) {
				baseParam = bp;
				break;
			}
		}
		if (baseParam != null) {
			TmriJaxRpcOutNewAccessServiceStub.IP = baseParam.getParamValue();
		} else {
			throw new  ApplicationException("从数据字典中获取不到平台IP地址！");
		}
		return new TmriJaxRpcOutNewAccessServiceStub();
	}

	public TmriJaxRpcOutNewAccessServiceStub.QueryObjectOut createQueryObjectOut() {
		String yhbz = "";
		String yhxm = "";
		if (session != null) {
			User user = (User) session.getAttribute("user");
			if (user != null) {
				yhbz = user.getSfzh();
				yhxm = user.getYhxm();
			}
		}
		TmriJaxRpcOutNewAccessServiceStub.QueryObjectOut qo = new TmriJaxRpcOutNewAccessServiceStub.QueryObjectOut();
		qo.setJkxlh(jkxlh);
		qo.setXtlb(xtlb);
		qo.setDwmc(dwmc);
		qo.setDwjgdm(dwjgdm);
		qo.setYhbz(yhbz);
		qo.setYhxm(yhxm);
		qo.setZdbs(zdbs);
		return qo;
	}

	public TmriJaxRpcOutNewAccessServiceStub.WriteObjectOut createWriteObjectOut() {

		String yhbz = "";
		String yhxm = "";
		if (session != null) {
			User user = (User) session.getAttribute("user");
			if (user != null) {
				yhbz = user.getSfzh();
				yhxm = user.getYhxm();
			}
		}
		TmriJaxRpcOutNewAccessServiceStub.WriteObjectOut wo = new TmriJaxRpcOutNewAccessServiceStub.WriteObjectOut();
		wo.setJkxlh(jkxlh);
		wo.setXtlb(xtlb);
		wo.setDwmc(dwmc);
		wo.setDwjgdm(dwjgdm);
		wo.setYhbz(yhbz);
		wo.setYhxm(yhxm);
		wo.setZdbs(zdbs);

		return wo;
	}

	public static String urlDecode(String str) {
		String xml = "";
		try {
			xml = URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}

	public static String urlEncode(String str) {
		String newStr = "";
		try {
			newStr = URLEncoder.encode(str, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return newStr;
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

}