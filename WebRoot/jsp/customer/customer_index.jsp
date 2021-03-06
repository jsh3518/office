<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link type="text/css" rel="stylesheet" href="../css/main.css"/>
</head>
<body>
	<form action="query.html" method="post" name="customerForm" id="customerForm">
	<div class="search_div">
		公司名称：<input type="text" name="companyName" value="${companyName }"/>
		域名：<input type="text" name="domain" value="${domain }"/>
		<input type="text" name="flag" value="${flag }" style="display: none"/>
		<a href="javascript:search();" class="myBtn"><em>查询</em></a>
	</div>
	<table id="table" width="98%" border="0" cellpadding="0" cellspacing="0" class="main_table">
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
						<td>${customer.companyName }</td>
						<td>${customer.domain }.partner.onmschina.cn</td>
						<td>${customer.provincialName }${customer.cityName }${customer.regionName }</td>
						<td>${customer.lastName }${customer.firstName }</td>
						<td>${customer.phoneNumber }</td>
						<td>${customer.email }</td>
						<td><a href="javascript:select('${customer.id}');">选择</a></td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr class="main_info">
					<td colspan="8">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
		<div class="page_and_btn">${page.pageStr }</div>
	</form>
	<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
			var obj = parent.document.getElementById("customerFrame"); //取得父页面IFrame对象
			obj.height = this.body.clientHeight+10; //调整父页面中IFrame的高度为此页面的高度 
		});
		
		function search(){
			$("#customerForm").submit();
		}
		
		//选择客户
		function select(customerId){
			parent.select(customerId);
		}

	</script>
</body>
</html>