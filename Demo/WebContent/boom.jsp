<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>sms BOOM!!</title>
</head>
<body>

	<script type="text/javascript">
		function ajax() {

			//先声明一个异步请求对象
			var xmlHttpReg = null;
			if (window.ActiveXObject) {//如果是IE

				xmlHttpReg = new ActiveXObject("Microsoft.XMLHTTP");

			} else if (window.XMLHttpRequest) {

				xmlHttpReg = new XMLHttpRequest(); //实例化一个xmlHttpReg
			}

			//如果实例化成功,就调用open()方法,就开始准备向服务器发送请求
			if (xmlHttpReg != null) {

				var url = "/Demo/sms?phone="
						+ document.getElementById("phone").value
						+ "&times="
						+ document.getElementById("times").value;

				xmlHttpReg.open("get", url, true);
				1
				xmlHttpReg.send(null);
				xmlHttpReg.onreadystatechange = doResult; //设置回调函数

			}

			//回调函数
			//一旦readyState的值改变,将会调用这个函数,readyState=4表示完成相应

			//设定函数doResult()
			function doResult() {

				if (xmlHttpReg.readyState == 4) {//4代表执行完成

					if (xmlHttpReg.status == 200) {//200代表执行成功
						//将xmlHttpReg.responseText的值赋给ID为resText的元素
						document.getElementById("resText").readOnly=true;
						document.getElementById("resText").value = xmlHttpReg.responseText;
						document.getElementById("resText").readOnly=true;

					}
				}

			}

		}
	</script>

	<form>
		<span>Phone Number:</span><input type="text" id="phone" /><br> 
		<span>Send	Times:</span><input type="text" id="times" /><br>
			<input type="button" value="send" onclick="ajax();" />
	</form>
<input type="text" id="resText" value="18217185596"/>
</body>
</html>