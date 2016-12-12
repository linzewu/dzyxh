package com.xs.dzyxh.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.common.Constant;
import com.xs.common.ResultHandler;
import com.xs.common.Annotation.FunctionAnnotation;
import com.xs.common.Annotation.ModuleAnnotation;
import com.xs.dzyxh.entity.system.WinEquipment;
import com.xs.dzyxh.manager.window.IWinEquipmentManager;

@Controller
@RequestMapping(value = "/win")
@ModuleAnnotation(modeName = Constant.ConstantDZYXH.MODE_NAME_WINDOW, appName = Constant.ConstantDZYXH.APP_NAME_EQUIPMENT,href="/dzyxh/page/window/winEquipment.html",icoUrl="/dzyxh/images/pd_48.png",modeIndex=1,appIndex=3)
public class WinEquipmentController {
	@Resource(name = "winEquipmentManager")
	private IWinEquipmentManager winEquipmentManager;
	
	@FunctionAnnotation(name = "窗口设备查询")
	@RequestMapping(value = "getWins", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getUsers(WinEquipment win, Integer page, Integer rows) {
		List<WinEquipment> winList = winEquipmentManager.getWinEquipments(win, page, rows);
		Integer count = winEquipmentManager.getWinEquipmentCount(win);
		return ResultHandler.toMyJSON(winList, count);
	}

	@FunctionAnnotation(name = "窗口设备查询")
	@RequestMapping(value = "getWin", method = RequestMethod.POST)
	public @ResponseBody WinEquipment getWin(String ip) {
		WinEquipment win = winEquipmentManager.getWinEquipment(ip);
		return win;
	}

	@FunctionAnnotation(name = "编辑窗口设备")
	@RequestMapping(value = "saveWin", method = RequestMethod.POST)
	public @ResponseBody Map saveWin(WinEquipment win, BindingResult result) {
		if (!result.hasErrors()) {
			win = winEquipmentManager.saveWinEquipment(win);
			return ResultHandler.resultHandle(result, win, Constant.ConstantMessage.SAVE_SUCCESS);
		} else {
			return ResultHandler.resultHandle(result, null, null);
		}
	}

	@FunctionAnnotation(name = "删除窗口设备")
	@RequestMapping(value = "deleteWin", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> delete(WinEquipment win) {
		winEquipmentManager.deleteWinEquipment(win);
		return ResultHandler.toSuccessJSON("窗口设备删除成功");
	}
}
