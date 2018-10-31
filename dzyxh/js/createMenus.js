//获取用户所有功能点
var allRolePower;
//当前登录用户
var userInfo;


function Map() {
	function KeyIndex(key, index) {
		this.key = key;
		this.index = index;
	}
	/** 存放键的数组(遍历用到) */
	this.keys = new Array();
	/** 存放数据 */
	this.data = new Object();
	var objectList = new Array();
	/**
	 * 放入一个键值对
	 * 
	 * @param {String}
	 *            key
	 * @param {Object}
	 *            value
	 * @param {Object}
	 *            index 顺序，不传默认为0
	 */
	this.put = function(key, value, index) {
		if (!index) {
			index = 0;
		}
		if (this.data[key] == null) {
			this.keys.push(key);
			objectList.push(new KeyIndex(key, index));
		}
		this.data[key] = value;
	};

	/**
	 * 获取某键对应的值
	 * 
	 * @param {String}
	 *            key
	 * @return {Object} value
	 */
	this.get = function(key) {
		return this.data[key];
	};

	/**
	 * 删除一个键值对
	 * 
	 * @param {String}
	 *            key
	 */
	this.remove = function(key) {
		this.keys.remove(key);
		this.data[key] = null;
	};

	/**
	 * 遍历Map,执行处理函数
	 * 
	 * @param {Function}
	 *            回调函数 function(key,value,index){..}
	 */
	this.each = function(fn) {
		if (typeof fn != 'function') {
			return;
		}
		objectList.sort(function(a, b) {
			return a.index - b.index
		});
		var len = objectList.length;
		for (var i = 0; i < len; i++) {
			var k = objectList[i].key;
			fn(k, this.data[k], i);
		}
	};

	/**
	 * 获取键值数组(类似Java的entrySet())
	 * 
	 * @return 键值对象{key,value}的数组
	 */
	this.entrys = function() {
		var len = this.keys.length;
		var entrys = new Array(len);
		for (var i = 0; i < len; i++) {
			entrys[i] = {
				key : this.keys[i],
				value : this.data[i]
			};
		}
		return entrys;
	};

	/**
	 * 判断Map是否为空
	 */
	this.isEmpty = function() {
		return this.keys.length == 0;
	};

	/**
	 * 获取键值对数量
	 */
	this.size = function() {
		return this.keys.length;
	};

	/**
	 * 重写toString
	 */
	this.toString = function() {
		var s = "{";
		for (var i = 0; i < this.keys.length; i++, s += ',') {
			var k = this.keys[i];
			s += k + "=" + this.data[k];
		}
		s += "}";
		return s;
	};
}
// 解析菜单
$(function() {
	
	userInfo = $.ajax({
		url : "user/getCurrentUser",
		async : false,
		type:'POST'
	}).responseText;

	userInfo=$.parseJSON(userInfo);
	console.log("&&&"+userInfo)
	allRolePower=$.ajax({
		url : "role/getRolePower",
		async : false,
		type:'POST'
	}).responseText;
	
	function createTab(data){
		$.each(data,function(i,n){
			$("#tabAll").tabs("add",{
				title:n.module,
				selected:i==0?true:false,
				href:n.url==null?"page/defaultTemplate.html":n.url,
				onLoad:function(){
					if(n.url==null){
						createDefaultMenu(n.module,n.group);
					}
				}
			});
		});
	};
	
	function createDefaultMenu(title,group){
		var currentPanel = $("#tabAll").tabs("getTab",title);
		var accordion =$(currentPanel).find(".easyui-accordion");
		var centerPanel = currentPanel.find(".easyui-layout").layout('panel','center');
		$.each(group,function(i,item){
			
			var menus = [];
			$.each(item.menus,function(i,n){
				console.log(allRolePower+" "+n.permission)
				if(allRolePower.indexOf(n.permission) != -1){
					menus.push(n);
				}
			});
			accordion.accordion('add', {
				title: item.groupName,
				content: createItemMume(centerPanel,item.menus),//createItemMume(centerPanel,menus),授权后修改
				selected: i==0?true:false
			});
			
		});
		
		
	}
	
	function createItemMume(centerPanel, data) {
		var ul = $("<ul class='menus'></ul>");
		$.each(data,function(i,n){
			var li = $("<li><a href=\"javascript:void(0)\"><img></a></li>");
			li.find("img").attr("src", n.icon);
			li.find("a").append(n.title);
			li.click(function(){
				centerPanel.panel("refresh",n.href);
			});
			ul.append(li);
			if(i==0&&centerPanel.html()==""){
				li.click();
			}
		});
		return ul;
	}
	
	
	
	$.get("page/sysMenu.json",function(data){
		$("#tabAll").tabs();
		createTab(data);
	},"json");
	
/*	$.post("role/menu.json",{},function(json){
		json=jQuery.parseJSON(json);
		var m = new Map();
		var temp = {};
		
		$.each(json, function(i, val) {
			var module = val["module"];
			var app = val["app"];
			var appIndex = val["appIndex"];
			var moduIndex = val["moduIndex"];
			var icoUrl = val["icoUrl"];
			val["target"] = "#"+module + "Contex";
			val["icon"] = icoUrl;
			val["title"] = app;
			if (!temp[module]) {
				temp[module] = true;
				var s = new Map();
				m.put(module, s, moduIndex)
				s.put(app, val, appIndex);
			} else {
				m.get(module).put(app, val, appIndex);
			}
			
		});
		m.each(function(k, data, i) {
			var tab = $("<div title=\"" + k + "\"></div>");	
			var page = $('<div class="" data-options="fit:true">'
					+ '<div data-options="region:\'west\',title:\'' + k
					+ '\',split:true" style="width: 200px;" ><ul id="' + k
					+ 'Mune" class="menus"></ul></div>'
					+ '<div data-options="region:\'center\'" id="' + k
					+ 'Contex"></div></div>');
			tab.append(page);	
			$("#tabAll").append(tab);
			page.layout();
			comm.createMume(k + 'Mune', data);
		})
		$("#tabAll").tabs();
		$("#tabAll").tabs("add",{
			title:"机动车档案管理",
			selected:false,
			content:comm.creaTemplate({
				id:'jdcdagl',
				title:"机动车档案管理",
				menus : [
					{
						"icon" : "images/fxp_48.png",
						"title" : "业务归档",
						href : "page/jdc/ywgd.html"
					},
					{
						"icon" : "images/group_48.png",
						"title" : "已归档业务",
						href : "page/jdc/ygdyw.html"
					},
					{
						"icon" : "images/car_48.png",
						"title" : "档案查询",
						href : "page/jdc/dacx.html"
					},
					{
						"icon" : "images/suiji_48.png",
						"title" : "业务流水查询",
						href : "page/jdc/ygdyw.html"
					}
				]
			})
		});
		
		$("#tabAll").tabs("add",{
			title:"库房档案管理",
			selected:false,
			content:comm.creaTemplate({
				id:'kfdagl',
				title:"库房档案管理",
				menus : [
					{
						"icon" : "images/car_48.png",
						"title" : "档案查询",
						href : "page/kf/dacx.html"
					},
					{
						"icon" : "images/box_in_48.png",
						"title" : "档案入库",
						href : "page/kf/dark.html"
					},
					{
						"icon" : "images/box_out_48.png",
						"title" : "档案出库",
						href : "page/kf/dack.html"
					}
				]
			})
		});
		
		$("#tabAll").tabs("add",{
			title:"业务监督及预警",
			selected:false,
			content:comm.creaTemplate({
				id:'ywjdyj',
				title:"业务监督及预警",
				menus : [
					{
						"icon" : "images/pd_48.png",
						"title" : "业务黑名单",
						href : "page/ywjd/hmd.html"
					},
					{
						"icon" : "images/group_48.png",
						"title" : "业务预警信息",
						href : "page/ywjd/ywyj.html"
					}
				]
			})
		});
	},"json");*/
	
})