<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

 <%
    //1.从session中获取对应的用户名
    Object user=session.getAttribute("user");
    if(user==null){
    	//2.不存在，跳转到登录
    	response.sendRedirect("login.jsp");
    }
    
    
  %>