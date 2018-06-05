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
<title>Office订单管理系统后台</title>
<style type="text/css">
	body{margin-left: 0px;margin-top: 0px;margin-right: 0px;margin-bottom: 0px;background-color: #1B3142;}
	.header{width:100%;height:41px;background: url(images/login-top-bg.gif) repeat-x;}
	.center{width:100%;height:542px;background: url(images/login_bg.jpg) repeat-x;}
	.login_right{float:right;width:50%;height:100%;background: url(images/login-wel.gif) bottom no-repeat;}
	.login_left{float:right;width:350px;height:100%;background: url(images/login-content-bg.gif) no-repeat;background-size:350px}
	.login_title{margin-left:35px;font-family: Arial, Helvetica, sans-serif;font-size: 14px;height:36px;line-height: 36px;color: #666666;font-weight: bold;}
	.login_info{margin-left:35px;font-family: Arial, Helvetica, sans-serif;font-size: 12px;height:36px;line-height: 36px;color: #333333;}
	.login_input{width:150px;height:20px;margin-left:30px;border:1px solid #7F9DB9;vertical-align: middle;}
	.login_code{width:70px;height:20px;margin-left:30px;border:1px solid #7F9DB9;vertical-align: middle;}
	.btn{width:65px;height:25px;border-width:0px;background: url(images/btn-bg2.gif) no-repeat;letter-spacing: 0px;margin-right:20px;cursor: pointer;text-align: center;}
	.login_info img{vertical-align: middle;cursor: pointer;}
	.errInfo{display:none;color:red;}
	.logo{width:100%;height:40px;background: url(images/office_logo.png) no-repeat;_background:none;_filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/office_logo.png';)}
	.left_txt{font-family: Arial, Helvetica, sans-serif;font-size: 32px;font-weight:bold ; line-height: 10px;color: #4ae;}
	
	.bottom{width:100%;height:auto;text-align:center;font-family: Arial, Helvetica, sans-serif;font-size: 13px;color: #ABCAD3;text-decoration: none;line-height: 30px;vertical-align: middle;}
</style>
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
</head>
<body>
<div style="width:100%;height:600px;position: absolute;top:50%;left:50%;margin-top:-320px;margin-left:-50%;">
	<div class="header"></div>
	<div class="center">
		<div class="login_right">
			<div style="width:100%;height:auto;margin-top:150px;">
			<form action="login.html" method="post" name="loginForm" onsubmit="return check();">
				<div class="login_title">
					已有账号，请登录
				</div>
				<div class="login_info">
					<label>用户名：</label><input type="text" name="loginname" id="loginname" class="login_input" value="${loginname }"/>
					&nbsp;<span id="nameerr" class="errInfo"></span>
				</div>
				<div class="login_info">
					<label>密　码：</label><input type="password" name="password" id="password" class="login_input" value="${password }"/>
					&nbsp;<span id="pwderr" class="errInfo"></span>
				</div>
				<div class="login_info">
					<label>验证码：</label><input type="text" name="code" id="code" class="login_code"/>&nbsp;&nbsp;
					<img id="codeImg" alt="点击更换" title="点击更换" src=""/>
					&nbsp;<span id="codeerr" class="errInfo"></span>
				</div>
				<div class="login_info">
					<input type="submit" name="loginBtn" value="登录" class="btn"/>
					<input type="button" name="registBtn" value="注册" class="btn" onclick="regist()"/>
					<!-- <input type="reset" name="cancelBtn" value="取消" class="btn"/> -->
					<input type="button" name="renewBtn" value="忘记密码" class="btn"  onclick="renew()"/>
				</div>
			</form>
			</div>
		</div>
		<div class="login_left">
			<div style="width:100%;height:auto;margin-top:150px;">
				<div class="logo"></div>
				<div class="left_txt">
				Office订单管理系统
				</div>
			</div>
		</div>
	</div>
	<div class="bottom">
	Copyright &copy; 2018 伟仕佳杰
	</div>
</div>
	<script type="text/javascript">
	//如果是session过期后跳转至登入界面，则需要重新刷新一下父层
	if($('#mainFrame', parent.document).length >0){
		parent.location = window.location;
	}
		var errInfo = "${errInfo}";
		$(document).ready(function(){
			changeCode();
			$("#codeImg").bind("click",changeCode);
			if(errInfo!=""){
				if(errInfo.indexOf("验证码")>-1){
					$("#codeerr").show();
					$("#codeerr").html(errInfo);
					$("#code").focus();
				}else{
					$("#nameerr").show();
					$("#nameerr").html(errInfo);
				}
			}
			$("#loginname").focus();
		});
	
		function genTimestamp(){
			var time = new Date();
			return time.getTime();
		}
	
		function changeCode(){
			$("#codeImg").attr("src","code.html?t="+genTimestamp());
		}
		
		function resetErr(){
			$("#nameerr").hide();
			$("#nameerr").html("");
			$("#pwderr").hide();
			$("#pwderr").html("");
			$("#codeerr").hide();
			$("#codeerr").html("");
		}
		
		function check(){
			resetErr();
			if($("#loginname").val()==""){
				$("#nameerr").show();
				$("#nameerr").html("用户名不得为空！");
				$("#loginname").focus();
				return false;
			}
			if($("#password").val()==""){
				$("#pwderr").show();
				$("#pwderr").html("密码不得为空！");
				$("#password").focus();
				return false;
			}
			if($("#code").val()==""){
				$("#codeerr").show();
				$("#codeerr").html("验证码不得为空！");
				$("#code").focus();
				return false;
			}
			return true;
		}
		
		//用户注册--跳转至注册界面
		function regist(){
			window.location="<%=basePath%>user/toRegedit.html";
		}
		
		//用户注册--重置密码
		function renew(){
			window.location="<%=basePath%>user/toRenew.html";
		}
	</script>
</body>
</html>