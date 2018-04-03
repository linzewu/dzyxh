package com.xs.dzyxh.controller;

import java.io.File;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.common.Constant;
import com.xs.common.Sql2WordUtil;
import com.xs.common.Annotation.FunctionAnnotation;
import com.xs.common.Annotation.ModuleAnnotation;
import com.xs.dzyxh.manager.tongan.ITonGanManager;

@Controller
@RequestMapping(value = "/dbc")
@ModuleAnnotation(modeName = Constant.ConstantDZYXH.MODE_NAME_WINDOW, appName = Constant.ConstantDZYXH.APP_NAME_DRIVING,icoUrl="/dzyxh/images/fxp_48.png",href="/dzyxh/page/window/jsz.html",modeIndex=1,appIndex=2)
public class DrivingBaseController {
	
	Logger logger = Logger.getLogger(DrivingBaseController.class);
	public static final int DRIVER_SQB=1;
	public static final int DRIVER_TJB=2;
	public static final int DRIVER_KM1=3;
	public static final int DRIVER_KM2=4;
	public static final int DRIVER_KM3=5;
	public static final int DRIVER_KM4=6;
	
	@Resource(name = "tonGanManager")
	private ITonGanManager tonGanManager;
	
	@Resource(name="tonganHibernateTemplate")
	private HibernateTemplate  tonganHibernateTemplate ;
	
	@FunctionAnnotation(name = "查询基本信息")
	@RequestMapping(value = "getBaseLists")
	public @ResponseBody Map<String, Object> getBaseLists(@RequestParam Map<String,Object> param){
		 return tonGanManager.getBaseList(param);
	}
	
	
	@FunctionAnnotation(name = "查询基本信息")
	@RequestMapping(value = "getDriverImage",method = RequestMethod.GET)
	public String getDriverImage(@RequestParam String sfzmhm,@RequestParam String qh, @RequestParam String jxdm,@RequestParam String zplx){
		
		String sql = buildSqlByZplx(zplx, sfzmhm, qh, jxdm);
		if(sql==null){
			return "forward:/images/no-image.jpg";
		}
		
		String imgname =jxdm+"_"+qh+"_"+sfzmhm+"_"+zplx+".jpg";
		
		String patch =  Sql2WordUtil.getCacheDir()+imgname;
		File file=new File(patch);
		String filePath=imgname;
		try {
			if(!file.exists()) {
				filePath = sql2WordMeth(zplx,sql,imgname);
				if(filePath==null) {
					return "forward:/images/no-image.jpg";
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "forward:/images/no-image.jpg";
		}
		
		return "forward:/images/cache/"+filePath;
		
	}
	
	
	private String buildSqlByZplx(String zplx,String sfzmhm,String qh,String jxdm){
		String sql=null;
		switch (Integer.parseInt(zplx)) {
			case DRIVER_SQB: //2016最新机动车驾驶人身体条件证明
				logger.info("DRIVER_SQB"+zplx);
				sql = "select tm1.*,tm2.photo_xy,tm2.photo_ysqm,tm2.photo_xyqm "
						+ "from (select t1.*,t2.yymc,t2.dwyztp from (select * from drv_temp_mid t where "
						+ " t.sfzmhm = "
						+ sfzmhm
						+ " and t.qh = "
						+ qh
						+ " ) t1 "
						+ "left join tj_user t2 on substr(t1.tjyymc,0,instr(t1.tjyymc,'-')-1) = t2.yymc ) tm1 "
						+ "left join tj_photos tm2 on tm1.sfzmhm = tm2.idno";
				break;
				
			case DRIVER_TJB: //2016机动车驾驶证申请表
				logger.info("DRIVER_TJB"+zplx);
				sql = "select tm1.*,tm2.czrq,tm2.photo_mgzp,tm2.photo_sqrqm,tm3.info as photo_infol,tm4.info as photo_infor "
						+ "from (select * from drv_temp_mid t where t.sfzmhm = "
						+ sfzmhm
						+ " and t.qh = "
						+ qh
						+ " and t.jxdm = "
						+ jxdm
						+ ") tm1 left join (select * from sq_photos t where t.sfzmhm = "
						+ sfzmhm
						+ " and t.qh = "
						+ qh
						+ " and t.jxdm = "
						+ jxdm
						+ ") tm2 on tm1.sfzmhm = tm2.sfzmhm left join (select * from zw_info_data t where t.user_id = "
						+ sfzmhm
						+ " and t.sub_key='L1') tm3 on tm1.sfzmhm = tm3.user_id left join (select * from zw_info_data t where t.user_id = "
						+ sfzmhm
						+ " and t.sub_key='R1') tm4 on tm1.sfzmhm = tm4.user_id ";
				break;
	
			default:
				break;
		}
		return sql;
	}
	
	
	private String sql2WordMeth(String zplx,String sql,String imgname){
		String template = null;
		String filePath = null;
		HibernateTemplate ht = null;
		try {
			switch (Integer.parseInt(zplx)) {
				case DRIVER_SQB: 
					template = "\\2016最新机动车驾驶人身体条件证明.docx";
					ht = tonganHibernateTemplate;
					break;
				case DRIVER_TJB: 
					template = "\\2016机动车驾驶证申请表.docx";
					ht = tonganHibernateTemplate;
					break;
				default:
					break;
			}
			filePath = Sql2WordUtil.sql2WordUtilCase(template, sql, ht, imgname);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return filePath;
	}
}
