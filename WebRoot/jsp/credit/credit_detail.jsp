<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<title>Office订单管理系统</title>
<style type="text/css">
	body{margin-left: 0px;margin-top: 0px;margin-right: 0px;margin-bottom: 0px;background: url(images/login_bg.jpg);}
	.center{width:100%;margin-top:10px; height:405px;background: url(images/login_bg.jpg);}
	.title{text-align:left;font-family:Arial,Helvetica,sans-serif;font-size:14px;height:30px;line-height:30px;color:#666666;font-weight:bold;}
	.info{font-family:Arial,Helvetica,sans-serif;font-size:13px;height:20px;line-height:20px;color:#333333;float: left;margin-bottom: 20px}
	.select{width:200px;height:25px;border:1px solid #ccc;vertical-align:middle;font-size:12px;color:#222;}
	.btn{width:60px;height:25px;border-width:0px;background-image: url(images/btn-bg2.gif);letter-spacing: 5px;margin-left:15px;cursor: pointer;text-align: center}
	.left{width:100px;vertical-align:middle;text-align:right; color:#262626;float:left;height:20px;}
	.right{width:200px;height:20px;vertical-align:middle;text-align: left;float:left;}
	.input{width:200px;height:20px;align:center;vertical-align:middle;font-size:13px;border:1px solid #ccc;border-radius:4px;}
	.readonly{color:#222;background-color:#D4E8E3;}
	.error{float: left;color: #ea644a;font-family:Arial,Helvetica,sans-serif;font-size:8px;}
</style>
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
</head>
<body>
<div style="width:850px;height:auto;margin-left:auto;margin-right:auto;">
	<div class="center">
		<form action="credit/editCredit.html" method="post" name="creditForm" onsubmit="return check();">
			<fieldset style="margin-top: 20px">
				<legend>代理商信息</legend>
				<div class="info">
					<input style="display: none" id="agentId" name="agentId" value="${agent.userId }"/>
					<label class="left">代理商名称：</label>
					<div class="right"><input class="input readonly" id="agentName" name="agentName" readonly="readonly" value="${agent.username }"/></div>
				</div>
				<div class="info">
					<label class="left">联系人：</label>
					<div class="right"><input class="input readonly" readonly="readonly" value="${agent.contact }"/></div>
				</div>
				<div class="info">	
					<label class="left">联系方式：</label>
					<div class="right"><input class="input readonly" readonly="readonly" value="${agent.phone}"/></div>
				</div>
				<div class="info">
					<label class="left">邮箱：</label>
					<div class="right"><input class="input readonly" readonly="readonly" value="${agent.email }"/></div>
				</div>
				<div class="info">
					<label class="left">联系地址：</label>
					<div class="right"><input class="input readonly" readonly="readonly" value="${agent.province.orgName }${agent.city.orgName }${agent.county.orgName }"/></div>
				</div>
				<div class="info">
					<label class="left">税号：</label>
					<div class="right"><input class="input readonly" readonly="readonly" value="${agent.tax }"/></div>
				</div>
			</fieldset>
			<fieldset  style="margin-top: 20px">
				<legend>信用信息</legend>
				<input name="id" id="id" style="display: none" value="${credit.id }"/>
				<input name="valid" id="valid" style="display: none" value="1"/>
				<div class="info">
					<label class="left">信用等级<font color="red">*</font>：</label>
					<div class="right" ><div style="float: left" id ="creditDiv">
						<select name="creditRating" id="creditRating" class="select">
							<option value="">请选择</option>
							<c:forEach items="${creditList}" var="creditRating">
								<option <c:if test="${creditRating.code == credit.creditRating}"> selected="selected"</c:if> value="${creditRating.code }">${creditRating.name}</option>
							</c:forEach>
						</select>
					</div>
					</div>
				</div>
				<div class="info">
					<label class="left">信用额度(元)<font color="red">*</font>：</label>
					<div class="right">
						<input type="text" id="creditLine" name="creditLine" class="input" value="${credit.creditLine }">
					</div>
				</div>
				<div class="info">
					<label class="left">账期<font color="red">*</font>：</label>
					<div class="right">
						<div  id="unitDiv">
							<input type="text" id="account" name="account" class="input" style="width: 70px" value="${credit.account }">
							<select name="unit" id="unit" class="select" style="width: 120px">
								<option value="">请选择</option>
								<c:forEach items="${unitList}" var="timeUnit">
									<option <c:if test="${timeUnit.code == credit.unit}"> selected="selected"</c:if> value="${timeUnit.code }">${timeUnit.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="info">
					<label class="left">折扣：</label>
					<div class="right">
						<input type="text" id="discount" name="discount" class="input" style="width: 100px" value="${credit.discount }">&nbsp;折
					</div>
				</div>
			</fieldset>
			<div class="info" style="margin-top: 10px">
				<input type="submit" name="submitBtn" id="submitBtn" value="确认" class="btn"/>
				<input type="button" name="returnBtn" id="returnBtn" value="返回" class="btn" onclick="forReturn()"/>
			</div>
		</form>
	</div>
</div>
	<script type="text/javascript">
		$(document).ready(function(){
			
		});
		
		var con;
		function check(){
			con = 1;
			$("#submitBtn").attr("disabled",false);
			$("#returnBtn").attr("disabled",false);
			valCreditRating();
			valCreditLine();
			valAccount();
			valDiscount();
			if(con==1){
				return true;
			}else{
				$("#submitBtn").attr("disabled",false);
				$("#returnBtn").attr("disabled",false);
				return false;
			}
		}
		
		function valCreditRating(){
			var creditDiv = $("#creditDiv");
			creditDiv.siblings('.error').remove();
			if($("#creditRating").val() ==""){
				creditDiv.after('<div class="error">请选择信用等级！</div>');
				con = 0;
			}
		}
		
		function valCreditLine(){
			$("#creditLine").siblings('.error').remove();
			if($("#creditLine").val() ==""){
				$("#creditLine").after('<div class="error">请输入信用额度！</div>');
				con = 0;
			}
		}
		
		function valAccount(){
			$("#unitDiv").siblings('.error').remove();
			if($("#account").val() ==""){
				$("#unitDiv").after('<div class="error">请输入账期！</div>');
				con = 0;
			}else if(isNaN($("#account").val())){
				$("#unitDiv").after('<div class="error">账期仅可输入数字，请重新输入！</div>');
				con = 0;
			}else	if($("#unit").val() ==""){
				$("#unitDiv").after('<div class="error">请选择账期单位！</div>');
				con = 0;
			}
		}
		
		function valDiscount(){
			$("#discount").siblings('.error').remove();
			if($("#discount").val() !=""){
				var reg = /^([1-9]\d?|100)$/;
				if(!reg.test($("#discount").val())){
					$("#discount").after('<div class="error">折扣范围1-100内的正整数！</div>');
					con = 0;
				}
			}
		}

		function forReturn(){
			window.location="<%=basePath%>credit.html";
		}
	</script>
</body>
</html>