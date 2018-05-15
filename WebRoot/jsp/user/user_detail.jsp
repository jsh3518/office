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
<title>用户信息</title>
<style type="text/css">
	body{margin-left: 0px;margin-top: 0px;margin-right: 0px;margin-bottom: 0px;}
	.header{height:30px;line-height:20px;text-align:right;padding-top:5px; margin-right:100px;background-color: #d9dde8;}
	.center{width:100%;height:420px;margin-top:1px;background: url(images/login_bg.jpg);}
	.left{float:left;width:50%;height:auto;margin-top: 10px}
	.title{text-align:center;width:100%;font-family: Arial, Helvetica, sans-serif;font-size: 20px;height:50px;line-height: 50px;color: #666666;font-weight: bold;}
	.info{font-family: Arial, Helvetica, sans-serif;font-size: 12px;height:35px;line-height: 30px;color: #333333;}
	.label{margin-left:20px;width:115px;height:20px;line-height: 20px;vertical-align: middle;display:inline-block;text-align: right;}
	.input{width:250px;height:20px;margin-left:20px;border:1px solid #7F9DB9;vertical-align: middle;}
	.file{width:250px;height:20px;margin-left:20px;vertical-align: middle;}
	.organ_select{width:82px;height:20px;border:1px solid #7F9DB9;vertical-align: middle;}
	.btn{width:60px;height:25px;border-width:0px;background-image: url(images/btn-bg2.gif);letter-spacing: 5px;margin-right:10px;cursor: pointer;}
	.info img{vertical-align: middle;cursor: pointer;width:430px;height:auto;}
	.errInfo{color:red;width:150px;height:20px;line-height: 20px;vertical-align: middle;display:inline-block; }
	.font{color:red;display: none }
</style>
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
</head>
<body>
<div style="width:100%;height:auto;margin-left:auto;margin-right:auto;">
	<form action="user/updateUser.html" method="post" name="detailForm" onsubmit="return check();" enctype="multipart/form-data">
		<div class="center">
			<div class="header">
				<input type="button" name="editBtn" id="editBtn" value="编辑" class="btn" onclick="forEdit()"/>
				<input type="submit" name="saveBtn" id="saveBtn" value="保存" class="btn" style="display: none"/>
				<input type="button" name="backBtn" id="backBtn" value="取消" class="btn" onclick="forBack()" style="display: none"/>
			</div>
			<div class="left">
				<div class="info">
					<label class="label">用户名<font class="font">*</font></label>
					<input type="text" name="loginname" id="loginname" class="input" value="${user.loginname }"/>
				</div>
				<div class="info">
					<label class="label">公司名称<font class="font">*</font></label>
					<input type="text" name="username" id="username" class="input" value="${user.username }"/>
					&nbsp;<span id="usererr" class="errInfo">&nbsp;</span>
				</div>
				<div class="info">
					<label class="label">主要联系人<font class="font">*</font></label>
					<input type="text" name="contact" id="contact" class="input" value="${user.contact }"/>
					&nbsp;<span id="contacterr" class="errInfo">&nbsp;</span>
				</div>
				<div class="info">
					<label class="label">主要联系人电话号码<font class="font">*</font></label>
					<input type="text" name="phone" id="phone" class="input" value="${user.phone }"/>
					&nbsp;<span id="phoneerr" class="errInfo">&nbsp;</span>
				</div>
				<div class="info">
					<label class="label">主要联系人电子邮箱<font class="font">*</font></label>
					<input type="text" name="email" id="email" class="input" value="${user.email }"/>
					&nbsp;<span id="emailerr" class="errInfo">&nbsp;</span>
				</div>
				<div class="info">
					<label class="label">公司地址</label>
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
				<div class="info">
					<label class="label"></label>
					<input type="text" name="address" id="address" class="input" value="${user.address }"/>
					&nbsp;<span id="addresserr" class="errInfo"></span>
				</div>
				<div class="info">
					<label class="label">邮政编码</label>
					<input type="text" name="post" id="post" class="input" value="${user.post }"/>
					&nbsp;<span id="posterr" class="errInfo"></span>
				</div>
				<div class="info" id="mpnDiv">
					<label class="label">MPNID</label>
					<input type="text" name="mpnId" id="mpnId" class="input" value="${user.mpnId }" onblur="valMpnId()"/>
					&nbsp;<span id="mpnIderr" class="errInfo"></span>
				</div>
				<div class="info">
					<label class="label">税号<font class="font">*</font></label>
					<input type="text" name="tax" id="tax" class="input" value="${user.tax }"/>
				</div>
			</div>
			<div class="left">
				<div class="info">
					<label class="label">营业执照扫描件<font class="font">*</font></label><br>
					<div style="height:280px;width: 450px;overflow:auto;margin-left: 20px">
						<img src="<%=basePath%>files/${user.file}">
					</div>
				</div>
			</div>
		</div>
		<input type="text" name="userId" id="userId" style="display: none"  value="${user.userId }"/>
	</form>
</div>
	<script type="text/javascript">
	$(document).ready(function(){
		$("input:text").attr("disabled",true);
		$(".organ_select").attr("disabled",true);
		valMpnId();
	})
	
	//清除错误信息
	function resetErr(){
		$("#nameerr").html("");
		$("#contacterr").html("");
		$("#phoneerr").html("");
		$("#emailerr").html("");
		$("#posterr").html("");
		$("#mpnIderr").html("");
	}
	
	function forEdit(){
		$("input:text").attr("disabled",false);
		$(".organ_select").attr("disabled",false);
		$("#loginname").attr("disabled",true);
		$("#tax").attr("disabled",true);
		$("#editBtn").hide();
		$("#saveBtn").show();
		$("#backBtn").show();
		$("font").show();
	}
	
		function forBack(){
			window.location="<%=basePath%>user/detail.html";
		}
		
		var con;
		function check(){
			con=1;
			resetErr();
			valName();
			valUsername();
			valContact();
			valPhone();
			valEmail();
			valPost();
			valTax();
			valFile();
			valCode();
			valMpnId();
			if(con==1){
				return true;
			}else{
				return false;
			}
		}
		
		function valName(){
			$("#nameerr").html("");
		  reg=/^[0-9a-zA-Z_]{2,30}[0-9a-zA-Z]$/;
		  var loginname = $("#loginname").val();
		  if( loginname ==""){
				$("#nameerr").show();
				$("#nameerr").html("用户名不能为空！");
		    con = 0;
		  }else if(loginname.length>32){
				$("#nameerr").show();
				$("#nameerr").html("用户名过长！");
		    con = 0;
		  }else if(loginname.length<4){
				$("#nameerr").show();
				$("#nameerr").html("用户名不足3位！");
		    con = 0;
		  }else{
			  var url = "user/checkUser.html";
				var postData = {"loginname":loginname};
				$.post(url,postData,function(data){
					if(data>=1){
						$("#nameerr").show();
						$("#nameerr").html("用户名已存在！");
				    con = 0;
					}
				});
		  }
		}
		
		//判断公司名称
		function valUsername(){
			$("#usererr").html("");
			  if( $("#username").val() ==""){
				  $("#usererr").html("公司名称不能为空!");
				  con=0;
			  }
		}
		
		//判断主要联系人
		function valContact(){
			$("#contacterr").html("");
			if( $("#contact").val() ==""){
				$("#contacterr").html("主要联系人不能为空!");
				con=0;
			}
		}
		
		//判断邮箱
		function valEmail(){
			$("#emailerr").html("");
		  reg=/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
		  if( $("#email").val()==""){
		     $("#emailerr").html("请输入邮箱地址！");
		     con = 0;
		  }else if(!reg.test($("#email").val())){
			   $("#emailerr").html("邮箱格式错误！");
			   con = 0;
		  }
		}

		//判断电话号码
		function valPhone(){
			$("#phoneerr").html("");
			var reg =/^(1[3|4|5|8|7][0-9]\d{4,8})|(0\d{2,3}-?\d{7,8})|(0\d{23}-?\d{8}(-?\d{1,4})?)|(8\d{23}-?\d{7,8}(-?\d{1,4})?)$/i;//验证电话号码正则
		  if( $("#phone").val()==""){
			  $("#phoneerr").html("请输入电话号码！");
				con = 0;
		  }else if(!reg.test($("#phone").val())){
			  $("#phoneerr").html("电话号码格式有误！");
			  con = 0;
		  }else if($("#phone").val().length<8){
			  $("#phoneerr").html("电话号码长度有误！");
		    con = 0;
		  }
		}
		
		
		//判断邮政编码
		function valPost(){
			$("#posterr").html("");
			var reg= /^[0-9]{6,6}$/;
			if($("#post").val() !=""&&!reg.test($("#post").val())){
				$("#posterr").html("请输入正确的邮政编码！");
				con = 0;
			}
		}
		
		//判断税号
		function valTax(){
			$("#taxerr").html("");
			if( $("#tax").val() ==""){
				$("#taxerr").html("税号不能为空!");
				con=0;
			}else{
				  var url = "user/checkUser.html";
					var postData = {"tax":$("#tax").val()};
					$.post(url,postData,function(data){	
						if(data>=1){
							$("#taxerr").show();
							$("#taxerr").html("该税号已存在，请确认是否正确！");
					    con = 0;
						}
					});
			  }
		}
		
		//判断营业执照扫描件
		function valFile(){
			$("#fileerr").html("");
			if( $("#buslic").val() ==""){
				$("#fileerr").html("请上传营业执照扫描件!");
				con=0;
			}
		}
		
		//判断验证码
		function valCode(){
			$("#codeerr").html("");
			if( $("#code").val() ==""){
				$("#codeerr").html("验证码不能为空!");
				con=0;
			}else{
				  var url = "code/checkCode.html";
					var postData = {"code":$("#code").val()};
					$.post(url,postData,function(data){	
						if(data!=""){
							$("#codeerr").show();
							$("#codeerr").html(data);
					    con = 0;
						}
					});
			  }
		}
		
		//验证mpnId
		function valMpnId(){
			$("#mpnIderr").html("");
			var mpnId = $("#mpnId").val();
			if(mpnId!=""){
				  var url = "orders/checkMpnId.html";
					var postData = {"mpnId":mpnId};
					$.post(url,postData,function(data){
						var mpn = JSON.parse(data);
						if(mpn.responseCode==200){
							var result = JSON.parse(mpn.result.substr(mpn.result.indexOf("{")));
							$("#mpnIderr").css("color","blue");
							$("#mpnIderr").html(result.partnerName);
						}else	if(mpn.responseCode==401){
							$("#mpnIderr").css("color","red");
							$("#mpnIderr").html("查询请求被拒绝！");
						}else{
							$("#mpnIderr").css("color","red");
							$("#mpnIderr").html("找不到记录！");
							con = 0;
						}
					});
			}
		}
		
		//组织机构onchange事件
		function changeOrg(type,parentId){
			var url = "organ/getOrgans.html";
			var select = $("#"+type);
			var level;
			if(type=="cityId"){
				$("#countyId").html("");
				$("#countyId").append("<option value=''>请选择</option>");
				level = 2;
			}else if(type=="countyId"){
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