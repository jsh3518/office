<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<title>发票信息</title>
<link type="text/css" rel="stylesheet" href="css/content.css" />
<style type="text/css">
.font {
	color: red;
	display: none
}
.info{
	margin-top: 25px;
}
.hide {
	display: none
}
</style>
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
</head>
<body>
	<div style="width: 450px; height: auto; margin-left: auto; margin-right: auto;">
		<div class="center">
			<div style="width: 100%; height: auto; margin-top: 20px;">
				<form action="invoice/save.html" method="post"	name="invoiceForm" onsubmit="return check();">
					<div class="info">
						<label class="label">公司名称<font class="font">*</font></label>
						<div class="right">
							<input type="text" name="company" id="company" class="input" value="${invoice.company }" disabled="disabled"/>
							 &nbsp;<span id="companyerr" class="errInfo">&nbsp;</span>
						</div>
					</div>
					<div class="info">
						<label class="label">纳税人识别号<font class="font">*</font></label>
						<div class="right">
							<input type="text" name="tax" id="tax"	class="input" value="${invoice.tax }" disabled="disabled"/>
							&nbsp;<span id="taxerr" class="errInfo"></span>
						</div>
					</div>
					<div class="info">
						<label class="label">公司地址<font class="font">*</font></label>
						<div class="right">
							<input type="text" name="address" id="address"	class="input" value="${invoice.address }"  disabled="disabled"/>
							&nbsp;<span id="addresserr"	class="errInfo"></span>
						</div>
					</div>
					<div class="info">
						<label class="label">开户行<font class="font">*</font></label>
						<div class="right">
							<input type="text" name="bank" id="bank" class="input" value="${invoice.bank }" disabled="disabled" />
							&nbsp;<span	id="bankerr" class="errInfo"></span>
						</div>
					</div>
					<div class="info">
						<label class="label">银行账号<font class="font">*</font></label>
						<div class="right">
							<input type="text" name="account" id="account" class="input"	value="${invoice.account }" disabled="disabled" />
							&nbsp;<span id="accounterr" class="errInfo"></span>
						</div>
					</div>
					<div class="info">
						<label class="label">电话<font class="font">*</font></label>
						<div class="right">
							<input type="text" name="phone" id="phone" class="input"	value="${invoice.phone }" disabled="disabled" />
							&nbsp;<span id="phoneerr" class="errInfo"></span>
						</div>
					</div>
					<div class="info hide">
						<label class="label">验证码<font class="font">*</font></label>
						<div class="right">
							<input type="text" name="code" id="code" class="input"	style="width: 60%" />&nbsp;&nbsp;
							<img id="codeImg" alt="点击更换" title="点击更换" src="" style="vertical-align: middle;" />
							&nbsp;<span id="codeerr" class="errInfo"></span>
						</div>
					</div>
					<div>
						<input type="hidden" name="id" id="id" value="${invoice.id}" />
						<input type="hidden" name="userId" id="userId" value="${invoice.userId }" />
						<input type="hidden" name="valid" id="valid" value="${invoice.valid }" />
					</div>
					<div class="info" style="margin-left: 40px">
						<input type="button" name="editBtn" id="editBtn" value="编辑"	class="btn" onclick="edit()" />
						<input type="submit"	name="saveBtn" id="saveBtn" value="保存" class="btn"	style="display: none" />
						<input type="reset" name="cancelBtn"	id="cancelBtn" value="取消" class="btn" onclick="cancel()" style="display: none" />
					</div>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var message = "${message}";
		$.ajaxSetup({ 
		    async : false 
		});  
		$(document).ready(function(){
			changeCode();
			$("#codeImg").bind("click",changeCode);
			 if(message=="success"){
					alert("保存成功！");
			 }else if(message!=""){
					$(".hide").show();
					$(".font").show();
					$(".input").attr("disabled",false);
					$("#editBtn").hide();
					$("#saveBtn").show();
					$("#cancelBtn").show();
					alert(message);
			}
		});
	
		function genTimestamp(){
			var time = new Date();
			return time.getTime();
		}
	
		function changeCode(){
			$("#codeImg").attr("src","code.html?t="+genTimestamp());
		}
		
		function edit(){
			$(".hide").show();
			$(".font").show();
			$(".input").attr("disabled",false);
			//$("#tax").attr("readonly","readonly");
			if($("#id").val()==""){
				$("#tax").val("${user.tax}");
			}
			$("#editBtn").hide();
			$("#saveBtn").show();
			$("#cancelBtn").show();
		}
		
		function cancel(){
			$(".errInfo").html("");
			$(".hide").hide();
			$(".font").hide();
			$(".input").attr("disabled",true);
			$("#editBtn").show();
			$("#saveBtn").hide();
			$("#cancelBtn").hide();
		}

		var con;
		function check() {
			con = 1;
			$(".errInfo").html("");
			valCompany();
			valTax();
			valAddress();
			valBank();
			valAccount();
			valPhone();
			valCode();
			if (con == 1) {
				return true;
			} else {
				return false;
			}
		}

		//判断公司名称
		function valCompany() {
			$("#companyerr").html("");
			var company = $("#company").val();
			if (company == "") {
				$("#companyerr").html("公司名称不能为空!");
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
			}
		}
		
		//判断公司地址
		function valAddress() {
			$("#addresserr").html("");
			if( $("#address").val() ==""){
				$("#addresserr").html("公司地址不能为空!");
				con=0;
			}
		}
		
		//判断开户行
		function valBank() {
			$("#bankerr").html("");
			if( $("#bank").val() ==""){
				$("#bankerr").html("开户行不能为空!");
				con=0;
			}
		}
		
		//判断银行账号
		function valAccount() {
			$("#accounterr").html("");
			var num = /^\d*$/; //全数字
			//验证银行账号前6位
			var str = "10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";
			if( $("#account").val() ==""){
				$("#accounterr").html("银行账号不能为空!");
				con=0;
			}else if($("#account").val().length < 16 || $("#account").val().length > 19){
				$("#accounterr").html("银行卡号长度必须在16到19之间!");
				con=0;
			}else if(!num.test($("#account").val())){
				$("#accounterr").html("银行卡号必须全为数字!");
				con=0;
			}else if(str.indexOf($("#account").val().substring(0, 2)) == -1){
				$("#accounterr").html("银行卡号开头6位不符合规范!");
				con=0;
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
		
		//判断验证码
		function valCode() {
			$("#codeerr").html("");
			if ($("#code").val() == "") {
				$("#codeerr").html("验证码不能为空!");
				con = 0;
			} else {
				var url = "code/checkCode.html";
				var postData = {
					"code" : $("#code").val()
				};
				$.post(url, postData, function(data) {
					if (data != "") {
						$("#codeerr").show();
						$("#codeerr").html(data);
						con = 0;
					}
				});
			}
		}
	</script>
</body>
</html>