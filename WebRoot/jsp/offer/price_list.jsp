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
	<form action="getOfferPrice.html" method="post" name="offerPriceForm" id="offerPriceForm">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
		<tr class="main_head">
			<th width="15%">序号</th>
			<th style="display: none">id</th>
			<th style="display: none">产品ID</th>
			<th width="25%">产品名称</th>
			<th width="15%">单价（月）</th>
			<th width="15%">单价（年）</th>
			<th width="15%">单位</th>
			<th width="15%">操作</th>
		</tr>
		<c:choose>
			<c:when test="${not empty listOfferPrice }">
				<c:forEach items="${listOfferPrice }" var="offerPrice" varStatus="vs">
					<tr class="main_info">
						<td>${vs.index+1 }</td>
						<td style="display: none">${offerPrice.id}</td>
						<td style="display: none">${offerPrice.offerId}</td>
						<td style="text-align: left;">${offerPrice.offerName}</td>
						<td>${offerPrice.priceMonth}</td>
						<td>${offerPrice.priceYear}</td>
						<td>${offerPrice.unitName}<c:if test="${not empty offerPrice.unitName}">/许可证</c:if></td>
						<td><a href="javascript:detail('${offerPrice.id}','${offerPrice.offerId}','${offerPrice.offerName}','query');">查看</a> | <a href="javascript:detail('${offerPrice.id}','${offerPrice.offerId}','${offerPrice.offerName}','edit');">修改</a></td>
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
		${offerPrice.page.pageStr }
	</div>
	</form>
	<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="../js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		function forBack(){
			window.location="../customer.html";
		}
		
		function detail(id,offerId,offerName,method){
			var dg = new $.dialog({
				title:'价格管理',
				id:'price_edit',
				width:540,
				height:400,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				btnBar:false,
				resize:false,
				page:'detail.html?id='+id+"&offerId="+offerId+"&offerName1="+offerName+"&method="+method
			});
    	dg.ShowDialog();
		}
		
		
		function search(){
			$("#ordersForm").submit();
		}
	</script>
</body>
</html>