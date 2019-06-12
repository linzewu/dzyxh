$(function(){
	$("input[name=buttonOK]").parent().append("<input type=button class=button z_idx value='电子申请表' onClick=createImg()>");
});
function createImg(){//(URL, PARAMS){
	var URL = "photoCompose/toHctp";
	var PARAMS = [];
	
	var hphm=$("input[name=hphm]").val();
	var hpzl=$("input[name=hpzl]").val();
	PARAMS.clsbdh = "123";
	PARAMS.ywlx = "A";
	 var temp = document.createElement("form");
     temp.action = URL;
     temp.target="_blank"
     temp.method = "post";
     temp.style.display = "none";
     for (var x in PARAMS) {
         var opt = document.createElement("input");
         opt.name = x;
         opt.value = PARAMS[x];
         // alert(opt.name)
         temp.appendChild(opt);
     }
     document.body.appendChild(temp);
     temp.submit();
     return temp;
	
	/**$.post("photoCompose/composeImage",{clsbdh:"123"}, function(data){
		if(data.state==1){
			window.location.href="index.html";
		}else{
			$.messager.alert("登陆失败",data.message,"info");
			
		}
	},"json").error(function(e){
		$.messager.alert("提示",e.responseText);
	}).complete(function(){
	})**/
}