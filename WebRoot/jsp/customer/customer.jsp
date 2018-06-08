<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- <base href="<%=basePath%>"> -->
<!-- 此处不能使用base标签，是因为使用了分页插件Page，Page和当前文件不在同一层级，点击其它页时会因为权限问题转到登录页面 -->
<title>客户信息</title>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/main.css"/>
</head>
<body>
	<form action="<%=basePath%>customer/query.html" method="post" name="customerForm" id="customerForm">
	<div class="search_div">
		公司名称：<input type="text" name="companyName" value="${customer.companyName }"/>
		域名：<input type="text" name="domain" value="${customer.domain }"/>
		<a href="javascript:search();" class="myBtn"><em>查询</em></a>
	</div>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
		<tr class="main_head">
			<th>序号</th>
			<th>公司名称</th>
			<th>主域名</th>
			<th>地址</th>
			<th>联系人</th>
			<th>电话号码</th>
			<th>邮箱</th>
			<th>操作</th>
		</tr>
		<c:choose>
			<c:when test="${not empty customerList }">
				<c:forEach items="${customerList }" var="customer"  varStatus="vs">
					<tr class="main_info">
						<td>${vs.index+1 }</td>
						<td><a href="javascript:detail('${customer.id }','${customer.status }');">${customer.companyName }</a></td>
						<td>${customer.domain }.partner.onmschina.cn</td>
						<td>${customer.provincialName }${customer.cityName }${customer.regionName }</td>
						<td>${customer.lastName }${customer.firstName }</td>
						<td>${customer.phoneNumber }</td>
						<td>${customer.email }</td>
						<td><a href="javascript:addSubscription(${customer.id },'${customer.tenantId }');">添加订阅</a> | <a href="javascript:querySubscription(${customer.id });">查看订阅</a></td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr class="main_info">
					<td colspan="6">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
		<div class="page_and_btn">
		<div>
			<a href="javascript:addCustomer();" class="myBtn"><em>新增</em></a>
		</div>
		${page.pageStr }
	</div>
	</form>
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		function addCustomer(){
			window.location="<%=basePath%>customer/forAdd.html";
		}
		
		function search(){
			$("#customerForm").submit();
		}
		
		function detail(customerId,status){
			var url = "<%=basePath%>customer/detailCustomer.html?method=detail&customerId="+customerId;
			if(status==0||status==3){//新增或退回状态客户可编辑
				url = "<%=basePath%>customer/detailCustomer.html?method=edit&customerId="+customerId;
			}
			var dg = new $.dialog({
				title:'客户信息',
				id:'detail',
				width:980,
				height:400,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				btnBar:false,
				resize:false,
				page:url
				});
    		dg.ShowDialog();
		}
		
		//添加订阅
		function addSubscription(customerId,tenantId){
			window.location="<%=basePath%>orders/forAdd.html?customerId="+customerId;
		}
		
		//查看订阅
		function querySubscription(customerId){
			window.location="<%=basePath%>orders/querySubscription.html?customerId="+customerId;
		}
		
	</script>
</body>
</html>