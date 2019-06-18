<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.Map,java.util.Iterator"%>
<%
//String clsbdh =request.getParameter("clsbdh");
//String ywlx =request.getParameter("ywlx");
//String mbmc = request.getParameter("mbmc");
//System.out.println(clsbdh+" "+ywlx);

String bgdImage = request.getAttribute("bgdImage").toString();
System.out.println(bgdImage);
Map params=request.getParameterMap();
Iterator it = params.keySet().iterator();



%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.signElement {
	background-color:white;
    width:600px;
    height:300px;
    margin-left:100px;
    border: 1px solid; z-index: 999;
}
</style>
<link rel="stylesheet" href="/dzyxh/js/viewer/viewer.min.css">
<link rel="stylesheet" type="text/css"
	href="/dzyxh/js/easyui/themes/gray/easyui.css">
<!--[if lt IE 9]>  
<script src="/dzyxh/js/viewer/html5shiv.min.js"></script>  
<script src="/dzyxh/js/viewer/respond.min.js"></script>  
<![endif]-->  

<script type="text/javascript" src="/dzyxh/js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="/dzyxh/js/viewer/viewer.min.js"></script>

<script type="text/javascript" src="/dzyxh/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/dzyxh/js/common.js"></script>
<script type="text/javascript" src="/dzyxh/bps/all.js"></script>
</head>
<body>
<form action="/dzyxh/photoCompose/composeImage"  method="post" name="myform" id="myform">
	<img alt="" src="" id="bgdImage">
	<input type="hidden" name="base64Img" id="base64Img" value="">
	
</form>
<div  class="signElement">
	<div style="text-align: center;">
		<em><h2 id="signTitle">请用户签名</h2></em>
	</div>
	<object ID="signObject" border=0 width="580" height="250" classid="clsid:BFAA2FD5-B6E7-41D0-B104-A433ED33F461"></object>
	<div style="text-align: center;">
		<input id="signType" type="hidden"  />
		<input class="submit_01" type="button" value="确定" onclick="save()" /> 
		<input class="submit_01" type="button" value="重签" onclick="clearSign()" /> 
		<input class="submit_01" type="button" value="关闭" onclick="wClose()" />
	</div>
</div>
</body>
</html>

<script type="text/javascript">

<%

while(it.hasNext()){
    String paramName = (String) it.next();
    String paramValue = request.getParameter(paramName);
    %>
	    var temp = document.getElementById("myform");
	    var opt = document.createElement("input");
	    opt.name = "<%=paramName%>";
	    opt.value = "<%=paramValue%>";
	    opt.type = "hidden";
	    // alert(opt.name)
	    temp.appendChild(opt);
    <%
    
    //处理你得到的参数名与值
    System.out.println(paramName+"="+paramValue);
}
%>

