package com.xs.dzyxh.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

//import org.apache.axiom.om.util.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.common.Constant;
import com.xs.common.ImageBase64Cash;
import com.xs.common.ResultHandler;
import com.xs.dzyxh.entity.system.BaseParams;
import com.xs.dzyxh.entity.vehimg.VehImg;
import com.xs.dzyxh.manager.veh.IVehImgManager;

@Controller
@RequestMapping(value = "/vehWin")
public class VehWinController {
	
	@Resource(name="vehImgManager")
	private IVehImgManager vehImgManager;
	
	
	@RequestMapping(value = "saveImg", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveImg(VehImg vehimg,String base64Img) {
		
		if(base64Img==null||base64Img.equals("")){
			return ResultHandler.toErrorJSON("图片不能为空");
		}else{
			this.vehImgManager.updateVehImgZpztOfzplx(vehimg.getLsh(), vehimg.getZplx());
			byte[] zp = Base64Utils.decodeFromString(base64Img);//Base64.decode(base64Img);
			vehimg.setZp(zp);
			vehimg.setPssj(new Date());
			vehimg.setZpzt("0");
			this.vehImgManager.saveImg(vehimg);
			return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "保存成功", vehimg);
		}
	}
	
	@RequestMapping(value = "getImgBylsh", method = RequestMethod.POST)
	public @ResponseBody List<VehImg>  getImgBylsh(@RequestParam String lsh) {
		return this.vehImgManager.getVehImgByLshOfzc(lsh);
	}
	
	@RequestMapping(value = "getZpzl", method = RequestMethod.POST)
	public @ResponseBody List<BaseParams>  getZpzl(@RequestParam String ywlx) {
		return this.vehImgManager.getZpzl(ywlx);
	}
	
	@RequestMapping(value = "getImgById", method = RequestMethod.GET)
	public String getImgById(Integer id) throws IOException{
		
		if(!ImageBase64Cash.getInstance().exists(id.toString())){
			VehImg img = this.vehImgManager.getImgById(id);
			ImageBase64Cash.getInstance().cashIamge(img.getZp(), img.getId().toString());
		}
		return "forward:/images/cache/"+id+".jpg";
	}
	
	
	@RequestMapping(value = "getYwByClsbdh", method = RequestMethod.POST)
	public @ResponseBody List<Map<String,Object>>  getYwByClsbdh(@RequestParam String clsbdh) {
		
		return this.vehImgManager.getYwListByClsbdh(clsbdh);
	}
	
	
	

}
