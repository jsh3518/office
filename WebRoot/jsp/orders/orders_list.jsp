<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>My Test</title>
<link type="text/css" rel="stylesheet" href="../css/main.css"/>
</head>
<body>
	<form action="listOrders.html" method="post" name="ordersForm" id="ordersForm">
	<div class="search_div">
		<input type="hidden" name="flag" value="${flag }" />
		订单编号：<input type="text" name="ordersNo" value="${ordersNo }"/>
		公司名称：<input type="text" name="companyName" value="${companyName }"/>
		域名：<input type="text" name="domain" value="${domain }"/>
		<a href="javascript:search();" class="myBtn"><em>查询</em></a>
	</div>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
		<tr class="main_head">
			<th>序号</th>
			<th>订单编号</th>
			<th>公司名称</th>
			<th>主域名</th>
			<th style="display: none">地址</th>
			<th>联系人</th>
			<th>电话号码</th>
			<th>下单时间</th>
			<th>订单状态</th>
		</tr>
		<c:choose>
			<c:when test="${not empty ordersList }">
				<c:forEach items="${ordersList }" var="orders" varStatus="vs">
					<tr class="main_info">
						<td>${vs.index+1 }</td>
						<td><a href="javascript:ordersDetail(${orders.id });">${orders.ordersNo }</a></td>
						<td>${orders.customer.companyName }</td>
						<td>${orders.customer.domain }.partner.onmschina.cn</td>
						<td style="display: none" >${orders.customer.provincialName }${orders.customer.cityName }${orders.customer.regionName }</td>
						<td>${orders.customer.lastName }${orders.customer.firstName }</td>
						<td>${orders.customer.phoneNumber }</td>
						<td><fmt:formatDate value="${orders.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${statusMap[orders.status]}</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr class="main_info">
					<td colspan="9">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
		<div class="page_and_btn">
		<c:if test="${flag != 'audit'}">
		<div>
			<a href="javascript:addCustomer();" class="myBtn"><em>新增</em></a>
		</div>
		</c:if>
		${page.pageStr }
	</div>
	</form>
	<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		function addCustomer(){
			window.location="../customer/forAdd.html";
		}
		
		function ordersDetail(id){
			var url = "getOrders.html?id="+id+"&flag=${flag}";
			window.location=url;
		}
		
		function search(){
			$("#ordersForm").submit();
		}
	</script>
</body>
</html>