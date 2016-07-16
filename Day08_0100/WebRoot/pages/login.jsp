<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'login.jsp' starting page</title>
    


  </head>
  
  <body>
   
   <form action="<%=path %>/doLogin" method="post">
   <input type="text" name="uname" value=""  />
   <input type="text"  name="upwd" value=""  />
    <input type="submit" value="Login"/>
   </form>
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
  </body>
</html>
