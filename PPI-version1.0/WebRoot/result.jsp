<%@ page language="java" import="java.util.*,com.ppi.tools.OneSentence" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>获得结果</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <%
  		OneSentence os = new OneSentence();
  		ArrayList<OneSentence> array = (ArrayList<OneSentence>)request.getAttribute("array");
   %>
  
  <body>
  	<center>
  		
  		<br />
  		<table>
  			<tr>
  				<img align="left" src="images/result.png"/>
  			</tr>
  			<tr>
  			 		 <td width="140" height="30" bgcolor="#E8E8E8">PMID</td>
  					 <td width="140" bgcolor="#E8E8E8">ProteinA</td>
 					 <td width="140" bgcolor="#E8E8E8">ProteinB</td>
  					 <td width="140" bgcolor="#E8E8E8">Interaction</td>
  					 <td width="140" bgcolor="#E8E8E8">Sentence</td>
  			</tr>
  			<%
  			for(int i=0; i<array.size(); i++){
  				os = array.get(i);
  				%>
  				<tr height="40">
  				<td><%=os.getPMID()%></td>
  				<td><%=os.getPA() %></td>
  				<td><%=os.getPB() %></td>
  				<td><%=os.getITA() %></td>
  				<td><%=os.getSEN() %></td>
  				<%
  			}
  			%>
  		</table>
  	</center>
    	
  </body>
</html>
