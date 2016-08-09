var win = {
		menus : [
		{
			"icon" : "/dzyxh/images/car_48.png",
			"title" : "机动车业务",
			href : "/dzyxh/page/window/carManager.html",
			target : "#windowContex"
		}, {
			"icon" : "/dzyxh/images/fxp_48.png",
			"title" : "驾驶人业务",
			href : "/dzyxh/page/window/jsz.html",
			target : "#windowContex"
		},{
			"icon" : "/dzyxh/images/pd_48.png",
			"title" : "图像采集设备",
			href : "/dzyxh/page/window/pd.html",
			target : "#windowContex"
		}],
		initEvents : function() {
			comm.createMume("winMune", win.menus);
		}
}

var business = {
		menus : [{
			"icon" : "/dzyxh/images/suiji_48.png",
			"title" : "随机抽查",
			href : "/dzyxh/page/business/sjchh.html",
			target : "#businessContex"
		},{
			"icon" : "/dzyxh/images/yujing_48.png",
			"title" : "业务预警",
			href : "/dzyxh/page/business/yujing.html",
			target : "#businessContex"
		}, {
			"icon" : "/dzyxh/images/user.png",
			"title" : "业务员统计",
			href : "/dzyxh/page/business/rytj.html",
			target : "#businessContex"
		},{
			"icon" : "/dzyxh/images/yuedu_48.png",
			"title" : "月度统计",
			href : "/dzyxh/page/business/ydtj.html",
			target : "#businessContex"
		},{
			"icon" : "/dzyxh/images/jidu_48.png",
			"title" : "季度统计",
			href : "/dzyxh/page/business/jdtj.html",
			target : "#businessContex"
		},{
			"icon" : "/dzyxh/images/year_report_48.png",
			"title" : "年度统计",
			href : "/dzyxh/page/business/ndtj.html",
			target : "#businessContex"
		}],
		initEvents : function() {
			comm.createMume("businessMune", business.menus);
		}
}

var storage = {
		menus : [{
			"icon" : "/dzyxh/images/write_48px.png",
			"title" : "档案批量采集",
			href : "/dzyxh/page/storage/batch.html",
			target : "#storageContex"
		}, {
			"icon" : "/dzyxh/images/storage_48.png",
			"title" : "库房管理",
			href : "/dzyxh/page/storage/storageManager.html",
			target : "#storageContex"
		},{
			"icon" : "/dzyxh/images/box_in_48.png",
			"title" : "档案入库",
			href : "/dzyxh/page/storage/storageIn.html",
			target : "#storageContex"
		},{
			"icon" : "/dzyxh/images/box_out_48.png",
			"title" : "档案出库",
			href : "/dzyxh/page/storage/storageOut.html",
			target : "#storageContex"
		},{
			"icon" : "/dzyxh/images/sq_48.png",
			"title" : "档案授权",
			href : "/dzyxh/page/storage/dasq.html",
			target : "#storageContex"
		}],
		initEvents : function() {
			comm.createMume("storageMune", storage.menus);
		}
}

var system = {
		menus : [{
			"icon" : "/dzyxh/images/user.png",
			"title" : "用户管理",
			href : "/dzyxh/page/system/UserManager.html",
			target : "#sysContex"
		},{
			"icon" : "/dzyxh/images/system.png",
			"title" : "系统参数管理",
			href : "/dzyxh/page/system/systemInfo.html",
			target : "#sysContex"
		},{
			"icon" : "/dzyxh/images/group_48.png",
			"title" : "角色权限管理",
			href : "/dzyxh/page/system/RoleManager.html",
			target : "#sysContex"
		},{
			"icon" : "/dzyxh/images/Organization_48.png",
			"title" : "组织架构管理",
			href : "/dzyxh/page/system/department.html",
			target : "#sysContex"
		}],
		initEvents : function() {
			comm.createMume("sysMune", system.menus);
		}
}
