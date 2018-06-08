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
	body{margin-left: 0px;margin-top: 0px;margin-right: 0px;margin-bottom: 0px;background-color: #EAEDF2;}
	.center{width:100%;margin-top:10px; height:415px;background-color: #EAEDF2;}
	.title{text-align:left;font-family:Arial,Helvetica,sans-serif;font-size:13px;height:20px;line-height:20px;color:#666666;font-weight:bold;}
	.info{width:100%;font-family:Arial,Helvetica,sans-serif;font-size:12px;height:20px;line-height:20px;color:#333333;float: left;margin-bottom:3px}
	.select{width:150px;height:20px;border:1px solid #ccc;vertical-align:middle;font-size:12px;color:#222;}
	.btn{width:60px;height:25px;border-width:0px;background-image: url(images/btn-bg2.gif);letter-spacing: 5px;margin-left:15px;cursor: pointer;text-align: center}
	.left{width:105px;vertical-align:middle;text-align:right; color:#262626;float:left;}
	.right{width:180px;vertical-align:middle;text-align: left;float:left;}
	.input{width:200px;height:18px;align:center;vertical-align:middle;font-size:12px;color:#222;background-color:#D4E8E3; border:1px solid #ccc;border-radius:4px;}
	.checkbox{width:15px;height:15px;vertical-align:middle;font-size:12px;color:#222;border:1px solid #ccc;border-radius:4px;}
	.error{float: left;color: #ea644a;font-family:Arial,Helvetica,sans-serif;font-size:12px;}
	.table{margin-left:20px;width:100%;cellpadding:0; cellspacing:0;font-family:Arial,Helvetica,sans-serif;}
	.table td{font-size:13px;text-align: left;width:33%;}
	.info img{vertical-align: middle;cursor: pointer;width:auto;height:225px;}
</style>
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
</head>
<body>
<div style="width:900px;height:auto;margin-left:auto;margin-right:auto;">
	<div class="center">
		<form action="orders/auditOrders.html" method="post" name="ordersForm">
			<fieldset>
				<legend>订单信息</legend>
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
					<label class="left">订单类型：</label>
					<div class="right">
						<input name="type" id="type" style="display:none" value="${orders.type }"/>
						<input name="typeName" id="typeName" class="input" readonly="readonly" value="${typeMap[empty orders.type?'0':orders.type]}"/>
					</div>
					<label class="left">客户名称：</label>
					<div class="right"><input class="input" readonly="readonly" value="${orders.customer.companyName }"/></div>
					<label class="left">主域名：</label>
					<div class="right"><input class="input" readonly="readonly" value="${orders.customer.domain }.partner.onmschina.cn"/></div>
				</div>
				<div class="info">
					<label class="left">主要联系人：</label>
					<div class="right"><input class="input" readonly="readonly" value="${orders.customer.lastName }${orders.customer.firstName }"/></div>
					<label class="left">邮箱：</label>
					<div class="right"><input class="input" readonly="readonly" value="${orders.customer.email }"/></div>
					<label class="left">经销商：</label>
					<div class="right"><input class="input" readonly="readonly" value="${orders.reseller }"/></div>
				</div>
			</fieldset>
			<fieldset>
				<legend>订阅信息</legend>
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
			</fieldset>
			<fieldset>
				<legend>付款信息</legend>
				<div class="info" id ="sumDiv">
					<label class="left">金额：</label>
					<div class="right"><input class="input" readonly="readonly" name="sum" id="sum" value="${orders.sum }"/></div>
					<label class="left">结算金额：</label>
					<div class="right"><input class="input" readonly="readonly" name="actualSum" id="actualSum" value="${orders.actualSum }"/></div>
					<label class="left">折扣：</label>
					<div class="right"><input class="input" readonly="readonly" name="discount" id="discount" value="${orders.discount }" style="width: 40px" onblur="calSum()"/>%</div>
				</div>
				<div class="info" id ="paymentDiv">
					<label class="left">计费频率<font color="red">*</font>：</label>
					<div class="right" id="billings">
	      		<c:forEach var="billing" varStatus="status" items="${billingCycleList}">
	      			<span <c:if test="${orders.billingCycle=='none'&&billing.code!='none'||orders.billingCycle!='none'&&billing.code=='none'}">style="display:none"</c:if>>
	      				<input type="radio" name="billingCycle" id="billingCycle" value="${billing.code }" <c:if test="${orders.billingCycle==billing.code}">checked="checked"</c:if> disabled="disabled"/>${billing.name }
	          	</span>
	          </c:forEach>
	     		</div>
					<label class="left">付款方式<font color="red">*</font>：</label>
					<div class="right">
						<select name="payment" id="payment" class="select" onChange="changePayment(this.value)" disabled="disabled">
							<option value="">请选择</option>
							<c:forEach items="${paymentList}" var="payment">
								<option <c:if test="${payment.code == orders.payment}"> selected="selected"</c:if> value="${payment.code }">${payment.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="info" id="voucherDiv" style="height: auto;">
					<label class="left">付款凭证<font color="red">*</font>：</label>
					<input type="file" id="voucher" name="voucher" class="file" value="${orders.file }" disabled="disabled">
					<img id="voucherImg" src="<%=basePath%>files/${orders.file}">
				</div>
			</fieldset>
			<div class="info" style="margin-top: 5px">
				<input type="button" name="submitBtn" id="submitBtn" value="确认" class="btn"  onclick="forSubmit()"/>
				<input type="button" name="backBtn" id="backBtn" value="退回" class="btn" onclick="forBack()"/>
				<input type="button" name="returnBtn" id="returnBtn" value="返回" class="btn" onclick="forReturn()"/>
			</div>
		</form>
	</div>
</div>
	<script type="text/javascript">
		$(document).ready(function(){
			var status = "${orders.status}";
			if(status=="1"){//如果订单是“已提交”状态
				$("input[name='billingCycle']").attr("disabled",false);
				$("#submitBtn").show();
				$("#backBtn").show();
			}else{
				$("#submitBtn").hide();
				$("#backBtn").hide();
			}

			var payment = $("#payment").val();
			if(payment==1){//信用付款不需要上传付款凭证
				$("#voucherDiv").hide();
			}else{
				$("#voucherDiv").show();
				var fileName = "${orders.file}";
				if(fileName==""){
					$("#voucher").show();
					$("#voucherImg").hide();
				}else{
					$("#voucher").hide();
					$("#voucherImg").show();
				}
			}
		});
		
		function forReturn(){
			window.location="<%=basePath%>orders/listOrders.html?flag=audit";
		}
		
		//退回
		function forBack(){
			$("#submitBtn").attr("disabled",true);
			$("#backBtn").attr("disabled",true);
			$("#returnBtn").attr("disabled",true);
			$.ajax({
				type:"POST",
				url:"orders/auditOrders.html",
				data:{"id":"${orders.id }","opinion":"0","billingCycle":$("#billingCycle:checked").val()},
				dataType: "text",
				success:function(data){
					if (data == "success") {
						alert("操作成功！");
						window.location="orders/listOrders.html?flag=audit";
					}else{
						alert("操作失败！");
						$("#submitBtn").attr("disabled",false);
						$("#backBtn").attr("disabled",false);
						$("#returnBtn").attr("disabled",false);
						return;
					}
				},
				error:function(){
					alert("操作失败！");
					$("#submitBtn").attr("disabled",false);
					$("#backBtn").attr("disabled",false);
					$("#returnBtn").attr("disabled",false);
				}
			});
		}
		
		//审核
		function forSubmit(){
			$("#submitBtn").attr("disabled",true);
			$("#backBtn").attr("disabled",true);
			$("#returnBtn").attr("disabled",true);
			$.ajax({
				type:"POST",
				url:"orders/auditOrders.html",
				data:{"id":"${orders.id }","opinion":"1","billingCycle":$("#billingCycle:checked").val()},
				dataType: "text",
				success:function(data){
					if (data == "success") {
						alert("审核成功！");
						window.location="orders/listOrders.html?flag=audit";
					}else{
						alert(data);
						$("#submitBtn").attr("disabled",false);
						$("#backBtn").attr("disabled",false);
						$("#returnBtn").attr("disabled",false);
						return;
					}
				},
				error:function(){
					alert("操作失败！");
					$("#submitBtn").attr("disabled",false);
					$("#backBtn").attr("disabled",false);
					$("#returnBtn").attr("disabled",false);
				}
			});
		}
	</script>
</body>
</html>