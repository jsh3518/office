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
<base href="<%=basePath%>">
<title>产品信息</title>
<link type="text/css" rel="stylesheet" href="css/content.css"/>
</head>
<body>
	<div style="width: 900px; height: auto; margin-left: auto; margin-right: auto;">
		<div class="center">
			<form action="offer/rootOffers.html" method="post" name="offerForm" id="offerForm">
				<div class="info" style="width: 100%">
				  <ul id="tab_list" class="tab">
				  	<c:forEach items="${offerList}" var="offer" varStatus="status">
							<li id="${offer.offerId}" onclick="clickTab('${offer.offerId}')" >${offer.offerName}</li>
						</c:forEach>
		   		</ul>
				</div>
				<div class="info" style="width: 100%;margin-top: 20px">	
					<div class="left" id="offerDiv"	style="width: 100%; height: 410px;">
						<iframe width="100%" height="100%" frameborder="0"	scrolling="auto" src="" name="offerFrame"	id="offerFrame"></iframe>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#tab_list li:first").click();
		});
		
		function clickTab(id){
			$("ul.tab li").removeClass("color");
			$("#"+id).addClass("color");
			$("#offerFrame").attr("src","offer/getOffer.html?parent="+id);
		}
	</script>
</body>
</html>