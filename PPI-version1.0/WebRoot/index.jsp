<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>蛋白质相互作用关系挖掘</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<!-- javascript check函数：检查输入的账号和密码是否为空  BY LZJ -->
	<script type="text/javascript">
		function formreset(obj){     
			 obj.reset();
			 return;
		}
		function check(obj){
			if(document.getElementById("t").value == ""){
				window.alert("不能为空！");
				return false;
			}else{
				obj.submit();
			}
			
		}		
	</script>
	
  </head>
  
  <body>
  
  <center>
  <img align="top" src="images/head.png"/>
  <br/>
  <form id="ff" action="ReceiveText" method="post">
  	<textarea id="t" name="text" rows="20" cols="100"></textarea>
  	<br/>
  	<input type="image" src="images/submit.png" onclick="return check(ff)">
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  	<input type="image" src="images/reset.png" onclick="formreset(ff)">
  </form>
  <br />
  <br />
  <br />
  <img align="bottom" src="images/bottom.png"/>
  
  </center>
  </body>
</html>
