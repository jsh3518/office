<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>My Test</title>
<link type="text/css" rel="stylesheet" href="css/main.css"/>
</head>
<body>
	<form action="puCode.html?classId=${classId }" method="post" name="pubCodeForm" id="pubCodeForm">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
		<tr class="main_head">
			<th>序号</th>
			<th>编码</th>
			<th>名称</th>
			<th>排序</th>
			<th>是否有效</th>
			<th>操作</th>
		</tr>
		<c:choose>
			<c:when test="${not empty pubCodeList }">
				<c:forEach items="${pubCodeList }" var="pubCode"  varStatus="vs">
					<tr class="main_info">
						<td>${vs.index+1 }</td>
						<td>${pubCode.code }</td>
						<td>${pubCode.name }</td>
						<td>${pubCode.sort }</td>
						<td><c:if test="${pubCode.valid==1 }">是</c:if><c:if test="${pubCode.valid==0 }">否</c:if></td>
						<td><a href="javascript:editPubCode('${pubCode.id }')">修改</a> | <a href="javascript:deletePubCode('${pubCode.id }')">删除</a></td>
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
			<a href="javascript:addPubCode('${classId }');" class="myBtn"><em>新增</em></a>
		</div>
		${pubCode.page.pageStr }
	</div>
	</form>
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		function addPubCode(classId){
			var url = "pubCode/queryPubCode.html?classId="+classId;
			var dg = new $.dialog({
				title:'基础信息',
				id:'detail',
				width:600,
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
		
		function search(){
			$("#customerForm").submit();
		}
		
		function editPubCode(id){
			var url = "pubCode/queryPubCode.html?id="+id;
			var dg = new $.dialog({
				title:'基础信息',
				id:'detail',
				width:600,
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
		
		//删除
		function deletePubCode(id){
			if(confirm("确定要删除该记录？")){
				var url = "pubCode/delete.html?id="+id;
				$.get(url,function(data){
					alert(data);
					document.location.reload();
				});
			}
		}
		
	</script>
</body>
</html>