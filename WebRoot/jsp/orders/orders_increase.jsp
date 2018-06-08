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
<title>增加坐席</title>
<link type="text/css" rel="stylesheet" href="css/content.css"/>
<style type="text/css">
.info {
	float: left;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 14px;
	height: auto;
	line-height: 25px;
	color: #333333;
	width: 100%;
}
</style>
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
<script type="text/javascript" src="js/common.js"></script>
</head>
<body>
<div style="width:900px;height:auto;margin-left:auto;margin-right:auto;">
	<div class="center">
		<form action="orders/increaseOrders.html" method="post" name="ordersForm" onsubmit="return check();">
			<div class="info">
				<div class="title">客户信息</div>
			</div>
			<div class="info">
				<label class="label">客户名称：</label>
				<div class="right" style="width: 180px"><input class="input" disabled="disabled" value="${customer.companyName }"/></div>
				<label class="label">主域名：</label>
				<div class="right" style="width: 180px"><input class="input" disabled="disabled" value="${customer.domain }.partner.onmschina.cn"/></div>
				<label class="label">主要联系人：</label>
				<div class="right" style="width: 180px"><input class="input" disabled="disabled" value="${customer.lastName }${customer.firstName }"/></div>
			</div>
			<div class="info">
				<label class="label">地址：</label>
				<div class="right" style="width: 180px"><input class="input" disabled="disabled" value="${customer.provincialName }${customer.cityName }${customer.regionName }"/></div>
				<label class="label">邮箱：</label>
				<div class="right" style="width: 180px"><input class="input" disabled="disabled" value="${customer.email }"/></div>
				<label class="label">电话号码：</label>
				<div class="right" style="width: 180px"><input class="input" disabled="disabled" value="${customer.phoneNumber }"/></div>
			</div>
			<div class="info">
				<div class="title">订阅信息</div>
			</div>
			<div class="info">
				<label class="label">订阅昵称：</label>
				<div class="right" style="width: 180px">${subscription.offerName}</div>
				<label class="label">数量：</label>
				<div class="right" style="width: 320px"><input name="quantity"	id="quantity" type="text"	style="width: 70px;"	onblur="verifyValue()"/>&nbsp;许可证</div>
			</div>
			<div class="info" style="margin-top: 25px">
				<input type="submit" name="submitBtn" id="submitBtn" value="确定" class="btn"/>
				<input type="button" name="backBtn" id="backBtn" value="返回" class="btn" onclick="forBack()"/>
			</div>
			<div style="display: none">
				<input id="detailId" name="detailId" readonly="readonly" value="${subscription.detailId }"/>
			</div>
		</form>
	</div>
</div>
	<script type="text/javascript">
		$(document).ready(function(){
			
		});
		
		function forBack(){
			window.location="orders/querySubscription.html?customerId=${customer.id}";
		}
		
		var con;
		function check(){
			con=1;
			$("#submitBtn").attr("disabled",true);
			$("#backBtn").attr("disabled",true);
			verifyValue();
			if(con==1){
				return true;
			}else{
				$("#submitBtn").attr("disabled",false);
				$("#backBtn").attr("disabled",false);
				return false;
			}
		}
		
		//判断输入的数量是否超过最大允许值
		function verifyValue(){
			var value = $("#quantity").val();
			var parent = "${offer.parent}";
			var maxinum = ${offer.maxinum};
			
			if("${customer.id}"==""||"${customer.tenantId}"==""||"${subscription.offerId}"==""){
				alert("参数信息有误！");
				con = 0;
			}
			$("#quantity").siblings(".error").remove();
		  if(isNaN(value)||value==""){
				$("#quantity").after('<div class="error" style="width:400px">请输入数字！</div>');
				con = 0;
	    }else if(value<=0){
				$("#quantity").after('<div class="error" style="width:400px">订阅数量必须大于0！</div>');
				con = 0;
	    }else if(parseInt(value)>maxinum){
				$("#quantity").after('<div class="error" style="width:400px">最大'+maxinum+'许可证</div>');
				con = 0;
			}else if(parent=="Business"){//商业版总代下允许订阅坐席总数不超过300
				var url = "orders/checkQuantity.html";
				var postData = {"customerId":"${customer.id}","tenantId":"${customer.tenantId}","offerId":"${subscription.offerId}","subscriptionId":"${subscription.subscriptionId}"};
				$.post(url,postData,function(data){
					var data = JSON.parse(data);
					if(data!=""){
						var tmpCount = data.tmpCount;
						var quantity = data.quantity;
						if(parseInt(value)+tmpCount+quantity>maxinum){
							$("#quantity").siblings(".error").remove();
							$("#quantity").after('<div class="error">最大'+maxinum+'许可证，已下订单'+(tmpCount+quantity)+'许可证，其中'+quantity+'许可证已生效</div>');
							con = 0;
						}
					}
				})
			}	
		}
	</script>
</body>
</html>