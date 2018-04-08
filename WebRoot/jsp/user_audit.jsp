<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<title>Office订单管理系统-用户审核</title>
<style type="text/css">
	body{margin-left: 0px;margin-top: 0px;margin-right: 0px;margin-bottom: 0px;}
	.header{width:100%;height:50px;margin-top:5px;background-color: #d9dde8;}
	.center{width:100%;height:350px;margin-top:1px;background: url(images/login_bg.jpg);}
	.audit_left{float:left;width:50%;height:auto;margin-top: 10px}
	.login_title{text-align:center;width:100%;font-family: Arial, Helvetica, sans-serif;font-size: 20px;height:50px;line-height: 50px;color: #666666;font-weight: bold;}
	.regist_info{font-family: Arial, Helvetica, sans-serif;font-size: 12px;height:30px;line-height: 30px;color: #333333;}
	.regist_label{margin-left:20px;width:120px;height:20px;line-height: 20px;vertical-align: middle;display:inline-block;text-align: right;}
	.regist_input{width:250px;height:20px;margin-left:20px;border:1px solid #7F9DB9;vertical-align: middle;}
	.file{width:250px;height:20px;margin-left:20px;vertical-align: middle;}
	.organ_select{width:82px;height:20px;border:1px solid #7F9DB9;vertical-align: middle;}
	.regist_code{width:70px;height:20px;margin-left:30px;border:1px solid #7F9DB9;vertical-align: middle;}
	.btn{width:60px;height:25px;border-width:0px;background-image: url(images/btn-bg2.gif);letter-spacing: 5px;margin-right:10px;cursor: pointer;}
	.regist_info img{vertical-align: middle;cursor: pointer;}
	.errInfo{color:red;width:180px;height:20px;line-height: 20px;vertical-align: middle;display:inline-block; }
	.left_txt{font-family: Arial, Helvetica, sans-serif;font-size: 12px;line-height: 25px;color: #666666;}
	
	.bottom{width:100%;height:auto;text-align:center;font-family: Arial, Helvetica, sans-serif;font-size: 10px;color: #ABCAD3;text-decoration: none;line-height: 20px;}
</style>
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
</head>
<body>
<div style="width:100%;height:auto;margin-left:auto;margin-right:auto;">
	<form action="user/forAudit.html" method="post" name="registForm" onsubmit="return check();" enctype="multipart/form-data">
		<div class="header">
			<div  class="login_title">
				代理商审核
			</div>
		</div>
		<div class="center">
			<div class="regist_info" style="text-align:right;width: 100%;padding-top: 10px">
				<input type="button" name="auditBtn" value="审核" class="btn" onclick="userAudit()"/>
				<input type="button" name="auditBtn" value="取消" class="btn" onclick="forBack()"/>
			</div>
			<div class="audit_left">
				<div class="regist_info">
					<label class="regist_label">用户名：</label>
					<input type="text" name="loginname" id="loginname" class="regist_input" value="${user.loginname }"/>
				</div>
				<div class="regist_info">
					<label class="regist_label">公司名称：</label>
					<input type="text" name="username" id="username" class="regist_input" value="${user.username }"/>
				</div>
				<div class="regist_info">
					<label class="regist_label">主要联系人：</label>
					<input type="text" name="contact" id="contact" class="regist_input" value="${user.contact }"/>
				</div>
				<div class="regist_info">
					<label class="regist_label">主要联系人电话号码：</label>
					<input type="text" name="phone" id="phone" class="regist_input" value="${user.phone }"/>
				</div>
				<div class="regist_info">
					<label class="regist_label">主要联系人电子邮箱：</label>
					<input type="text" name="email" id="email" class="regist_input" value="${user.email }"/>
				</div>
				<div class="regist_info">
					<label class="regist_label">公司地址：</label>
					<select name="provincialId" id="provincialId" class="organ_select" style="margin-left: 20px" onChange="changeOrg('cityId',this.value)">
						<option value="">请选择</option>
						<c:forEach items="${provinList}" var="organ">
							<option <c:if test="${user.provincialId == organ.orgId}"> selected="selected"</c:if> value="${organ.orgId }">${organ.orgName }</option>
						</c:forEach>
					</select>
					<select name="cityId" id="cityId" class="organ_select" onChange="changeOrg('countyId',this.value)">
						<option value="">请选择</option>
						<c:forEach items="${cityList}" var="organ">
							<option <c:if test="${user.cityId == organ.orgId}"> selected="selected"</c:if> value="${organ.orgId }">${organ.orgName }</option>
						</c:forEach>
					</select>
					<select name="countyId" id="countyId" class="organ_select">
						<option value="">请选择</option>
						<c:forEach items="${countyList}" var="organ">
							<option <c:if test="${user.countyId == organ.orgId}"> selected="selected"</c:if> value="${organ.orgId }">${organ.orgName}</option>
						</c:forEach>
					</select>
				</div>
				<div class="regist_info">
					<label class="regist_label"></label>
					<input type="text" name="address" id="address" class="regist_input" value="${user.address }"/>
				</div>
				<div class="regist_info">
					<label class="regist_label">邮政编码：</label>
					<input type="text" name="post" id="post" class="regist_input" value="${user.post }"/>
				</div>
				<div class="regist_info">
					<label class="regist_label">税号：</label>
					<input type="text" name="tax" id="tax" class="regist_input" value="${user.tax }"/>
				</div>
			</div>
			<div class="audit_left">
				<div class="regist_info">
					<label class="regist_label">代理商角色：</label>
					<select name="roleId" id="roleId" class="role_select">
						<option value="2">总代理商</option>
						<option value="3" selected="selected">代理商</option>
					</select>
				</div>
				<div class="regist_info">
					<label class="regist_label">营业执照扫描件：</label><br>
					<div style="height:280px;width: 450px;overflow:auto;margin-left: 20px">
						<img height="auto" width="430px" src="<%=basePath%>files/${user.file}">
					</div>
				</div>
			</div>

		</div>
		<input type="text" name="userId" id="userId" style="display: none"  value="${user.userId }"/>
	</form>
</div>
	<script type="text/javascript">
	$("input:text").attr("disabled",true);
	$(".organ_select").attr("disabled",true);
		var msg = "${msg}";
		//代理商审核
		function userAudit(){
				  var url = "user/userAudit.html";
					var postData = {"userId":$("#userId").val(),"roleId":$("#roleId").val()};
					$.post(url,postData,function(data){
						alert("审核成功！");
						window.location="<%=basePath%>user/toAudit.html";
					});
		}
		
		function forBack(){
			window.location="<%=basePath%>user/toAudit.html";
		}
	</script>
</body>
</html>