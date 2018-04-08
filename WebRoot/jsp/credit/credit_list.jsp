<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<title>代理商列表</title>
<link type="text/css" rel="stylesheet" href="css/main.css"/>
</head>
<body>
	<form action="credit.html" method="post" name="creditForm" id="creditForm">
	<div class="search_div">
		公司名称：<input type="text" name="agentName" value="${credit.agentName }"/>
		<a href="javascript:search();" class="myBtn"><em>查询</em></a>
	</div>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
		<tr class="main_head">
			<th>序号</th>
			<th>公司名称</th>
			<th>信用等级</th>
			<th>信用额度（元）</th>
			<th>账期</th>
			<th>操作</th>
		</tr>
		<c:choose>
			<c:when test="${not empty creditList}">
				<c:forEach items="${creditList}" var="credit" varStatus="vs">
				<tr class="main_info">
				<td>${vs.index+1}</td>
				<td>${credit.agentName }</td>
				<td>${credit.creditRating }</td>
				<td>${credit.creditLine }</td>
				<td>${credit.account }${credit.unit }</td>
				<td><a href="javascript:queryCredit(${credit.agentId });">维护</a></td>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr class="main_info">
					<td colspan="7">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
	<div class="page_and_btn">
		${credit.page.pageStr }
	</div>
	</form>
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		function search(){
			$("#creditForm").submit();
		}
		
		function queryCredit(agentId){
			window.location="<%=basePath%>credit/queryCredit.html?agentId="+agentId;
		}
		
	</script>
</body>
</html>