package com.xs.dzyxh.manager.window.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspose.words.License;
import com.xs.common.BaseParamsUtil;
import com.xs.common.FormatUtil;
import com.xs.dzyxh.entity.aspose.ImageData;
import com.xs.dzyxh.entity.driimg.DrivingPhoto;
import com.xs.dzyxh.entity.driver.DrivingApply;
import com.xs.dzyxh.entity.driver.DrivingBase;
import com.xs.dzyxh.entity.driver.DrivingExamination;

public class DrivingApplyUitl {
	
	public static InputStream is;
	public static InputStream license ;
	static {
		
		license = DrivingApplyUitl.class.getClassLoader().getResourceAsStream("\\asposelicense.xml"); // license路径	
		//is = DrivingApplyUitl.class.getClassLoader().getResourceAsStream("\\jdcjszsqb-2016.doc"); // 原始word路径
		License aposeLic = new License();
		try {
			aposeLic.setLicense(DrivingApplyUitl.license);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String dateFormat(Date data) {
		if (data == null) {
			return "  年      月      日";
		}
		return FormatUtil.date.format(data);
	}

	public static String dg(Object value, String code) {
		if (value != null && value.toString().equalsIgnoreCase(code)) {
			return "☑";
		} else {
			return "□";
		}
	}

	public static List<ImageData> convertSqbImgData(Collection<DrivingPhoto> photos) {
		List<ImageData> imgs = new ArrayList<ImageData>();
		if (photos != null) {
			for (DrivingPhoto p : photos) {
				String zl = p.getZpzl();
				ImageData img = null;
				if ("04".equals(zl)) {
					img = new ImageData("txtp", new ByteArrayInputStream(p.getZp()), 152, 196,1);
				} else if ("05".equals(zl)) {
					img = new ImageData("sqrqz", new ByteArrayInputStream(p.getZp()), 110, 26,0);
				} else if ("06".equals(zl)) {
					img = new ImageData("dlrqz", new ByteArrayInputStream(p.getZp()), 110, 26,0);
				} else if ("07".equals(zl)) {
					img = new ImageData("zw1", new ByteArrayInputStream(p.getZp()), 80, 95,1);
				} else if ("08".equals(zl)) {
					img = new ImageData("zw2", new ByteArrayInputStream(p.getZp()), 80, 95,1);
				} else if ("09".equals(zl)) {
					img = new ImageData("jbrqz", new ByteArrayInputStream(p.getZp()), 110, 24,0);
				}else{
					continue;
				}
				imgs.add(img);
				// ImageData img=new ImageData();
			}
		}

		return imgs;
	}
	
	public static List<ImageData> convertTjbImgData(Collection<DrivingPhoto> photos) {
		List<ImageData> imgs = new ArrayList<ImageData>();
		if (photos != null) {
			for (DrivingPhoto p : photos) {
				String zl = p.getZpzl();
				ImageData img = null;
				if ("04".equals(zl)) {
					img = new ImageData("txtp", new ByteArrayInputStream(p.getZp()), 152, 196,1);
				} else if ("10".equals(zl)) {
					img = new ImageData("tjsqrqz", new ByteArrayInputStream(p.getZp()), 110, 26,1);
				} else if ("11".equals(zl)) {
					img = new ImageData("tjysqz", new ByteArrayInputStream(p.getZp()), 110, 26,1);
				} else if ("12".equals(zl)) {
					img = new ImageData("tjdlrqz", new ByteArrayInputStream(p.getZp()), 110, 26,1);
				} else if ("13".equals(zl)) {
					img = new ImageData("yyyz", new ByteArrayInputStream(p.getZp()), 120, 120,1);
				} else{
					continue;
				}
				imgs.add(img);
				// ImageData img=new ImageData();
			}
		}

		return imgs;
	}
	
	public static Map<String, String> convertTjbData( DrivingExamination examination) {
		if ( examination == null) {
			return null;
		}
		Map<String, String> datas = new HashMap<String, String>();
		datas.put("xm", formatString(examination.getXm()));// 姓名
		datas.put("xb", BaseParamsUtil.getBaseParamNameByType("xb", examination.getXb()));// 性别
		datas.put("csrq", dateFormat(examination.getCsrq()));// 出生日期
		datas.put("gj", BaseParamsUtil.getBaseParamNameByType("gj", examination.getGj()));// 国籍
		datas.put("lxdh", formatString(examination.getLxdh()));// 联系电话
		// datas.put("lsh", apply.getLsh());
		datas.put("sfzmhm", examination.getSfzmhm());// 身份证
		// datas.put("qh", apply.getId().getQh());
		// datas.put("jxdm", apply.getId().getJxdm());
		//datas.put("zzzm", formatString(examination.getZzzm()));
		datas.put("dabh", formatString(examination.getDabh()));
		

		// datas.put("ywlx",formatString(apply.getYwlx()));//业务类型
		datas.put("sqzjcxdh",  formatString(examination.getSqzjcxdh()));//申请/已具有的准驾车型代号
		datas.put("sg", formatString(examination.getSg()));// 身高

		
		datas.put("zsl", formatString(examination.getZsl()));// 左视力
		datas.put("ysl", formatString(examination.getYsl()));// 右视力	
		
		datas.put("dyslzes", dg(examination.getDyslze(),"1"));// 单眼视力障碍
		datas.put("dyslzef", dg(examination.getDyslze(),"0"));// 无单眼视力障碍
		datas.put("yyspsy", formatString(examination.getYyspsy()));// 优眼视野水平
		datas.put("zysjz", dg(examination.getZysfjz(),"1"));// 左眼是矫正
		datas.put("zyfjz", dg(examination.getZysfjz(),"0"));// 左眼否矫正
		
		datas.put("yysjz", dg(examination.getYysfjz(),"1"));// 右眼是矫正
		datas.put("yyfjz", dg(examination.getYysfjz(),"0"));// 右眼否矫正
		
		datas.put("ztl", formatString(examination.getZetl()).equals("1")?"合格":"不合格");// 左听力
		datas.put("ytl", formatString(examination.getYetl()).equals("1")?"合格":"不合格");// 右听力
		datas.put("zsz", formatString(examination.getZsz()).equals("1")?"合格":"不合格");// 左上肢
		datas.put("ysz", formatString(examination.getYsz()).equals("1")?"合格":"不合格");// 右上肢
		datas.put("zxz", formatString(examination.getZxz()).equals("1")?"合格":"不合格");// 左下肢
		datas.put("yxz", formatString(examination.getYxz()).equals("1")?"合格":"不合格");// 右下肢
		
		datas.put("bsly", dg(examination.getBsl(),"1"));// 辨视力-色盲
		datas.put("bslw", dg(examination.getBsl(),"0"));// 辨视力-无色盲
		datas.put("qgjby", dg(examination.getQgjb(),"1"));// 躯干颈部-有
		datas.put("qgjbw", dg(examination.getQgjb(),"0"));// 躯干颈部-无
		datas.put("tlsjz", dg(examination.getTlsfjz(),"1"));//听力是校正
		datas.put("tlfjz", dg(examination.getTlsfjz(),"0"));//听力否校正
		
		datas.put("tjrq", dateFormat(examination.getTjrq()));// 体检日期
		datas.put("tjyymc", formatString(examination.getTjyymc()));//体检医院名称
		
		datas.put("yjdz", formatString(examination.getYjdz()));//邮寄地址
		datas.put("yjdh", formatString(examination.getYjdh()));//邮寄电话
		
		datas.put("jyjb", dg(examination.getSfjyjb(),"1"));//具有疾病
		datas.put("bjyjb", dg(examination.getSfjyjb(),"0"));//不具有疾病
		
		datas.put("jyjbqkxzb", dg(examination.getJyjbqk(),"1"));//器质性心脏病
		datas.put("jyjbqkdx", dg(examination.getJyjbqk(),"2"));//癫痫
		datas.put("jyjbqkmnes", dg(examination.getJyjbqk(),"3"));//美尼尔氏综合症
		datas.put("jyjbqkxy", dg(examination.getJyjbqk(),"4"));//眩晕
		datas.put("jyjbqkyb", dg(examination.getJyjbqk(),"5"));//癔病
		datas.put("jyjbqkzcmb", dg(examination.getJyjbqk(),"6"));//震颤麻痹
		datas.put("jyjbqkjsb", dg(examination.getJyjbqk(),"7"));//精神病
		datas.put("jyjbqkcd", dg(examination.getJyjbqk(),"8"));//痴呆
		datas.put("jyjbqksjxtjb", dg(examination.getJyjbqk(),"9"));//影响肢体活动
		datas.put("jyjbqkzsdp", dg(examination.getJyjbqk(),"10"));//三年内吸食、注射毒品行为
		
		datas.put("wtdlrxm", formatString(examination.getWtdlrxm()));// 委托代理人姓名
		datas.put("wtdlrsfzmc", formatString(examination.getWtdlrsfzmc()));// 委托代理人身份名称
		datas.put("wtdlrsfzmhm", formatString(examination.getWtdlrsfzmhm()));// 委托代理人身份号码
		datas.put("wtdlrlxdz", formatString(examination.getWtdlrlxdz()));// 委托代理人联系地址
		datas.put("wtdlrlxdh", formatString(examination.getWtdlrlxdh()));// 委托代理人联系电话
		datas.put("brsqfs", dg(examination.getSqfs(),"1"));//申请方式-本人
		datas.put("wtsqfs", dg(examination.getSqfs(),"2"));//申请方式-委托
		
		return datas;
	}


	public static Map<String, String> convertData(DrivingBase base, DrivingApply apply) {
		if (base == null || apply == null) {
			return null;
		}
		Map<String, String> datas = new HashMap<String, String>();
		datas.put("xm", formatString(base.getXm()));// 姓名
		datas.put("xb", BaseParamsUtil.getBaseParamNameByType("xb", base.getXb()));// 性别
		datas.put("csrq", dateFormat(base.getCsrq()));// 出生日期
		datas.put("gj",BaseParamsUtil.getBaseParamNameByType("gj", formatString(base.getGj())) );// 国籍
		datas.put("lxdh", formatString(base.getLxdh()));// 联系电话
		// datas.put("lsh", apply.getLsh());
		datas.put("sfzmhm", apply.getId().getSfzmhm());// 身份证
		// datas.put("qh", apply.getId().getQh());
		// datas.put("jxdm", apply.getId().getJxdm());
		datas.put("zzzm", formatString(apply.getZzzm()));
		datas.put("dabh", formatString(apply.getDabh()));
		datas.put("yjdz", formatString(apply.getYjdz()));

		// datas.put("ywlx",formatString(apply.getYwlx()));//业务类型
		datas.put("ccsq", dg(apply.getYwlx(), "A"));// 初次申请
		datas.put("zjzjcx", dg(apply.getYwlx(), "B"));// 增加准驾车型

		datas.put("sqzjcxdh", formatString(apply.getSqzjcxdh()));// 申请准驾车型代号
		// datas.put("scjszzl",formatString(apply.getScjszzl()));//所持驾驶证种类
		datas.put("bgsx", formatString(apply.getBgsx()));
		datas.put("bgnr", formatString(apply.getBgnr()));
		datas.put("cydw", formatString(apply.getCydw()));
		// datas.put("sqyq",formatString(apply.getSqyq()));//申请原因

		// datas.put("sqfs",formatString(apply.getSqfs()));//申请方式
		datas.put("brsq", dg(apply.getSqfs(), "1"));// 本人申请
		datas.put("jhrsq", dg(apply.getSqfs(), "2"));// 监护人申请
		datas.put("wt", dg(apply.getSqfs(), "3"));// 代理申请

		datas.put("wtjhrxm", formatString(apply.getWtjhrxm()));// 委托\监护人姓名
		datas.put("wtjhrsfzmc", formatString(apply.getWtjhrsfzmc()));// 委托\监护人身份名称
		datas.put("wtjhrsfzmhm", formatString(apply.getWtjhrsfzmhm()));// 委托\监护人身份号码
		datas.put("wtjhrlxdz", formatString(apply.getWtjhrlxdz()));// 委托\监护人联系地址
		datas.put("wtjhrlxdh", formatString(apply.getWtjhrlxdh()));// 委托\监护人联系电话
		if ("1".equals(apply.getSqfs())) {
			datas.put("brsqrq", dateFormat(apply.getSqrq()));// 本人申请日期
			datas.put("dlrsqrq", dateFormat(null));// 代理人申请日期
		} else {
			datas.put("brsqrq", dateFormat(null));// 本人申请日期
			datas.put("dlrsqrq", dateFormat(apply.getSqrq()));// 代理人申请日期
		}

		return datas;
	}

	public static String formatString(Object val) {
		if (val == null) {
			return "";
		}
		return val.toString();
	}
}
