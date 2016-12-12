
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
	$.post("role/menu.json",{},function(json){
		json=jQuery.parseJSON(json);
		var m = new Map();
		var temp = {};
/*		alert(menus["state"]);
		if(menus["state"]==600){
			 window.location.href="login.html";
	         return;
		}*/
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
	},"json");
	
})