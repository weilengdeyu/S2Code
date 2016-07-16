<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP '1.jsp' starting page</title>
    
	
  </head>
  
  <body>
      我是2<hr/>
      
       <%=pageContext.getAttribute("unamepageContext") %>
      
      <%=request.getAttribute("unamerequest") %>
  </body>
</html>
