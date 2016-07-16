<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
  //1.清空session
  session.removeAttribute("user");

  //2. 跳转到登录
  response.sendRedirect(path+"/mysession/login.jsp");


%>