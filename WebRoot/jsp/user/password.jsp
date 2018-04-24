<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<title>Office订单管理系统后台</title>
<style type="text/css">
	body{margin-left: 0px;margin-top: 0px;margin-right: 0px;margin-bottom: 0px;}
	.center{width:100%;height:400px;background: url(images/login_bg.jpg);}
	.left{float:left;width:100%;height:100%;background: url(images/login-wel.gif) bottom no-repeat;}
	.info{font-family: Arial, Helvetica, sans-serif;font-size: 12px;height:32px;line-height: 32px;color: #333333;}
	.label{margin-left:50px;width:120px;height:20px;line-height: 20px;vertical-align: middle;display:inline-block;text-align: right;}
	.input{width:250px;height:20px;margin-left:20px;border:1px solid #7F9DB9;vertical-align: middle;}
	.btn{width:60px;height:25px;border-width:0px;background-image: url(images/btn-bg2.gif);letter-spacing: 5px;margin-right:15px;cursor: pointer;}
	.errInfo{color:red;width:250px;height:20px;line-height: 20px;vertical-align: middle;display:inline-block; }
	.left_txt{font-family: Arial, Helvetica, sans-serif;font-size: 12px;line-height: 25px;color: #666666;}
	.bottom{width:100%;height:auto;text-align:center;font-family: Arial, Helvetica, sans-serif;font-size: 10px;color: #ABCAD3;text-decoration: none;line-height: 20px;}
</style>
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
</head>
<body>
<div style="width:100%;height:auto;margin-left:auto;margin-right:auto;">
	<div class="center">
		<div class="left">
			<div style="width:100%;height:auto;margin-top:5px;">
			<form action="user/changePassword.html" method="post" name="registForm" onsubmit="return check();" enctype="multipart/form-data">
				<div class="info">
					<label class="label">原密码<font color="red">*</font></label>
					<input type="password" name="password" id="password" class="input" value=""/>
					&nbsp;<span id="pwderr" class="errInfo"></span>
				</div>
				<div class="info">
					<label class="label">新密码<font color="red">*</font></label>
					<input type="password" name="newPassword" id="newPassword" class="input" value="" placeholder="数字和字母组成，六位以上"/>
					&nbsp;<span id="newPwderr" class="errInfo"></span>
				</div>
				<div class="info">
					<label class="label">确认密码<font color="red">*</font></label>
					<input type="password" name="password1" id="password1" class="input" value=""/>
					&nbsp;<span id="pwderr1" class="errInfo"></span>
				</div>
				<div class="info" style="margin-left: 120px">
					<input type="button" name="saveBtn" value="保存" class="btn" onclick="save()"/>
					<input type="reset" name="cancelBtn" value="取消" class="btn" onclick="resetErr()"/>
				</div>
			</form>
			</div>
		</div>
	</div>
	<div class="bottom">
	Copyright &copy; 2018 伟仕佳杰
	</div>
</div>
	<script type="text/javascript">
		var msg = "${msg}";
		$.ajaxSetup({ 
		    async : false 
		});  
		$(document).ready(function(){

		});
	
		function genTimestamp(){
			var time = new Date();
			return time.getTime();
		}
		
		function resetErr(){
			$("#pwderr").html("");
			$("#newPwderr").html("");
			$("#pwderr1").html("");
		}
		
		function forBack(){
			window.location="<%=basePath%>login.html";
		}
		
		var con;
		function check(){
			con=1;
			resetErr();
			valPassword();
			valNewPassword();
			valPassword1();
			if(con==1){
				return true;
			}else{
				return false;
			}
		}
		
		//判断原密码
		function valPassword(){
			$("#pwderr").html("");
		  var Pval = $("#password").val();
		  if( Pval ==""){
			  $("#pwderr").html("请输入原密码!");
		    con = 0;
		  }
		}
		
		//判断新密码
		function valNewPassword(){
			$("#newPwderr").html("");
		  reg1=/^.*[\d]+.*$/;
		  reg2=/^.*[A-Za-z]+.*$/;
		  reg3=/^.*[_@#%&^+-/*\/\\]+.*$/;//验证密码
		  var Pval = $("#newPassword").val();
		  if( Pval ==""){
			  $("#newPwderr").html("新密码不能为空!");
		    con = 0;
		  }else if(Pval.length<6){
			  $("#newPwderr").html("密码不能小于6位，区分大小写!");
			  con = 0;
		  }else if(!((reg1.test(Pval) && reg2.test(Pval)) || (reg1.test(Pval) && reg3.test(Pval)) || (reg2.test(Pval) && reg3.test(Pval)))){
			  $("#newPwderr").html("需至少包含数字、字母和符号中的两种类型!");
			  con = 0;
		  }else if(Pval == $("#password").val()){
			  $("#newPwderr").html("新密码不能和原密码相同，请重新设置密码!");
			  con = 0;
		  }
		}

		//判断密码
		function valPassword1(){
			$("#pwderr1").html("");
		  var Pval = $("#newPassword").val();
		  var Pval1 = $("#password1").val();
		  if( Pval !=Pval1){
			  $("#pwderr1").html("两次输入的密码不一致!");
			  con=0;
		  }
		}
		
		function save(){
			if(check()){
				var url = "user/changePassword.html";
	
				var postData = {"password":$("#password").val(),"newPassword":$("#newPassword").val()};
				$.post(url,postData,function(data){
					if(data=="success"){
						alert("密码修改成功，请重新登陆！");
						document.location = "login.html";
					}else{
						alert(data);
						return;
					}
				});
			}
		}
	</script>
</body>
</html>