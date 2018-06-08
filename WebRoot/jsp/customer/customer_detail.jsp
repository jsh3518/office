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
	body{margin-left: 0px;margin-top: 0px;margin-right: 0px;margin-bottom: 0px;background: url(images/login_bg.jpg);}
	.center{width:100%;height:300px;background: url(images/login_bg.jpg);}
	.title{text-align:center;padding-left:40px;padding-bottom:10px;font-family:Arial,Helvetica,sans-serif;font-size:14px;height:25px;line-height:25px;color:#666666;font-weight:bold;}
	.info{font-family:Arial,Helvetica,sans-serif;font-size:12px;height:25px;line-height:25px;color:#333333;margin-bottom:25px}
	.select{width:100px;height:25px;border:1px solid #ccc;vertical-align:middle;font-size:12px;color:#222;}
	.btn{width:60px;height:25px;border-width:0px;background-image: url(images/btn-bg2.gif);letter-spacing: 5px;margin-right:15px;cursor: pointer;}
	.right{width:310px;margin-left:20px;vertical-align:middle;text-align: left;float:left;}
	.input{width:100%;height:25px;line-height:25px; align:center;vertical-align:middle;font-size:12px;color:#222;background-color:#D4E8E3;border:1px solid #ccc;border-radius:4px;}
	.left{color:#262626;text-align:right;width:100px;vertical-align:middle;float:left}
	.error{float: left;color: #ea644a;}
</style>
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
</head>
<body>
<div style="width:900px;height:auto;margin-left:auto;margin-right:auto;">
	<div class="center">
		<form action="orders/forNew.html" method="post" name="customerForm" onsubmit="return check();">
			<div style="width: 49%;float: left;margin-top:10px">
				<div class="title">
					公司
				</div>
				<div class="info">
					<label class="left">公司名称<font color="red">*</font></label>
					<div class="right">
	      			<input name="companyName" id="companyName" type="text" value="${customer.companyName }" class="input"/>
	     		</div>
				</div>
				<div class="info">
					<label class="left">主域名<font color="red">*</font></label>
					<div class="right">
						<div id="domainDiv">
							<input type="text" name="domain" id="domain" class="input" value="${customer.domain }" onblur="valDomain()" style="width: 180px"/>.partner.onmschina.cn
						</div>
					</div>
				</div>
				<div class="info">
					<label class="left">邮政编码<font color="red">*</font></label>
					<div class="right">
						<input type="text" name="postalCode" id="postalCode" class="input" value="${customer.postalCode }"/>
					</div>
				</div>
				<div class="info">
					<label class="left">公司地址<font color="red">*</font></label>
					<div class="right">
						<select name="province" id="province" class="select" onChange="changeOrg('city',this.value)">
							<option value="">请选择</option>
							<c:forEach items="${provinList}" var="organ">
								<option <c:if test="${customer.province == organ.orgId}"> selected="selected"</c:if> value="${organ.orgId }">${organ.orgName }</option>
							</c:forEach>
						</select>
						<select name="city" id="city" class="select" onChange="changeOrg('region',this.value)">
							<option value="">请选择</option>
							<c:forEach items="${cityList}" var="organ">
								<option <c:if test="${customer.city == organ.orgId}"> selected="selected"</c:if> value="${organ.orgId }">${organ.orgName }</option>
							</c:forEach>
						</select>
						<select name="region" id="region" class="select">
						<option value="">请选择</option>
						<c:forEach items="${regionList}" var="organ">
							<option <c:if test="${customer.region == organ.orgId}"> selected="selected"</c:if> value="${organ.orgId }">${organ.orgName}</option>
						</c:forEach>
					</select>
					</div>
				</div>
				<div class="info">
					<label class="left"><font color="red">*</font></label>
					<div class="right">
						<input type="text" name="address" id="address" class="input" value="${customer.address }"/>
					</div>
				</div>
			</div>
			<div style="width: 49%;float: left;margin-top:10px">
				<div class="title">
					主要联系人
				</div>
				<div class="info">
					<label class="left">名字<font color="red">*</font></label>
					<div class="right">
						<input type="text" name="firstName" id="firstName" class="input" value="${customer.firstName }"/>
					</div>
				</div>
				<div class="info">
					<label class="left">姓氏<font color="red">*</font></label>
					<div class="right">
						<input type="text" name="lastName" id="lastName" class="input" value="${customer.lastName }"/>
					</div>
				</div>
				<div class="info">
					<label class="left">电话号码</label>
					<div class="right">
						<input type="text" name="phoneNumber" id="phoneNumber" class="input" value="${customer.phoneNumber }"/>
					</div>
				</div>
				<div class="info">
					<label class="left">电子邮箱地址<font color="red">*</font></label>
					<div class="right">
						<input type="text" name="email" id="email" class="input" value="${customer.email }"/>
					</div>
				</div>
			</div>
			<div class="info" style="width:100%;float: left;margin-left: 25px">
				<input type="button" name="closeBtn" id="closeBtn" value="关闭" class="btn" onclick="cancel()"/>
			</div>
		</form>
	</div>
</div>
	<script type="text/javascript">
		var dg;
		$(document).ready(function(){
			dg = frameElement.lhgDG;
			$("input.input").attr("disabled",true);
			$("select").attr("disabled",true);
		});
		
		function cancel(){
			dg.cancel();
		}
	</script>
</body>
</html>