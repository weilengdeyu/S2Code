<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP '1.jsp' starting page</title>

  </head>
  
  <body>
     <%
       request.setAttribute("happy", "微冷的大大爷！");
     %>
     <!-- 将域中的内容显示到jsp页面上 -->
    <c:out value="${happy }"></c:out>
    <!-- set -->
    <c:set var="num" value="${happy }">
    </c:set>
     <c:out value="${num }"></c:out>
     
      <!-- remove-->
      <c:remove var="num"/>
     <br/>
     ***********************
      <c:out value="${num }"></c:out>
      ***********************
     
     <hr/>
       <c:out escapeXml="true" value="<a  href='http://www.baidu.com'>百度</a>"/>
     
     
     
     
     
     
     
     
     
     
     
     
     
     
  </body>
</html>
