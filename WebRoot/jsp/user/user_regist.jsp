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
	body{margin-left: 0px;margin-top: 0px;margin-right: 0px;margin-bottom: 0px;background-color: #1B3142;}
	.header{width:100%;height:20px;background: url(images/login-top-bg.gif) repeat-x;}
	.center{width:100%;height:532px;background: url(images/login_bg.jpg);}
	.regist_left{float:left;width:100%;height:100%;background: url(images/login-wel.gif) bottom no-repeat;}
	.login_title{text-align:center;font-family: Arial, Helvetica, sans-serif;font-size: 14px;height:32px;line-height: 32px;color: #666666;font-weight: bold;}
	.regist_info{font-family: Arial, Helvetica, sans-serif;font-size: 12px;height:32px;line-height: 32px;color: #333333;}
	.regist_label{margin-left:50px;width:120px;height:20px;line-height: 20px;vertical-align: middle;display:inline-block;text-align: right;}
	.regist_input{width:250px;height:20px;margin-left:20px;border:1px solid #7F9DB9;vertical-align: middle;}
	.file{width:250px;height:20px;margin-left:20px;vertical-align: middle;}
	.organ_select{width:82px;height:20px;border:1px solid #7F9DB9;vertical-align: middle;}
	.regist_code{width:70px;height:20px;margin-left:30px;border:1px solid #7F9DB9;vertical-align: middle;}
	.btn{width:60px;height:25px;border-width:0px;background-image: url(images/btn-bg2.gif);letter-spacing: 5px;margin-right:15px;cursor: pointer;}
	.regist_info img{vertical-align: middle;cursor: pointer;}
	.errInfo{color:red;width:180px;height:20px;line-height: 20px;vertical-align: middle;display:inline-block; }
	.left_txt{font-family: Arial, Helvetica, sans-serif;font-size: 12px;line-height: 25px;color: #666666;}
	.bottom{width:100%;height:auto;text-align:center;font-family: Arial, Helvetica, sans-serif;font-size: 10px;color: #ABCAD3;text-decoration: none;line-height: 20px;}
</style>
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
</head>
<body>
<div style="width:650px;height:auto;margin-left:auto;margin-right:auto;">
	<div class="header"></div>
	<div class="center">
		<div class="regist_left">
			<div style="width:100%;height:auto;margin-top:5px;">
			<form action="user/regedit.html" method="post" name="registForm" onsubmit="return check();" enctype="multipart/form-data">
				<div class="login_title">
					新用户注册
				</div>
				<div class="regist_info">
					<label class="regist_label">用户名<font color="red">*</font></label>
					<input type="text" name="loginname" id="loginname" class="regist_input" value="${user.loginname }" placeholder="必须是三位以上的英文字母或数字"/>
					&nbsp;<span id="nameerr" class="errInfo">&nbsp;</span>
				</div>
				<div class="regist_info">
					<label class="regist_label">密码<font color="red">*</font></label>
					<input type="password" name="password" id="password" class="regist_input" value="" placeholder="数字和字母组成，六位以上"/>
					&nbsp;<span id="pwderr" class="errInfo"></span>
				</div>
				<div class="regist_info">
					<label class="regist_label">确认密码<font color="red">*</font></label>
					<input type="password" name="password1" id="password1" class="regist_input" value=""/>
					&nbsp;<span id="pwderr1" class="errInfo"></span>
				</div>
				<div class="regist_info">
					<label class="regist_label">公司名称<font color="red">*</font></label>
					<input type="text" name="username" id="username" class="regist_input" value="${user.username }"/>
					&nbsp;<span id="usererr" class="errInfo"></span>
				</div>
				<div class="regist_info">
					<label class="regist_label">主要联系人<font color="red">*</font></label>
					<input type="text" name="contact" id="contact" class="regist_input" value="${user.contact }"/>
					&nbsp;<span id="contacterr" class="errInfo"></span>
				</div>
				<div class="regist_info">
					<label class="regist_label">主要联系人电话号码<font color="red">*</font></label>
					<input type="text" name="phone" id="phone" class="regist_input" value="${user.phone }"/>
					&nbsp;<span id="phoneerr" class="errInfo"></span>
				</div>
				<div class="regist_info">
					<label class="regist_label">主要联系人电子邮箱<font color="red">&nbsp;*</font></label>
					<input type="text" name="email" id="email" class="regist_input" value="${user.email }"/>
					&nbsp;<span id="emailerr" class="errInfo"></span>
				</div>
				<div class="regist_info">
					<label class="regist_label">公司地址<font color="red">&nbsp;*</font></label>
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
					&nbsp;<span id="addresserr" class="errInfo"></span>
				</div>
				<div class="regist_info">
					<label class="regist_label">邮政编码&nbsp;&nbsp;</label>
					<input type="text" name="post" id="post" class="regist_input" value="${user.post }"/>
					&nbsp;<span id="posterr" class="errInfo"></span>
				</div>
				<div class="regist_info">
					<label class="regist_label">税号<font color="red">&nbsp;*</font></label>
					<input type="text" name="tax" id="tax" class="regist_input" value="${user.tax }"/>
					&nbsp;<span id="taxerr" class="errInfo"></span>
				</div>
				<div class="regist_info">
					<label class="regist_label">MPNID&nbsp;&nbsp;</label>
					<input type="text" name="mpnId" id="mpnId" class="regist_input" value="${user.mpnId }" onblur="valMpnId()"/>
					&nbsp;<span id="mpnIderr" class="errInfo"></span>
				</div>
				<div class="regist_info">
					<label class="regist_label">上传营业执照扫描件<font color="red">&nbsp;*</font></label><input type="file" id="buslic" name="buslic" class="file" onchange="verifyFile(this)">
					&nbsp;<span id="fileerr" class="errInfo"></span>
				</div>
				<div class="regist_info">
					<label class="regist_label">验证码<font color="red">&nbsp;*</font></label>
					<input type="text" name="code" id="code" class="regist_code"/>&nbsp;&nbsp;
					<img id="codeImg" alt="点击更换" title="点击更换" src=""/>
					&nbsp;<span id="codeerr" class="errInfo"></span>
				</div>
				<div class="regist_info" style="text-align:center">
					<input type="submit" name="registBtn" value="注册" class="btn"/>
					<input type="reset" name="cancelBtn" value="取消" class="btn" onclick="resetErr()"/>
					<input type="button" name="backBtn" value="返回" class="btn" onclick="forBack()"/>
				</div>
				<input type="text" name="type" id="type" value="1" style="display: none"/>
			</form>
			</div>
		</div>
	</div>
	<div class="bottom">
	Copyright &copy; 2018 伟仕佳杰
	</div>
</div>
	<script type="text/javascript">
		var msg = "${msg}";
		var result = "${result}";
		changeCode();
		$.ajaxSetup({ 
		    async : false 
		});  
		$(document).ready(function(){
			if(result){//跳转
				alert("注册成功！");
				window.location="<%=basePath%>login.html";
			}else{
				$("#codeImg").bind("click",changeCode);
				if(msg!=""){
					if(msg.indexOf("验证码")>-1){
						$("#codeerr").html(msg);
						$("#code").focus();
					}else{
						$("#nameerr").html(msg);
						$("#loginname").focus();
					}
				}
			}

		});
	
		function genTimestamp(){
			var time = new Date();
			return time.getTime();
		}
	
		function changeCode(){
			$("#codeImg").attr("src","code.html?t="+genTimestamp());
		}
		
		function resetErr(){
			$("#nameerr").html("");
			$("#pwderr").html("");
			$("#pwderr1").html("");
			$("#usererr").html("");
			$("#contacterr").html("");
			$("#phoneerr").html("");
			$("#emailerr").html("");
			$("#posterr").html("");
			$("#taxerr").html("");
			$("#fileerr").html("");
			$("#mpnIderr").html("");
			$("#codeerr").html("");
		}
		
		function forBack(){
			window.location="<%=basePath%>login.html";
		}
		
		var con;
		function check(){
			con=1;
			resetErr();
			valName();
			valPassword();
			valPassword1();
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

		//判断密码
		function valPassword(){
			$("#pwderr").html("");
		  reg1=/^.*[\d]+.*$/;
		  reg2=/^.*[A-Za-z]+.*$/;
		  reg3=/^.*[_@#%&^+-/*\/\\]+.*$/;//验证密码
		  var Pval = $("#password").val();
		  if( Pval ==""){
			  $("#pwderr").html("密码不能为空!");
		    con = 0;
		  }else if(Pval.length<6){
			  $("#pwderr").html("密码不能小于6位，区分大小写!");
			    con = 0;
		  }else if(!((reg1.test(Pval) && reg2.test(Pval)) || (reg1.test(Pval) && reg3.test(Pval)) || (reg2.test(Pval) && reg3.test(Pval)))){
			  $("#pwderr").html("需至少包含数字、字母和符号中的两种类型!");
			    con = 0;
		  }
		}

		//判断密码
		function valPassword1(){
			$("#pwderr1").html("");
		  var Pval = $("#password").val();
		  var Pval1 = $("#password1").val();
		  if( Pval !=Pval1){
			  $("#pwderr1").html("两次输入的密码不一致!");
			  con=0;
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
			var reg = /^[A-Z0-9]{15}$|^[A-Z0-9]{16}$|^[A-Z0-9]{17}$|^[A-Z0-9]{18}$|^[A-Z0-9]{20}$/;
			if( $("#tax").val() ==""){
				$("#taxerr").html("税号不能为空!");
				con=0;
			}else if(!reg.test($("#tax").val())){
				$("#taxerr").html("税号不正确，请重新输入!");
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
		
		//验证文件大小和类型
		function verifyFile(obj){
			var type = ".jpg,.png,.gif";//图片
			return getFileSize(obj, type, 20, "MB");
		}
	</script>
</body>
</html>