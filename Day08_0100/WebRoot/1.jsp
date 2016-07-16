<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP '1.jsp' starting page</title>
  </head>
  
  <body>
     <c:set var="uname" value="happy"></c:set>
     
     
     <!-- -取值 -->
     <c:out value="${uname }"></c:out>
      
      <c:remove var="uname"/>
     <hr/>
      
       <c:out value="${uname }"></c:out>
     <c:out escapeXml="Y" value="<a href='http://www.baidu.com'>百度</a>"></c:out>
  </body>
</html>
