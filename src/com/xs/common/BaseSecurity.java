package com.xs.common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONObject;

public class BaseSecurity {

	Properties p = null;

	String sign = null;

	String publicKey = null;

	String data = null;

	public BaseSecurity() throws IOException {
		p = new Properties();

		p.load(BaseSecurity.class.getClassLoader().getResourceAsStream("license.properties"));

		sign = p.getProperty("sign");

		publicKey = p.getProperty("publicKey");

		data = p.getProperty("License");
	}

	protected LicenseMessager validateLicens() throws Exception {
		
		LicenseMessager messager=new LicenseMessager();
		
		if (!DSACoder.verify(data.getBytes(), publicKey, sign)) {
			
			messager.setErrorCode(-1);
			
			messager.setMessager("license与密钥不匹配！");
			
			return messager;
		}

		String strLic = DesSecurityUtil.decrypt(data);

		JSONObject licJson = JSONObject.fromObject(strLic);

		String strStartDate = (String) licJson.get("BEGIN");
		String strEndDate = (String) licJson.get("END");
		String licMacs = (String) licJson.get("MAC");
		String licIps = (String) licJson.get("IPAddr");

		if (strStartDate == null || "".equals(strStartDate.trim())){
			throw new Exception("软件有效开始时间为空");
		}
		
		if (strEndDate == null || "".equals(strEndDate.trim())){
			throw new Exception("软件有效结束时间为空");
		}
		
		if (licMacs == null|| "".equals(licMacs.trim()) ){
			throw new Exception("绑定 MAC 地址为空");
		}
		
		if (licIps == null || "".equals(licIps.trim())){
			throw new Exception("IP 地址为空");
		}
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		
		Date startDate = sdf.parse(strStartDate);
		
		Date endDate = sdf.parse(strEndDate);
		
		Date nowDate=new Date();
		
		if(nowDate.getTime()<startDate.getTime()){
			messager.setErrorCode(4001);
			messager.setMessager("系统当前时间小于授权开始时间");
			return messager;
		}
		
		if(nowDate.getTime()>endDate.getTime()){
			messager.setErrorCode(4002);
			messager.setMessager("软件授权已过期");
			return messager;
		}

		List<String> ipAndMacList = ComputerInfoUtil.getIpAndMac();
		
		String[] licIpArry=licIps.split(",");
		String[] licMacArry=licMacs.split(",");
		
		boolean ipFlag=false;
		
		boolean macFlag =false;
		
		
		
		for(String ipAndMac :  ipAndMacList){
			String[] temps = ipAndMac.split(",");
			String localip=temps[0];
			String localmac =temps[1];
			
			for(String licIp:licIpArry){
				if(licIp.trim().equals(localip)){
					ipFlag=true;
					break;
				}
			}
			
			for(String licMac:licMacArry){
				if(licMac.trim().equals(localmac)){
					macFlag=true;
					break;
				}
			}
			if(ipFlag&&macFlag){
				break;
			}
		}
		

		return null;

	}

}
