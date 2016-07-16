<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>application</title>
   
  </head>
  
  <body>
   <%
     
      //1.从作用域中取值
     Integer count= (Integer)application.getAttribute("count");
	   if(count==null){
	  	 
	  	 //证明内存中没有count对应的application对象
	  	 count=1; 
	   }else{
		   count++;
		   //重新赋值给作用域的count
		   application.setAttribute("count",count);
	   }
   %>
  
    您是第<%=count%>位访问者
  </body>
</html>
