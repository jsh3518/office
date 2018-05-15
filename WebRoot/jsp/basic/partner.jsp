<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<title>Office订单管理系统后台</title>
<link type="text/css" rel="stylesheet" href="css/content.css" />
<style type="text/css">
.font {
	color: red;
	display: none
}

.hide {
	display: none
}
</style>
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
</head>
<body>
	<div style="width: 450px; height: auto; margin-left: auto; margin-right: auto;">
		<div class="center">
			<div style="width: 100%; height: auto; margin-top: 20px;">
				<form action="partner/editPartner.html" method="post"	name="partnerForm" onsubmit="return check();">
					<div class="info">
						<label class="label">账号<font class="font">*</font></label>
						<div class="right">
							<input type="text" name="username" id="username" class="input"	value="${partner.username }" disabled="disabled" />
							 &nbsp;<span id="usererr" class="errInfo">&nbsp;</span>
						</div>
					</div>
					<div class="info hide">
						<label class="label">密码<font class="font">*</font></label>
						<div class="right">
							<input type="password" name="password" id="password"	class="input" />
							&nbsp;<span id="pwderr" class="errInfo"></span>
						</div>
					</div>
					<div class="info hide">
						<label class="label">确认密码<font class="font">*</font></label>
						<div class="right">
							<input type="password" name="password1" id="password1"	class="input" value="" />
							&nbsp;<span id="pwderr1"	class="errInfo"></span>
						</div>
					</div>
					<div class="info">
						<label class="label">应用Id<font class="font">*</font></label>
						<div class="right">
							<input type="text" name="appid" id="appid" class="input" value="${partner.appid }" disabled="disabled" />
							&nbsp;<span	id="apperr" class="errInfo"></span>
						</div>
					</div>
					<div class="info">
						<label class="label">资源url<font class="font">*</font></label>
						<div class="right">
							<input type="text" name="resource" id="resource" class="input"	value="${partner.resource }" disabled="disabled" />
							&nbsp;<span id="resourceerr" class="errInfo"></span>
						</div>
					</div>
					<div class="info hide">
						<label class="label">验证码<font class="font">*</font></label>
						<div class="right">
							<input type="text" name="code" id="code" class="input"	style="width: 60%" />&nbsp;&nbsp;
							<img id="codeImg" alt="点击更换" title="点击更换" src="" style="vertical-align: middle;" />
							&nbsp;<span id="codeerr" class="errInfo"></span>
						</div>
					</div>
					<div>
						<input type="hidden" name="id" id="id" value="${partner.id }" />
					</div>
					<div class="info" style="margin-left: 40px">
						<input type="button" name="editBtn" id="editBtn" value="编辑"	class="btn" onclick="edit()" />
						<input type="submit"	name="saveBtn" id="saveBtn" value="保存" class="btn"	style="display: none" />
						<input type="reset" name="cancelBtn"	id="cancelBtn" value="取消" class="btn" onclick="cancel()" style="display: none" />
					</div>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var message = "${message}";
		changeCode();
		$.ajaxSetup({ 
		    async : false 
		});  
		$(document).ready(function(){
				$("#codeImg").bind("click",changeCode);
				 if(message=="success"){
						alert("保存成功！");
				 }else if(message!=""){
						$(".hide").show();
						$(".font").show();
						$(".input").attr("disabled",false);
						$("#editBtn").hide();
						$("#saveBtn").show();
						$("#cancelBtn").show();
						alert(message);
				}
		});
	
		function genTimestamp(){
			var time = new Date();
			return time.getTime();
		}
	
		function changeCode(){
			$("#codeImg").attr("src","code.html?t="+genTimestamp());
		}
		
		function edit(){
			$(".hide").show();
			$(".font").show();
			$(".input").attr("disabled",false);
			$("#editBtn").hide();
			$("#saveBtn").show();
			$("#cancelBtn").show();
		}
		
		function cancel(){
			$(".errInfo").html("");
			$(".hide").hide();
			$(".font").hide();
			$(".input").attr("disabled",true);
			$("#editBtn").show();
			$("#saveBtn").hide();
			$("#cancelBtn").hide();
		}
		
		function forBack(){
			window.location="<%=basePath%>login.html";
		}

		var con;
		function check() {
			con = 1;
			$(".errInfo").html("");
			valUsername();
			valPassword();
			valPassword1();
			valCode();
			if (con == 1) {
				return true;
			} else {
				return false;
			}
		}

		//判断账号
		function valUsername() {

			$("#usererr").html("");
			var username = $("#username").val();
			if (username == "") {
				$("#usererr").html("账号不能为空!");
				con = 0;
			} else if (/.*[\u4e00-\u9fa5]+.*$/.test(username)) {
				$("#usererr").html("账号不能包含中文!");
				con = 0;
			} else if (username.indexOf("@") < 0
					|| username.substr(username.length - 21).toLowerCase() != ".partner.onmschina.cn") {
				$("#usererr").html("账号输入错误，请重新输入!");
				con = 0;
			}
		}

		//判断密码
		function valPassword() {
			$("#pwderr").html("");
			reg1 = /^.*[\d]+.*$/;
			reg2 = /^.*[A-Za-z]+.*$/;
			reg3 = /^.*[_@#%&^+-/*\/\\]+.*$/;//验证密码
			var Pval = $("#password").val();
			if (Pval == "") {
				$("#pwderr").html("密码不能为空!");
				con = 0;
			} else if (Pval.length < 8) {
				$("#pwderr").html("密码不能小于8位，区分大小写!");
				con = 0;
			} else if (!((reg1.test(Pval) && reg2.test(Pval))
					|| (reg1.test(Pval) && reg3.test(Pval)) || (reg2.test(Pval) && reg3
					.test(Pval)))) {
				$("#pwderr").html("需至少包含数字、字母和符号中的两种类型!");
				con = 0;
			}
		}

		//判断密码
		function valPassword1() {
			$("#pwderr1").html("");
			var Pval = $("#password").val();
			var Pval1 = $("#password1").val();
			if (Pval != Pval1) {
				$("#pwderr1").html("两次输入的密码不一致!");
				con = 0;
			}
		}

		//判断验证码
		function valCode() {
			$("#codeerr").html("");
			if ($("#code").val() == "") {
				$("#codeerr").html("验证码不能为空!");
				con = 0;
			} else {
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
	</script>
</body>
</html>