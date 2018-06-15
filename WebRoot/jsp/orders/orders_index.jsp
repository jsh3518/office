<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link type="text/css" rel="stylesheet" href="css/content.css"/>
<style type="text/css">
.info {
	float: left;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 14px;
	height: auto;
	line-height: 25px;
	color: #333333;
}
</style>
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
</head>
<body style="background-image:none;">
	<div style="width: 900px; height: auto; margin-left: auto; margin-right: auto;">
		<div class="center" style="background-image:none;">
			<form action="orders/saveOrders.html" method="post" name="ordersForm" onsubmit="return check();">
				<div class="info" style="width: 100%">
					<div id="title" class="title" style="width: 100%">新订阅</div>
					<div class="left" style="width: 100%">
						如果是已有客户，请点击 <input type="button" name="queryBtn" id="queryBtn"	value="搜索" onclick="queryCustomer()" /> 客户。
					</div>
					<div class="left" style="width: 100%">
						否则，请点击 <input type="button" name="addBtn" id="addBtn" value="新增" onclick="addCustomer()" /> 客户。
					</div>
					<div class="left" id="customerDiv"	style="width: 100%; height: auto; display: none">
						<iframe width="100%" height="100%" frameborder="0"	scrolling="auto" src="" name="customerFrame"	id="customerFrame"></iframe>
					</div>
					<div class="left" id="customerDiv2" style="width: 100%;height: auto;display: none">
						<div style="width: 50%;float: left;margin-top:10px;">
							<div class="info">
								<label class="label">公司名称<font color="red">*</font></label>
								<div class="right">
				      			<input name="companyName" id="companyName" type="text" value="${customer.companyName }" class="input"/>
				     		</div>
							</div>
							<div class="info">
								<label class="label">主域名<font color="red">*</font></label>
								<div class="right">
									<div id="domainDiv">
										<input type="text" name="domain" id="domain" class="input" value="${customer.domain }" onblur="valDomain()" style="width: 100px"/>.partner.onmschina.cn
									</div>
								</div>
							</div>
							<div class="info">
								<label class="label">邮政编码<font color="red">*</font></label>
								<div class="right">
									<input type="text" name="postalCode" id="postalCode" class="input" value="${customer.postalCode }"/>
								</div>
							</div>
							<div class="info">
								<label class="label">公司地址<font color="red">*</font></label>
								<div class="right">
									<select name="province" id="province" class="select" onChange="changeOrg('city',this.value)" style="width: 95px">
										<option value="">请选择</option>
										<c:forEach items="${provinList}" var="organ">
											<option value="${organ.orgId }">${organ.orgName }</option>
										</c:forEach>
									</select>
									<select name="city" id="city" class="select" onChange="changeOrg('region',this.value)" style="width: 95px">
										<option value="">请选择</option>
										<c:forEach items="${cityList}" var="organ">
											<option value="${organ.orgId }">${organ.orgName }</option>
										</c:forEach>
									</select>
									<select name="region" id="region" class="select"  style="width: 95px">
										<option value="">请选择</option>
										<c:forEach items="${regionList}" var="organ">
											<option value="${organ.orgId }">${organ.orgName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="info">
								<label class="label"><font color="red">*</font></label>
								<div class="right">
									<input type="text" name="address" id="address" class="input" value="${customer.address }"/>
								</div>
							</div>
						</div>
						<div style="width: 50%;float: left;margin-top:10px;">
							<div class="info">
								<label class="label">名字<font color="red">*</font></label>
								<div class="right">
									<input type="text" name="firstName" id="firstName" class="input" value="${customer.firstName }"/>
								</div>
							</div>
							<div class="info">
								<label class="label">姓氏<font color="red">*</font></label>
								<div class="right">
									<input type="text" name="lastName" id="lastName" class="input" value="${customer.lastName }"/>
								</div>
							</div>
							<div class="info">
								<label class="label">电话号码</label>
								<div class="right">
									<input type="text" name="phoneNumber" id="phoneNumber" class="input" value="${customer.phoneNumber }"/>
								</div>
							</div>
							<div class="info">
								<label class="label">电子邮箱地址<font color="red">*</font></label>
								<div class="right">
									<input type="text" name="email" id="email" class="input" value="${customer.email }"/>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div id="billingDiv" class="info" style="display: none">
					<div class="title" style="width: 100%">计费频率</div>
					<div class="left" id="billings">
						<c:forEach var="billing" varStatus="status" items="${billingList}">
							<input type="radio" name="billingCycle" id="billingCycle"	value="${billing.code }" onchange="change('${billing.code }')"
								<c:if test="${status.first==true}">checked="checked"</c:if> />${billing.name }
          	</c:forEach>
					</div>
				</div>
				<div id="product" class="info">
					<div class="title" id="product" style="width: 100%">产品目录</div>
					<div class="info"  style="height: 40px">
						<ul id="tab-list" class="tab">
							<c:forEach items="${offerList}" var="offer" varStatus="status">
								<li id="tab_${offer.offerId}"	<c:if test="${status.first==true}">class="color"</c:if>	onclick="myclick('${offer.offerId}')">${offer.offerName}<span	style="display: none"></span></li>
							</c:forEach>
						</ul>
					</div>
					<c:forEach items="${offerList}" var="offer" varStatus="status">
						<div class="info color"	style="height: 250px;overflow:auto;margin-left:20px;border:1px solid #def;<c:if test="${status.first==false}">display: none</c:if>"	id="content_${offer.offerId}">
							<c:forEach items="${offer.subOffer}" var="offerChild">
								<div style="width: 650px; float: left; margin-left: 20px">
									<div style="width: 500px; float: left">
										<input name="checkbox" type="checkbox"	onclick="myCheck(this,'${offer.offerId}',${offerChild.mininum})"	value="${offerChild.offerId}" class="checkbox"
											<c:if test="${!empty ordersDetailMap&&ordersDetailMap.containsKey(offerChild.offerId)}">checked="checked"</c:if> />${offerChild.offerName}
									</div>
									<div class="content" id="div_${offerChild.offerId}"
										<c:if test="${empty ordersDetailMap||!ordersDetailMap.containsKey(offerChild.offerId)}">style="display:none"</c:if>>
										<input name="quantity_${offerChild.offerId}"	id="quantity_${offerChild.offerId}" type="text"	style="width: 70px;"	onblur="checkValue('${offerChild.offerId}')"
											value="${ordersDetailMap==null?'':ordersDetailMap.get(offerChild.offerId)}" alt="${offer.offerId}"/>许可证
										<input name="maxinum_${offerChild.offerId}"	id="maxinum_${offerChild.offerId}" type="text"	style="display:none"	value="${offerChild.maxinum}" />
									</div>
								</div>
							</c:forEach>
						</div>
					</c:forEach>
				</div>
				<div class="info" id="resellerDiv"
					style="margin-top: 5px; margin-bottom: 5px; display: none">
					<label class="left"><font style="font-weight: bold;">间接经销商</font></label>
					<div class="left" id="mpnIdDiv">
						<input type="text" name="mpnId" id="mpnId" onblur="valMpnId()" />
						<input type="hidden" id="reseller" name="reseller" />
					</div>
				</div>
				<div class="info" style="width: 100%">
					<input type="submit" name="registBtn" id="registBtn" value="订阅"	class="btn" />
					<input type="button" name="backBtn" id="backBtn"	value="返回" class="btn" onclick="forBack()" />
				</div>
				<div style="display: none">
					<input type="text" name="customerId" id="customerId" value="${customerId}" />
					<input type="text" name="tenantId" id="tenantId"/>
					<input type="text" name="id" id="id" value="${orders.id}" />
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
			$("#registBtn").attr("disabled",true);
			$("#backBtn").attr("disabled",true);
			if($("#customerId").val()==""){
				checkCustomer();
			}
			valBilling();
			valOffer();
			valQuantity();
			valMpnId();
			if(con==1&&numFlag==1){
				return true;
			}else{
				$("#registBtn").attr("disabled",false);
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
		
		function checkValue(id){
			numFlag = 1;
			verifyValue(id);
		}
		
		//判断输入的数量是否超过最大允许值
		function verifyValue(id){
			var value = $("#quantity_"+id).val();
			var rootId = $("#quantity_"+id).attr("alt");
			var maxinum = $("#maxinum_"+id).val();
			var num = /^\d*$/; //全数字
			$("#div_"+id).siblings(".error").remove();
		  if(!num.test(value)||value==""){
				$("#div_"+id).after('<div class="error" style="float:right">请输入正确的订阅数量！</div>');
				numFlag = 0;
	    }else if(value<=0){
				$("#div_"+id).after('<div class="error" style="float:right">订阅数量必须大于0！</div>');
				con = 0;
	    }else if(parseInt(value)>maxinum){
				$("#div_"+id).after('<div class="error" style="float:right">最大'+maxinum+'许可证</div>');
				numFlag = 0;
			}else if(rootId=="Business"&&$("#customerId").val()!=""){//商业版总代下允许订阅坐席总数不超过300
				var url = "orders/checkQuantity.html";
				var postData = {"customerId":$("#customerId").val(),"tenantId":$("#tenantId").val(),"offerId":id};
				$.post(url,postData,function(data){
					var data = JSON.parse(data);
					if(data!=""){
						var tmpCount = data.tmpCount;
						var quantity = data.quantity;
						if(parseInt(value)+tmpCount+quantity>maxinum){
							$("#div_"+id).siblings(".error").remove();
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
		
		function queryCustomer(){
			clear();
			$("#customerDiv").show();
			$("#customerDiv2").hide();
			$("#customerFrame").attr("src","customer/query.html?flag=index");
		}
		
		function addCustomer(){
			clear();
			$("#customerDiv").hide();
			$("#customerDiv2").show();
			$("input.input").attr("disabled",false);
			$("select").attr("disabled",false);
			$("input.input").css("background-color","#FFFFFF");
			$("select").css("background-color","#FFFFFF");
			$("#city").html("");
			$("#region").html("");
			$("#city").append("<option value =''>请选择</option>");
			$("#region").append("<option value =''>请选择</option>");
		}
		
		//清除客户信息
		function clear(){
			$(".error").remove();
			$("input.input").val("");
			$("select").val("");
			$("#customerId").val("");
			$("#tenantId").val("");
		}
		
		function checkCustomer(){
			var title = $("#title");
			title.siblings(".error").remove();
			if($("#customerDiv2").is(":hidden")){
				title.after('<div class="error" style="margin-left: 20px">请选择客户！</div>');
			  con = 0;
			}
			valName();
			valDomain();
			valAddress();
			valPostalCode();
			valFirstName();
			valLastName();
			valEmail();
			valPhone();
		}
		
		function valName(){
			var companyName = $("#companyName");
			companyName.siblings(".error").remove();
		  if(companyName.val() ==""){
			  companyName.after('<div class="error">请输入公司名称</div>');
		    con = 0;
		  }
		}

		//判断域名
		function valDomain(){
			var domainDiv = $("#domainDiv");
			domainDiv.siblings(".error").remove();
			domainDiv.siblings(".message").remove();
			var reg =/^[0-9a-zA-Z]+$/;
		  if($("#domain").val() ==""){
			  domainDiv.after('<div class="error">请输入域名！</div>');
			  con = 0;
		  }else if(!reg.test($("#domain").val())){
			  domainDiv.after('<div class="error">域名仅可以包含字母和数字，请重新输入！</div>');
			  con = 0;
		  }else if($("#domain").val() !=""){
			  var url = "customer/domain.html";
				var postData = {"domain":$("#domain").val(),"customerId":"${customer.id }"};
				$.post(url,postData,function(data){
					if(data==200){
						domainDiv.after('<div class="error">域名已被占用！</div>');
						con = 0;
					}else	if(data==900){
						domainDiv.after('<div class="error">域名在本系统中被临时占用！</div>');
						con = 0;
					}else if(data==404){
						domainDiv.after('<div class="message" style="font-size:12px">'+$("#domain").val()+'.partner.onmschina.cn可用！</div>');
					}else{
						domainDiv.after('<div class="error">域名验证失败！</div>');
					}
				});
		  }
		}

		//判断地址
		function valAddress(){
			var province = $("#province");
			var city = $("#city");
			var address = $("#address");
			$("#address").siblings(".error").remove();
			if(province.val() ==""||city.val() ==""||address.val() ==""){
				$("#address").after('<div class="error">请选择公司地址！</div>');
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
			postalCode.siblings(".error").remove();
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
			firstName.siblings(".error").remove();
			if(firstName.val() ==""){
				firstName.after('<div class="error">请输入联系人名字！</div>');
				con = 0;
			}
		}
		
		//判断姓氏
		function valLastName(){
			var lastName = $("#lastName");
			lastName.siblings(".error").remove();
			if(lastName.val() ==""){
				lastName.after('<div class="error">请输入联系人姓氏！</div>');
				con = 0;
			}
		}
		
		//判断邮箱
		function valEmail(){
			var email = $("#email");
			email.siblings(".error").remove();
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
			phoneNumber.siblings(".error").remove();
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
		
		//选择客户
		function select(customerId){
			$("#customerDiv").hide();
			$("#customerDiv2").show();
			$(".error").remove();
			var url = "customer/getCustomer.html";
			var postData = {"customerId":customerId};
			$.post(url,postData,function(data){
				var data = JSON.parse(data);
				if(data != ""){
					$("input.input").attr("disabled",true);
					$("select").attr("disabled",true);
					$("input.input").css("background-color","#EAEDF2");
					$("select").css("background-color","#EAEDF2");
					if(data.customer!=""){
						$("#city").html("");
						$("#region").html("");
						$("#city").append("<option value ='"+data.customer.city+"'> "+ data.customer.cityName +"</option>");
						$("#region").append("<option value ='"+data.customer.region+"'> "+ data.customer.regionName +"</option>");
						$("#companyName").val(data.customer.companyName);
						$("#domain").val(data.customer.domain);
						$("#postalCode").val(data.customer.postalCode);
						$("#province").val(data.customer.province);
						$("#city").val(data.customer.city);
						$("#region").val(data.customer.region);
						$("#address").val(data.customer.address);
						$("#firstName").val(data.customer.firstName);
						$("#lastName").val(data.customer.lastName);
						$("#phoneNumber").val(data.customer.phoneNumber);
						$("#email").val(data.customer.email);
						$("#customerId").val(data.customer.id);
						$("#tenantId").val(data.customer.tenantId);
					}
				}
			});
		}
	</script>
</body>
</html>