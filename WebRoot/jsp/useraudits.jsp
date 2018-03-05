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
<title>代理商审核</title>
<link type="text/css" rel="stylesheet" href="css/main.css"/>
</head>
<body>
	<form action="user/toAudit.html" method="post" name="userForm" id="userForm">
	<div class="search_div">
		用户名：<input type="text" name="loginname" value="${user.loginname }"/>
		公司名称：<input type="text" name="username" value="${user.username }"/>
		登录日期：<input type="text" name="lastLoginStart" value="<fmt:formatDate value="${user.lastLoginStart}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker()" readonly="readonly" style="width:70px;"/> -
		<input type="text" name="lastLoginEnd" value="<fmt:formatDate value="${user.lastLoginEnd}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker()" readonly="readonly" style="width:70px;"/>
		<a href="javascript:search();" class="myBtn"><em>查询</em></a>
	</div>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
		<tr class="main_head">
			<th><input type="checkbox" name="sltAll" id="sltAll" onclick="sltAllUser()"/></th>
			<th>序号</th>
			<th>用户名</th>
			<th>公司名称</th>
			<th>税号</th>
			<th>联系人</th>
			<th>联系电话</th>
			<th width="160">邮箱</th>
			<th width="160">地址</th>
			<th>邮编</th>
			<th>操作</th>
		</tr>
		<c:choose>
			<c:when test="${not empty userList}">
				<c:forEach items="${userList}" var="user" varStatus="vs">
				<tr class="main_info">
				<td><input type="checkbox" name="userIds" id="userIds${user.userId}" value="${user.userId }"/></td>
				<td>${vs.index+1}</td>
				<td>${user.loginname }</td>
				<td>${user.username }</td>
				<td>${user.tax }</td>
				<td>${user.contact }</td>
				<td>${user.phone }</td>
				<td>${user.email }</td>
				<td>${user.province.orgName }${user.city.orgName }${user.county.orgName }</td>
				<td>${user.post }</td>
				<td><a href="javascript:auditUser(${user.userId });">审核</a></td>
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
		<div>
			<a href="javascript:addUser();" class="myBtn"><em>新增</em></a>
			<a href="javascript:exportUser();" class="myBtn"><em>导出</em></a>
		</div>
		${user.page.pageStr }
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
				$("input[name='userIds']").attr("checked",true);
			}else{
				$("input[name='userIds']").attr("checked",false);
			}
		}
		
		function addUser(){
			//$(".shadow").show();
			var dg = new $.dialog({
				title:'新增用户',
				id:'user_new',
				width:330,
				height:300,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:false,
				page:'user/add.html'
				});
    		dg.ShowDialog();
		}
		
		function editUser(userId){
			var dg = new $.dialog({
				title:'修改用户',
				id:'user_edit',
				width:330,
				height:300,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				resize:false,
				page:'user/edit.html?userId='+userId
				});
    		dg.ShowDialog();
		}
		
		function delUser(userId){
			if(confirm("确定要删除该记录？")){
				var url = "user/delete.html?userId="+userId;
				$.get(url,function(data){
					if(data=="success"){
						document.location.reload();
					}
				});
			}
		}
		
		function editRights(userId){
			var dg = new $.dialog({
				title:'用户授权',
				id:'auth',
				width:280,
				height:370,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				resize:false,
				page:'user/auth.html?userId='+userId
				});
    		dg.ShowDialog();
		}
		
		function search(){
			$("#userForm").submit();
		}
		
		function auditUser(userId){
			window.location="<%=basePath%>user/forAudit.html?userId="+userId;
		}
	</script>
</body>
</html>