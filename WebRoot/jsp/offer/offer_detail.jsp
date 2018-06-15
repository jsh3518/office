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
<title>产品信息</title>
<link type="text/css" rel="stylesheet" href="css/content.css" />
<style type="text/css">
.font {
	color: red;
	display: none
}
.info{
	margin-top: 23px;
}
.color{
	background-color:#EAEDF2;
}
</style>
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
</head>
<body>
	<div style="width: 450px; height: auto; margin-left: auto; margin-right: auto;">
		<div style="text-align: left;">
			<div style="width: 100%; height: auto;">
				<form action="offer/saveOffer.html" method="post"	name="detailForm" target="result" onsubmit="return check();">
					<div class="info">
						<label class="label">产品Id<font class="font">*</font></label>
						<div class="right">
							<input type="text" name="offerId" id="offerId" class="input color" value="${offer.offerId }" readonly="readonly"/>
							 &nbsp;<span id="iderr" class="errInfo">&nbsp;</span>
						</div>
					</div>
					<div class="info">
						<label class="label">产品名称<font class="font">*</font></label>
						<div class="right">
							<input type="text" name="offerName" id="offerName"	class="input" value="${offer.offerName }"/>
							&nbsp;<span id="nameerr" class="errInfo"></span>
						</div>
					</div>
					<div class="info">
						<label class="label">最小数量<font class="font">*</font></label>
						<div class="right">
							<input type="text" name="mininum" id="mininum" class="input" value="${offer.mininum }"/>
							&nbsp;<span	id="mininumerr" class="errInfo"></span>
						</div>
					</div>
					<div class="info">
						<label class="label">最大数量<font class="font">*</font></label>
						<div class="right">
							<input type="text" name="maxinum" id="maxinum" class="input"	value="${offer.maxinum }"/>
							&nbsp;<span id="maxinumerr" class="errInfo"></span>
						</div>
					</div>
					<div class="info">
						<label class="label">排序<font class="font">*</font></label>
						<div class="right">
							<input type="text" name="sort" id="sort" class="input"	value="${offer.sort }"/>
							&nbsp;<span id="sorterr" class="errInfo"></span>
						</div>
					</div>
					<div class="info">
						<label class="label">是否有效<font class="font">*</font></label>
						<div class="right">
							<select name="valid" id="valid" class="select">
								<option <c:if test="${offer.valid==1}"> selected="selected"</c:if> value="1">是</option>
								<option <c:if test="${offer.valid==0}"> selected="selected"</c:if> value="0">否</option>
							</select>
							&nbsp;<span id="validerr" class="errInfo"></span>
						</div>
					</div>
					<div class="info" style="margin-left: 40px">
						<input type="submit"	name="saveBtn" id="saveBtn" value="保存" class="btn" />
						<input type="button" name="backBtn"	id="backBtn" value="返回" class="btn" onclick="forBack()" />
					</div>
					<input type="hidden" name="isTrial" id="isTrial" value="${offer.isTrial }"/>
					<input type="hidden" name="parent" id="parent" value="${offer.parent }"/>
					<input type="hidden" name="level" id="level" value="${offer.level }"/>
				</form>
				<iframe name="result" id="result" src="about:blank" frameborder="0" width="0" height="0"></iframe>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$.ajaxSetup({ 
		    async : false 
		});  

		var dg;
		$(document).ready(function(){
			dg = frameElement.lhgDG;
			if("${offer.offerId}"==""){
				$("#offerId").attr("readonly",false);
				$("#offerId").removeClass("color");
			}
		});
		
		//返回
		function forBack(){
			dg.cancel();
		}
		
		var con;
		function check() {
			con = 1;
			$(".errInfo").html("");
			$("#saveBtn").attr("disabled",true);
			$("#backBtn").attr("disabled",true);
			valOfferId();
			valOfferName();
			valParent();
			valMininum();
			valMaxinum();
			valSort();
			valTrial();
			valValid();
			if (con == 1) {
				return true;
			} else {
				$("#saveBtn").attr("disabled",false);
				$("#backBtn").attr("disabled",false);
				return false;
			}
		}

		//判断产品Id
		function valOfferId() {
			$("#iderr").html("");
			var offerId = $("#offerId").val();
			if (offerId == "") {
				$("#iderr").html("产品Id不能为空!");
				con = 0;
			}else if("${offer.offerId}"==""){
				var url = "<%=basePath%>offer/checkOfferId.html";
				var postData = {"offerId":$("#offerId").val()};
				$.post(url,postData,function(data){
					if(data!=""){
						$("#iderr").html(data);
						con = 0;
					}
				})
			}
		}
		
		//判断产品名称
		function valOfferName(){
			$("#nameerr").html("");
			if( $("#offerName").val() ==""){
				$("#nameerr").html("产品名称不能为空!");
				con=0;
			}
		}
		
		//判断产品类别
		function valParent() {
			$("#parenterr").html("");
			if( $("#parent").val() ==""){
				$("#parenterr").html("产品类别不能为空!");
				con=0;
			}
		}
		
		//判断最小数量
		function valMininum() {
			var num = /^\d*$/; //全数字
			$("#mininumerr").html("");
			if(!num.test($("#mininum").val())){
				$("#mininumerr").html("最小数量必须为数字!");
				con=0;
			}else if($("#mininum").val()<0){
				$("#mininumerr").html("最小数量必须大于0!");
				con=0;
			}
		}
		
		//判断最大数量
		function valMaxinum() {
			var num = /^\d*$/; //全数字
			$("#maxinumerr").html("");
			if(!num.test($("#maxinum").val())){
				$("#maxinumerr").html("最大数量必须为数字!");
				con=0;
			}else if($("#maxinum").val()<0){
				$("#maxinumerr").html("最大数量必须大于0!");
				con=0;
			}
		}

		//判断排序
		function valSort(){
			var num = /^\d*$/; //全数字
			$("#sorterr").html("");
			if($("#sort").val()==""){
				$("#sorterr").html("排序不能为空!");
				con=0;
			}else if(!num.test($("#sort").val())){
				$("#sorterr").html("排序必须为数字!");
				con=0;
			}else if($("#sort").val()<0){
				$("#sorterr").html("排序必须大于0!");
				con=0;
			}
		}
		
		//判断是否试用
		function valTrial() {
			$("#trialerr").html("");
			if ($("#isTrial").val() == "") {
				$("#trialerr").html("请选择是否试用!");
				con = 0;
			} 
		}
		
		//判断是否有效
		function valValid() {
			$("#validerr").html("");
			if ($("#valid").val() == "") {
				$("#validerr").html("请选择是否有效!");
				con = 0;
			} 
		}
		
		function success(){
			alert("产品信息已修改！");
			dg.curWin.location.reload();
			dg.cancel();
		}
		
		function failed(){
			alert("产品信息修改失败！");
			$("#saveBtn").attr("disabled",false);
			$("#backBtn").attr("disabled",false);
		}
		
	</script>
</body>
</html>