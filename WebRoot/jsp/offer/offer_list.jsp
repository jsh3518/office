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
<title>产品信息列表</title>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/main.css"/>
</head>
<body style="background-color: #def">
	<form action="<%=basePath%>offer/getOffer.html" method="post" name="offerListForm" id="offerListForm">
		<div class="search_div">
			产品ID：<input type="text" name="offerId" value="${offer.offerId }"/>
			产品名称：<input type="text" name="offerName" value="${offer.offerName }"/>
			是否有效：
			<select name="valid" id="valid" class="select">
				<option value=""></option>
				<option <c:if test="${offer.valid=='1'}"> selected="selected"</c:if> value="1">是</option>
				<option <c:if test="${offer.valid=='0'}"> selected="selected"</c:if> value="0">否</option>
			</select>
			<input type="text" name="parent" value="${offer.parent }" style="display: none"/>
			&nbsp;<a href="javascript:search();" class="myBtn" style="margin-bottom: 4px;"><em>查询</em></a>
		</div>
		<table id="table" width="98%" border="0" cellpadding="0" cellspacing="0" class="main_table">
			<tr class="main_head">
				<th>序号</th>
				<th>产品ID</th>
				<th>产品名称</th>
				<th>最小数量</th>
				<th>最大数量</th>
				<th>排序</th>
				<th>是否有效</th>
				<th>操作</th>
			</tr>
			<c:choose>
				<c:when test="${not empty listOffer }">
					<c:forEach items="${listOffer }" var="offer"  varStatus="vs">
						<tr class="main_info">
							<td>${vs.index+1 }</td>
							<td>${offer.offerId }</td>
							<td align="left">${offer.offerName }</td>
							<td>${offer.mininum }</td>
							<td>${offer.maxinum }</td>
							<td>${offer.sort }</td>
							<td><c:if test="${offer.valid == 1}">是</c:if><c:if test="${offer.valid == 0}">否</c:if></td>
							<td><a href="javascript:editOffer('${offer.offerId}');">修改</a> | <a href="javascript:deleteOffer('${offer.offerId}');">删除</a></td>
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
		<div class="page_and_btn">
			<div>
				<a href="javascript:addOffer();" class="myBtn"><em>新增</em></a>
			</div>
			${offer.page.pageStr }
		</div>
	</form>
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		function search(){
			$("#offerListForm").submit();
		}
		
		function editOffer(offerId){
			var dg = new $.dialog({
				title:"产品信息",
				id:"offer_edit",
				width:500,
				height:390,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				resize:false,
				page:"<%=basePath%>offer/detail.html?offerId="+offerId
				});
    		dg.ShowDialog();
		}
		
		function addOffer(){
			var dg = new $.dialog({
				title:"新增产品",
				id:"offer_new",
				width:500,
				height:390,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				resize:false,
				page:"<%=basePath%>offer/detail.html?parent=${offer.parent }"
				});
    		dg.ShowDialog();
		}
		
		
		function deleteOffer(offerId){
			if(confirm("请确认是否删除？")){
				var url = "<%=basePath%>offer/deleteOffer.html";
				var postData = {"offerId":offerId};
				$.post(url,postData,function(data){
					if(data=="success"){
						alert("删除成功！");
					}else{
						alert("删除失败！");
					}
					window.location="<%=basePath%>offer/getOffer.html?parent=${offer.parent }";
				})
			}
		}
	</script>
</body>
</html>