<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
  <c:choose>
     <c:when test="${empty user}">
      <%request.getRequestDispatcher("/pages/login.jsp").forward(request, response); %>
     </c:when>
     <c:otherwise>
       ${user }
     </c:otherwise>
  </c:choose>
  
 
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
  </body>
</html>
