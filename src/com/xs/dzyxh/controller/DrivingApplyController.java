package com.xs.dzyxh.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.common.Common;
import com.xs.common.ResultHandler;
import com.xs.common.Annotation.FunctionAnnotation;
import com.xs.dzyxh.entity.driimg.DrivingPhoto;
import com.xs.dzyxh.entity.driver.DrivingApply;
import com.xs.dzyxh.entity.driver.DrivingBase;
import com.xs.dzyxh.manager.driverimg.IDrivingPhotoManager;
import com.xs.dzyxh.manager.window.IDrivingApplyManager;
import com.xs.dzyxh.manager.window.IDrivingBaseManager;

@Controller
@RequestMapping(value = "/apply")
public class DrivingApplyController {
	@Resource(name = "driverApplyManager")
	private IDrivingApplyManager drivingApplyManager;

	@Resource(name = "driverManager")
	private IDrivingBaseManager drivingBaseManager;

	@Resource(name = "driimgManager")
	IDrivingPhotoManager drivingPhotoManager;

	@FunctionAnnotation(name = "申请表信息查询")
	@RequestMapping(value = "getApplyInfos")
	public @ResponseBody Map<String, Object> getDriBases(DrivingApply base, Integer page, Integer rows) {
		List<DrivingApply> baseList = drivingApplyManager.getDrivingApplys(base, page, rows);
		if (baseList != null) {
			for (DrivingApply apply : baseList) {
				DrivingPhoto driving = new DrivingPhoto();
				// 有流水号则使用流水号查询
				if (apply.getLsh() != null) {
					driving.setLsh(apply.getLsh());
				} else if (apply.getId() != null) {
					// 使用身份证、期号、驾校代码查询图片
					driving.setSfzmhm(apply.getId().getSfzmhm());
					driving.setQh(apply.getId().getQh());
					driving.setJxdm(apply.getId().getJxdm());
				}
				List picIds = drivingPhotoManager.getDrivingPhotoIds(driving, null, null);
				apply.setPicIds(picIds);
			}
		}
		return ResultHandler.toMyJSON(baseList, null);
	}

	@FunctionAnnotation(name = "申请表图片获取")
	@RequestMapping(value = "getApplyPic")
	public void getApplyPic(DrivingApply apply, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		apply = drivingApplyManager.getDrivingApplyById(apply);
		if (apply == null) {
			return;
		}
		DrivingBase base = new DrivingBase();
		base.setSfzmhm(apply.getId().getSfzmhm());
		base = drivingBaseManager.getDrivingBaseById(base);
		byte[] datas = drivingApplyManager.getApplyImgToByte(base, apply);
		OutputStream out = null;
		response.setContentType("multipart/form-data");
		// 设置Content-Disposition
		response.setHeader("Content-Disposition",
				"attachment;filename=" + java.net.URLEncoder.encode(base.getXm() + "-申请表.png", "UTF-8"));
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
	@FunctionAnnotation(name = "打印图片")
	@RequestMapping(value = "printImg")
	public void printImg(DrivingApply apply, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>打印图片</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<img src=\""+request.getContextPath()+"/" + request.getParameter("url") + "\">");
		out.println("</body>");
		out.println("</html>");

	}
}
