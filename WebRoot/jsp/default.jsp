<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <base href="<%=basePath%>">
    
  <title>佳杰科技</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=basePath%>/css/css.css" rel="stylesheet" type="text/css"/>
	<link href="<%=basePath%>/css/softwhy.css" rel="stylesheet" type="text/css"/>
	<script src="<%=basePath%>/js/jquery-1.5.1.min.js"></script>
	<style type="text/css">

	#softwhy a
	{
	 text-decoration:underline;
	 color:red;
	}
	.phcolor{ color:#999;}
	</style>
  </head>
  <body style="text-align: center;">
   	<div class="menu">
    <div id="menu1" class="menusel">
      <h2><a>家用版</a></h2>
      <div class="position">
        <ul class="clearfix typeul">
         <li><a href="https://products.office.com/zh-cn/compare-all-microsoft-office-products?tab=1">计划与定价</a></li>
         <li><a href="https://products.office.com/zh-cn/office-365-home">家用版</a></li>
         <li><a href="https://products.office.com/zh-cn/office-365-personal">个人版</a></li>
         <li><a href="https://products.office.com/zh-cn/office-online/documents-spreadsheets-presentations-office-online">Office Online</a></li>
         <li class="lli"><a href="#">其他</a></li>
        </ul>
      </div>
    </div>
  
   <div id="menu2" class="menusel">
      <h2><a>商业版</a></h2>
      <div class="position">
        <ul class="clearfix typeul">
         <li><a href="https://products.office.com/zh-cn/compare-all-microsoft-office-products?tab=2">计划与定价</a></li>
         <li><a href="https://products.office.com/zh-cn/business/explore-office-365-for-business">小型企业版</a></li>
         <li><a href="https://products.office.com/zh-cn/business/enterprise-productivity-tools">企业版</a></li>
         <li><a href="https://products.office.com/zh-cn/business/enterprise-firstline-workers">一线员工</a></li>
         <li><a href="https://products.office.com/zh-CN/business/office">查看所有商业版</a></li>
         <!--  
        <c:forEach var="ms" items="${requestScope.msls}">
            <li><a href="findSer!findCompanySerById.action?id=${ms.id }">${ms.servicename }</a></li>
          </c:forEach>
         -->
        </ul>
      </div>
    </div>
    <div id="menu3" class="menusel">
      <h2><a>教育版</a></h2>
      <div class="position">
        <ul class="clearfix typeul">
          <li><a href="https://products.office.com/zh-cn/home-and-student">面向学生和老师</a></li>
          <li><a href="https://products.office.com/zh-cn/academic/office-365-education-plan">面相学校</a></li>
          <li class="lli"><a href="pageNews!findNewsList.action?beginIndex=0&newstype=3">技术</a></li>
        </ul>
      </div>
    </div>
      <div id="menu4" class="menusel">
      <h2><a>应用程序</a></h2>
      <div class="position">
        <ul class="clearfix typeul">
          <li><a href="https://products.office.com/zh-cn/outlook/email-and-calendar-software-microsoft-outlook">Outlook</a></li>
          <li><a href="https://products.office.com/zh-cn/onedrive-for-business/online-cloud-storage">OneDrive</a></li>
          <li><a href="https://products.office.com/zh-cn/word">Word</a></li>
          <li><a href="https://products.office.com/zh-cn/excel">Excel</a></li>
          <li><a href="https://products.office.com/zh-cn/powerpoint">PowerPoint</a></li>
        </ul>
      </div>
    </div>
    </div>
    <div class="banner">
	  	<div style="position:relative; height:300px;">
			  <div class="banner1"></div>
			  <div class="bannerWrap1">
			    <ul class="showList">
			      <li class="hand special" img="images/images/img_1.jpg"></li>
			     	<li class="hand" img="images/images/img_2.jpg"></li>
			      <li class="hand" img="images/images/img_3.jpg"></li>
			      <li class="hand" img="images/images/img_4.jpg"></li>
			      <li class="hand" img="images/images/img_5.jpg"></li> 
			    </ul>
  			</div>
			</div>
		</div>
		<script src="<%=basePath%>/js/meun.js"></script>
</body>
</html>
