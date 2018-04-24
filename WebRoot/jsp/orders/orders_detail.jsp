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
<title>Office订单管理系统后台</title>
<style type="text/css">
	body{margin-left: 0px;margin-top: 0px;margin-right: 0px;margin-bottom: 0px;background: url(images/login_bg.jpg);}
	.center{width:100%;margin-top:10px; height:415px;background: url(images/login_bg.jpg);}
	.title{text-align:left;float:left; font-family:Arial,Helvetica,sans-serif;font-size:18px;height:20px;line-height:20px;color:#666666;font-weight:800;text-overflow: ellipsis;margin:3px}
	.info{width:100%;font-family:Arial,Helvetica,sans-serif;font-size:13px;height:20px;line-height:20px;color:#333333;float: left;margin-bottom:5px}
	.edit{font-family:Arial,Helvetica,sans-serif;font-size:12px;color:#006CD8;margin:3px;display:none}
	.select{width:150px;height:20px;border:1px solid #ccc;vertical-align:middle;font-size:12px;color:#222;}
	.btn{width:60px;height:25px;border-width:0px;background-image: url(images/btn-bg2.gif);letter-spacing: 5px;margin-left:15px;cursor: pointer;text-align: center}
	.left{width:105px;vertical-align:middle;text-align:right; color:#262626;float:left;}
	.right{width:180px;vertical-align:middle;text-align: left;float:left;}
	.input{width:200px;height:18px;align:center;vertical-align:middle;font-size:12px;color:#222;background-color:#D4E8E3; border:1px solid #ccc;border-radius:4px;}
	.error{float: left;color: #ea644a;font-family:Arial,Helvetica,sans-serif;font-size:12px;}
	.table{margin-left:20px;width:100%;cellpadding:0; cellspacing:0;font-family:Arial,Helvetica,sans-serif;}
	.table td{font-size:13px;text-align: left;width:33%;}
</style>
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
</head>
<body>
<div style="width:900px;height:auto;margin-left:auto;margin-right:auto;">
	<div class="center">
		<form action="orders/confirmOrders.html" method="post" name="ordersForm" onsubmit="return check();" enctype="multipart/form-data">
			<div class="info">
				<label class="left">订单编号：</label>
				<div class="right"><input name="ordersNo" id="ordersNo" class="input" readonly="readonly" value="${orders.ordersNo }"/></div>
				<label class="left">订单时间：</label>
				<div class="right"><div class="input"><fmt:formatDate value="${orders.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></div></div>
				<label class="left">订单状态：</label>
				<div class="right">
					<input name="status" id="status" style="display:none" value="${orders.status }"/>
					<input name="statusName" id="statusName" class="input" readonly="readonly" value="${statusMap[orders.status]}"/>
				</div>
			</div>
			<div class="info">
				<div class="title">客户信息</div>
				<div class="edit" id="customerDiv"><a href="javascript:editCustomer(${orders.customer.id})" style="white-space: nowrap;margin-left: 20px">修改</a></div>
			</div>
			<div class="info">
				<label class="left">客户名称：</label>
				<div class="right"><input class="input" readonly="readonly" value="${orders.customer.companyName }"/></div>
				<label class="left">主域名：</label>
				<div class="right"><input class="input" readonly="readonly" value="${orders.customer.domain }.partner.onmschina.cn"/></div>
				<label class="left">主要联系人：</label>
				<div class="right"><input class="input" readonly="readonly" value="${orders.customer.lastName }${orders.customer.firstName }"/></div>
			</div>
			<div class="info">
				<label class="left">地址：</label>
				<div class="right"><input class="input" readonly="readonly" value="${orders.customer.provincialName }${orders.customer.cityName }${orders.customer.regionName }"/></div>
				<label class="left">邮箱：</label>
				<div class="right"><input class="input" readonly="readonly" value="${orders.customer.email }"/></div>
				<label class="left">电话号码：</label>
				<div class="right"><input class="input" readonly="readonly" value="${orders.customer.phoneNumber }"/></div>
			</div>
			<div class="info">
				<div class="title">订阅信息</div>
				<div class="edit" id="ordersDiv"><a href="javascript:editOrders(${orders.id})" style="white-space: nowrap;margin-left: 20px">更新</a></div>
			</div>
			<table class="table">
				<c:forEach items="${ordersDetailMap}" var="ordersDetailMap" >
				<tr>
					<td class="title">${ordersDetailMap.key}</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
					<c:forEach items="${ordersDetailMap.value}" var="ordersDetail">
						<tr>
							<td>${ordersDetail.offerName}</td>
							<td>${ordersDetail.quantity}许可证</td>
							<td>${orders.reseller}</td>
						</tr>
					</c:forEach>
				</c:forEach>
			</table>
				<div class="title"  style="width:100%;">付款信息</div>
				<div class="info" id ="sumDiv">
					<label class="left">金额：</label>
					<div class="right"><input class="input" readonly="readonly" value="${orders.sum }"/></div>
					<label class="left">结算金额：</label>
					<div class="right"><input class="input" readonly="readonly" value="${orders.actualSum }"/></div>
				</div>
				<div class="info" id ="paymentDiv">
					<label class="left">付款方式<font color="red">*</font>：</label>
					<div class="right">
						<select name="payment" id="payment" class="select" onChange="changePayment(this.value)">
							<option value="">请选择</option>
							<c:forEach items="${paymentList}" var="payment">
								<option <c:if test="${payment.code == orders.payment}"> selected="selected"</c:if> value="${payment.code }">${payment.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="info" id="voucherDiv" style="height: auto">
					<label class="left">付款凭证<font color="red">*</font>：</label>
					<input type="file" id="voucher" name="voucher" class="file" value="${orders.file }">
					<img height="220px" id="voucherImg" width="auto" src="<%=basePath%>files/${orders.file}">
				</div>
			<input type="hidden" name="id" id="id" value="${orders.id}">
			<div class="info" style="margin-top: 5px">
				<input type="submit" name="submitBtn" id="submitBtn" value="确认" class="btn" style="display:none"/>
				<input type="button" name="backBtn" id="backBtn" value="返回" class="btn" onclick="forBack()"/>
			</div>
		</form>
	</div>
</div>
	<script type="text/javascript">
		$(document).ready(function(){
			if("${orders.customer.status}"==0||"${orders.customer.status}"==3){
				//如果客户是“新增”或“退回”状态，允许修改客户信息
				$("#customerDiv").show();
			}
			var status = "${orders.status}";
			if(status=="0"||status=="3"){//如果订单是“新增”或“退回”状态，允许修改订阅信息、付款方式和付款凭证
				if("${orders.type}"==1){//如果为续订，则订阅信息不允许修改
					$("#ordersDiv").hide();
				}else{
					$("#ordersDiv").show();
				}
				$("#payment").attr("disabled",false);
				$("#voucher").show();
				$("#voucherImg").hide();
				$("#submitBtn").show();
			}else{
				$("#payment").attr("disabled",true);
				$("#voucher").hide();
				$("#voucherImg").show();
				$("#submitBtn").hide();
			}

			var payment = $("#payment").val();
			if(payment==1){//信用付款不需要上传付款凭证
				$("#voucherDiv").hide();
			}else{
				$("#voucherDiv").show();
			}
		});
		
		function forBack(){
			window.location="<%=basePath%>orders/listOrders.html";
		}
		
		function editCustomer(customerId){

			var dg = new $.dialog({
				title:'客户信息',
				id:'detail',
				width:950,
				height:400,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				btnBar:false,
				resize:false,
				page:"customer/detailCustomer.html?method=edit&customerId="+customerId
				});
    		dg.ShowDialog();
		}
		
		function editOrders(ordersId){
			window.location="<%=basePath%>orders/forEdit.html?ordersId="+ordersId;
		}
		
		var con;
		function check(){
			con=1;
			$("#submitBtn").attr("disabled",true);
			$("#backBtn").attr("disabled",true);
			valPayment();
			valFile();
			if(con==1){
				return true;
			}else{
				$("#submitBtn").attr("disabled",false);
				$("#backBtn").attr("disabled",false);
				return false;
			}
		}
		
		function valPayment(){
			var paymentDiv = $("#paymentDiv");
			paymentDiv.siblings('.error').remove();
			if($("#payment").val() ==""){
				paymentDiv.after('<div class="error">请选择付款方式！</div>');
				con = 0;
			}
		}
		
		function valFile(){
			var voucherDiv = $("#voucherDiv");
			if($("#payment").val()!=1&&$("#voucher").val() ==""){
				voucherDiv.after('<div class="error">请上传付款凭证！</div>');
				con = 0;
			}
		}
		
		//onchange事件
		function changePayment(payment){
			$("div.error").remove();
			if(payment==1){
				$("#voucherDiv").hide();
			}else{
				$("#voucherDiv").show();
			}
		}
	</script>
</body>
</html>