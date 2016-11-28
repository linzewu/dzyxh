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

import com.xs.common.ResultHandler;
import com.xs.common.Annotation.FunctionAnnotation;
import com.xs.dzyxh.entity.driimg.DrivingPhoto;
import com.xs.dzyxh.manager.driverimg.IDrivingPhotoManager;

@Controller
@RequestMapping(value = "/img")
public class DrivingPhotoController {
	@Resource(name = "driimgManager")
	IDrivingPhotoManager drivingPhotoManager;

	@FunctionAnnotation(name = "根据ID获取图片")
	@RequestMapping(value = "getDriBaseById")
	public @ResponseBody void getDrivingBaseById(DrivingPhoto base, HttpServletRequest request,
			HttpServletResponse response) {
		DrivingPhoto data = drivingPhotoManager.getDrivingPhotoById(base);
		if(data==null||data.getZp()==null){
			return;
		}
		OutputStream out = null;
		response.setContentType("image/gif");
		try {
			out = response.getOutputStream();
			out.write(data.getZp());
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
	
	@FunctionAnnotation(name = "图片ID信息查询")
	@RequestMapping(value = "getDriImgs")
	public @ResponseBody Map<String, Object> getDriImgs(DrivingPhoto base, Integer page, Integer rows) {
		List<String> baseList = drivingPhotoManager.getDrivingPhotoIds(base, page, rows);
		Integer count = 0;
		return ResultHandler.toMyJSON(baseList, null);
	}
}
