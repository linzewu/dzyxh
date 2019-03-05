$(function(){
	$("input[name=buttonScan]").parent().append("<input type=button class=button z_idx value='影像化扫描' onClick=openImageScan()>");
});

function openImageScan(){
	var dzyxh_win_ip="10.39.147.126";
	var dzyxh_win_port="8080";
	var clsbdh=$("form[name=tmri] input[name=clsbdh]").val();
	var lsh=$("form[name=agentInfoForm] input[name=lsh]").val();
	
	if(lsh==""){
		alert("无法获取流水号，请先确定办理该业务！");
		return false;
	}
	
	var param="clsbdh="+clsbdh+"&lsh="+lsh;
	
	window.open("http://"+dzyxh_win_ip+":"+dzyxh_win_port+"/dzyxh/page/scan_upload.html?"+param)
}