$("#bgdImage").attr("src","<%=bgdImage%>");
	$(function(){
		/**var ywlx=GetQueryString("ywlx");
		var lsh=GetQueryString("lsh");
		var clsbdh=GetQueryString("clsbdh");
		var hpzl=GetQueryString("hpzl");
		var hphm=GetQueryString("hphm");
		
		$("#hphm").text(hphm);
		$("#hpzl").text(comm.getParamNameByValue("hpzl",hpzl));
		$("#clsbdh").text(clsbdh);
		$("#lsh").text(lsh);
		$("#ywlx").text(comm.getParamNameByValue("ywlx",ywlx));

		$.post("/dzyxh/vehWin/getZpzl",{ywlx:ywlx},function(data){
			$.each(data,function(i,n){
				var temp="<li><img  src=\"/dzyxh/images/no-image.jpg\" alt="+n.paramName+" zplx="+n.paramValue+"></img><br/><input value="+n.paramValue+" type=radio name=zplx /> <label>"+n.paramName+"</label> </li>";
				$(".docs-pictures").append(temp);
			});

			$.post("/dzyxh/vehWin/getImgBylsh",{lsh:lsh},function(data){
				$.each(data,function(i,n){
					$(".docs-pictures img[zplx="+n.zplx+"]").attr("src","/dzyxh/vehWin/getImgById?id="+n.id);
					$(".docs-pictures input[value="+n.zplx+"]").attr("zpid",n.id);
				});

				$(".docs-pictures").viewer({
					show:function(){
						$("#ScanCtrl").hide();
					},
					hide:function(){
						$("#ScanCtrl").show();
				}});
			});
		});

		start_preview();**/
		
		
		initSign();
	});

	var m_deviceID=2;
	var ulFormat=2;
	var signZpzlValue='099';
	var proxySignZpzlValue='098';
	function initSign(){
		var signObject = document.getElementById("signObject");
	    var canvasWidth = 580;
	    var canvasHeight = 250;
	    var initial;
	    initial = signObject.PPL398_InitialDevice(m_deviceID, 0, 0, canvasWidth, canvasHeight);
	    if (initial == 0) {
	        setTimeout(function() {
	        	signObject.PPL398_SetCanvasSize(m_deviceID, 0, 0, canvasWidth, canvasHeight);
	        	signObject.PPL398_SetPenColor(m_deviceID, 0, 0, 0);
	        	signObject.PPL398_EnableSaveVideoData(m_deviceID, true);
	            var i = signObject.PPL398_SignatureStatusCallback(m_deviceID, 'jsPPL398_StatusChanged');
	        }, 50);
	        //jsSetCtrStatusByInitial();
	       // InitialSelectOption();
	      //  disabledPointOptions();
	    } else {
	        alert('没有接入签名设备');
	    }
	    //setTimeout("$('.signElement').hide()",1000);
	}

	function signClose(){
		$(".signElement").hide();
		$("#ScanCtrl").show();
	}
	
	function wClose(){
		window.close();
	}

	function clearSign(){
		var signObject = document.getElementById("signObject");
		signObject.PPL398_Clear(m_deviceID);
	}

	function saveSign(){
		var signObject = document.getElementById("signObject");
		var img64 = signObject.PPL398_PacketsBase64Encode(m_deviceID, ulFormat);
		
		if(!img64){
			alert("签名异常！");
			return;
		}
		
		var ywlx=GetQueryString("ywlx");
		var lsh=GetQueryString("lsh");
		var clsbdh=GetQueryString("clsbdh");
		var hpzl=GetQueryString("hpzl");
		var hphm=GetQueryString("hphm");
		var param={};
		param.ywlx=ywlx;
		param.lsh=lsh;
		param.clsbdh=clsbdh;
		param.hpzl=hpzl;
		param.hphm=hphm;
		param.base64Img=img64;
		param.zplx=$("#signType").val();
		var zpObj= $("input[name=zplx][value="+param.zplx+"]");
		var zpid=zpObj.attr("zpid");
		if(zpid){
			param.zpid=zpid;
		}
		$.post("/dzyxh/vehWin/saveImg",param,function(data){
			if(data.state==1){
				$(".docs-pictures img[zplx="+data.data.zplx+"]").attr("src","/dzyxh/vehWin/getImgById?id="+data.data.id);
				zpObj.attr("zpid",data.data.id);
				signClose();
			}
		});
		
		

	}
	
	function save(){
		var signObject = document.getElementById("signObject");
		var img64 = signObject.PPL398_PacketsBase64Encode(m_deviceID, ulFormat);
		//String base64="";
		$("#base64Img").val(img64);
		//document.myform.submit();
		$.post("/dzyxh/photoCompose/composeImage",$('#myform').serialize(),function(data){
			if(data.state==1){
				//$(".docs-pictures img[zplx="+data.data.zplx+"]").attr("src","/dzyxh/vehWin/getImgById?id="+data.data.id);
				//zpObj.attr("zpid",data.data.id);
				$("#bgdImage").attr("src",data.data);
				signClose();
			}
		});
	}
</script>