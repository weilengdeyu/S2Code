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
    <form action="mysession/control.jsp" method="post">
	       用户名：<input type="text" name="txtName"/>
	      密码：<input type="password" name="txtPwd"/><br/>
       <input type="submit" value="登录"/>
    </form>
  </body>
</html>
