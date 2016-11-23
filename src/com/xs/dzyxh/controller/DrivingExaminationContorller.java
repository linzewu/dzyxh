package com.xs.dzyxh.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xs.common.Annotation.FunctionAnnotation;
import com.xs.dzyxh.entity.driver.DrivingBase;
import com.xs.dzyxh.entity.driver.DrivingExamination;
import com.xs.dzyxh.manager.driimg.IDrivingPhotoManager;
import com.xs.dzyxh.manager.driver.IDrivingBaseManager;
import com.xs.dzyxh.manager.driver.IDrivingExaminationManager;

@Controller
@RequestMapping(value = "/exa")
public class DrivingExaminationContorller {
	@Resource(name="drivingExamination")
	private IDrivingExaminationManager drivingExaminationManager;
	@Resource(name = "driverManager")
	private IDrivingBaseManager drivingBaseManager;
	
	@Resource(name = "driimgManager")
	IDrivingPhotoManager drivingPhotoManager;
	
	@FunctionAnnotation(name = "体检表图片获取")
	@RequestMapping(value = "getExaPic")
	public void getApplyPic(DrivingExamination dir, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		dir=drivingExaminationManager.getDrivingExamination(dir);
		if(dir==null){
			return;
		}
		DrivingBase base=new DrivingBase();
		base.setSfzmhm(dir.getId().getSfzmhm());
		base=drivingBaseManager.getDrivingBaseById(base);
		byte[] datas=drivingExaminationManager.getExaminationImgToByte(base, dir);
		OutputStream out = null;
		response.setContentType("multipart/form-data");  
		  //设置Content-Disposition  
        response.setHeader("Content-Disposition", "attachment;filename="+java.net.URLEncoder.encode(base.getXm()+"-体检表.png", "UTF-8"));  
		try {
			out = response.getOutputStream();
			out.write(datas);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
