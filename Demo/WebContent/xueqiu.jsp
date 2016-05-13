<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>获取雪球用户头像原图! this is just for fun, be happy!</title>

</head>
<body>
	<h1>娱乐至上，天天涨停！获取雪球用户头像大图</h1>
	<div>\o=^=o/ 点开心仪的用户头像，复制浏览器地址栏最后的数字http://xueqiu.com/2298190325，如<font color="red">2298190325</font>，O(∩_∩)O哈哈~）</div>
	<br>
	<span>请输入雪球用户id</span>
	<input id="userid" style="width: 150px; height:30px;}"/>
	<input type="button" id="send" style="width: 90px ;height:35px;"" value="获取头像"/>
	<span id="result" ></span><br>
	<img id="img1" src="http://xavatar.imedao.com/community/20160/1453200316664-1453200335038.png!30x30.png">
	<br>
	<br>
	<img id="img2" src="http://xavatar.imedao.com/community/20160/1453200316664-1453200335038.png!50x50.png">
</body>

<script type="text/javascript">
function show(id){
	var xmlhttp;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var data = xmlhttp.responseText;
			//document.getElementById("result").innerHTML = data;
			var arr= new Array(); //定义一数组
			arr=data.split(";")
			
			document.getElementById("img1").setAttribute("src",arr[0]);
			document.getElementById("img2").setAttribute("src",arr[1]);
		}
	}
	
	xmlhttp.open("GET","xueqiu?userid="+id,true);
	xmlhttp.send();
}
document.getElementById("send").onclick = function(){
	document.getElementById("img1").setAttribute("src","");
	document.getElementById("img1").setAttribute("alt","头像记载中");
	document.getElementById("img2").setAttribute("src","");
	document.getElementById("img2").setAttribute("alt","原始头像加载中..");
	var id = document.getElementById("userid").value;
	show(id);

}

</script>
</html>