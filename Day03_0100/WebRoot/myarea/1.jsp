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
    
      <%
       String name="张靓颖";
       pageContext.setAttribute("unamepageContext", name);
       request.setAttribute("unamerequest", name);
       session.setAttribute("unamesession", name);
       application.setAttribute("unameapplication", name);
     %>
     
     
     <%=pageContext.getAttribute("unamepageContext") %>
     
    <%
     request.getRequestDispatcher("/myarea/2.jsp").forward(request, response);
    %>
  </body>
</html>
