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
<link type="text/css" rel="stylesheet" href="css/content.css"/>
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
</head>
<body>
<div style="width:100%;height:350px;margin-left:auto;margin-right:auto;">
	<div class="center">
		<div style="width:500px;height:auto;margin:auto;">
			<form action="user/renewPassword.html" method="post" name="renewForm" onsubmit="return check();">
				<div class="info">
					<label class="label">账号</label>
					<div class="right">
						<input type="text" name="loginname" id="loginname" class="input" value=""/>
						&nbsp;<span id="nameerr" class="errInfo"></span>
					</div>
				</div>
				<div class="info" style="height: 25px">
					<label class="label">验证码</label>
					<div class="right">
						<input type="text" name="code" id="code" class="code" />&nbsp;&nbsp;
						<img id="codeImg" alt="点击更换" title="点击更换" src="" align="middle"/>
						&nbsp;<span id="codeerr" class="errInfo"></span>
					</div>
				</div>
				<div class="info" style="text-align: center;">
					<input type="button" name="saveBtn" value="提交" class="btn" onclick="save()"/>
					<input type="button" name="backBtn" value="返回" class="btn" onclick="back()"/>
				</div>
			</form>
		</div>
	</div>
</div>
<div class="bottom">
	Copyright &copy; 2018 伟仕佳杰
</div>
	<script type="text/javascript">

		$.ajaxSetup({ 
		    async : false 
		});  
		$(document).ready(function(){
			changeCode();
		});
	
		function genTimestamp(){
			var time = new Date();
			return time.getTime();
		}
		
		function changeCode(){
			$("#codeImg").attr("src","code.html?t="+genTimestamp());
		}

		function back(){
			window.location="<%=basePath%>login.html";
		}
		
		var con;
		function check(){
			con=1;
			resetErr();
			valLoginname();
			valCode();
			if(con==1){
				return true;
			}else{
				changeCode();
				return false;
			}
		}
		
		function resetErr(){
			$("#nameerr").html("");
			$("#codeerr").html("");
		}
		
		//判断原密码
		function valLoginname(){
			if($("#loginname").val()==""){
				$("#nameerr").show();
				$("#nameerr").html("请输入用户名！");
				con = 0;
			}else{
			  var url = "user/checkUser.html";
				var postData = {"loginname":$("#loginname").val()};
				$.post(url,postData,function(data){
					if(data<=0){
						$("#nameerr").show();
						$("#nameerr").html("用户不存在！");
				    con = 0;		
					}else if(data>1){
						$("#nameerr").show();
						$("#nameerr").html("该账号在系统中存在多个，请联系管理员处理！");
				    con = 0;
					}
				});
			}
		}
		//判断验证码
		function valCode() {
			$("#codeerr").html("");
			if ($("#code").val() == "") {
				$("#codeerr").html("验证码不能为空!");
				con = 0;
			}else{
				var url = "code/checkCode.html";
				var postData = {
					"code" : $("#code").val()
				};
				$.post(url, postData, function(data) {
					if (data != "") {
						$("#codeerr").show();
						$("#codeerr").html(data);
						con = 0;
					}
				});
			}
		}
		
		function save(){
			if(check()){
				var url = "user/renewPassword.html";
				var postData = {"loginname":$("#loginname").val()};
				$.post(url,postData,function(data){
					if(data=="success"){
						alert("充值后的密码已发送至您的邮箱，请重新登录！");
						document.location = "login.html";
					}else if(data=="fail"){
						alert("请联系管理员修改密码！");
						document.location = "toRenew.html";
					}else{
						alert(data);
						document.location = "toRenew.html";
					}
				});
			}
		}
	</script>
</body>
</html>