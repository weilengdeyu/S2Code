<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>1</title>
  </head>
  
  <body>
    
    request传值:<%=request.getAttribute("uname") %><br/>
    session传值:<%=session.getAttribute("uname") %>
    application传值:<%=application.getAttribute("uname") %>
    
  </body>
</html>
