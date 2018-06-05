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
	<div id="table" style="overflow: auto">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="main_table">
			<tr class="main_head">
				<th>序号</th>
				<th>公司名称</th>
				<th>主域名</th>
				<c:if test="${roleId==2 }">
					<th>经销商</th>
				</c:if>
				<th>订阅产品</th>
				<th>坐席数量</th>
				<th>生效时间</th>
				<th>到期时间</th>
				<c:if test="${flag=='0' }">
					<th>操作</th>
				</c:if>
			</tr>
			<c:choose>
				<c:when test="${not empty ordersList }">
					<c:forEach items="${ordersList }" var="orders" varStatus="vs">
						<tr class="main_info">
							<td>${vs.index+1 }</td>
							<td>${orders.customer.companyName }</td>
							<td>${orders.customer.domain }.partner.onmschina.cn</td>
							<c:if test="${roleId==2 }">
								<td>${orders.reseller }</td>
							</c:if>
							<td align="left"><a href="javascript:ordersDetail(${orders.id });">${orders.ordersNo }</a></td>
							<td></td>
							<td></td>
							<td></td>
							<c:if test="${flag=='0' }">
								<td><a href="javascript:deleteOrders(${orders.id },'${orders.status }');">删除</a></td>
							</c:if>
						</tr>
						<c:if test="${not empty orders.detailList }">
							<c:forEach items="${orders.detailList }" var="ordersDetail" varStatus="vs2">
							<tr style="height:24px;line-height:24px;" <c:if test="${vs.index%2==0 }"> class="main_table_even" </c:if>>
								<td></td>
								<td></td>
								<td></td>
								<c:if test="${roleId==2 }">
									<td></td>
								</c:if>
								<td align="left"><img <c:choose><c:when test="${vs2.index+1==orders.detailList.size() }">src="../images/joinbottom.gif"</c:when><c:otherwise>src="../images/join.gif"</c:otherwise></c:choose> style="vertical-align: middle;"/>${ordersDetail.offerName }</td>
								<td>${ordersDetail.quantity }</td>
								<td><fmt:formatDate value="${ordersDetail.effectTime }" pattern="yyyy-MM-dd"/>&nbsp;</td>
								<td><fmt:formatDate value="${ordersDetail.dueTime }" pattern="yyyy-MM-dd"/>&nbsp;</td>
								<c:if test="${flag=='0' }">
									<td></td>
								</c:if>
							</tr>
							</c:forEach>
						</c:if>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="main_info">
						<td colspan="9">没有相关数据</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
	</div>
	<div class="page_and_btn">
		${page.pageStr }
	</div>
	</form>
	<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			resetHeight();
			$(".main_info:even").addClass("main_table_even");
		});
		//设置高度
		function resetHeight(){
			var height = $(window).height()<$(document).height()?$(window).height():$(document).height();
			$("#table").height(height-90);
		}
		$(window).resize(function() {
			resetHeight();
		});
		function addCustomer(){
			window.location="../customer/forAdd.html";
		}
		
		function ordersDetail(id){
			var url = "getOrders.html?id="+id+"&flag=${flag}";
			window.location=url;
		}
		
		//删除订单
		function deleteOrders(id,status){
			if(status==0||status==3){
				if(confirm("确定要删除该记录？")){
					var url = "deleteOrders.html?id="+id;
					$.get(url,function(data){
						if(data=="success"){
							alert("订单已删除！");
						}
						document.location.reload();
					});
				}
			}else{
				alert("订单已提交，不能删除！");
				return;
			}
		}
		
		function search(){
			$("#ordersForm").submit();
		}
	</script>
</body>
</html>