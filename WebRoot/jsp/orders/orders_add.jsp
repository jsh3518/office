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
	body{margin-left: 0px;margin-top: 0px;margin-right: 0px;margin-bottom: 0px;background: url(images/login_bg.jpg);}
	.center{width:100%;height:400px;background: url(images/login_bg.jpg);}
	.title{text-align:left; padding-left:20px;font-family:Arial,Helvetica,sans-serif;font-size:14px;height:20px;line-height:20px;color:#666666;font-weight:bold;margin-top: 5px}
	.info{font-family:Arial,Helvetica,sans-serif;font-size:14px;height:25px;line-height:25px;color:#333333;}
	.select{width:150px;height:25px;border:1px solid #ccc;vertical-align:middle;font-size:12px;color:#222;}
	.btn{width:60px;height:25px;border-width:0px;background-image: url(images/btn-bg2.gif);letter-spacing: 5px;margin-left:15px;cursor: pointer;text-align: center}
	.left{margin-left:20px;vertical-align:middle;text-align: left;float:left;}
	.checkbox{width:15px;height:15px;vertical-align:middle;font-size:12px;color:#222;border:1px solid #ccc;border-radius:4px;}
	.error{float: left;color: #ea644a;}
	.tab-div {width: 400px;position: relative;}  
  ul {list-style: none;}  
  .tab {overflow: hidden;padding:0;position: relative;margin-left:20px;}
  .tab li{float: left;width: 150px;border:1px solid #def;height: 25px;line-height: 25px;text-align: center;cursor: pointer;border-radius:4px;}
  .color{background-color:#def}
  .content{width: 120px;float: left;}
</style>
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
</head>
<body>
<div style="width:900px;height:auto;margin-left:auto;margin-right:auto;">
	<div class="center">
		<form action="orders/addOrders.html" method="post" name="ordersForm" onsubmit="return check();">
			<div class="title">
				新订阅
			</div>
			<div id="billingDiv" class="info" style="display: none">
				<label class="left">计费频率</label>
				<div class="left" id="billings">
      		<c:forEach var="billing" varStatus="status" items="${billingList}">
      			<input type="radio" name="billingCycle" id="billingCycle"  value="${billing.code }" onchange="change('${billing.code }')" <c:if test="${status.first==true}">checked="checked"</c:if> />${billing.name }
          </c:forEach>
     		</div>
			</div>
			<div class="title" id="product">
				产品目录
			</div>
			<div class="info">
			  <ul id="tab-list" class="tab">
			  	<c:forEach items="${offerList}" var="offer" varStatus="status">
						<li id="tab_${offer.offerId}" <c:if test="${status.first==true}">class="color"</c:if> onclick="myclick('${offer.offerId}')" >${offer.offerName}<span style="display: none"></span></li>
					</c:forEach>
    		</ul>
			</div>
			<c:forEach items="${offerList}" var="offer" varStatus="status">
				<div  class="info color" style="height: 250px;overflow:auto;margin-left:20px;border:1px solid #def;<c:if test="${status.first==false}">display: none</c:if>" id="content_${offer.offerId}">
					<c:forEach items="${offer.subOffer}" var="offerChild">
						<div style="width: 650px;float: left;margin-left: 20px">
							<div style="width: 500px;float: left">
								<input name="checkbox" type="checkbox" onclick="myCheck(this,'${offer.offerId}',${offerChild.mininum})" value="${offerChild.offerId}" class="checkbox"
								<c:if test="${!empty ordersDetailMap&&ordersDetailMap.containsKey(offerChild.offerId)}">checked="checked"</c:if>/>${offerChild.offerName}
							</div>
							<div class="content" id="div_${offerChild.offerId}" <c:if test="${empty ordersDetailMap||!ordersDetailMap.containsKey(offerChild.offerId)}">style="display:none"</c:if>>
								<input name="quantity_${offerChild.offerId}" id="quantity_${offerChild.offerId}" type="text" style="width: 70px;" onblur="checkValue('${offerChild.offerId}')"
								 value="${ordersDetailMap==null?'':ordersDetailMap.get(offerChild.offerId)}" alt="${offer.offerId}"/>许可证
								<input name="maxinum_${offerChild.offerId}"	id="maxinum_${offerChild.offerId}" type="text"	style="display:none"	value="${offerChild.maxinum}" />
							</div>
						</div>
					</c:forEach>
				</div>
			</c:forEach>
			<div class="info" id="resellerDiv" style="margin-top: 5px;margin-bottom: 5px;display: none" >
				<label class="left"><font style="font-weight: bold;">间接经销商</font></label>
				<div class="left" id="mpnIdDiv">
      		<input type="text" name="mpnId" id="mpnId" onblur="valMpnId()"/>
      		<input type="hidden" id="reseller" name ="reseller"/>
     		</div>
			</div>
			<div class="info" style="margin-top: 5px;">
				<input type="submit" name="submitBtn" id="submitBtn" value="订阅" class="btn"/>
				<input type="button" name="backBtn" id="backBtn" value="返回" class="btn" onclick="forBack()"/>
			</div>
			<div style="display: none">
				<input type="text" name="customerId" id="customerId" value="${customer.id}"/>
				<input type="text" name="tenantId" id="tenantId"  value="${customer.tenantId}"/>
				<input type="text" name="id" id="id" value="${orders.id}"/>
			</div>
		</form>
	</div>
</div>
	<script type="text/javascript">
		$.ajaxSetup({ 
		    async : false 
		});
		
		$(document).ready(function(){

			var id = $("#billingCycle:checked").val();
			setDisabled(id);
			
			if("${roleId}"==2){
				$("#billingDiv").show();
				$("#resellerDiv").show();
			}
			setName();
		});
		
		//修改ul li的名称
		function setName(){
			var ulNode = $("ul.tab");
			var childrens = ulNode.children();
			var len = childrens.length;
			//循环遍历所有孩子结点，并根据子节点中checkbox选中的值修改名称
			for(var i=0;i<len;i++){
				var id = childrens[i].id.substring(4);//截取出id
				var count = $("#content_"+id+" input.checkbox:checked").length;
				if(count==0){
					$("#tab_"+id+" span").hide();
				}else{
					$("#tab_"+id+" span").show();
					$("#tab_"+id+" span").text("("+count+")");
				}
			}
		}
		
		function forBack(){
			//history.back(-1);
			var message = "未创建新订阅，是否确定返回？";
			var url = "<%=basePath%>customer/query.html";
			if("${flag}"=="edit"){
				message = "订阅信息未修改，是否确定返回？";
				url = "<%=basePath%>orders/getOrders.html?id=${orders.id}";
			}
			if(confirm(message)){
				window.location=url;
			}
		}
		
		var con;
		var numFlag = 1;//判断输入的订阅数量是否符合要求
		function check(){
			con=1;
			$("#submitBtn").attr("disabled",true);
			$("#backBtn").attr("disabled",true);
			valBilling();
			valOffer();
			valQuantity();
			valMpnId();
			if(con==1&&numFlag==1){
				return true;
			}else{
				$("#submitBtn").attr("disabled",false);
				$("#backBtn").attr("disabled",false);
				return false;
			}
		}
		
		function valBilling(){
			var billingCycle = $("#billings");
			billingCycle.siblings(".error").remove();
		  if( $("#billingCycle:checked").length==0){
			  billingCycle.after('<div class="error" style="margin-left: 20px">请选择计费频率！</div>');
		    con = 0;
		  }
		}

		function valOffer(){
			var count = $("div.color input.checkbox:checked").length;
			var offer = $("ul.tab li").last();
			offer.siblings(".error").remove();
		  if( count<=0){
			  offer.after('<div class="error">请选择产品！</div>');
		    con = 0;
		  }
		}
		
		function valMpnId(){
			var mpnId = $("#mpnId").val();
			$("#mpnIdDiv").siblings(".error").remove();
			$("#mpnIdDiv").siblings(".message").remove();
			if(mpnId!=""){
				  var url = "orders/checkMpnId.html";
					var postData = {"mpnId":mpnId};
					$.post(url,postData,function(data){
						var mpn = JSON.parse(data);
						if(mpn.responseCode==200){
							var result = JSON.parse(mpn.result.substr(mpn.result.indexOf("{")));
							$("#mpnIdDiv").after('<div class="message">'+result.partnerName+'</div>');
							$("#reseller").val(result.partnerName);
						}else{
							$("#mpnIdDiv").after('<div class="error">找不到记录！</div>');
							con = 0;
						}
					});
			}
		}
		
		function myclick(id){
			$("ul.tab li").removeClass("color");
			$("#tab_"+id).addClass("color");
			$("div.color").hide();
			$("#content_"+id).show();
		}
		
		function change(id){
			$("ul.tab span").hide();
			$("div.content").hide();	
			$("input.checkbox").attr("checked",false);
			$("input[name='quantity']").val();
			$(".error").remove();
			setDisabled(id);
		}

		//设置产品（checkBox）是否可选；当计费频率为试用时，只允许选择试用版本；其他计费频率不允许选择试用版本。
		function setDisabled(id){
			if(id=="none"){
				$("div.color input.checkbox").attr("disabled",true);
				$("#content_Trial input.checkbox").attr("disabled",false);
			}else{
				$("div.color input.checkbox").attr("disabled",false);
				$("#content_Trial input.checkbox").attr("disabled",true);
			}
		}
		function myCheck(obj,id,mininum){
			if(obj.checked){
				
				if(id=="Trial"){//如果是使用产品，则数量不允许修改
					$("#quantity_"+obj.value).attr("readonly","readonly");
				}
				$("#div_"+obj.value).show();
				$("#quantity_"+obj.value).val(mininum);
			}else{
				$("#div_"+obj.value).hide();
				$("#quantity_"+obj.value).val();
			}
			setName();
		}
		
		//判断输入的数量是否超过最大允许值
		function checkValue(obj,maxinum,id){
			numFlag = 1;
			$("#div_"+id).siblings(".error").remove();
			if(obj.value>maxinum){
				$("#div_"+id).after('<div class="error" style="float:right">最大'+maxinum+'许可证</div>');
				numFlag = 0;
			}
		}
		
		//判断输入的数量是否超过最大允许值
		function checkValue(id){
			numFlag = 1;
			verifyValue(id);
		}
		
		function verifyValue(id){

			var value = $("#quantity_"+id).val();
			var rootId = $("#quantity_"+id).attr("alt");
			var maxinum = $("#maxinum_"+id).val();
			
			$("#div_"+id).siblings(".error").remove();
	    if(isNaN(value)||value==""){
				$("#div_"+id).after('<div class="error" style="float:right">请输入数字！</div>');
				numFlag = 0;
	    }else if(value<=0){
				$("#div_"+id).after('<div class="error" style="float:right">订阅数量必须大于0！</div>');
				con = 0;
	    }else	if(parseInt(value)>maxinum){
				$("#div_"+id).after('<div class="error" style="float:right">最大'+maxinum+'许可证</div>');
				numFlag = 0;
			}else	if(rootId=="Business"&&$("#customerId").val()!=""){//商业版总代下允许订阅坐席总数不超过300

				var url = "orders/checkQuantity.html";
				var postData = {"customerId":$("#customerId").val(),"tenantId":$("#tenantId").val(),"offerId":id};
				$.post(url,postData,function(data){
					var data = JSON.parse(data);
					if(data!=""){
						var tmpCount = data.tmpCount;
						var quantity = data.quantity;
						if(parseInt(value)+tmpCount+quantity>maxinum){
							$("#div_"+id).after('<div class="error" style="float:right">最大'+maxinum+'许可证，已下订单'+(tmpCount+quantity)+'许可证，其中'+quantity+'许可证已生效</div>');
							numFlag = 0;
						}
					}
				})
			}
		}
			
		function valQuantity(){
			numFlag = 1;
			var rootId = "Enterprise";
			$.each($("div.color input.checkbox:checked"),function(){
				var id = $(this).val();
				//var rootId = $(this).parents(".color").attr("id").replace("content_","");
				verifyValue(id);
				if(numFlag==0){
					rootId = $("#quantity_"+id).attr("alt");
				}
			});
			//切换tab页
			if(numFlag==0){
				  myclick(rootId);
			}
		}
	</script>
</body>
</html>