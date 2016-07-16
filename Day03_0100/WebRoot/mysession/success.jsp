<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'success.jsp' starting page</title>
  

  </head>
  
  <body>
   <!-- 都是对session验证 -->
   <%@ include file="/tool/permission.jsp" %>

     欢迎您：<%=session.getAttribute("user") %> <a href="<%=path%>/mysession/loginout.jsp">注销</a>
  </body>
</html>
