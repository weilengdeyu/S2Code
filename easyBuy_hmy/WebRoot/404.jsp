<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <title>找不到该页面</title>
	
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
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
       
      <img src="<%=path %>/images/404.png"width="1100" height="450"></img>
    </div>
  </body>
</html>
