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
    
    <title>My JSP '2.jsp' starting page</title>
   
  </head>
  
  <body>
    <c:set var="userRole" value="employee"></c:set>
    
    <c:if test="${userRole eq 'admin'}">
   	   <c:out value="恭喜，您是admin"></c:out>
    </c:if>
    
     <c:if test="${userRole eq 'employee'}">
       <c:out value="不好意思，普通用户"></c:out>
    </c:if>
    
   
    
    
    
    
    
  </body>
</html>
