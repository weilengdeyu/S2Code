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
    
    <title>My JSP '3.jsp' starting page</title>
    


  </head>
  
  <body>
   
   <%
     request.setAttribute("permissionLevel","2");
   %>
   
   
  <c:choose>
     <c:when test="${ permissionLevel==1}">
         <c:out value="你是Super Administrator"></c:out>
     </c:when>
      <c:when test="${ permissionLevel==2}">
         <c:out value="你是Ordinary Administrator"></c:out>
     </c:when>
     <c:otherwise>
     
        <c:out value="你是Ordinary User"></c:out>
     </c:otherwise>
  
  
  
  
  </c:choose>
   
   
   
   
   
   
   
   
   
   
   
  </body>
</html>
