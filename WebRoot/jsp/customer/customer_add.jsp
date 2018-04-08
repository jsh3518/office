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
<title>Office订单管理系统后台</title>
<style type="text/css">
	body{margin-left: 0px;margin-top: 0px;margin-right: 0px;margin-bottom: 0px;background: url(images/login_bg.jpg);w}
	.center{width:100%;height:400px;background: url(images/login_bg.jpg);}
	.title{text-align:left;padding-left:20px;font-family:Arial,Helvetica,sans-serif;font-size:14px;height:25px;line-height:25px;color:#666666;font-weight:bold;}
	.info{font-family:Arial,Helvetica,sans-serif;font-size:12px;height:25px;line-height:25px;color:#333333;margin-bottom:25px}
	.select{width:100px;height:25px;border:1px solid #ccc;vertical-align:middle;font-size:12px;color:#222;}
	.btn{width:60px;height:25px;border-width:0px;background-image: url(images/btn-bg2.gif);letter-spacing: 5px;margin-right:15px;cursor: pointer;}
	.right{width:310px;margin-left:20px;vertical-align:middle;text-align: left;float:left;}
	.input{width:100%;height:25px;line-height:25px; align:center;vertical-align:middle;font-size:12px;color:#222;background-color:#fff;border:1px solid #ccc;border-radius:4px;}
	.left{color:#262626;text-align:right;width:100px;vertical-align:middle;float:left}
	.error{float: left;color: #ea644a;}
</style>
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
</head>
<body>
<div style="width:900px;height:auto;margin-left:auto;margin-right:auto;">
	<div class="center">
		<form action="orders/forNew.html" method="post" name="customerForm" onsubmit="return check();">
			<div class="info" style="text-align:right;margin-top: 15px">
				<input type="submit" name="registBtn" id="registBtn" value="下一步" class="btn"/>
				<input type="button" name="backBtn" id="backBtn" value="返回" class="btn" onclick="forBack()"/>
			</div>
			<div style="width: 50%;float: left">
				<div class="title">
					公司
				</div>
				<div class="info">
					<label class="left">国家/地区</label>
					<div class="right">中国</div>
				</div>
				<div class="info">
					<label class="left">公司名称<font color="red">*</font></label>
					<div class="right">
	      			<input name="companyName" id="companyName" type="text" value="${customer.companyName }" class="input"/>
	     		</div>
				</div>
				<div class="info">
					<label class="left">主域名<font color="red">*</font></label>
					<div class="right">
						<div id="domainDiv">
							<input type="text" name="domain" id="domain" class="input" value="${customer.domain }" onblur="valDomain()" style="width: 180px"/>.partner.onmschina.cn
						</div>
					</div>
				</div>
				<div class="info">
					<label class="left">邮政编码<font color="red">*</font></label>
					<div class="right">
						<input type="text" name="postalCode" id="postalCode" class="input" value="${customer.postalCode }"/>
					</div>
				</div>
				<div class="info">
					<label class="left">公司地址<font color="red">*</font></label>
					<div class="right">
						<select name="province" id="province" class="select" onChange="changeOrg('city',this.value)">
							<option value="">请选择</option>
							<c:forEach items="${provinList}" var="organ">
								<option <c:if test="${customer.province == organ.orgId}"> selected="selected"</c:if> value="${organ.orgId }">${organ.orgName }</option>
							</c:forEach>
						</select>
						<select name="city" id="city" class="select" onChange="changeOrg('region',this.value)">
							<option value="">请选择</option>
							<c:forEach items="${city}" var="organ">
								<option <c:if test="${customer.city == organ.orgId}"> selected="selected"</c:if> value="${organ.orgId }">${organ.orgName }</option>
							</c:forEach>
						</select>
						<select name="region" id="region" class="select">
						<option value="">请选择</option>
						<c:forEach items="${regionList}" var="organ">
							<option <c:if test="${customer.region == organ.orgId}"> selected="selected"</c:if> value="${organ.orgId }">${organ.orgName}</option>
						</c:forEach>
					</select>
					</div>
				</div>
				<div class="info">
					<label class="left"><font color="red">*</font></label>
					<div class="right">
						<input type="text" name="address" id="address" class="input" value="${customer.address }"/>
					</div>
				</div>
			</div>
			<div style="width: 50%;float: left">
				<div class="title">
					主要联系人
				</div>
				<div class="info"></div>
				<div class="info">
					<label class="left">名字<font color="red">*</font></label>
					<div class="right">
						<input type="text" name="firstName" id="firstName" class="input" value="${customer.firstName }"/>
					</div>
				</div>
				<div class="info">
					<label class="left">姓氏<font color="red">*</font></label>
					<div class="right">
						<input type="text" name="lastName" id="lastName" class="input" value="${customer.lastName }"/>
					</div>
				</div>
				<div class="info">
					<label class="left">电话号码</label>
					<div class="right">
						<input type="text" name="phoneNumber" id="phoneNumber" class="input" value="${customer.phoneNumber }"/>
					</div>
				</div>
				<div class="info">
					<label class="left">电子邮箱地址<font color="red">*</font></label>
					<div class="right">
						<input type="text" name="email" id="email" class="input" value="${customer.email }"/>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
	<script type="text/javascript">
		$.ajaxSetup({ 
		    async : false 
		});  
		$(document).ready(function(){

		});
	
		function genTimestamp(){
			var time = new Date();
			return time.getTime();
		}
		
		function forBack(){
			window.location="<%=basePath%>default.html";
		}
		
		var con;
		function check(){
			con=1;
			$("#registBtn").attr("disabled",true);
			$("#backBtn").attr("disabled",true);
			valName();
			valDomain();
			valAddress();
			valPostalCode();
			valFirstName();
			valLastName();
			valEmail();
			valPhone();
			if(con==1){
				return true;
			}else{
				$("#registBtn").attr("disabled",false);
				$("#backBtn").attr("disabled",false);
				return false;
			}
		}
		
		function valName(){
			var companyName = $("#companyName");
			companyName.siblings('.error').remove();
		  if( companyName.val() ==""){
			  companyName.after('<div class="error">请输入公司名称</div>');
		    con = 0;
		  }
		}

		//判断域名
		function valDomain(){
			var domainDiv = $("#domainDiv");
			domainDiv.siblings('.error').remove();
			domainDiv.siblings('.message').remove();
			var reg =/^[0-9a-zA_Z]+$/;
		  if($("#domain").val() ==""){
			  domainDiv.after('<div class="error">请输入域名！</div>');
			  con = 0;
		  }else if(!reg.test($("#domain").val())){
			  domainDiv.after('<div class="error">域名仅可以包含字母和数字，请重新输入！</div>');
			  con = 0;
		  }else if($("#domain").val() !=""){
			  var url = "customer/domain.html";
				var postData = {"domain":$("#domain").val()+".partner.onmschina.cn"};
				$.post(url,postData,function(data){
					if(data==200){
						domainDiv.after('<div class="error">域名已被占用！</div>');
						con = 0;
					}else if(data==404){
						domainDiv.after('<div class="message">'+$("#domain").val()+'.partner.onmschina.cn可用！</div>');
					}else{
						domainDiv.after('<div class="error">域名验证失败！</div>');
					}
					
				});
		  }
		}

		//判断地址
		function valAddress(){
			var province = $("#province");
			var address = $("#address");
			$("#address").siblings('.error').remove();
			if(province.val() ==""||address.val() ==""){
				$("#address").after('<div class="error">请输入公司地址！</div>');
				con = 0;
			}else if(province.val()==710000||province.val()==810000||province.val()==820000){
				$("#address").after('<div class="error">香港、澳门、台湾地区客户无法在本系统注册，请您理解！</div>');
				con = 0;
			}
		}
		
		//判断邮政编码
		function valPostalCode(){
			var postalCode = $("#postalCode");
			var reg= /^[0-9]{6,6}$/;
			postalCode.siblings('.error').remove();
			if(postalCode.val() ==""){
				postalCode.after('<div class="error">请输入邮政编码！</div>');
				con = 0;
			}else if(!reg.test(postalCode.val())){
				postalCode.after('<div class="error">请输入正确的邮政编码！</div>');
				con = 0;
			}
		}
		
		//判断名字
		function valFirstName(){
			var firstName = $("#firstName");
			firstName.siblings('.error').remove();
			if(firstName.val() ==""){
				firstName.after('<div class="error">请输入联系人名字！</div>');
				con = 0;
			}
		}
		
		//判断姓氏
		function valLastName(){
			var lastName = $("#lastName");
			lastName.siblings('.error').remove();
			if(lastName.val() ==""){
				lastName.after('<div class="error">请输入联系人姓氏！</div>');
				con = 0;
			}
		}
		
		//判断邮箱
		function valEmail(){
			var email = $("#email");
			email.siblings('.error').remove();
		  reg=/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
		  if(email.val()==""){
			  email.after('<div class="error">请输入电子邮箱地址！</div>');
		    con = 0;
		  }else if(!reg.test(email.val())){
			  email.after('<div class="error">电子邮箱格式错误！</div>');
			  con = 0;
		  }
		}

		//判断电话号码
		function valPhone(){
			var phoneNumber = $("#phoneNumber");
			phoneNumber.siblings('.error').remove();
			if(phoneNumber.val()!=""){
				var reg =/^(1[3|4|5|8|7][0-9]\d{4,8})|(0\d{2,3}-?\d{7,8})|(0\d{23}-?\d{8}(-?\d{1,4})?)|(8\d{23}-?\d{7,8}(-?\d{1,4})?)$/i;//验证电话号码正则
			  if(!reg.test(phoneNumber.val())){
				  phoneNumber.after('<div class="error">电话号码格式有误！</div>');
				  con = 0;
			  }else if(phoneNumber.val().length<8){
				  phoneNumber.after('<div class="error">电话号码长度有误！</div>');
			    con = 0;
			  }
		  } 
		}
		
		//组织机构onchange事件
		function changeOrg(type,parentId){
			var url = "organ/getOrgans.html";
			var select = $("#"+type);
			var level;
			if(type=="city"){
				$("#region").html("");
				$("#region").append("<option value=''>请选择</option>");
				level = 2;
			}else if(type=="region"){
				level = 3;
			}
			select.html("");
			select.append("<option value=''>请选择</option>");

			var postData = {"level":level,"parentId":parentId};
			$.post(url,postData,function(data){
				var organ = JSON.parse(data);
				if(organ.length>0){
					for(var i=0;i<organ.length;i++){
						select.append("<option value ='" + organ[i].orgId + "'> "+ organ[i].orgName +"</option>");
					}
				}
			});
		}
	</script>
</body>
</html>