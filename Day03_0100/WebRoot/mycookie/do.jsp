<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'do.jsp' starting page</title>

  </head>
  
  <body>
    我是牛X的处理页面
    <%
    //获取用户名
    
     String uname=request.getParameter("txtName");
    //获取密码
    String upwd=request.getParameter("txtPwd");
     if(uname.equals("1")&&upwd.equals("1")){
    	//登录成功
    	//记录session
    	session.setAttribute("uname", uname);
    	//记录cookie
    	Cookie cookie=new Cookie("cookieName",uname);
    	
    	//设置cookie的过期
    	cookie.setMaxAge(60*5);
    	//将cookie响应给客户端
    	response.addCookie(cookie);
    	 
    	 //转发不用加项目名
    	 request.getRequestDispatcher("/mycookie/welcome.jsp").forward(request, response);
     }else{
    	 //用户名或者密码错误
    	 //重定向
    	 response.sendRedirect(path+"/mycookie/login.jsp");
     }
    %>
  </body>
</html>


















