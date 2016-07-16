<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>页面损坏</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 <style type="text/css">
        h1 {
       
        }
          a{
            font-size:18px;
            color: #00dddd;
        }
         #erroy{
           
             height:480px;
         }    
    </style>
  </head>
  
  <body>
  <div id="erroy">
   <h1>  <a href="<%=path %>/servlet/doIndexServlet">   返回首页   </a>       </h1>
    <img src="<%=path %>/images/505.jpg"width="1150" height="512"></img>
  </div>
  
  </body>
</html>
