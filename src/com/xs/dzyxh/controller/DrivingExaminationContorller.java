package com.xs.dzyxh.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.common.Constant;
import com.xs.common.ResultHandler;
import com.xs.common.Annotation.FunctionAnnotation;
import com.xs.common.Annotation.ModuleAnnotation;
import com.xs.dzyxh.entity.driver.DrivingBase;
import com.xs.dzyxh.entity.driver.DrivingExamination;
import com.xs.dzyxh.manager.driverimg.IDrivingPhotoManager;
import com.xs.dzyxh.manager.window.IDrivingBaseManager;
import com.xs.dzyxh.manager.window.IDrivingExaminationManager;

@Controller
@RequestMapping(value = "/exa")
//@ModuleAnnotation(modeName = Constant.ConstantDZYXH.MODE_NAME_WINDOW, appName = Constant.ConstantDZYXH.APP_NAME_EXAMINATION,icoUrl="/dzyxh/images/car_48.png",href="/dzyxh/page/window/tjbxx.html",modeIndex=1,appIndex=1)
public class DrivingExaminationContorller {
	@Resource(name = "drivingExamination")
	private IDrivingExaminationManager drivingExaminationManager;
	@Resource(name = "driverManager")
	private IDrivingBaseManager drivingBaseManager;

	@Resource(name = "driimgManager")
	IDrivingPhotoManager drivingPhotoManager;

	@FunctionAnnotation(name = "体检表图片获取")
	@RequestMapping(value = "getExaPic")
	public void getApplyPic(DrivingExamination dir, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		dir = drivingExaminationManager.getDrivingExamination(dir);
		if (dir == null) {
			return;
		}
		/*DrivingBase base = new DrivingBase();
		base.setSfzmhm(dir.getSfzmhm());
		base = drivingBaseManager.getDrivingBaseById(base);*/
		byte[] datas = drivingExaminationManager.getExaminationImgToByte( dir);
		OutputStream out = null;
		response.setContentType("multipart/form-data");
		// 设置Content-Disposition
		response.setHeader("Content-Disposition",
				"attachment;filename=" + java.net.URLEncoder.encode(dir.getXm() + "-体检表.png", "UTF-8"));
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

	@FunctionAnnotation(name = "体检表信息查询")
	@RequestMapping(value = "getDriExaminations")
	public @ResponseBody Map<String, Object> getDriExaminations(DrivingExamination examination, Integer page,
			Integer rows) {
		List<DrivingExamination> baseList = drivingExaminationManager.getDrivingExaminations(examination, page, rows);
		Integer count = 0;
		if (page != null && rows != null) {
			count = drivingExaminationManager.getUserCount(examination);
		}
		return ResultHandler.toMyJSON(baseList, count);
	}

}
