<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>logout</title>
</head>
<body>
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			//如果父级有多个frame，则由父级跳转至退出界面，防止出现界面混乱情况
			if(parent.frames.length >0){
				parent.location =  "logout.html";
			}else{
				document.location = "login.html";
			}
		});
		
	</script>
</body>
</html>