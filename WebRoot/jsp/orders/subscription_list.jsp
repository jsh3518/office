<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订阅信息列表</title>
<link type="text/css" rel="stylesheet" href="../css/main.css"/>
</head>
<body>
	<form action="querySubscription.html?customerId=${customerId }" method="post" name="ordersForm" id="ordersForm">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
		<tr class="main_head">
			<th width="5%">序号</th>
			<th style="display: none">id</th>
			<th width="25%">订阅ID</th>
			<th width="15%">订阅产品</th>
			<th width="10%">许可证</th>
			<th width="10%">生效时间</th>
			<th width="10%">到期时间</th>
			<th width="15%">经销商</th>
			<th width="10%">操作</th>
		</tr>
		<c:choose>
			<c:when test="${not empty subscriptionList }">
				<c:forEach items="${subscriptionList }" var="subscription" varStatus="vs">
					<tr class="main_info">
						<td>${vs.index+1 }</td>
						<td style="display: none">${subscription["id"]}</td>
						<td>${subscription["subscriptionid"]}</td>
						<td style="text-align: left;">${subscription["offername"]}</td>
						<td>${subscription["quantity"] }</td>
						<td><fmt:formatDate value="${subscription[\"effecttime\"]}" pattern="yyyy-MM-dd"/></td>
						<td><fmt:formatDate value="${subscription[\"duetime\"]}" pattern="yyyy-MM-dd"/></td>
						<td>${subscription["reseller"] }</td>
						<td><a href="javascript:renew(${subscription['detailid']});">续订</a> | <a href="javascript:increase(${subscription['id']});">增加坐席</a></td>
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
		<div>
			<a href="javascript:forBack();" class="myBtn"><em>返回</em></a>
		</div>
		${page.pageStr }
	</div>
	</form>
	<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		function forBack(){
			window.location="../customer/query.html";
		}
		
		//续订
		function renew(detailId){
			window.location="renewOrders.html?detailId="+detailId;
		}
		
		//增加坐席
		function increase(id){
			window.location="forIncrease.html?id="+id;
		}
		
		function search(){
			$("#ordersForm").submit();
		}
	</script>
</body>
</html>