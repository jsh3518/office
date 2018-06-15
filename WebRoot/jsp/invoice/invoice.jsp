<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发票信息列表</title>
<link type="text/css" rel="stylesheet" href="css/main.css"/>
</head>
<body>
	<form action="invoice.html" method="post" name="invoiceForm" id="invoiceForm">
	<div class="search_div">
		公司名称：<input type="text" name="company" value="${invoice.company }"/>
		<a href="javascript:search();" class="myBtn"><em>查询</em></a>
	</div>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
		<tr class="main_head">
			<th><input type="checkbox" name="sltAll" id="sltAll" onclick="sltAllUser()"/></th>
			<th>序号</th>
			<th>公司名称</th>
			<th>纳税人识别号</th>
			<th>公司地址</th>
			<th>开户行</th>
			<th>银行账号</th>
			<th>电话</th>
		</tr>
		<c:choose>
			<c:when test="${not empty invoiceList}">
				<c:forEach items="${invoiceList}" var="invoice" varStatus="vs">
				<tr class="main_info">
				<td><input type="checkbox" name="invoiceIds" id="invoiceIds${invoice.id }" value="${invoice.id }"/></td>
				<td>${vs.index+1}</td>
				<td>${invoice.company }</td>
				<td>${invoice.tax }</td>
				<td>${invoice.address }</td>
				<td>${invoice.bank }</td>
				<td>${invoice.account }</td>
				<td>${invoice.phone }</td>
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
		${invoice.page.pageStr }
	</div>
	</form>
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		function sltAllUser(){
			if($("#sltAll").attr("checked")){
				$("input[name='invoiceIds']").attr("checked",true);
			}else{
				$("input[name='invoiceIds']").attr("checked",false);
			}
		}
		
		function add(){
// 			window.location="invoice/add.html";

			var dg = new $.dialog({
				title:'新增信息',
				id:'invoice_new',
				width:330,
				height:300,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:false,
				page:'invoice/add.html'
				});
    		dg.ShowDialog();
		}
		
		function search(){
			$("#invoiceForm").submit();
		}
	</script>
</body>
</html>