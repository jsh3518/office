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
<title>基础信息维护</title>
<link type="text/css" rel="stylesheet" href="css/content.css"/>
<style type="text/css">
.info{
	margin-top: 25px;
}
</style>
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
</head>
<body>
<div style="width:420px;height:auto;margin-left:auto;margin-right:auto;">
	<div class="center">
		<form action="pubCode/editPubCode.html" method="post" target="result" name="pubCodeForm" id="pubCodeForm" onsubmit="return check();">
			<div style="width: 100%;float: left;margin-top:20px">
				<div class="info">
					<label class="label">编码<font color="red">*</font></label>
					<div class="right">
	      		<input type="text" name="code" id="code" class="input" value="${pubCode.code }" <c:if test="${not empty pubCode.id }"> readonly="readonly" </c:if>/>
	     			&nbsp;<span id="codeerr" class="errInfo"></span>
	     		</div>
				</div>
				<div class="info">
					<label class="label">名称<font color="red">*</font></label>
					<div class="right">
						<input type="text" name="name" id="name" class="input" value="${pubCode.name }"/>
						&nbsp;<span id="nameerr" class="errInfo"></span>
					</div>
				</div>
				<div class="info">
					<label class="label">排序</label>
					<div class="right">
						<input type="text" name="sort" id="sort" class="input" value="${pubCode.sort }"/>
					</div>
				</div>
				<div class="info">
					<label class="label">是否有效</label>
					<div class="right">
						<select name="valid" id="valid" class="select">
							<option <c:if test="${pubCode.valid==1}"> selected="selected"</c:if> value="1">是</option>
							<option <c:if test="${pubCode.valid==0}"> selected="selected"</c:if> value="0">否</option>
						</select>
					</div>
				</div>
			</div>
			<div style="display: none">
				<input type="text" name="classId" id="classId" value="${pubCode.classId }"/>
				<input type="text" name="id" id="id" value="${pubCode.id }"/>
				<input type="text" name="level" id="level" value="${pubCode.level }"/>
				<input type="text" name="detail" id="detail" value="${pubCode.detail }"/>
			</div>
			<div class="info" style="width:80%;float: left;text-align: center;">
				<input type="submit" name="saveBtn" id="saveBtn" value="保存" class="btn"/>
				<input type="button" name="closeBtn" id="closeBtn" value="关闭" class="btn" onclick="cancel()"/>
			</div>
		</form>
		<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>
	</div>
</div>
	<script type="text/javascript">
		$.ajaxSetup({ 
		    async : false 
		});
		var dg;
		$(document).ready(function(){
			dg = frameElement.lhgDG;
		});

		function success(){
			alert("保存成功！");
			if(dg.curWin.document.forms[0]){
				dg.curWin.document.forms[0].action = dg.curWin.location+"";
				dg.curWin.document.forms[0].submit();
			}else{
				dg.curWin.location.reload();
			}
			cancel();
		}
		
		function failed(){
			alert("保存失败！");
		}
		
		function cancel(){
			dg.cancel();
		}
		
		var con;
		function check() {
			con = 1;
			$(".errInfo").html("");
			valCode();
			valName();
			if (con == 1) {
				return true;
			} else {
				return false;
			}
		}
		
		//判断编码
		function valCode() {
			$("#codeerr").html("");
			if ($("#code").val() == "") {
				$("#codeerr").html("编码不能为空!");
				con = 0;
			}	else {
				var url = "pubCode/checkCode.html";
				var postData = {
					"code" : $("#code").val(),"id":"${pubCode.id }","classId":"${pubCode.classId }"
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
		
		//判断名称
		function valName() {
			$("#nameerr").html("");
			if ($("#name").val() == "") {
				$("#nameerr").html("名称不能为空!");
				con = 0;
			}
		}

	</script>
</body>
</html>