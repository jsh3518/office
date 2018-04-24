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
<title>产品价格信息</title>
<style type="text/css">
	body{margin-left: 0px;margin-top: 0px;margin-right: 0px;margin-bottom: 0px;background: url(images/login_bg.jpg);}
	.center{width:100%;height:300px;background: url(images/login_bg.jpg);}
	.title{text-align:center;padding-left:40px;padding-bottom:10px; font-family:Arial,Helvetica,sans-serif;font-size:14px;height:25px;line-height:25px;color:#666666;font-weight:bold;}
	.info{font-family:Arial,Helvetica,sans-serif;font-size:12px;height:25px;line-height:25px;color:#333333;margin-bottom:25px}
	.select{width:60px;height:25px;border:1px solid #ccc;vertical-align:middle;font-size:12px;color:#222;}
	.btn{width:60px;height:25px;border-width:0px;background-image: url(images/btn-bg2.gif);letter-spacing: 5px;margin-right:15px;cursor: pointer;}
	.right{width:280px;margin-left:20px;vertical-align:middle;text-align: left;float:left;}
	.input{width:100%;height:25px;line-height:25px; align:center;vertical-align:middle;font-size:12px;color:#222;background-color:#fff;border:1px solid #ccc;border-radius:3px;}
	.left{color:#262626;text-align:right;width:80px;vertical-align:middle;float:left}
	.error{float: left;color: #ea644a;}
</style>
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
</head>
<body>
<div style="width:400px;height:auto;margin-left:auto;margin-right:auto;">
	<div class="center">
		<form action="offerPrice/saveOfferPrice.html" method="post" target="result" name="offerPriceForm" id="offerPriceForm">
			<div style="width: 100%;float: left;margin-top:10px">
				<div class="info">
					<label class="left">产品名称</label>
					<div class="right">
	      			<input type="text" name="offerName" id="offerName" class="input" value="${offerPrice.offerName }" readonly="readonly"/>
	     		</div>
				</div>
				<div class="info">
					<label class="left">价格（月）</label>
					<div class="right">
							<input type="text" name="priceMonth" id="priceMonth" class="input" value="${offerPrice.priceMonth }"/>
					</div>
				</div>
				<div class="info">
					<label class="left">价格（年）</label>
					<div class="right">
						<input type="text" name="priceYear" id="priceYear" class="input" value="${offerPrice.priceYear }"/>
					</div>
				</div>
				<div class="info">
					<label class="left">单位</label>
					<div class="right">
						<select name="unit" id="unit" class="select">
							<c:forEach items="${unitList}" var="pubCode">
								<option <c:if test="${pubCode.code == offerPrice.unit}"> selected="selected"</c:if> value="${pubCode.code }">${pubCode.name }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="info">
					<label class="left">是否有效</label>
					<div class="right">
						<select name="valid" id="valid" class="select">
							<option <c:if test="${offerPrice.valid==1}"> selected="selected"</c:if> value="1">是</option>
							<option <c:if test="${offerPrice.valid==0}"> selected="selected"</c:if> value="0">否</option>
						</select>
					</div>
				</div>
			</div>
			<div style="display: none">
				<input type="text" name="offerId" id="offerId" value="${offerPrice.offerId }"/>
				<input type="text" name="id" id="id" value="${offerPrice.id }"/>
			</div>
			<div class="info" style="width:100%;float: left;padding-left: 50px">
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
			if("${method}"=="edit"){
				$("input").attr("disabled",false);
				$("select").attr("disabled",false);
			}else{
				$("input.input").attr("disabled",true);
				$("select").attr("disabled",true);
			}
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
	</script>
</body>
</html>