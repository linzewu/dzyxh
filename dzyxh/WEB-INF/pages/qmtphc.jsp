<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String clsbdh =request.getParameter("clsbdh");
String ywlx =request.getParameter("ywlx");
System.out.println(clsbdh+" "+ywlx);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.signElement {
	background-color:white;
    position: absolute; left: 50%; top: 50%;
    margin-top: -300px;    /* 高度的一半 */
    margin-left: -400px;    /* 宽度的一半 */
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
<form action="/dzyxh/photoCompose/composeImage"  method="post" name="myform">
	<input type="text"  name="clsbdh" id="clsbdh">
	<input type="text"  name="ywlx" id="ywlx">
	<input type="text" name="base64Img" id="base64Img" value="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">
	<input type="button"  onclick="save()" value="save">
</form>
<div  class="signElement">
	<div style="text-align: center;">
		<em><h2 id="signTitle">请用户签名</h2></em>
	</div>
	<object ID="signObject" border=0 width="800" height="500" classid="clsid:BFAA2FD5-B6E7-41D0-B104-A433ED33F461"></object>
	<div style="text-align: center;">
		<input id="signType" type="hidden"  />
		<input class="submit_01" type="button" value="确定" onclick="saveSign()" /> 
		<input class="submit_01" type="button" value="重签" onclick="clearSign()" /> 
		<input class="submit_01" type="button" value="关闭" onclick="signClose()" />
	</div>
</div>
</body>
</html>

<script type="text/javascript">
alert("<%=clsbdh%>")
$("#clsbdh").val("<%=clsbdh%>");
$("#ywlx").val("<%=ywlx%>");
	
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
		console.log("<%=clsbdh%>")
		
		initSign();
	});

	var m_deviceID=2;
	var ulFormat=2;
	var signZpzlValue='099';
	var proxySignZpzlValue='098';
	function initSign(){
		var signObject = document.getElementById("signObject");
	    var canvasWidth = 800;
	    var canvasHeight = 500;
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
	    setTimeout("$('.signElement').hide()",1000);
	}

	function signClose(){
		$(".signElement").hide();
		$("#ScanCtrl").show();
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
		//String base64="";
		//$("#base64Img").val(base64);
		document.myform.submit();
	}
</script>